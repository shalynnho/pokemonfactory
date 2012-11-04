package DeviceGraphics;

import Networking.*;
import GraphicsInterfaces.*;
import Utils.*;
import factory.data.*;

import java.util.ArrayList;

/**
 * This class contains the graphics logic for a lane.
 * 
 * @author Shalynn Ho
 * 
 */
public class LaneGraphics extends DeviceGraphics implements
		GraphicsInterfaces.LaneGraphics {
	// max number of parts that can be on a Lane
	private static final int MAX_PARTS = 8;
	// start and end x-coordinates of Part on the Lane
	private static final int LANE_BEG_X = 650;
	private static final int LANE_END_X = 450;
	// horizontal length of the Lane image
	private static final int LANE_LENGTH = 200;

	// y-coordinates of Part on Lane, depending on laneID
	private static final int LANE0_Y = 500;
	private static final int LANE1_Y = 450;
	private static final int LANE2_Y = 400;
	private static final int LANE3_Y = 350;
	private static final int LANE4_Y = 300;
	private static final int LANE5_Y = 250;
	private static final int LANE6_Y = 200;
	private static final int LANE7_Y = 150;

	// width and height of the part
	private static final int PART_WIDTH = 20, PART_HEIGHT = 20;

	// start location of the part
	private Location startLoc;

	// instructions to display graphics will be sent through the server
	private Server server;
	// the ID of this Lane
	private int laneID;
	// the Nest associated with this LaneGraphics object
	private NestGraphics nest;

	// dynamically stores Parts currently on Lane
	private ArrayList<PartGraphics> partsOnLane;

	// vibration setting; how quickly parts vibrate down Lane
	private int amplitude;

	// true if Lane is on
	private boolean laneOn;

	/**
	 * 
	 * @param s
	 * @param id
	 * @param n
	 */
	public LaneGraphics(Server s, int id, NestGraphics n) {
		server = s;
		laneID = id;
		nest = n;

		partsOnLane = new ArrayList<PartGraphics>();
		amplitude = 1; // WHAT IS DEFAULT AMP??????, also must set parameters
						// for amp
		laneOn = true;

		setValues(laneID);
	}

	/**
	 * Called when part is delivered to this lane
	 * 
	 * @param pg
	 *            - the part passed to this lane
	 */
	public void receivePart(Part p) {
		PartGraphics pg = p.part;
		partsOnLane.add(pg);
		pg.setLocation(startLoc);
		PartType pt = p.type;
		
		server.sendData(new Request(Constants.LANE_NEW_PART_COMMAND, Constants.LANE_TARGET+laneID, pt));
		
		// later pass if good/bad part also
	}

	/**
	 * Called when part needs to be given to the nest associated with this lane.
	 * Basically, this lane doesn't care about that part anymore.
	 * 
	 * @param pg
	 *            - the part passed to the nest associated with this lane
	 */
	public void givePartToNest(Part p) {
		 PartGraphics pg = p.part;
		/*
		 * at the end of the Lane, gives the Part to the Nest - receive message
		 * from LGD that Part is at end of Lane and Nest not full - tell
		 * NestGraphicsLogic that we are passing Part - remove from Lane parts
		 * queue
		 */
		// do i need to check if nest is full first? (or do agents do this?)
		// just to double check, i don't call nest.receivePart(part) right?

		partsOnLane.remove(0); // this is kind of dangerous. check that correct
								// part is removed.
	}

	/**
	 * 
	 */
	public void purge() {
		partsOnLane.clear();
		// TODO: set location of parts to fall off lane
		server.sendData(new Request(Constants.LANE_PURGE_COMMAND,
				Constants.LANE_TARGET + laneID, null));
	}

	/**
	 * Sets the vibration amplitude of this lane (how quickly parts vibrate down
	 * lane)
	 * 
	 * @param amp
	 *            - the amplitude
	 */
	public void setAmplitude(int amp) {
		amplitude = amp;
		server.sendData(new Request(Constants.LANE_SET_AMPLITUDE_COMMAND,
				Constants.LANE_TARGET + laneID, amp));
	}

	/**
	 * Sets location of the lane lines to animate motion
	 */
	public void setLaneLocation() {
		// TODO: implementation depends on how the lane images are drawn
	}

	public void setPartsLocation() {
		
		// TODO: calculate timer delay to move parts down lane
		
		if (nest.isFull()) { // parts start backing up
			for (int i = 0; i < partsOnLane.size(); i++) {
				PartGraphics pg = partsOnLane.get(i);
				Location loc = pg.getLocation();
				if (i == 0) {
					loc.setX(LANE_END_X);
				} else {
					loc.setX(LANE_END_X + (i * PART_WIDTH));
				}
				
				vibrateParts(i, loc);
				pg.setLocation(loc);
			}
		} else { // nest is not full
			for (int i = 0; i < partsOnLane.size(); i++) {

				PartGraphics pg = partsOnLane.get(i);
				Location loc = pg.getLocation();
				if (i == 0) {
					loc.setX(LANE_END_X);
				} else {
					loc.setX(LANE_END_X + (i * 2 * PART_WIDTH));
				}
				vibrateParts(i, loc);
				pg.setLocation(loc);

			}
		}
	}
	
	/**
	 * change y-coords to show vibration down lane (may have to adjust values)
	 */
	private void vibrateParts(int i, Location loc) {
		// to show vibration down lane (may have to adjust values)
		if (i % 2 == 0) {
			loc.incrementY();
		} else {
			loc.incrementY(-1);
		}
	}

	/**
	 * 
	 * @return true if this lane is full
	 */
	public boolean isFull() {
		return partsOnLane.size() >= MAX_PARTS;
	}

	/**
	 * 
	 * @param on
	 *            - on/off switch for this lane
	 */
	public void toggleSwitch(boolean on) {
		laneOn = on;
		server.sendData(new Request(Constants.LANE_TOGGLE_COMMAND,
				Constants.LANE_TARGET + laneID, laneOn));
	}

	/**
	 * 
	 * @param r
	 */
	public void receiveData(Request r) {
		// must parse data request here
		// if-else for every possible command

		// We want confirmation from Display each time an animation is
		// completed.

	}

	/**
	 * Sends an instance of Animation through the server. Tells the display
	 * class end Location of animation and duration allotted.
	 */
	private void sendAnimation(Animation ani) {
		server.sendData(new Request(Constants.LANE_SEND_ANIMATION_COMMAND,
				Constants.LANE_TARGET + laneID, ani));
	}

	private void setValues(int id) {

		switch (id) {
		case 0:
			startLoc = new Location(LANE_BEG_X, LANE0_Y);
			break;
		case 1:
			startLoc = new Location(LANE_BEG_X, LANE1_Y);
			break;
		case 2:
			startLoc = new Location(LANE_BEG_X, LANE2_Y);
			break;
		case 3:
			startLoc = new Location(LANE_BEG_X, LANE3_Y);
			break;
		case 4:
			startLoc = new Location(LANE_BEG_X, LANE4_Y);
			break;
		case 5:
			startLoc = new Location(LANE_BEG_X, LANE5_Y);
			break;
		case 6:
			startLoc = new Location(LANE_BEG_X, LANE6_Y);
			break;
		case 7:
			startLoc = new Location(LANE_BEG_X, LANE7_Y);
			break;
		default:
			System.out.println("id not recognized.");
		}

		server.sendData(new Request(Constants.LANE_SET_STARTLOC_COMMAND,
				Constants.LANE_TARGET + laneID, startLoc));

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
