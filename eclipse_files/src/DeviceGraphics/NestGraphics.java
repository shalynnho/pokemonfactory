package DeviceGraphics;

import java.util.ArrayList;
import java.util.Map;

import factory.PartType;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.Agent;
import agent.NestAgent;

/**
 * This class represents the graphics logic for a nest.
 * @author Shalynn Ho, Harry Trieu
 */
public class NestGraphics implements GraphicsInterfaces.NestGraphics,
		DeviceGraphics {
	// max number of parts this Nest holds
	private static final int MAX_PARTS = 8;
	// width and height of the nest
	private static final int NEST_WIDTH = 75, NEST_HEIGHT = 70;
	// y-coordinates of the nest0
	private static final int NEST_Y = 45, NEST_Y_INCR = 75;
	// width and height of a part
	private static final int PART_WIDTH = 20, PART_HEIGHT = 50;
	private static final int PART_OFFSET = 19;
	private static final int BOTTOM_ROW_OFFSET = 23;
	// start and end x-coordinates of Part on the Lane
	private static final int LANE_END_X = 640;

	// instructions to display graphics will be sent through the server
	private final Server server;
	// the ID of this Nest
	private int nestID;
	// the NestAgent
	private NestAgent nestAgent;
	// array of part locations in nest
	private ArrayList<Location> partLocs;
	// Location of upper left corner of this nest
	private final Location location;
	private final Location partStartLoc;
	// dynamically stores the parts currently in the Nest
	private ArrayList<PartGraphics> partsInNest;

	public NestGraphics(Server s, int nid, Agent agent) {
		server = s;
		nestID = nid;
		nestAgent = (NestAgent) agent;
		
		partsInNest = new ArrayList<PartGraphics>(MAX_PARTS);
		location = new Location(LANE_END_X - NEST_WIDTH, NEST_Y + nestID * NEST_Y_INCR);
		partStartLoc = new Location(LANE_END_X, location.getY()
				+ (PART_WIDTH / 2) - PART_OFFSET);
		generatePartLocations();
	}

	/**
	 * @param -
	 */
	@Override
	public void receivePart(PartGraphics pg) {
		partsInNest.add(pg);
		pg.setLocation(partLocs.get(partsInNest.size() - 1));
		PartType type = pg.getPartType();
		server.sendData(new Request(Constants.NEST_RECEIVE_PART_COMMAND,
				Constants.NEST_TARGET + nestID, type));
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
	 * @param
	 */
	@Override
	public void givePartToPartsRobot(PartGraphics pg) {

		int i = partsInNest.indexOf(pg); // this might not work. depends on if
											// part passed in matches what is
											// already in nest
											// otherwise, must find a way to
											// figure out which part is being
											// taken from which spot in the nest
		if (partsInNest.size() > 0) {
			partsInNest.remove(i);
		}
		setPartLocations();
		if (server != null) {
			server.sendData(new Request(
					Constants.NEST_GIVE_TO_PART_ROBOT_COMMAND,
					Constants.NEST_TARGET + nestID, pg));
		}
	}

	/**
	 * 
	 */
	@Override
	public void purge() {
		partsInNest.clear();
		server.sendData(new Request(Constants.NEST_PURGE_COMMAND,
				Constants.NEST_TARGET + nestID, null));
	}

	/**
	 * Receives message data from the Server
	 * @param r - the request to be parsed
	 */
	@Override
	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.NEST_RECEIVE_PART_COMMAND + Constants.DONE_SUFFIX)) {
			nestAgent.msgReceivePartDone();
			
		} else if (req.getCommand().equals(
			Constants.NEST_GIVE_TO_PART_ROBOT_COMMAND + Constants.DONE_SUFFIX)) {
			nestAgent.msgGivePartToPartsRobotDone();
			
		} else if (req.getCommand().equals(
			Constants.NEST_PURGE_COMMAND + Constants.DONE_SUFFIX)) {
			nestAgent.msgPurgingDone();
			
		} else if (req.getCommand().equals(
			// is this necessary?
			Constants.NEST_GIVE_TO_PART_ROBOT_COMMAND)) {
			server.sendData(new Request(
					Constants.NEST_GIVE_TO_PART_ROBOT_COMMAND,
					Constants.NEST_TARGET + nestID, null));
			
		} else if (req.getCommand().equals(Constants.NEST_RECEIVE_PART_COMMAND)) {
			// is this necessary?
			server.sendData(new Request(Constants.NEST_RECEIVE_PART_COMMAND,
					Constants.NEST_TARGET + nestID, null));
		}

	}

	/**
	 * @return
	 */
	public boolean isFull() {
		return partsInNest.size() >= MAX_PARTS;
	}

	/**
	 * V2 ONLY
	 * @return
	 */
	public boolean allPartsUnanalyzed() {
		// TODO: IMPLEMENT THIS METHOD FOR V2
		return false;
	}

	/**
	 * V2 ONLY
	 * @return
	 */
	public boolean allPartsGood() {
		// TODO: IMPLEMENT THIS METHOD FOR V2
		return false;
	}

	/**
	 * V2 ONLY
	 * @return
	 */
	public boolean allPartsBad() {
		// TODO: IMPLEMENT THIS METHOD FOR V2
		return false;
	}

	/**
	 * @return location of this nest
	 */
	@Override
	public Location getLocation() {
		return location;
	}

	/**
	 * @returns an array list of the parts in nest
	 */
	public ArrayList<PartGraphics> getPartsInNest() {
		return partsInNest;
	}

	/**
	 * V2 ONLY, no bad parts in V1.
	 * @return
	 */
	public Map<PartGraphics, Boolean> getQualityOfParts() {
		// TODO: IMPLEMENT THIS METHOD FOR V2
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public int getNestID() {
		return nestID;
	}

	public void setNestID(int nestID) {
		this.nestID = nestID;
	}

	public NestAgent getNestAgent() {
		return nestAgent;
	}

	public void setNestAgent(NestAgent nestAgent) {
		this.nestAgent = nestAgent;
	}

	public void setPartsInNest(ArrayList<PartGraphics> partsInNest) {
		this.partsInNest = partsInNest;
	}
}
