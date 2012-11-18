package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import manager.FactoryProductionManager;
import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;
import factory.PartType;

/**
 * @author Shalynn Ho, Harry Trieu
 * 
 */
public class NestGraphicsDisplay extends DeviceGraphicsDisplay {
	// max number of parts this Nest holds
	private static final int MAX_PARTS = 8;
	// width and height of the nest
	private static final int NEST_WIDTH = 75, NEST_HEIGHT = 70;
	// width and height of a part
	private static final int PART_WIDTH = 20, PART_HEIGHT = 50;
	private static final int PART_OFFSET = 19;
	private static final int BOTTOM_ROW_OFFSET = 23;
	// end x-coordinates of the Lane
	private static final int LANE_END_X = 640;
	// y-coordinates of the nest0
	private static final int NEST_Y = 45, NEST_Y_INCR = 75;

	// the id of this nest
	private int nestID;
	// array of part locations in nest
	private ArrayList<Location> partLocs;
	// controls animation
	private boolean receivingPart, purging;
	private boolean receivePartDoneSent = false, purgeDoneSent = false;
	// dynamically stores the parts currently in the Nest
	private ArrayList<PartGraphicsDisplay> partsInNest;
	// start location of a part entering the nest
	private Location partStartLoc;
	// purge location
	private Location purgeLoc;

	/**
	 * Default constructor
	 */
	public NestGraphicsDisplay(Client c, int id) {
		client = c;
		nestID = id;
		receivingPart = false;
		purging = false;

		location = new Location(LANE_END_X - NEST_WIDTH, NEST_Y + nestID * NEST_Y_INCR);
		partsInNest = new ArrayList<PartGraphicsDisplay>();
		partStartLoc = new Location(LANE_END_X, location.getY()
				+ (PART_WIDTH / 2) - PART_OFFSET);
		purgeLoc = new Location(location.getX() - PART_WIDTH, partStartLoc.getY());
		generatePartLocations();
	}

	/**
	 * Handles drawing of NestGraphicsDisplay objects
	 */
	public void draw(JComponent c, Graphics2D g) {
		g.drawImage(Constants.NEST_IMAGE, location.getX() + client.getOffset()
				, location.getY(), c);
		
		if (!isFull()) {
			if(receivingPart) {	// part in motion
				// get last part added to nest
				int index = partsInNest.size() - 1;
				PartGraphicsDisplay pgd = partsInNest.get(index);
				pgd.setLocation(partStartLoc);
				Location partLoc = pgd.getLocation();
				Location endLoc = partLocs.get(index);
				
				// check x-coord
				if(partLoc.getX() >= endLoc.getX()) {
					partLoc.incrementX(-1);
				}
				// check y-coord
				if((index % 2 == 0) && partLoc.getY() >= endLoc.getY()) { // top row
					partLoc.incrementY(-1);
				} else if((index % 2 != 0) && partLoc.getY() <= endLoc.getY()) { // bottom row
					partLoc.incrementY();
				}
				
				// check if part in place
				if (partLoc.equals(endLoc)) {
					receivingPart = false;
					msgAgentReceivePartDone();
				}
			}
		}
		
		if(purging) {
			// TODO
			// check x-coord
			// check y-coord
			// remove first part
			// move all parts up a space in nest, animate
			
		}
		
		for (PartGraphicsDisplay part : partsInNest) {
			part.getLocation().incrementX(client.getOffset());
			part.draw(c, g);
		}
	}

	/**
	 * Processes requests targeted at the NestGraphicsDisplay
	 */
	public void receiveData(Request req) {

		if (req.getCommand().equals(Constants.NEST_RECEIVE_PART_COMMAND)) {
			if (partsInNest.size() >= MAX_PARTS) {
				// TODO should this be a message back to the server?
				// NOTE: according to the agents, this should never happen
				// anyway
				System.out.println("Nest is full");
			} else {
				PartType type = (PartType) req.getData();
				receivePart(type);
			}

		} else if (req.getCommand().equals(
				Constants.NEST_GIVE_TO_PART_ROBOT_COMMAND)) {
			givePartToPartsRobot();

		} else if (req.getCommand().equals(Constants.NEST_PURGE_COMMAND)) {
			purge();
		}
	}

	/**
	 * Generates an array of Locations for the parts in the nest.
	 */
	private void generatePartLocations() {
		partLocs = new ArrayList<Location>(MAX_PARTS);
		for (int i = 0; i < MAX_PARTS; i++) {
			if (i % 2 == 0) { // top row
				partLocs.add(new Location((location.getX() + (i / 2)
						* PART_WIDTH), (location.getY() - PART_OFFSET)));
			} else { // bottom row
				partLocs.add(new Location((location.getX() + (i / 2)
						* PART_WIDTH),
						(location.getY() + BOTTOM_ROW_OFFSET - PART_OFFSET)));
			}
		}
	}

	private void givePartToPartsRobot() {
		partsInNest.remove(0); // TODO: later might need to animate this
		setPartLocations();
		client.sendData(new Request(Constants.NEST_GIVE_TO_PART_ROBOT_COMMAND
				+ Constants.DONE_SUFFIX, Constants.NEST_TARGET + nestID, null));
	}

	private void purge() {
		// TODO: (later) animate parts out of nest
		purging = true;
		
		
		
		
		
		for (int i = 0; i < partsInNest.size(); i++) {
			partsInNest.remove(0);
		}

	}

	private void receivePart(PartType type) {
		PartGraphicsDisplay pgd = new PartGraphicsDisplay(type);
		partsInNest.add(pgd);
		setPartLocations();
		receivingPart = true;
		receivePartDoneSent = false;
	}
	
	private boolean isFull() {
		return partsInNest.size() >= MAX_PARTS;
	}

	/**
	 * Sets/updates the locations of the parts in the nest.
	 */
	private void setPartLocations() {
		// whichever is less
		int min = (MAX_PARTS < partsInNest.size()) ? MAX_PARTS : partsInNest.size();
		for (int i = 0; i < min; i++) {
			partsInNest.get(i).setLocation(partLocs.get(i));
		}
	}
	
	/**
	 * Tells the agent that the part has reached its place in the nest.
	 * Make sure only sends message once for each part, not on every call to draw.
	 */
	private void msgAgentReceivePartDone() {
		if(!receivingPart && (!receivePartDoneSent)) {
			client.sendData(new Request(Constants.NEST_RECEIVE_PART_COMMAND
					+ Constants.DONE_SUFFIX, Constants.NEST_TARGET + nestID, null));
			receivePartDoneSent = true;
		}
	}
	
	/**
	 * Tells the agent that purging is done.
	 * Make sure only sends message once for each part, not on every call to draw.
	 */
	private void msgAgentPurgingDone() {
		if((partsInNest.size() == 0) && (!purgeDoneSent)) {
			client.sendData(new Request(Constants.NEST_PURGE_COMMAND
					+ Constants.DONE_SUFFIX, Constants.NEST_TARGET + nestID, null));
			purgeDoneSent = true;
		}
	}
}
