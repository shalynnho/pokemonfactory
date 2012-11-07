package agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import agent.data.Kit;
import agent.data.PartType;
import agent.interfaces.Conveyor;
import agent.interfaces.KitRobot;
import agent.test.mock.MockGraphics;

import DeviceGraphics.DeviceGraphics;
import GraphicsInterfaces.ConveyorGraphics;

/**
 * Conveyor brings empty kits into and takes completed (i.e. assembled and
 * inspected) kits out of the kitting cell. Interacts with the Factory Control
 * System (FCS) and the Kit Robot.
 * @author Daniel Paje
 */
public class ConveyorAgent extends Agent implements Conveyor {

	private final List<MyKit> kitsOnConveyor = Collections
			.synchronizedList(new ArrayList<MyKit>());

	private final ArrayList<PartType> kitConfig = new ArrayList<PartType>();

	private MyKit incomingKit;
	private MyKit outgoingKit;
	private int numKitsToDeliver;

	// Used to prevent animations from overlapping
	Semaphore animation = new Semaphore(1, true);

	// References to other agents
	private KitRobot kitrobot;
	private FCSAgent fcs;
	private ConveyorGraphics conveyorGraphics;
	private MockGraphics mockgraphics;

	// Name of the conveyor
	private final String name;

	/**
	 * Inner class encapsulates kit and adds states relevant to the conveyor
	 * @author dpaje
	 */
	private class MyKit {
		public Kit kit;
		public KitStatus KS;

		public MyKit(Kit k) {
			this.kit = k;
			this.KS = KitStatus.MovingIn;
		}
	}

	public enum KitStatus {
		MovingIn, AwaitingPickup, PickedUp, AwaitingDelivery, MovingOut, Delivered
	};

	/**
	 * Constructor for ConveyorAgent class
	 * @param name name of the conveyor
	 */
	public ConveyorAgent(String name) {
		super();

		this.name = name;
		this.numKitsToDeliver = 0;
	}

	/*
	 * Messages.
	 */

	@Override
	public void msgNeedKit() {
		print("Received msgNeedKit");
		numKitsToDeliver++;
		stateChanged();
	}

	@Override
	public void msgTakeKitAway(Kit k) {
		print("Received msgTakeKitAway");
		MyKit mk = new MyKit(k);
		outgoingKit = mk;
		mk.KS = KitStatus.AwaitingDelivery;
		kitsOnConveyor.add(mk);
		stateChanged();
	}

	@Override
	public void msgBringEmptyKitDone() {
		print("Received msgBringEmptyKitDone");
		animation.release();
		incomingKit.KS = KitStatus.AwaitingPickup;
		stateChanged();
	}

	@Override
	public void msgGiveKitToKitRobotDone() {
		print("Received msgGiveKitToKitRobotDone");
		animation.release();
		stateChanged();
	}

	@Override
	public void msgReceiveKitDone() {
		print("Received msgReceiveKitDone");
		kitsOnConveyor.remove(outgoingKit);
		animation.release();
		stateChanged();
	}

	/*
	 * Scheduler
	 * @see agent.Agent#pickAndExecuteAnAction()
	 */

	@Override
	public boolean pickAndExecuteAnAction() {

		synchronized (kitsOnConveyor) {
			// Send the kit if it has reached the "stop" position on the
			// conveyor where the kit robot can pick it up.
			for (MyKit mk : kitsOnConveyor) {
				if (mk.KS == KitStatus.AwaitingPickup) {
					sendKit(mk);
					return true;
				}
			}

			// Place kit onto conveyor and start moving it out of the cell if
			// the kit robot has dropped off a completed kit
			for (MyKit mk : kitsOnConveyor) {
				if (mk.KS == KitStatus.AwaitingDelivery) {
					deliverKit(mk);
					return true;
				}
			}

			// Place kit onto conveyor and start moving it into the cell if a
			// new kit was requested by the kit robot
			if (numKitsToDeliver > 0) {
				prepareKit();
				return true;
			}

		}

		/*
		 * Tried all rules and found no actions to fire. Return false to the
		 * main loop of abstract base class Agent and wait.
		 */
		return false;
	}

