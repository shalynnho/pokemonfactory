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
	private static final Location PART_END = new Location(,);
	
	// instructions to display graphics will be sent through the server
	private Server server;
	// the ID of this Lane
	private int laneID;
	// the Nest associated with this LaneGraphics object
	private NestGraphics nest;
	
	// dynamically stores Parts currently on Lane
	private ArrayList<PartGraphics> partsOnLane;
	
	// number of parts currently on Lane
	private int numPartsOnLane = 0;
	// vibration setting; how quickly parts vibrate down Lane
	private int amplitude;
	
	// true if Lane is on
	private boolean laneOn;
	
	
	public LaneGraphics(Server s, int id, NestGraphics n) {
		server = s;
		laneID = id;
		nest = n;
		
		partsOnLane = new ArrayList<PartGraphics>();
		amplitude = (WHAT IS DEFALUT AMP?);
		laneOn = true;
	}
	
	
	public void receivePart(PartGraphics pg) {
		partsOnLane.add(pg);
	}
	
	
	public void givePartToNest(PartGraphics pg) {
		
	}

	
	public void purge() {
		
	}
	
	
	public void setAmplitude(int amp) {
		amplitude = amp;
	}
	
	
	public boolean isFull() {
		return partsOnLane.size() == MAX_PARTS;
	}
	
	
	public void toggleSwitch(boolean on) {
		laneOn = on;
	}
	
	
	public void receiveData(Request r) {
		// must parse data request here
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
