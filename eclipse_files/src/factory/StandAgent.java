package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.Agent;
import factory.data.Kit;
import factory.interfaces.KitRobot;
import factory.interfaces.PartsRobot;
import factory.interfaces.Stand;

/**
 * Stand manages the kits that are placed on the kitting stand. Interacts with
 * the Factory Control System (FCS), the Parts Robot and the Kit Robot.
 * @author dpaje
 */
public class StandAgent extends Agent implements Stand {

	// References to other agents
	private KitRobot kitrobot;
	private PartsRobot partsrobot;
	private FCS fcs;

	private final String name;

	// Kits received but not yet placed. Should only have <= 2 keys at any given
	// time.
	private final Map<MyKit, Integer> myKits = Collections
			.synchronizedMap(new HashMap<MyKit, Integer>());

	private int numKitsToMake;

	private final List<Kit> kitsOnStand = Collections
			.synchronizedList(new ArrayList<Kit>());

	/**
	 * Inner class encapsulates kit and adds states relevant to the stand
	 * @author dpaje
	 */
	private class MyKit {
		public Kit kit;
		public KitStatus KS;

		public MyKit(Kit k) {
			this.kit = k;
			this.KS = KitStatus.Received;
		}
	}

	public enum KitStatus {
		Received, PlacedOnStand, Assembled, MarkedForInspection, AwaitingInspection, Inspected, Shipped;
	};

	/**
	 * Constructor for StandAgent class
	 * @param name name of the stand
	 */
	public StandAgent(String name) {
		super();

		this.name = name;
		numKitsToMake = 0;
	}

	/*
	 * Messages
	 */

	@Override
	public void msgMakeKits(int numKits) {
		numKitsToMake = numKits;
		stateChanged();
	}

	@Override
	public void msgHereIsKit(Kit k, int destination) {
		myKits.put(new MyKit(k), destination);
		stateChanged();
	}

	@Override
	public void msgKitAssembled(Kit k) {
		for (MyKit mk : myKits.keySet()) {
			if (mk.kit == kitsOnStand.get(0)) {
				mk.KS = KitStatus.Assembled;
				numKitsToMake--;
				break;
			}
		}
		stateChanged();
	}

	@Override
	public void msgShippedKit() {
		for (MyKit mk : myKits.keySet()) {
			if (mk.kit == kitsOnStand.get(0)) {
				mk.KS = KitStatus.Shipped;
				break;
			}
		}
		stateChanged();
	}

	/*
	 * Scheduler
	 * @see agent.Agent#pickAndExecuteAnAction()
	 */
	@Override
	public boolean pickAndExecuteAnAction() {
		synchronized (myKits) {
			// Received a kit from kit robot
			for (MyKit mk : myKits.keySet()) {
				if (mk.KS == KitStatus.Received) {
					placeKit(mk.kit);
					return true;
				}
			}

			// Kit robot shipped a kit
			for (MyKit mk : myKits.keySet()) {
				if (mk.KS == KitStatus.Shipped) {
					kitsOnStand.remove(mk.kit);
					myKits.remove(mk);
					return true;
				}
			}
		}

		synchronized (kitsOnStand) {
			// Stand has an empty position (does not check the inspection area
			// of the stand)
			if (kitsOnStand.get(1) == null || kitsOnStand.get(2) == null) {
				requestKit(kitsOnStand.get(1) == null ? 1 : 2);
				return true;
			}
		}

		synchronized (myKits) {
			// Kit needs to be inspected
			for (MyKit mk : myKits.keySet()) {
				if (mk.KS == KitStatus.Assembled) {
					requestInspection(mk.kit);
					return true;
				}
			}
		}

		if (numKitsToMake < 1) {
			finalizeOrder();
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
	 * Requests a kit from kit robot at the specified location.
	 * @param index the empty location on the stand where the new kit will be
	 * placed
	 */
	private void requestKit(int index) {
		kitrobot.msgNeedKit(index);
		stateChanged();
	}

	/**
	 * Places a kit into the list of kits on the stand
	 * @param k the kit being placed
	 */
	private void placeKit(Kit k) {
		int spot = myKits.get(k);

		for (MyKit mk : myKits.keySet()) {
			if (mk.kit == k) {
				mk.KS = KitStatus.PlacedOnStand;
				break;
			}
		}

		kitsOnStand.set(spot, k);
		partsrobot.msgUseThisKit(k);
		stateChanged();
	}

	/**
	 * Requests inspection of an assembled kit.
	 * @param k the kit to be inspected.
	 */
	private void requestInspection(Kit k) {
		kitrobot.msgMoveKitToInspectionArea(k);
		stateChanged();
	}

	/**
	 * Updates the FCS when a batch of kits has been completed.
	 */
	private void finalizeOrder() {
		fcs.msgOrderFinished();

		// No need to call stateChanged() here as presumably the kitting cell is
		// idle (i.e., no queued orders)
	}

	/**
	 * GUI Hack to set the reference to the partsrobot.
	 * @param pr the partsrobot
	 */
	public void setPartsRobot(PartsRobot pr) {
		this.partsrobot = pr;
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
	 * @param fcs the fcs
	 */
	public void setFCS(FCS fcs) {
		this.fcs = fcs;
		stateChanged();
	}
}
