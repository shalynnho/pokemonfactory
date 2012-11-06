package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

import DeviceGraphics.DeviceGraphics;
import GraphicsInterfaces.KitRobotGraphics;
import agent.Agent;
import factory.data.Kit;
import factory.interfaces.Camera;
import factory.interfaces.Conveyor;
import factory.interfaces.KitRobot;
import factory.interfaces.Stand;
import factory.test.mock.MockGraphics;

/**
 * Kit Robot brings moves kits to and from the conveyor and arranges kits on the
 * kitting stand. It is responsible for moving the assembled kits on the stand
 * into the inspection area for the Camera. Interacts with the stand, Conveyor
 * and Camera.
 * @author Daniel Paje
 */
public class KitRobotAgent extends Agent implements KitRobot {

	private final List<MyKit> myKits = Collections
			.synchronizedList(new ArrayList<MyKit>());

	// Tracks stand positions and whether or not they are open
	Map<Integer, Boolean> standPositions = Collections
			.synchronizedMap(new TreeMap<Integer, Boolean>());

	int numKitsToRequest;
	private boolean kitRequested;

	// Used to prevent animations from overlapping
	Semaphore animation = new Semaphore(1, true);

	// References to other agents
	private Stand stand;
	private Conveyor conveyor;
	private Camera camera;
	private KitRobotGraphics kitrobotGraphics;
	private MockGraphics mockgraphics;

	private final String name;

	private final Timer timer;

	/**
	 * Inner class encapsulates kit and adds states relevant to the stand
	 * @author dpaje
	 */
	public class MyKit {
		public Kit kit;
		public KitStatus KS;
		public int location;

		public MyKit(Kit k) {
			this.kit = k;
			this.KS = KitStatus.PickedUp;
		}
	}

	public enum KitStatus {
		PickedUp, OnStand, MarkedForInspection, AwaitingInspection, Inspected;
	};

	/**
	 * Constructor for KitRobotAgent class
	 * @param name name of the kitrobot
	 */
	public KitRobotAgent(String name) {
		super();

		this.name = name;
		numKitsToRequest = 0;
		kitRequested = false;

		// Don't assume stand is empty
		standPositions.put(0, false);
		standPositions.put(1, false);
		standPositions.put(2, false);

		timer = new Timer();
	}

	/*
	 * Messages
	 */

	@Override
	public void msgHereIsKit(Kit k) {
		print("Received msgHereIsKit");
		MyKit mk = new MyKit(k);
		myKits.add(mk);
		numKitsToRequest = 0;
		// print("Still need " + numKitsToRequest);
		kitRequested = false;
		stateChanged();
	}

	@Override
	public void msgNeedKit(int standLocation) {
		print("Received msgNeedKit");
		standPositions.put(standLocation, true);
		numKitsToRequest = 1;
		// print("Still need " + numKitsToRequest);
		stateChanged();
	}

	@Override
	public void msgMoveKitToInspectionArea(Kit k) {
		print("Received msgMoveKitToInspectionArea");
		for (MyKit mk : myKits) {
			if (mk.kit == k) {
				mk.KS = KitStatus.MarkedForInspection;
				break;
			}
		}
		stateChanged();
	}

	@Override
	public void msgKitPassedInspection() {
		print("Received msgKitPassedInspection");
		for (MyKit mk : myKits) {
			if (mk.KS == KitStatus.AwaitingInspection) {
				mk.KS = KitStatus.Inspected;
				break;
			}
		}
		stateChanged();
	}

	@Override
	public void msgPlaceKitOnConveyorDone() {
		print("Received msgPlaceKitOnConveyorDone");
		animation.release();
		stateChanged();
	}

	@Override
	public void msgPlaceKitInInspectionAreaDone() {
		print("Received msgPlaceKitInInspetionAreaDone");
		animation.release();
		stateChanged();
	}

	@Override
	public void msgPlaceKitOnStandDone() {
		print("Received msgPlaceKitOnStandDone");
		animation.release();
		stateChanged();
	}

