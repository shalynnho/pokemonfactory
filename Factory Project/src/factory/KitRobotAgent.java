package factory;

import agent.Agent;
import factory.data.Kit;
import factory.interfaces.Conveyor;
import factory.interfaces.KitRobot;

/**
 * Kit Robot brings moves kits to and from the conveyor and arranges kits on the
 * kitting stand. It is responsible for moving the assembled kits on the stand
 * into the inspection area for the Camera. Interacts with the Parts Robot,
 * Conveyor and Camera.
 * @author dpaje
 */
public class KitRobotAgent extends Agent implements KitRobot {

	// References to other agents
	private PartsRobot partsrobot;
	private Conveyor conveyor;
	private Camera camera;
	private GUIKitRobot guiKitRobot;

	private final String name;

	/**
	 * Constructor for KitRobotAgent class
	 * @param name name of the kitrobot
	 */
	public KitRobotAgent(String name) {
		super();

		this.name = name;
	}

	/*
	 * Messages
	 */

	@Override
	public void msgHereIsKit(Kit k) {
		// TODO Auto-generated method stub
		stateChanged();
	}

	@Override
	public void msgNeedKit(int standLocation) {
		// TODO Auto-generated method stub
		stateChanged();
	}

	@Override
	public void msgMoveKitToInspectionArea(Kit k) {
		// TODO Auto-generated method stub
		stateChanged();
	}

	@Override
	public void msgKitPassedInspection() {
		// TODO Auto-generated method stub
		stateChanged();
	}

	@Override
	public void msgPlaceKitOnConveyorDone() {
		// TODO Auto-generated method stub
		stateChanged();
	}

	@Override
	public void msgPlaceKitInInspectionAreaDone() {
		// TODO Auto-generated method stub
		stateChanged();
	}

	@Override
	public void msgPlaceKitOnStandDone() {
		// TODO Auto-generated method stub
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
	 * Requests a kit from the conveyor.
	 */
	private void requestKit() {
		stateChanged();
	}

	/**
	 * Takes a kit from the conveyor and place it on the stand.
	 */
	private void placeMyKitOnStand() {
		stateChanged();
	}

	/**
	 * Places an assembled kit on the stand into the inspection area (also on
	 * the stand).
	 * @param k the kit being placed.
	 */
	private void placeKitInInspectionArea(Kit k) {
		stateChanged();
	}

	/**
	 * Places a completed kit on the conveyor for removal from the kitting cell.
	 * @param k the kit being shipped out of the kitting cell.
	 */
	private void shipKit(Kit k) {
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
	 * GUI Hack to set the reference to the conveyor.
	 * @param co the conveyor
	 */
	public void setConveyor(Conveyor co) {
		this.conveyor = co;
		stateChanged();
	}

	/**
	 * GUI Hack to set the reference to the partsrobot.
	 * @param ca the camera
	 */
	public void setCamera(Camera ca) {
		this.camera = ca;
		stateChanged();
	}

	/**
	 * GUI Hack to set the reference to this class' gui component
	 * @param gc the gui representation of kit robot
	 */
	public void setGraphicalRepresentation(GUIKitRobot gkr) {
		this.guiKitRobot = gkr;
		stateChanged();
	}

}
