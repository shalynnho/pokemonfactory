package factory;

import agent.Agent;
import factory.data.Kit;
import factory.interfaces.Conveyor;
import factory.interfaces.KitRobot;

public class KitRobotAgent extends Agent implements KitRobot {

	// References to other agents
	private PartsRobot partsrobot;
	private Conveyor conveyor;
	private Camera camera;

	/*
	 * Messages
	 */

	@Override
	public void msgHereIsKit(Kit k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgNeedKit(int standLocation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgMoveKitToInspectionArea(Kit k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgKitPassedInspection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPlaceKitOnConveyorDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPlaceKitInInspectionAreaDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPlaceKitOnStandDone() {
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
	 * Requests a kit from the conveyor.
	 */
	private void requestKit() {

	}

	/**
	 * Takes a kit from the conveyor and place it on the stand.
	 */
	private void placeMyKitOnStand() {

	}

	/**
	 * Places an assembled kit on the stand into the inspection area (also on
	 * the stand).
	 * @param k the kit being placed.
	 */
	private void placeKitInInspectionArea(Kit k) {

	}

	/**
	 * Places a completed kit on the conveyor for removal from the kitting cell.
	 * @param k the kit being shipped out of the kitting cell.
	 */
	private void shipKit(Kit k) {

	}

	/**
	 * GUI Hack to set the reference to the partsrobot.
	 * @param pr the partsrobot
	 */
	public void setPartsRobot(PartsRobot pr) {
		this.partsrobot = pr;
	}

	/**
	 * GUI Hack to set the reference to the conveyor.
	 * @param co the conveyor
	 */
	public void setConveyor(Conveyor co) {
		this.conveyor = co;
	}

	/**
	 * GUI Hack to set the reference to the partsrobot.
	 * @param ca the camera
	 */
	public void setCamera(Camera ca) {
		this.camera = ca;
	}

}
