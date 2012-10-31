package factory;

import agent.Agent;
import factory.data.Kit;
import factory.interfaces.KitRobot;
import factory.interfaces.Stand;

public class StandAgent extends Agent implements Stand {

	// References to other agents
	private KitRobot kitrobot;
	private PartsRobot partsrobot;
	private FCS fcs;

	/*
	 * Messages
	 */

	@Override
	public void msgShippedKit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void msgKitAssembled(Kit k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgMakeKits(int numKits) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgHereIsKit(Kit k) {
		// TODO Auto-generated method stub

	}

	/*
	 * Scheduler
	 * @see agent.Agent#pickAndExecuteAnAction()
	 */
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
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

	}

	/**
	 * Places a kit into the list of kits on the stand
	 * @param k the kit being placed
	 */
	private void placeKit(Kit k) {

	}

	/**
	 * Requests inspection of an assembled kit.
	 * @param k the kit to be inspected.
	 */
	private void requestInspection(Kit k) {

	}

	/**
	 * Updates the FCS when a kit has been completed and is on its way out of
	 * the kitting cell.
	 */
	private void finalizeOrder() {

	}

	/**
	 * GUI Hack to set the reference to the partsrobot.
	 * @param pr the partsrobot
	 */
	public void setPartsRobot(PartsRobot pr) {
		this.partsrobot = pr;
	}

	/**
	 * GUI Hack to set the reference to the kitrobot.
	 * @param kr the kitrobot
	 */
	public void setKitRobot(KitRobot kr) {
		this.kitrobot = kr;
	}

	/**
	 * GUI Hack to set the reference to the FCS.
	 * @param fcs the fcs
	 */
	public void setFCS(FCS fcs) {
		this.fcs = fcs;
	}
}
