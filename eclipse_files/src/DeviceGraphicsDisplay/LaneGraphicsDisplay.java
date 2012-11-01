package DeviceGraphicsDisplay;

import Networking.*;
import GraphicsInterfaces.*;
import Utils.*;

import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * This class contains the graphics display components for a lane.
 * 
 * @author Shalynn Ho
 *
 */
public class LaneGraphicsDisplay extends DeviceGraphicsDisplay {
	// max number of parts that can be on a Lane
	private static final int MAX_PARTS = ;
	// horizontal length of the Lane image
	private static final int IMG_LENGTH = ;
	// x-coordinates of beginning and end of lane
	private static final int 	LANE_BEG_X = , 
								LANE_END_X = ;
	// width and height of the part
	private static final int 	PART_WIDTH = ,
								PART_HEIGHT = ;
	// y-coords of parts on lane, change shows vibration
	private static final int 	PART_Y1 = ,
								PART_Y2 = ;
	
	// stores static ImageIcon emptyLane1, emptyLane2
	private static ArrayList<ImageIcon> emptyLaneImgs = new ArrayList<ImageIcon>();
	
	// the LaneManager (client) which talks to the Server
	private LaneManager laneManager;
	// the ID of this Lane
	private int laneID;
	
	// dynamically stores images of Lane
	private ArrayList<ImageIcon> imgsOnLane;
	// true if Lane is on
	private boolean laneOn;
	
	
	public LaneGraphicsDisplay(LaneManager lm, int lid) {
		laneManager = lm;
		laneID = lid;
				
		// load empty lane images, add to array list
	}
	
	/**
	 * 
	 * @param g2
	 */
	public void paintComponent(Graphics2D g2) {
		
		// would we use Animation info to calculate how far to increment after each paint call?
		
	}
	
	/**
	 * 
	 * @param r
	 */
	public void receiveData(Request r) {
		// parse data request here
		// if-else for every possible command sent through server.........
		
		
	}
	
	/**
	 * 
	 * @param p
	 */
	public void receivePart(PartGraphicsDisplay p) {
		
	}
	
	/**
	 * 
	 * @param p
	 */
	public void givePartToNest(PartGraphicsDisplay p) {
		
	}
	
	/**
	 * 
	 */
	public void purge() {
		
	}
	
	/**
	 * 
	 * @param amp
	 */
	public void setAmplitude(int amp) {
		
	}
	
	/**
	 * 
	 * @param on
	 */
	public void toggleSwitch(boolean on) {
		
	}

	/**
	 * 
	 */
	private void movePartDownLane() {
		
	}

	/**
	 * 
	 */
	private void lineUpParts() {
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
