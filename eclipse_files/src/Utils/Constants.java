package Utils;

/**
 * Contains all the constants that we need in the project.
 * 
 * @author Peter Zhang
 */
public abstract class Constants {
	
	// SERVER SETTINGS
	//==================================
	
	public static int SERVER_PORT = 6889;
	
	
	// TARGET NAMES
	//==================================
	// Used so that we can create Request methods more easily.
	
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
	// Naming convention: DEVICENAME_ACTION
	
	public static final String IDENTIFY_COMMAND = "identify";
	public static final String FEEDER_INIT_GRAPHICS_COMMAND = "feederinitg"; // initialize the feeder graphics
	
	
	// CLIENT NAMES
	//==================================
	// Used to identify clients.
	
	// V0 Config
	public static final String KIT_ROBOT_CLIENT = "KitsRobot";
	public static final String PARTS_ROBOT_CLIENT = "PartsRobot";
	public static final String LANE_CLIENT = "Lane";
	
}
