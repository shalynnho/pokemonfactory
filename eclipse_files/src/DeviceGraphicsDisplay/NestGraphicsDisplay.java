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
 * @author Vansh Jain, Shalynn Ho, Harry Trieu
 * 
 */
public class NestGraphicsDisplay extends DeviceGraphicsDisplay {
	// max number of parts this Nest holds
	private static final int MAX_PARTS = 8;
	// width and height of the nest
	private static final int NEST_WIDTH = 75;
	private static final int NEST_HEIGHT = 70;
	// width and height of a part
	private static final int PART_WIDTH = 20, PART_HEIGHT = 50;
	private static final int PART_OFFSET = 19;
	private static final int BOTTOM_ROW_OFFSET = 23;

	// the LaneManager (client) which talks to the Server
	private Client manager;
	// the id of this nest
	private int nestID;
	// location of the nest
	private Location nestLocation;
	// array of part locations in nest
	private ArrayList<Location> partLocs;

	// boolean if the nest is full
	private boolean isFull;
	// dynamically stores the parts currently in the Nest
	private ArrayList<PartGraphicsDisplay> partsInNest = new ArrayList<PartGraphicsDisplay>();

	/**
	 * Default constructor
	 */
	public NestGraphicsDisplay(Client c, int id) {
		manager = c;
		nestID = id;
		isFull = true;

		nestLocation = new Location(485 - NEST_WIDTH, 45 + nestID * 75);
		generatePartLocations();

		// TEST HACK, remove later
		for (int i = 0; i < MAX_PARTS; i++) {
			PartGraphicsDisplay pgd = new PartGraphicsDisplay(
					Constants.DEFAULT_PARTTYPES.get(0));
			pgd.setLocation(partLocs.get(i));
			partsInNest.add(pgd);
		}
	}

	/**
	 * Handles drawing of NestGraphicsDisplay objects
	 */
	public void draw(JComponent c, Graphics2D g) {
		g.drawImage(Constants.NEST_IMAGE, nestLocation.getX(),
				nestLocation.getY(), c);
		for (PartGraphicsDisplay part : partsInNest) {
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
				partLocs.add(new Location((nestLocation.getX() + (i / 2)
						* PART_WIDTH), (nestLocation.getY() - PART_OFFSET)));
			} else { // bottom row
				partLocs.add(new Location((nestLocation.getX() + (i / 2)
						* PART_WIDTH),
						(nestLocation.getY() + BOTTOM_ROW_OFFSET - PART_OFFSET)));
			}
		}
	}

	private void givePartToPartsRobot() {
		partsInNest.remove(0); // TODO: later might need to animate this
		setPartLocations();
		manager.sendData(new Request(Constants.NEST_GIVE_TO_PART_ROBOT_COMMAND
				+ Constants.DONE_SUFFIX, Constants.NEST_TARGET + nestID, null));
	}

	private void purge() {
		// TODO: (later) animate parts out of nest
		for (int i = 0; i < partsInNest.size(); i++) {
			partsInNest.remove(0);
		}
		manager.sendData(new Request(Constants.NEST_PURGE_COMMAND
				+ Constants.DONE_SUFFIX, Constants.NEST_TARGET + nestID, null));
	}

	private void receivePart(PartType type) {
		PartGraphicsDisplay pgd = new PartGraphicsDisplay(type);
		partsInNest.add(pgd);
		manager.sendData(new Request(Constants.NEST_RECEIVE_PART_COMMAND
				+ Constants.DONE_SUFFIX, Constants.NEST_TARGET + nestID, null));
	}

	/**
	 * Sets/updates the locations of the parts in the nest.
	 */
	private void setPartLocations() {
		// whichever is less
		int min = (MAX_PARTS < partsInNest.size()) ? MAX_PARTS : partsInNest
				.size();
		for (int i = 0; i < min; i++) {
			partsInNest.get(i).setLocation(partLocs.get(i));
		}
	}

	/**
	 * Allows another object to set the NestGraphicsDisplay location This method
	 * should not be called.
	 * 
	 * @param newLocation
	 *            - the new location of the nest
	 */
	public void setLocation(Location newLocation) {
		// TODO Auto-generated method stub

	}
}
