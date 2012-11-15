package agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import factory.PartType;

import DeviceGraphics.DeviceGraphics;
import GraphicsInterfaces.GantryGraphics;
import agent.data.Bin;
import agent.data.Bin.BinStatus;
import agent.interfaces.Gantry;

/**
 * Gantry delivers parts to the feeder
 * @author Arjun Bhargava
 */
public class GantryAgent extends Agent implements Gantry {

	public List<Bin> binList = Collections.synchronizedList( new ArrayList<Bin>());
	public List<MyFeeder> feeders = Collections.synchronizedList(new ArrayList<MyFeeder>());

	// WAITING FOR GANTRYGRAPHICS
	private GantryGraphics GUIGantry;

	private final String name;
	
	private boolean waitForDrop = false;
	
	public class MyFeeder {
		public FeederAgent feeder;
		public PartType requestedType;
		public boolean request = false;
		
		public MyFeeder(FeederAgent feeder) {
			this.feeder = feeder;
		}
		public MyFeeder(FeederAgent feeder, PartType type) {
			this.feeder = feeder;
			this.requestedType = type;
		}
		public FeederAgent getFeeder() {
			return feeder;
		}
		public PartType getRequestedType() {
			return requestedType;
		}
		
	}

	public Semaphore animation = new Semaphore(0, true);

	public GantryAgent(String name) {
		super();
		this.name = name;
		print("I'm working");
	}

	@Override
	public void msgHereIsBin(Bin bin) {
		print("Received msgHereIsBinConfig");
		binList.add(bin);
		stateChanged();
	}

	@Override
	public void msgINeedParts(PartType type, FeederAgent feeder) {
		print("Received msgINeedParts");
		boolean temp = true;
		for(MyFeeder currentFeeder : feeders) {
			if(currentFeeder.getFeeder() == feeder) {
				currentFeeder.requestedType = type;
				currentFeeder.request = true;
				temp = false;
				break;
			}
		}
		if(temp == true) {
			MyFeeder currentFeeder = new MyFeeder(feeder, type);
			currentFeeder.request = true;
			feeders.add(currentFeeder);
		}
		stateChanged();
	}

	@Override
	public void msgReceiveBinDone(Bin bin) {
		print("Received msgReceiveBinDone from graphics");
		bin.binState = BinStatus.OVER_FEEDER;
		animation.release();
		stateChanged();
	}

	@Override
	public void msgDropBinDone(Bin bin) {
		print("Received msgdropBingDone from graphics");
		bin.binState = BinStatus.EMPTY;
		animation.release();
		waitForDrop = false;
		stateChanged();
	}

	@Override
	public void msgRemoveBinDone(Bin bin) {
		print("Received msgremoveBinDone from graphics");
		binList.remove(bin);
		animation.release();
		stateChanged();
	}

	
	//SCHEDULER
	@Override
	public boolean pickAndExecuteAnAction() {
		synchronized(binList) {
		for(Bin bin:binList) {
			if(bin.binState==BinStatus.PENDING){
				addBinToGraphics(bin);
				return true;
			}
		}
		}
		synchronized(feeders) {
		// TODO Auto-generated method stub
		if(waitForDrop == false) {
			for (MyFeeder currentFeeder : feeders) {
				for (Bin bin : binList) {
					if (bin.part.type.equals(currentFeeder.getRequestedType()) && bin.binState == BinStatus.FULL && currentFeeder.request == true) {
						print("Moving to feeder");
						currentFeeder.request = false;
						moveToFeeder(bin, currentFeeder.getFeeder());
						return true;
					}
				}
			}
		}	
		if(waitForDrop == true) {
			for (MyFeeder currentFeeder : feeders) {
				for (Bin bin : binList) {
					if (bin.part.type.equals(currentFeeder.getRequestedType())
							&& bin.binState == BinStatus.OVER_FEEDER) {
						fillFeeder(bin, currentFeeder.getFeeder());
						return true;
					}
				}
			}
		}
		if(waitForDrop == false) {
			for (MyFeeder currentFeeder : feeders) {
				for (Bin bin : binList) {
					if (bin.part.type.equals(currentFeeder.getRequestedType())
							&& bin.binState == BinStatus.EMPTY) {
						discardBin(bin);
						return true;
					}
				}
			}
		}
		}
		print("I'm returning false");
		return false;
	}

	
	//ACTIONS 
	@Override
	public void moveToFeeder(Bin bin, FeederAgent feeder) {
		print("Moving bin to over feeder");
		bin.binState = BinStatus.MOVING;

		GUIGantry.receiveBin(bin, feeder);
		waitForDrop = true;
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		stateChanged();
	}

	@Override
	public void fillFeeder(Bin bin, FeederAgent feeder) {
		print("Placing bin in feeder and filling feeder");
		bin.binState = BinStatus.FILLING_FEEDER;
		waitForDrop = false;
		
		GUIGantry.dropBin(bin, feeder);
		
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		feeder.msgHereAreParts(bin.part.type, bin);

		stateChanged();
	}

	@Override
	public void discardBin(Bin bin) {
		print("Discarding bin");
		bin.binState = BinStatus.DISCARDING;
		
		GUIGantry.removeBin(bin);
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		stateChanged();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setGraphicalRepresentation(DeviceGraphics dg) {
		this.GUIGantry = (GantryGraphics) dg;
	}
	
	public void addBinToGraphics(Bin bin){
		if(GUIGantry!=null){
			GUIGantry.hereIsNewBin(bin);
		}
		bin.binState=BinStatus.FULL;
		stateChanged();
	}

}
