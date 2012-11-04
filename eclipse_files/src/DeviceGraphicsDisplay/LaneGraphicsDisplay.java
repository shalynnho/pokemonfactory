package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import factory.data.PartType;

import DeviceGraphics.PartGraphics;
import Networking.Client;
import Networking.Request;
import Utils.Animation;
import Utils.Constants;
import Utils.Location;

/**
 * This class contains the graphics display components for a lane.
 * 
 * @author Shalynn Ho
 *
 */
public class LaneGraphicsDisplay extends DeviceGraphicsDisplay {
	// horizontal length of the Lane image
	private static final int LANE_LENGTH = 200;	
	// start and end x-coordinates of Part on the Lane
	private static final int LANE_BEG_X = 650;
	private static final int LANE_END_X = 450;
	// width and height of the part
	private static final int PART_WIDTH = 20, PART_HEIGHT = 20;
	// max number of parts that can be on a Lane
	private static final int MAX_PARTS = LANE_LENGTH/PART_WIDTH;
	
	// stores static ImageIcon emptyLane1, emptyLane2
	private static ArrayList<Image> LaneImgs = new ArrayList<Image>();
	private static Image laneImg;
	
	// stores the parts on the lane
	private ArrayList<PartGraphicsDisplay> partsOnLane;
	
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
	
	// true if Lane is on
	private boolean laneOn;
	// counter
	
	
	public LaneGraphicsDisplay(Client lm, int lid) {
		laneManager = lm;
		laneID = lid;
				
		//TODO: load empty lane images, add to array list (get image from CONSTANTS when added)
		laneImg = Toolkit.getDefaultToolkit().getImage("src/images/Lane.png");
		
		partsOnLane = new ArrayList<PartGraphicsDisplay>();
	}
	
	public LaneGraphicsDisplay(Client lm, Location l, int lid) {
		laneManager = lm;
		loc = l;
		laneID = lid;
				
		//TODO: load empty lane images, add to array list (get image from CONSTANTS when added)
		laneImg = Toolkit.getDefaultToolkit().getImage("src/images/Lane.png");
		
		partsOnLane = new ArrayList<PartGraphicsDisplay>();
	}
	
	@Override
	public void draw(JComponent c, Graphics2D g) {
		// TODO animate lane movement & move parts down lane
		if (laneOn) {
			// need image(s) of lane and/or lane lines?
			g.drawImage(laneImg, loc.getX(), loc.getY(), c);
			
			// TODO: animate parts moving down lane
			
			
			
			
		} else { // lane is off
			g.drawImage(laneImg, startLoc.getX(), startLoc.getY(), c);
		}
	}
	
	/**
	 * 
	 * @param r
	 */
	public void receiveData(Request r) {
		String cmd = r.getCommand();
		// parse data request here	
		
			
		if (cmd.equals(Constants.LANE_PURGE_COMMAND)) {
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
		} else if (cmd.equals(Constants.LANE_NEW_PART_COMMAND)) {
			// TODO: 
			PartType partType = (PartType) r.getData();
			partsOnLane.add(new PartGraphicsDisplay(partType));
			
		} else {
			System.out.println("LANEGRAPHICSDISP: command not recognized.");
		}
	}
	
	
	/**
	 * 
	 */
	public void purge() {
		// lane should continue as is
	}
	
	/**
	 * 
	 * @param amp
	 */
	public void setAmplitude(int amp) {
		amplitude = amp;
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
		laneOn = on;
	}

	/**
	 * 
	 */
	private void movePartDownLane() {
		// depends on if we're moving parts in logic or display side
	}

	/**
	 * 
	 */
	private void lineUpParts() {
		// depends on if we're moving parts in logic or display side

	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
