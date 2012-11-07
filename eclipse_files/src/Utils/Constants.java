package Utils;

import java.awt.Image;
import java.awt.Toolkit;

/**
 * Contains all the constants that we need in the project.
 * @author Peter Zhang
 */
public abstract class Constants {

	// SERVER SETTINGS
	// ==================================

	public static final int SERVER_PORT = 6889;

	// CLIENT SETTINGS
	// ==================================

	public static final int TIMER_DELAY = 50;

	public static final Image CLIENT_BG_IMAGE = Toolkit.getDefaultToolkit()
			.getImage("src/images/bg.jpg");

	// DEVICE SETTINGS
	// ==================================

	// Feeder Images
	public static final Image FEEDER_IMAGE = Toolkit.getDefaultToolkit()
			.getImage("src/images/Feeder.png");
	public static final Image LANE_LED_IMAGE = Toolkit.getDefaultToolkit()
			.getImage("src/images/FeederGreenLight.png");

	// Lane Images
	public static final Image LANE_IMAGE = Toolkit.getDefaultToolkit()
			.getImage("src/images/lane.png");
	public static final Image LANE_LINE = Toolkit.getDefaultToolkit().getImage(
			"src/images/laneline.png");

	// Conveyor Images
	public static final Image CONVEYOR_IMAGE = Toolkit.getDefaultToolkit()
			.getImage("src/images/Conveyor.jpg");
	public static final Image CONVEYOR_LINES_IMAGE = Toolkit
			.getDefaultToolkit().getImage("src/images/ConveyorLines.png");
	public static final Image EXIT_IMAGE = Toolkit.getDefaultToolkit()
			.getImage("src/images/ExitBelt.png");
	public static final Image EXIT_LINES_IMAGE = Toolkit.getDefaultToolkit()
			.getImage("src/images/ExitLines.png");

	// Kit Robot Images
	public static final Image KIT_ROBOT_IMAGE = Toolkit.getDefaultToolkit()
			.getImage("src/images/Square.jpg");

	// Kit Images
	public static final Image KIT_IMAGE = Toolkit.getDefaultToolkit().getImage(
			"src/images/Kit.jpg");

	// Nest Images
	public static final Image NEST_IMAGE = Toolkit.getDefaultToolkit()
			.getImage("src/images/Nest.png");
	
	// Camera Images
	public static final Image CAMERA_IMAGE = Toolkit.getDefaultToolkit().getImage("src/images/Square.jpg");

	// Part Images
	public static final Image PART_IMAGE = Toolkit.getDefaultToolkit()
			.getImage("src/images/samplepart.png");
	
	// Bin Images
	public static final Image BIN_IMAGE = Toolkit.getDefaultToolkit()
			.getImage("src/images/samplebin.png");

	// TARGET NAMES
	// ==================================
	// Used so that we can create Request methods more easily.
	// When the target has a specific ID, concatenate to the device target
	// e.g. String target = Constants.LANE_TARGET+":"+laneID;

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
	// ==================================
	// Used so that we can create Request methods more easily.
	// Naming convention: DEVICENAME_ACTION_COMMAND

	public static final String DONE_SUFFIX = "done";
	// for servers to identify managers
	public static final String IDENTIFY_COMMAND = "identify"; 

	// feeder logic to display commands
	// draw the diverter flipping
	public static final String FEEDER_FLIP_DIVERTER_COMMAND = "feederflipdiv";
	// a bin has been received
	public static final String FEEDER_RECEIVED_BIN_COMMAND = "feederrecbin"; 
	// purge bin
	public static final String FEEDER_PURGE_BIN_COMMAND = "feederpurgebin";
	// move part to diverter
	public static final String FEEDER_MOVE_TO_DIVERTER_COMMAND = "feedermovediv"; 
	// move part to lane
	public static final String FEEDER_MOVE_TO_LANE_COMMAND = "feedermovlane"; 
	// end feeder logic to display commands

