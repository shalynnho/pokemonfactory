package agent;

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
import agent.data.Kit;
import agent.interfaces.Camera;
import agent.interfaces.Conveyor;
import agent.interfaces.KitRobot;
import agent.interfaces.Stand;
import agent.test.mock.MockGraphics;

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

	private boolean kitWaitingOnConveyor;
	private int numKitsToMake;
	private int numKitsRequested;

	// Used to prevent animations from overlapping
	Semaphore animation = new Semaphore(0, true);

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
		kitWaitingOnConveyor = false;
		numKitsRequested = 0;
		numKitsToMake = 0;

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
	public void msgNeedThisManyKits(int total) {
		print("Received msgNeedThisManyKits");
		numKitsToMake = total;
		numKitsRequested = 0;
	}

	@Override
	public void msgHereIsKit(Kit k) {
		kitWaitingOnConveyor = true;
		print("Received msgHereIsKit");
		MyKit mk = new MyKit(k);
		myKits.add(mk);
		// print("Still need " + numKitsToRequest);
		stateChanged();
	}

	@Override
	public void msgNeedKit(int standLocation) {
		print("Received msgNeedKit");
		standPositions.put(standLocation, true);
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
		print("Received msgPlaceKitOnConveyorDone from graphics");
		animation.release();
		stateChanged();
	}

	@Override
	public void msgPlaceKitInInspectionAreaDone() {
		print("Received msgPlaceKitInInspetionAreaDone from graphics");
		animation.release();
		stateChanged();
	}

	@Override
	public void msgPlaceKitOnStandDone() {
		print("Received msgPlaceKitOnStandDone from graphics");
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
		// kit if necessary.
		if (!kitWaitingOnConveyor && numKitsRequested < numKitsToMake) {
			if (standPositions.get(1) || standPositions.get(2)) {
				numKitsRequested++;
				requestKit();
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
	 * Requests a kit from the conveyor.
	 */
	private void requestKit() {
		conveyor.msgNeedKit();
		print("So far I've requested: " + numKitsRequested + " out of "
				+ numKitsToMake + " needed.");
		stateChanged();
	}

	/**
	 * Takes a kit from the conveyor and place it on the stand.
	 */
	private void placeKitOnStand(MyKit mk) {
		for (int loc = 1; loc < 3; loc++) {
			if (standPositions.get(loc) == true) {
				kitWaitingOnConveyor = false;
				if (kitrobotGraphics != null) {
					kitrobotGraphics
							.msgPlaceKitOnStand(mk.kit.kitGraphics, loc);
				}
				if (mockgraphics != null) {
					mockgraphics.msgPlaceKitOnStand(mk.kit.kitGraphics, loc);
				}

				try {
					animation.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				standPositions.put(loc, false);
				mk.location = loc;
				stand.msgHereIsKit(mk.kit, loc);
				print("Placing kit on stand");
				// Only need to check 1 and 2
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
		mk.KS = KitStatus.AwaitingInspection;
		if (kitrobotGraphics != null) {
			if (mk.kit == null) {
				print("Inspection Kit is null");
			}
			if (mk.kit.kitGraphics == null) {
				print("Inspection KitGraphics null");
			}
			kitrobotGraphics.msgPlaceKitInInspectionArea(mk.kit.kitGraphics);
		}
		if (mockgraphics != null) {
			mockgraphics.msgPlaceKitInInspectionArea(mk.kit.kitGraphics);
		}

		// For testing, assume camera finishes after .1s
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				print("Faking camera finishing inspection");
				msgKitPassedInspection();
			}
		}, 100);

		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO This can't happen until the kit is placed
		// camera.msgInspectKit(mk.kit);

		stand.msgMovedToInspectionArea(mk.kit, mk.location);
		stateChanged();
	}

	/**
	 * Places a completed kit on the conveyor for removal from the kitting cell.
	 * @param k the kit being shipped out of the kitting cell.
	 */
	private void shipKit(MyKit mk) {
		if (kitrobotGraphics != null) {
			kitrobotGraphics.msgPlaceKitOnConveyor();
		}
		if (mockgraphics != null) {
			mockgraphics.msgPlaceKitOnConveyor();
		}
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	@Override
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

	public List<MyKit> getMyKits() {
		return myKits;
	}

	@Override
	public String getName() {
		return name;
	}

}
