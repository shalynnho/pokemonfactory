package DeviceGraphicsDisplay;

import Networking.*;
import GraphicsInterfaces.*;
import Utils.*;
import DeviceGraphics.PartGraphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * This class contains the graphics display components for a lane.
 * 
 * @author Shalynn Ho
 *
 */
public class LaneGraphicsDisplay extends DeviceGraphicsDisplay {
	// max number of parts that can be on a Lane
	private static final int MAX_PARTS = 4;
	// horizontal length of the Lane image
	private static final int IMG_LENGTH = 200;
	
	// stores static ImageIcon emptyLane1, emptyLane2
	private static ArrayList<ImageIcon> emptyLaneImgs = new ArrayList<ImageIcon>();
	
	// Location of this lane
	private Location loc;
	// start Location for a part on this lane
	private Location startLoc;
	
	// the LaneManager (client) which talks to the Server
	private Client laneManager;
	// the ID of this Lane
	private int laneID;
	// the amplitude of this lane
	private int amplitude = 1;
	
	// dynamically stores images of Lane
	private ArrayList<ImageIcon> imgsOnLane;
	// true if Lane is on
	private boolean laneOn;
	
	
	public LaneGraphicsDisplay(Client lm, int lid) {
		laneManager = lm;
		laneID = lid;
				
		// load empty lane images, add to array list
	}
	
	@Override
	public void draw(JFrame myJFrame, Graphics2D g) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @param r
	 */
	public void receiveData(Request r) {
		String cmd = r.getCommand();
		// parse data request here	
		
		if (cmd.equals(Constants.LANE_RECEIVE_PART_COMMAND)) {
			PartGraphics pg = (PartGraphics) r.getData();
			receivePart(pg);
			
		} else if (cmd.equals(Constants.LANE_GIVE_PART_TO_NEST_COMMAND)) {
			PartGraphics pg = (PartGraphics) r.getData();
			givePartToNest(pg);
			
		} else if (cmd.equals(Constants.LANE_PURGE_COMMAND)) {
			purge();
			
		} else if (cmd.equals(Constants.LANE_SEND_ANIMATION_COMMAND)) {
			Animation ani = (Animation) r.getData();
			// TODO: DO SOMETHING WITH THIS ANIMATION
			
		} else if (cmd.equals(Constants.LANE_SET_AMPLITUDE_COMMAND)) {
			amplitude = (Integer) r.getData();
			
		} else if (cmd.equals(Constants.LANE_TOGGLE_COMMAND)) {
			laneOn = (Boolean) r.getData();
			
		} else if (cmd.equals(Constants.LANE_SET_STARTLOC_COMMAND)) {
			startLoc = (Location) r.getData();
		} else {
			System.out.println("LANEGRAPHICSDISP: command not recognized.");
		}
	}
	
	/**
	 * 
	 * @param p
	 */
	public void receivePart(PartGraphics pg) {
		pg.setLocation(startLoc);
	}
	
	/**
	 * 
	 * @param p
	 */
	public void givePartToNest(PartGraphics pg) {
		
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
	
	@Override
	public void setLocation(Location newLocation) {
		loc = newLocation;
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
