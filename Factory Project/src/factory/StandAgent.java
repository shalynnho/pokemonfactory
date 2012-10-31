package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.Agent;
import factory.data.Kit;
import factory.interfaces.KitRobot;
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
			this.KS = KitStatus.PlacedOnStand;
		}
	}

	public enum KitStatus {
		PlacedOnStand, Assembled, MarkedForInspection, AwaitingInspection, Inspected, Shipped;
	};

	/**
	 * Constructor for StandAgent class
	 * @param name name of the stand
	 */
	public StandAgent(String name) {
		super();

		this.name = name;
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
		// TODO Auto-generated method stub
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
		stateChanged();
	}

	/**
	 * Places a kit into the list of kits on the stand
	 * @param k the kit being placed
	 */
	private void placeKit(Kit k) {
		stateChanged();
	}

	/**
	 * Requests inspection of an assembled kit.
	 * @param k the kit to be inspected.
	 */
	private void requestInspection(Kit k) {
		stateChanged();
	}

	/**
	 * Updates the FCS when a kit has been completed and is on its way out of
	 * the kitting cell.
	 */
	private void finalizeOrder() {
		stateChanged();
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
