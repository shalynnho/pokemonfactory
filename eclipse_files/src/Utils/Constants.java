package Utils;

/**
 * Contains all the constants that we need in the project.
 * 
 * @author Peter Zhang
 */
public abstract class Constants {
	
	// SERVER SETTINGS
	//==================================
	
	public static final int SERVER_PORT = 6889;
	
	
	// TARGET NAMES
	//==================================
	// Used so that we can create Request methods more easily.
	// When the target has a specific ID, concatenate to the device target
	// e.g. String target = Constants.LANE_TARGET+laneID;
	
	public static final String BIN_TARGET = "Bin";
	public static final String CAMERA_TARGET = "Camera";
	public static final String CONVEYOR_TARGET = "Conveyor";
	public static final String FEEDER_TARGET = "Feeder";
	public static final String LANE_TARGET = "Lane";
	
	public static final String KIT_ROBOT_TARGET = "KitRobot";
	public static final String GANTRY_ROBOT_TARGET = "GantryRobot";
	public static final String PARTS_ROBOT_TARGET = "PartsRobot";
	
	public static final String PART_TARGET = "Part";
	public static final String NEST_TARGET = "Nest";
	public static final String KIT_TARGET = "Kit";
	
	public static final String SERVER_TARGET = "Server";
	
	
	// COMMAND NAMES
	//==================================
	// Used so that we can create Request methods more easily.
	// Naming convention: DEVICENAME_ACTION_COMMAND
	
	public static final String IDENTIFY_COMMAND = "identify";					// for servers to identify managers
	
	public static final String FEEDER_INIT_GRAPHICS_COMMAND = "feederinitg"; 	// initialize the feeder graphics
	
	public static final String LANE_RECEIVE_PART = "lane receive part";			// when a part is given to the lane
	public static final String LANE_GIVE_PART_TO_NEST = "give part to nest";	// lane gives part to nest
	public static final String LANE_PURGE = "purge lane";						// purge lane
	public static final String LANE_SEND_ANIMATION = "lane animation";			// sends animation instructions to lane
	public static final String LANE_SET_AMPLITUDE = "lane set amp";				// sets lane amplitude
	public static final String LANE_TOGGLE = "lane toggle";						// turns lane on or off
	
	
	// CLIENT NAMES
	//==================================
	// Used to identify clients.
	
	// V0 Config - temporary names
	public static final String KIT_ROBOT_MNGR_CLIENT = "KitsRobotMngr";
	public static final String PARTS_ROBOT_MNGR_CLIENT = "PartsRobotMngr";
	public static final String LANE_MNGR_CLIENT = "LaneMngr";
	
}