	// lane logic to display commands
	// purge lane
	public static final String LANE_PURGE_COMMAND = "purge lane";
	// sends animation instructions to lane
	public static final String LANE_SEND_ANIMATION_COMMAND = "lane animation"; 
	// sets lane amplitude
	public static final String LANE_SET_AMPLITUDE_COMMAND = "lane set amp";
	// turns lane on or off
	public static final String LANE_TOGGLE_COMMAND = "lane toggle"; 
	// sets start loc for this lane
	public static final String LANE_SET_STARTLOC_COMMAND = "lane start loc"; 
	// new part added to lane
	public static final String LANE_RECEIVE_PART_COMMAND = "lane receive part"; 
	// gives part to nest
	public static final String LANE_GIVE_PART_TO_NEST = "lane give part to nest"; 
	// end lane commands

	// conveyor logic to display commands
	// conveyor gives kit to kit robot
	public static final String CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND = "give kit to kit robot";
	// conveyor receives a kit
	public static final String CONVEYOR_RECEIVE_KIT_COMMAND = "conveyor receive kit";
	// sends animation information to conveyor
	public static final String CONVEYOR_SEND_ANIMATION_COMMAND = "conveyor animation"; 
	// need to change velocity
	public static final String CONVEYOR_CHANGE_VELOCITY_COMMAND = "conveyor change velocity";
	public static final String CONVEYOR_MAKE_NEW_KIT_COMMAND = "make new kit";
	// end conveyor logic to display

	// kitrobot logic
	public static final String KIT_ROBOT_LOGIC_PICKS_CONVEYOR = "robot logic moves kit from conveyor";
	public static final String KIT_ROBOT_DISPLAY_PICKS_CONVEYOR= "robot display moves kit from conveyor ";
	
	public static final String KIT_ROBOT_LOGIC_PICKS_lOCATION1_TO_CONVEYOR = "robot logic moves loc1 to conv";
	public static final String KIT_ROBOT_DISPLAY_PICKS_LOCATION1_TO_CONVEYOR = "robot display moves kit to conv";
	// end kitrobot logic

	// partsrobot logic to display commands
	
	// parts robot moves to target nest
	public static final String PARTS_ROBOT_MOVE_TO_NEST1_COMMAND = "nest1";
	public static final String PARTS_ROBOT_MOVE_TO_NEST2_COMMAND = "nest2"; 	
	// parts robot rotate
	public static final String PARTS_ROBOT_ROTATE_COMMAND = "rotate"; 
	// pick up part
	public static final String PARTS_ROBOT_PICKUP_COMMAND = "pickup"; 
	// give part to kit
	public static final String PARTS_ROBOT_GIVE_COMMAND = "give"; 
	// parts robot goes back to initial location
	public static final String PARTS_ROBOT_GO_HOME_COMMAND = "gohome"; 
	// parts robot go to kit
	public static final String PARTS_ROBOT_GO_KIT_COMMAND = "gokit"; 
	
	// end partsrobot logic to display commands

	// nest logic to display commands
	public static final String NEST_RECEIVE_PART_COMMAND = "nestrecpart";
	public static final String NEST_GIVE_TO_PART_ROBOT_COMMAND = "nestgivetopr";
	public static final String NEST_PURGE_COMMAND = "nestpurge";
	// end nest logic to display commands
	
	// camera logic to display commands
	public static final String CAMERA_TAKE_NEST_PHOTO_COMMAND = "cameranest";
	public static final String CAMERA_TAKE_KIT_PHOTO_COMMAND = "camerakit";
	// end nestgraphics logic to display commands

	// CLIENT NAMES
	// ==================================
	// Used to identify clients.

	// V0 Config
	public static final String KIT_ROBOT_MNGR_CLIENT = "KitsRobotMngr";
	public static final String PARTS_ROBOT_MNGR_CLIENT = "PartsRobotMngr";
	public static final String LANE_MNGR_CLIENT = "LaneMngr";

	// Agent constants for StringUtil
	/** The number of milliseconds in a second */
	public static final long SECOND = 1000;
	/** The number of milliseconds in a minute */
	public static final long MINUTE = 60 * SECOND;
	/** The number of milliseconds in an hour */
	public static final long HOUR = 60 * MINUTE;
	/** The number of milliseconds in a day */
	public static final long DAY = 24 * HOUR;
	/** The number of milliseconds in a week */
	public static final long WEEK = 7 * DAY;

	/** The line separator string on this system */
	public static String EOL = System.getProperty("line.separator");

	/** The default encoding used when none is detected */
	public static String DEFAULT_ENCODING = "ISO-8859-1";

}
