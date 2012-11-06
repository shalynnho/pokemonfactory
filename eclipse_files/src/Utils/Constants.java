package Utils;

import java.awt.Image;
import java.awt.Toolkit;

/**
 * Contains all the constants that we need in the project.
 * 
 * @author Peter Zhang
 */
public abstract class Constants {
	
	// SERVER SETTINGS
	//==================================
	
	public static final int SERVER_PORT = 6889;
	
	// CLIENT SETTINGS
	//==================================
	
	public static final int TIMER_DELAY = 50;
	
	public static final Image CLIENT_BG_IMAGE = Toolkit.getDefaultToolkit().getImage("src/images/bg.jpg");
	
	
	//DEVICE SETTINGS
	//==================================
	
	// conveyor images
	public static final Image CONVEYOR_IMAGE = Toolkit.getDefaultToolkit().getImage("src/images/Conveyor.jpg");
	public static final Image CONVEYOR_LINES_IMAGE = Toolkit.getDefaultToolkit().getImage("src/images/ConveyorLines.png");
	public static final Image EXIT_IMAGE = Toolkit.getDefaultToolkit().getImage("src/images/ExitBelt.png");
	public static final Image EXIT_LINES_IMAGE = Toolkit.getDefaultToolkit().getImage("src/images/ExitLines.png");
	
	// Kit Robot Images
	public static final Image KIT_ROBOT_IMAGE = Toolkit.getDefaultToolkit().getImage("src/images/Square.jpg");
	
	// kit images
	public static final Image KIT_IMAGE = Toolkit.getDefaultToolkit().getImage("src/images/Kit.jpg");
	
	//nest images
	public static final Image NEST_IMAGE = Toolkit.getDefaultToolkit().getImage("src/images/Nest.png");
	
	// TARGET NAMES
	//==================================
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
	//==================================
	// Used so that we can create Request methods more easily.
	// Naming convention: DEVICENAME_ACTION_COMMAND
	
	public static final String DONE_SUFFIX = "done";
	
	public static final String IDENTIFY_COMMAND = "identify";					// for servers to identify managers
	
	// feeder logic to display commands
	public static final String FEEDER_INIT_GRAPHICS_COMMAND = "feederinitg"; 		// initialize the feeder graphics
	public static final String FEEDER_FLIP_DIVERTER_COMMAND = "feederflipdiv";  	// draw the diverter flipping
	public static final String FEEDER_RECEIVED_BIN_COMMAND = "feederrecbin";		// a bin has been received
	public static final String FEEDER_PURGE_BIN_COMMAND = "feederpurgebin";			// purge the bin
	public static final String FEEDER_MOVE_TO_DIVERTER_COMMAND = "feedermovediv";	// move part to diverter
	public static final String FEEDER_MOVE_TO_LANE_COMMAND = "feedermovlane";		// move part to lane
	// end feeder logic to display commands
	
	// lane logic to display commands
	public static final String LANE_PURGE_COMMAND = "purge lane";						// purge lane
	public static final String LANE_SEND_ANIMATION_COMMAND = "lane animation";			// sends animation instructions to lane
	public static final String LANE_SET_AMPLITUDE_COMMAND = "lane set amp";				// sets lane amplitude
	public static final String LANE_TOGGLE_COMMAND = "lane toggle";						// turns lane on or off
	public static final String LANE_SET_STARTLOC_COMMAND = "lane start loc";			// sets start loc for this lane
	public static final String LANE_RECEIVE_PART_COMMAND = "lane receive part";					// new part added to lane
	public static final String LANE_GIVE_PART_TO_NEST = "lane give part to nest";		// gives part to nest
	// end lane commands
	
	// conveyor logic to display commands
	public static final String CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND = "give kit to kit robot";   //conveyor gives kit to kit robot
	public static final String CONVEYOR_RECEIVE_KIT_COMMAND = "conveyor receive kit";              //conveyor receives a kit
	public static final String CONVEYOR_SEND_ANIMATION_COMMAND = "conveyor animation";             //sends animation information to conveyor
	public static final String CONVEYOR_CHANGE_VELOCITY_COMMAND = "conveyor change velocity";      //need to change velocity
	public static final String CONVEYOR_MAKE_NEW_KIT_COMMAND = "make new kit";
	// end conveyor logic to display
	
	// kitrobot logic
	public static final String KIT_ROBOT_PICKS_CONVEYOR= "robot moves kit from conveyor";
	public static final String KIT_ROBOT_PICKS_lOCATION1_TO_CONVEYOR="";
	
	//partsrobot logic to display commands
	public static final String PARTS_ROBOT_MOVE_TO_NEST1_COMMAND = "nest1"; //parts robot moves to nest1
	public static final String PARTS_ROBOT_MOVE_TO_NEST2_COMMAND = "nest2"; //parts robot moves to nest2
	public static final String PARTS_ROBOT_ROTATE_COMMAND = "rotate"; //parts robot rotate
	public static final String PARTS_ROBOT_PICKUP_COMMAND = "pickup"; //pick up part
	public static final String PARTS_ROBOT_GIVE_COMMAND = "give"; //give part to kit
	public static final String PARTS_ROBOT_GO_HOME_COMMAND ="gohome"; //parts robot goes back to initial location
	public static final String PARTS_ROBOT_GO_KIT_COMMAND = "gokit"; //go to kit
	//kitrobot logic to display commands
	
	// CLIENT NAMES
	
	
	//==================================
	// Used to identify clients.
	
	// V0 Config - temporary names
	public static final String KIT_ROBOT_MNGR_CLIENT = "KitsRobotMngr";
	public static final String PARTS_ROBOT_MNGR_CLIENT = "PartsRobotMngr";
	public static final String LANE_MNGR_CLIENT = "LaneMngr";
	
}
