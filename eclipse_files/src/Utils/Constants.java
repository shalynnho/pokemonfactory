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
	
	public static String BIN_TARGET = "Bin";
	public static String CAMERA_TARGET = "Camera";
	public static String CONVEYOR_TARGET = "Conveyor";
	public static String FEEDER_TARGET = "Feeder";
	public static String LANE_TARGET = "Lane";
	
	public static String KIT_ROBOT_TARGET = "KitRobot";
	public static String GANTRY_ROBOT_TARGET = "GantryRobot";
	public static String PARTS_ROBOT_TARGET = "PartsRobot";
	
	public static String PART_TARGET = "Part";
	public static String NEST_TARGET = "Nest";
	public static String KIT_TARGET = "Kit";
	
	public static String SERVER_TARGET = "Server";
	
	
	// COMMAND NAMES
	//==================================
	// Used so that we can create Request methods more easily.
	
	public static String IDENTIFY_COMMAND = "identify";
	
	
	// CLIENT NAMES
	//==================================
	// Used to identify clients.
	
	// V0 Config
	public static String KIT_ROBOT_CLIENT = "KitsRobot";
	public static String PARTS_ROBOT_CLIENT = "PartsRobot";
	public static String LANE_CLIENT = "Lane";
	
}
