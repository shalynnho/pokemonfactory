package agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DeviceGraphics.DeviceGraphics;
import Utils.Constants;
import agent.data.Bin;
import agent.interfaces.Conveyor;
import agent.interfaces.FCS;
import agent.interfaces.Gantry;
import agent.interfaces.Nest;
import agent.interfaces.PartsRobot;
import agent.interfaces.Stand;
import factory.Order;
import factory.PartType;

/**
 * Unused in V0
 * @author Daniel Paje, Michael Gendotti
 */
public class FCSAgent extends Agent implements FCS {

	private Stand stand;
	private PartsRobot partsRobot;
	private Gantry gantry;
	private ArrayList<Nest> nests;
	private Conveyor conveyor;
	private myState state;
<<<<<<< HEAD
	private List<Order> orders = Collections
			.synchronizedList(new ArrayList<Order>());
=======
	private List<Order> orders;
>>>>>>> branch 'master' of https://github.com/usc-csci200-fall2012/team09.git
	private int numOrdersFinished = 0;

	private factory.FCS fcs;

	private final String name;

	private boolean binsSet;
	private final ArrayList<PartType> binsToAdd;

	public enum myState {
		PENDING, STARTED, LOADED
	};

	public FCSAgent(String name) {
		super();
		this.name = name;
		this.nests = new ArrayList<Nest>();
		this.orders = Collections.synchronizedList(new ArrayList<Order>());
		binsSet = false;
		binsToAdd = new ArrayList<PartType>();
		state = myState.STARTED;
	}

	public FCSAgent() {
		super();
		this.name = "FCS Agent";
		binsSet = false;
		binsToAdd = new ArrayList<PartType>();
		state = myState.STARTED;
	}

	@Override
	public void msgAddKitsToQueue(Order o) {
		print("Received new order");
		orders.add(o);
		if (fcs != null) {
			fcs.updateQueue();
		}
		stateChanged();
	}

