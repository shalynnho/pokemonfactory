package DeviceGraphics;

import java.util.ArrayList;

/**
 * 
 * @author Shalynn Ho
 *
 */
public class LaneGraphics extends DeviceGraphics {
	// max number of parts that can be on a Lane
	private static final int MAX_PARTS;
	// start and end locations of Part on the Lane
	private static final Location PART_START, PART_END;
	
	// instructions to display graphics will be sent through the server
	private Server server;
	// the ID of this Lane
	private int laneID;
	// the Nest associated with this LaneGraphics object
	private Nest nest;
	
	// dynamically stores Parts currently on Lane
	private ArrayList<PartGraphics> partsOnLane;
	// number of parts currently on Lane
	private int numPartsOnLane;
	// vibration setting; how quickly parts vibrate down Lane
	
	private int amplitude;
	// true if Lane is on
	private boolean laneOn;
	
	
	
	public LaneGraphics(Server server, int laneID, Nest nest) {
		
	}
	
	
	public void receivePart(PartGraphics pg) {
		
	}
	
	
	public void givePartToNest(PartGraphics pg) {
		
	}

	
	public void purge() {
		
	}
	
	
	public void setAmplitude(int amp) {
		
	}
	
	
	public boolean isFull() {
		return null;
	}
	
	
	public void toggleSwitch(boolean on) {
		
	}
	
	
	public void receiveData(Request r) {
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
