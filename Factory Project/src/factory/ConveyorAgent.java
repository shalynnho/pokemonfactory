package factory;

import agent.Agent;
import factory.data.Kit;
import factory.interfaces.Conveyor;
import factory.interfaces.KitRobot;

public class ConveyorAgent extends Agent implements Conveyor {

	// References to other agents
	private KitRobot kitrobot;
	private FCS fcs;

	// Name of the conveyor
	private final String name;

	/**
	 * Constructor for ConveyorAgent class
	 * @param name name of the conveyor
	 */
	public ConveyorAgent(String name) {
		super();

		this.name = name;
	}

	/*
	 * Messages.
	 */

	@Override
	public void msgNeedKit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgTakeKitAway(Kit k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgBringEmptyKitDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgGiveKitToKitRobotDone() {
		// TODO Auto-generated method stub
	}

	@Override
	public void msgReceiveKitDone() {
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
	 * Generate a new kit to move into the kitting cell.
	 */
	private void prepareKit() {

	}

	/**
	 * Send an empty kit to the kitrobot
	 * @param k the kit being sent.
	 */
	private void sendKit(Kit k) {

	}

	/**
	 * Send a finished kit out of the cell.
	 * @param k the kit being delivered.
	 */
	private void deliverKit(Kit k) {

	}

	/**
	 * GUI Hack to set the reference to the kitrobot.
	 * @param kr the kitrobot
	 */
	public void setPartsRobot(KitRobot kr) {
		this.kitrobot = kr;
	}

	/**
	 * GUI Hack to set the reference to the FCS.
	 * @param fcs the FCS
	 */
	public void setFCS(FCS fcs) {
		this.FCS = fcs;
	}

}
