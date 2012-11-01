package DeviceGraphics;

import Networking.*;
import GraphicsInterfaces.*;
import Utils.*;

import java.util.ArrayList;


/**
 * This class contains the graphics logic for a lane.
 * 
 * @author Shalynn Ho
 *
 */
public class LaneGraphics extends DeviceGraphics implements GraphicsInterfaces.LaneGraphics {
	// max number of parts that can be on a Lane
	private static final int MAX_PARTS;
	// start and end locations of Part on the Lane
	private static final Location PART_START = new Location(,);
	private static final Location PART_END = new Location(,);	// might not need this
	
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
		amplitude = 1;	// WHAT IS DEFAULT AMP??????, also must set parameters for amp
		laneOn = true;
	}
	
	/**
	 * Called when part is delivered to this lane
	 * @param pg - the part passed to this lane
	 */
	public void receivePart(PartGraphics pg) {
		partsOnLane.add(pg);
		pg.setLocation(PART_START);
		server.sendData(new Request(Constants.LANE_RECEIVE_PART, Constants.LANE_TARGET+laneID, pg));
	}
	
	/**
	 * Called when part needs to be given to the nest associated with this lane.
	 * Basically, this lane doesn't care about that part anymore.
	 * @param pg - the part passed to the nest associated with this lane
	 */
	public void givePartToNest(PartGraphics pg) {
		/* at the end of the Lane, gives the Part to the Nest
		- receive message from LGD that Part is at end of Lane and Nest not full
		- tell NestGraphicsLogic that we are passing Part
		- remove from Lane parts queue */
		// do i need to check if nest is full first? (or do agents do this?)
		// just to double check, i don't call nest.receivePart(part) right?
		
		partsOnLane.remove(0); 	// this is kind of dangerous. check that correct part is removed.
		server.sendData(new Request(Constants.LANE_GIVE_PART_TO_NEST, Constants.LANE_TARGET+laneID, pg));
		
	}

	/**
	 * 
	 */
	public void purge() {
		partsOnLane.clear();
		server.sendData(new Request(Constants.LANE_PURGE, Constants.LANE_TARGET+laneID, null));
	}
	
	/**
	 * Sets the vibration amplitude of this lane (how quickly parts vibrate down lane)
	 * @param amp - the amplitude
	 */
	public void setAmplitude(int amp) {
		amplitude = amp;
	}
	
	/**
	 * 
	 * @return true if this lane is full
	 */
	public boolean isFull() {
		return partsOnLane.size() == MAX_PARTS;
	}
	
	/**
	 * 
	 * @param on - on/off switch for this lane
	 */
	public void toggleSwitch(boolean on) {
		laneOn = on;
	}
	
	/**
	 * 
	 * @param r
	 */
	public void receiveData(Request r) {
		// must parse data request here
		// if-else for every possible command
		
		// We want confirmation from Display each time an animation is completed.
	}
	
	/**
	 * Sends an instance of Animation through the server.
	 * Tells the display class end Location of animation and duration allotted.
	 */
	private void sendAnimation(Animation ani) {
		server.sendData(new Request(Constants.LANE_SEND_ANIMATION, Constants.LANE_TARGET+laneID, ani));
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