	@Override
	public void msgStopMakingKit(Order o) {
		synchronized (orders) {
			for (Order order : orders) {
				if (order.equals(o)) {
					o.cancel = true;
					if (fcs != null) {
						fcs.updateQueue();
					}
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgStartProduction() {
		state = myState.STARTED;
		stateChanged();
	}

	@Override
	public void msgAddNewPartType(PartType part) {
		binsToAdd.add(part);
		stateChanged();
	}

	@Override
	public void msgOrderFinished() {
<<<<<<< HEAD
		numOrdersFinished++;
		System.out.print("Order " + numOrdersFinished + " Done!!!!");
		synchronized (orders) {
			for (Order o : orders) {
				if (o.state == Order.orderState.ORDERED) {
					orders.remove(o);
					if (fcs != null) {
						fcs.updateQueue();
					}
=======
		print("Recieved msgOrderFinished");
		synchronized(orders){
			for (Order o : orders) {
				if (o.state == Order.orderState.ORDERED) {
					o.state = Order.orderState.FINISHED;
>>>>>>> branch 'master' of https://github.com/usc-csci200-fall2012/team09.git
					break;
				}
			}
		}
		stateChanged();
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		//print("I'm scheduling stuff");
		if (!orders.isEmpty()) {
			synchronized(orders){
				for (Order o : orders) {
					if (o.cancel) {
						cancelOrder(o);
						return true;
					}
				}
			}
		}
		if( state == myState.LOADED) {
			if (!orders.isEmpty()) {
				synchronized(orders){
					for (Order o : orders) {
						if (o.state == Order.orderState.FINISHED) {
							finishOrder(o);
							return true;
						}
					}
				}
			}
		}
		if (state == myState.STARTED) {
			if (!binsSet && gantry != null) {
				initializeBins();
				return true;
			}
			if (binsToAdd.size() > 0 && gantry != null) {
				addBin();
				return true;
			}
			if (!orders.isEmpty()) {
<<<<<<< HEAD
				synchronized (orders) {
					for (Order o : orders) {
						if (o.cancel) {
							cancelOrder(o);
							return true;
						}
					}
=======
				synchronized(orders){
>>>>>>> branch 'master' of https://github.com/usc-csci200-fall2012/team09.git
					for (Order o : orders) {
						if (o.state == Order.orderState.PENDING) {
							placeOrder(o);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public void placeOrder(Order o) {
		print("Placing Order");
		o.state = Order.orderState.ORDERED;
		state = myState.LOADED;
		if (fcs != null) {
			fcs.updateQueue();
		}
		if (conveyor == null) {
			print("conveyor is null");
		}
		conveyor.msgHereIsKitConfiguration(o.kitConfig);
		stand.msgMakeKits(o.numKits);

		partsRobot.msgHereIsKitConfiguration(o.kitConfig);

		/*
		 * for(PartType type:o.kitConfig.getConfig().keySet()) {
		 * gantry.msgHereIsBinConfig(new Bin(o.parts.get(i),i+1)); }
		 */
		int k = 0;
		for (PartType type : o.kitConfig.getConfig().keySet()) {
			for (int i = 0; i < o.kitConfig.getConfig().get(type); i++) {
				nests.get(k).msgHereIsPartType(type);
				k++;
			}
		}
		stateChanged();
	}

	public void cancelOrder(Order o) {
		if (o.state == Order.orderState.ORDERED) {
			// stand.msgStopMakingTheseKits(o.parts);
			orders.remove(o);
		} else {
			orders.remove(o);
		}
		if (fcs != null) {
			fcs.updateQueue();
		}
		stateChanged();
	}
	
	public void finishOrder(Order o) {
		numOrdersFinished++;
		System.out.println("Order " + numOrdersFinished + " Done!!!!");
		orders.remove(o);
		if (fcs != null) {
			fcs.updateQueue();
		}
		state = myState.STARTED;
		stateChanged();
	}

	public void initializeBins() {
		print("Messaging gantry about default bins");
		for (int i = 0; i < Constants.DEFAULT_PARTTYPES.size(); i++) {
			gantry.msgHereIsBin(new Bin(Constants.DEFAULT_PARTTYPES.get(i), i));
		}
		binsSet = true;
		stateChanged();
	}

	public void addBin() {
		for (int i = binsToAdd.size() - 1; i >= 0; i--) {
			gantry.msgHereIsBin(new Bin(binsToAdd.get(i),
					Constants.DEFAULT_PARTTYPES.size() - i));
			binsToAdd.remove(i);
		}
		stateChanged();
	}

	public void setStand(Stand stand) {
		this.stand = stand;
	}

	public void setPartsRobot(PartsRobot partsRobot) {
		this.partsRobot = partsRobot;
	}

	public void setGantry(Gantry gantry) {
		this.gantry = gantry;
	}

	public void setConveyor(Conveyor conveyor) {
		this.conveyor = conveyor;
	}

	public void setNest(Nest nest) {
		this.nests.add(nest);
	}

	public void setNests(ArrayList<Nest> nests) {
		this.nests = nests;
	}

	public Stand getStand() {
		return stand;
	}

	public PartsRobot getPartsRobot() {
		return partsRobot;
	}

	public Gantry getGantry() {
		return gantry;
	}

	public Conveyor getConveyor() {
		return conveyor;
	}

	public ArrayList<Nest> getNests() {
		return nests;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setGraphicalRepresentation(DeviceGraphics dg) {
		// fcsGraphics=(fcsGraphics) dg;
	}

	public DeviceGraphics getGraphicalRepresentation() {
		// return fcsGraphics;
		return null;
	}

<<<<<<< HEAD
	public ArrayList<Order> getOrders() {
		return (ArrayList<Order>) orders;
=======
	public List<Order> getOrders() {
		return orders;
>>>>>>> branch 'master' of https://github.com/usc-csci200-fall2012/team09.git
	}

	public void setFCS(factory.FCS fcs) {
		this.fcs = fcs;
	}

}