	/*
	 * Actions
	 */

	/**
	 * Generate a new kit to move into the kitting cell.
	 */
	private void prepareKit() {
		Kit k = new Kit(kitConfig);
		incomingKit = new MyKit(k);
		kitsOnConveyor.add(incomingKit);
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mockgraphics != null) {
			mockgraphics.msgBringEmptyKit(k.kit);
		}
		if (conveyorGraphics != null) {
			conveyorGraphics.msgBringEmptyKit(k.kit);
		}
		stateChanged();
	}

	/**
	 * Send an empty kit to the kitrobot
	 * @param k the kit being sent.
	 */
	private void sendKit(MyKit mk) {
		numKitsToDeliver--;
		kitrobot.msgHereIsKit(mk.kit);
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mk.KS = KitStatus.PickedUp;
		if (mockgraphics != null) {
			mockgraphics.msgGiveKitToKitRobot(mk.kit.kit);
		}
		if (conveyorGraphics != null) {
			conveyorGraphics.msgGiveKitToKitRobot(mk.kit.kit);
		}

		kitsOnConveyor.remove(mk);

		stateChanged();
	}

	/**
	 * Send a finished kit out of the cell.
	 * @param k the kit being delivered.
	 */
	private void deliverKit(MyKit mk) {
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mk.KS = KitStatus.MovingOut;
		if (mockgraphics != null) {
			mockgraphics.msgReceiveKit(mk.kit.kit);
		}
		if (conveyorGraphics != null) {
			conveyorGraphics.msgReceiveKit(mk.kit.kit);
		}
		stateChanged();
	}

	/**
	 * GUI Hack to set the reference to the kitrobot.
	 * @param kr the kitrobot
	 */
	public void setKitRobot(KitRobot kr) {
		this.kitrobot = kr;
		stateChanged();
	}

	/**
	 * GUI Hack to set the reference to the FCS.
	 * @param fcs the FCS
	 */
	public void setFCS(FCSAgent fcs) {
		this.fcs = fcs;
		stateChanged();
	}

	/**
	 * GUI Hack to set the reference to this class' gui component
	 * @param gc the gui representation of conveyor
	 */
	public void setGraphicalRepresentation(ConveyorGraphics gc) {
		this.conveyorGraphics = gc;
		stateChanged();
	}

	public MockGraphics getMockgraphics() {
		return mockgraphics;
	}

	public void setMockgraphics(MockGraphics mockgraphics) {
		this.mockgraphics = mockgraphics;
	}

	@Override
	public String getName() {
		return name;
	}

	public MyKit getIncomingKit() {
		return incomingKit;
	}

	public void setIncomingKit(MyKit incomingKit) {
		this.incomingKit = incomingKit;
	}

	public int getNumKitsToDeliver() {
		return numKitsToDeliver;
	}

	public void setNumKitsToDeliver(int numKitsToDeliver) {
		this.numKitsToDeliver = numKitsToDeliver;
	}

	public Semaphore getAnimation() {
		return animation;
	}

	public void setAnimation(Semaphore animation) {
		this.animation = animation;
	}

	public KitRobot getKitrobot() {
		return kitrobot;
	}

	public void setKitrobot(KitRobot kitrobot) {
		this.kitrobot = kitrobot;
	}

	public FCSAgent getFcs() {
		return fcs;
	}

	public void setFcs(FCSAgent fcs) {
		this.fcs = fcs;
	}

	public ConveyorGraphics getConveyorGraphics() {
		return conveyorGraphics;
	}

	public void setGraphicalRepresentation(DeviceGraphics conveyorGraphics) {
		this.conveyorGraphics = (ConveyorGraphics) conveyorGraphics;
	}

	public List<MyKit> getKitsOnConveyor() {
		return kitsOnConveyor;
	}

	@Override
	public void msgHereIsKitConfiguration(ArrayList<PartType> config) {

	}

}
