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

	// instructions to display graphics will be sent through the server
	private final Server server;
	// the ID of this Nest
	private int nestID;
	// the NestAgent
	private NestAgent nestAgent;

	// Location of upper left corner of this nest
	private final Location location;
	// dynamically stores the parts currently in the Nest
	private ArrayList<PartGraphics> partsInNest;

	// true if nest is full, can't receive parts
	private final boolean isFull;
	// true if spot is filled, false if not

	public NestGraphics(Server s, int nid, Agent agent) {
		server = s;
		nestID = nid;
		nestAgent = (NestAgent) agent;
		partsInNest = new ArrayList<PartGraphics>(MAX_PARTS);
		isFull = false;
		location = new Location(600, 100 + nestID * 75);
	}

	/**
	 * @param -
	 */
	@Override
	public void receivePart(PartGraphics pg) {
		partsInNest.add(pg);
		
		System.out.println("		NEST"+nestID+": receivePart called, receiving part number "+partsInNest.size());

		PartType type = pg.getPartType();
		server.sendData(new Request(Constants.NEST_RECEIVE_PART_COMMAND,
				Constants.NEST_TARGET + nestID, type));
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
		if (i >= 0) {
			partsInNest.remove(i);
		}

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
