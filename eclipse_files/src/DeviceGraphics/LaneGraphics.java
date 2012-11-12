package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.Agent;
import agent.FeederAgent;
import agent.LaneAgent;
import factory.PartType;

/**
 * This class contains the graphics logic for a lane.
 * 
 * @author Shalynn Ho
 * 
 */
public class LaneGraphics implements GraphicsInterfaces.LaneGraphics, DeviceGraphics {
	// width and height of the part
	private static final int PART_WIDTH = 20;
	// horizontal length of the Lane image
	private static final int LANE_LENGTH = 400;
	// max number of parts that can be on a Lane
	private static final int MAX_PARTS = LANE_LENGTH / PART_WIDTH;
	// start and end x-coordinates of Part on the Lane
	private static final int LANE_BEG_X = 599;
	private static final int LANE_END_X = 199;
	
	// y-coordinates of Part on Lane, depending on laneID
	// TODO: ADJUST THESE LATER. NOT FOR V0, NOT IN DESIGN
	private static final int LANE0_Y = 100;
	private static final int LANE1_Y = 175;
	private static final int LANE2_Y = 250;
	private static final int LANE3_Y = 325;
	private static final int LANE4_Y = 400;
	private static final int LANE5_Y = 475;
	private static final int LANE6_Y = 550;
	private static final int LANE7_Y = 625;

	// start location of the part
	private Location laneLoc;
	private int endY;

	// instructions to display graphics will be sent through the server
	private Server server;
	// the ID of this Lane
	private int laneID;
	// the lane agent
	private LaneAgent laneAgent;

	//REMOVE FOR V1 AND IN CONSTRUCTOR/DEVICES
	FeederAgent feederAgent;
	
	// dynamically stores Parts currently on Lane
	private ArrayList<PartGraphics> partsOnLane;

	// vibration setting; how quickly parts vibrate down Lane
	private int amplitude;
	// true if Lane is on
	private boolean laneOn;

	/**
	 * 
	 * @param s - the Server
	 * @param id - ID of this lane
	 * @param la - the LaneAgent
	 */

	public LaneGraphics(Server s, int id, Agent la, Agent f) {
		server = s;
		laneID = id;

		laneAgent = (LaneAgent) la;
		feederAgent = (FeederAgent) f;

		
		// initialize lane components
		partsOnLane = new ArrayList<PartGraphics>();
//		agentPartsOnLane = new ArrayList<Part>();
		amplitude = 5;
		laneOn = true;
		
		setLaneLoc(laneID);
		endY = laneLoc.getY();
		
//		FOR REFRENCE ONLY:
//		partStart = new Location(LANE_BEG_X, endY + (PART_WIDTH / 2));
//		partEnd = new Location(LANE_END_X, endY);
	}

	/**
	 * 
	 * @return true if this lane is full
	 */
	public boolean isFull() {
		return partsOnLane.size() >= MAX_PARTS;
	}

	/**
	 * Called when part needs to be given to the nest associated with this lane.
	 * Basically, this lane doesn't care about that part anymore.
	 * 
	 * @param pg
	 *            - the part passed to the nest associated with this lane
	 */
	public void givePartToNest(PartGraphics pg) {
		partsOnLane.remove(0); // make sure to check that correct part is removed
		server.sendData(new Request(Constants.LANE_GIVE_PART_TO_NEST, Constants.LANE_TARGET + laneID, null));
	}

	/**
	 * Purges this lane of all parts
	 */
	public void purge() {
		partsOnLane.clear();
		// TODO: set location of parts to fall off lane
			// currently doing this in Display side only
		server.sendData(new Request(Constants.LANE_PURGE_COMMAND,
				Constants.LANE_TARGET + laneID, null));
	}
	
	/**
	 * Called when part is delivered to this lane
	 * 
	 * @param pg
	 *            - the part passed to this lane
	 */
	public void receivePart(PartGraphics pg) {
		partsOnLane.add(pg);
		pg.setLocation(new Location (LANE_BEG_X, endY + (PART_WIDTH / 2)));
		PartType type = pg.getPartType();
		
		server.sendData(new Request(Constants.LANE_RECEIVE_PART_COMMAND, Constants.LANE_TARGET + laneID, type));
		
		// TODO: (V2) later pass if good/bad part
	}

	/**
	 * Sorts data and messages sent to this lane via the server
	 * @param r - the request
	 */
	public void receiveData(Request r) {
		String cmd = r.getCommand();	
		
//		if (cmd.equals(Constants.LANE_RECEIVE_PART_COMMAND)) {	// testing purposes only, remove later
//			receivePart(new PartGraphics(PartType.A));	
//		}
//		else 
		if (cmd.equals(Constants.LANE_RECEIVE_PART_COMMAND+Constants.DONE_SUFFIX)) {
			PartGraphics p = partsOnLane.get(partsOnLane.size()-1);
			p.setLocation(new Location(LANE_END_X, endY));
			laneAgent.msgReceivePartDone(p);
			
		} else if (cmd.equals(Constants.LANE_GIVE_PART_TO_NEST + Constants.DONE_SUFFIX)) {
			laneAgent.msgGivePartToNestDone(partsOnLane.get(0));
			partsOnLane.remove(0);
		}
//		else if (cmd.equals("TESTING_LANE")) {
//			System.out.println("Got TESTING_LANE request");
//			initializeV0Lane();
//		}
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
	 * Turns lane on or off
	 * @param on
	 *            - on/off switch for this lane
	 */
	public void toggleSwitch(boolean on) {
		laneOn = on;
		server.sendData(new Request(Constants.LANE_TOGGLE_COMMAND,
				Constants.LANE_TARGET + laneID, laneOn));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Sets lane location depending on ID assigned
	 * 
	 * @param id
	 *            - id of this lane
	 */
	private void setLaneLoc(int id) {

		switch (id) {
		case 0:
			laneLoc = new Location(LANE_BEG_X, LANE0_Y);
			break;
		case 1:
			laneLoc = new Location(LANE_BEG_X, LANE1_Y);
			break;
		case 2:
			laneLoc = new Location(LANE_BEG_X, LANE2_Y);
			break;
		case 3:
			laneLoc = new Location(LANE_BEG_X, LANE3_Y);
			break;
		case 4:
			laneLoc = new Location(LANE_BEG_X, LANE4_Y);
			break;
		case 5:
			laneLoc = new Location(LANE_BEG_X, LANE5_Y);
			break;
		case 6:
			laneLoc = new Location(LANE_BEG_X, LANE6_Y);
			break;
		case 7:
			laneLoc = new Location(LANE_BEG_X, LANE7_Y);
			break;
		default:
			System.out.println("LGD: ID not recognized.");
		}
	}

	
//	//GET RID OF FOR V1
//	public void initializeV0Lane() {
//		//feederAgent.thisLaneAgent(laneAgent);
//		laneAgent.msgINeedPart(new PartType("A"));
//		laneAgent.msgINeedPart(PartType.A);
//		laneAgent.msgINeedPart(PartType.A);
//		laneAgent.msgINeedPart(PartType.A);
//		laneAgent.msgINeedPart(PartType.A);
//		laneAgent.msgINeedPart(PartType.A);
//		
//	}
	
	
}
