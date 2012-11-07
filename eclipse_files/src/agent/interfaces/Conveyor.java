package agent.interfaces;

import java.util.ArrayList;

import agent.data.Kit;
import agent.data.PartType;


public interface Conveyor {

	/**
	 * KitRobot sends this when it needs a kit.
	 */
	public abstract void msgNeedKit();

	/**
	 * KitRobot sends this when a kit is completed and needs to be taken from
	 * the kitting cell.
	 * @param k completed kit
	 */
	public abstract void msgTakeKitAway(Kit k);
	
	/**
	 * Conveyor is sent this by the FCS and it is used to set the configuration for new kits
	 * @param config the configuration for new kits
	 */
	public abstract void msgHereIsKitConfiguration(ArrayList<PartType> config);

	/**
	 * GUI Conveyor sends this when the Empty Kit to initial position animation
	 * has been completed.
	 */
	public abstract void msgBringEmptyKitDone();

	/**
	 * GUI Conveyor sends this when the animation for the KitRobot taking the
	 * kit off the conveyor has been completed.
	 */
	public abstract void msgGiveKitToKitRobotDone();

	/**
	 * GUI Conveyor sends this when the animation for the conveyor sending the
	 * package out of the cell has been completed.
	 */
	public abstract void msgReceiveKitDone();

	public abstract boolean pickAndExecuteAnAction();

}