	/*
	 * Scheduler
	 * @see agent.Agent#pickAndExecuteAnAction()
	 */
	@Override
	public boolean pickAndExecuteAnAction() {

		synchronized (myKits) {
			// Kit needs to be shipped out of the kitting cell
			for (MyKit mk : myKits) {
				if (mk.KS == KitStatus.Inspected) {
					shipKit(mk);
					return true;
				}
			}

			// Kit needs to be inspected
			for (MyKit mk : myKits) {
				if (mk.KS == KitStatus.MarkedForInspection) {
					placeKitInInspectionArea(mk);
					return true;
				}
			}

			// Picked up a kit from conveyor
			for (MyKit mk : myKits) {
				if (mk.KS == KitStatus.PickedUp) {
					mk.KS = KitStatus.OnStand;
					placeKitOnStand(mk);
					return true;
				}
			}

		}

		// If other rules fail and there's a spot on the stand, request a new
		// kit
		for (int loc = 1; loc < 3; loc++) {
			if (standPositions.get(loc) == true) {
				if (kitRequested == false && numKitsToRequest > 0) {
					requestKit();
					return true;
				}
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
	 * Requests a kit from the conveyor.
	 */
	private void requestKit() {
		conveyor.msgNeedKit();
		kitRequested = true;
		stateChanged();
	}

	/**
	 * Takes a kit from the conveyor and place it on the stand.
	 */
	private void placeKitOnStand(MyKit mk) {
		print("Placing kit on stand");
		// Only need to check 1 and 2
		for (int loc = 1; loc < 3; loc++) {
			if (standPositions.get(loc) == true) {
				try {
					animation.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (kitrobotGraphics != null) {
					kitrobotGraphics.msgPlaceKitOnStand(mk.kit.kit, loc);
				}
				if (mockgraphics != null) {
					mockgraphics.msgPlaceKitOnStand(mk.kit.kit, loc);
				}
				standPositions.put(loc, false);
				mk.location = loc;
				stand.msgHereIsKit(mk.kit, loc);
				break;
			}
		}
		stateChanged();
	}

	/**
	 * Places an assembled kit on the stand into the inspection area (also on
	 * the stand).
	 * @param k the kit being placed.
	 */
	private void placeKitInInspectionArea(MyKit mk) {
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mk.KS = KitStatus.AwaitingInspection;
		if (kitrobotGraphics != null) {
			kitrobotGraphics.msgPlaceKitInInspectionArea(mk.kit.kit);
		}
		if (mockgraphics != null) {
			mockgraphics.msgPlaceKitInInspectionArea(mk.kit.kit);
		}

		// TODO This can't happen until the kit is placed
		camera.msgInspectKit(mk.kit);

		// For testing, assume camera finishes after 1s
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				print("Faking camera finishing inspection");
				msgKitPassedInspection();
			}
		}, 1000);

		stand.msgMovedToInspectionArea(mk.kit, mk.location);
		stateChanged();
	}

	/**
	 * Places a completed kit on the conveyor for removal from the kitting cell.
	 * @param k the kit being shipped out of the kitting cell.
	 */
	private void shipKit(MyKit mk) {
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (kitrobotGraphics != null) {
			kitrobotGraphics.msgPlaceKitOnConveyor();
		}
		if (mockgraphics != null) {
			mockgraphics.msgPlaceKitOnConveyor();
		}
		conveyor.msgTakeKitAway(mk.kit);
		stand.msgShippedKit();
		myKits.remove(mk);
		stateChanged();
	}

	/**
	 * GUI Hack to set the reference to the conveyor.
	 * @param co the conveyor
	 */
	public void setConveyor(Conveyor co) {
		this.conveyor = co;
		stateChanged();
	}

	/**
	 * GUI Hack to set the reference to the camera.
	 * @param ca the camera
	 */
	public void setCamera(Camera ca) {
		this.camera = ca;
		stateChanged();
	}

	/**
	 * GUI Hack to set the reference to the stand.
	 * @param st the stand
	 */
	public void setStand(Stand st) {
		this.stand = st;
		stateChanged();
	}

	/**
	 * GUI Hack to set the reference to this class' gui component
	 * @param gc the gui representation of kit robot
	 */
	public void setGraphicalRepresentation(DeviceGraphics gkr) {
		this.kitrobotGraphics = (KitRobotGraphics) gkr;
		stateChanged();
	}

	public MockGraphics getMockGraphics() {
		return mockgraphics;
	}

	public void setMockGraphics(MockGraphics mockgraphics) {
		this.mockgraphics = mockgraphics;
	}

	public Map<Integer, Boolean> getStandPositions() {
		return standPositions;
	}

	public void setStandPositions(Map<Integer, Boolean> standPositions) {
		this.standPositions = standPositions;
	}

	public int getNumKitsToRequest() {
		return numKitsToRequest;
	}

	public void setNumKitsToRequest(int numKitsToRequest) {
		this.numKitsToRequest = numKitsToRequest;
	}

	public List<MyKit> getMyKits() {
		return myKits;
	}

	@Override
	public String getName() {
		return name;
	}

}
