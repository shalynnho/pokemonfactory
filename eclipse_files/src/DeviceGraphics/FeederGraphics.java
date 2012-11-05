package DeviceGraphics;

import java.util.ArrayList;

import factory.data.PartType;

import Networking.Request;
import Networking.Server;
import Utils.Constants;

/**
 * This class handles the logic for the feeder animation.
 * @author Harry Trieu
 *
 */

public class FeederGraphics extends DeviceGraphics implements GraphicsInterfaces.FeederGraphics {
	// TODO ask 201 team what should be the threshold
	private static final int PARTS_LOW_THRESHOLD = 2;
	
	// a reference to the server
	private Server server;
		
	// true if the diverter is pointing to the top lane
	private boolean diverterTop;
	
	// the feeder's unique ID
	private int feederID;
	
	// number of parts fed
	private int partsFed;
	
	// number of parts left to be fed
	private int partsRemaining;
	
	// a part
	private PartGraphics partGraphics;
	
	// a bin
	private BinGraphics binGraphics;
	
	// temp ArrayList of parts
	private ArrayList<PartGraphics> partList = new ArrayList<PartGraphics>();
	
	/**
	 * This is the constructor.
	 * @param id the unique ID of the feeder (there will be 4 feeders so we need to uniquely identify them)
	 * @param myServer a reference to the Server
	 */
	public FeederGraphics(int id, Server myServer) {
		id = feederID;
		server = myServer;
		partsFed = 0;
		partsRemaining = 0;
		
		// TODO diverter starts on the top lane
		diverterTop = true; // this means it is currently pointing at the top lane
	}
	
	/**
	 * This function receives a bin.
	 * @param bg BinGraphics passed in by the Agent
	 */
	public void receiveBin(BinGraphics bg) {
		partsRemaining = bg.getQuantity();
		partGraphics = bg.getPart();
				
		server.sendData(new Request(Constants.FEEDER_RECEIVED_BIN_COMMAND, Constants.FEEDER_TARGET, null));
		
		server.sendData(new Request(Constants.FEEDER_RECEIVED_BIN_COMMAND, Constants.LANE_TARGET + ":" + 0, null));
		server.sendData(new Request(Constants.FEEDER_RECEIVED_BIN_COMMAND, Constants.LANE_TARGET + ":" + 1, null));
	}
	
	
	/**
	 * This function purges the bin.
	 * @param bg
	 */
	public void purgeBin(BinGraphics bg) {
		partsFed = 0;
		partsRemaining = 0;
		
		server.sendData(new Request(Constants.FEEDER_PURGE_BIN_COMMAND, Constants.FEEDER_TARGET, null));
	}
	
	/**
	 * This function determines if the part is low.
	 * @return partsRemaining is less than PARTS_LOW_THRESHOLD
	 */
	public boolean isPartLow() {
		return (partsRemaining < PARTS_LOW_THRESHOLD);
	}
	
	/**
	 * This function moves a part to the diverter.
	 * @param pg
	 */
	public void movePartToDiverter(PartGraphics pg) {
		server.sendData(new Request(Constants.FEEDER_MOVE_TO_DIVERTER_COMMAND, Constants.FEEDER_TARGET, pg.getPartType()));
	}
	
	/**
	 * This function moves a part to the lane.
	 */
	public void movePartToLane(PartGraphics pg) {
		pg.getLocation().incrementX();
		
		partsRemaining--;
		partsFed++;
		
		// TODO who draws the part moving?  I change its coordinates
		// server.sendData(new Request(Constants.FEEDER_MOVE_TO_LANE, Constants.PART_TARGET + ":" + feederID, pg.getLocation()));
	}
	
	/**
	 * This function flips the diverter.
	 */
	public void flipDiverter() {
		diverterTop = !diverterTop;
		server.sendData(new Request(Constants.FEEDER_FLIP_DIVERTER_COMMAND, Constants.FEEDER_TARGET, null));
	}

	@Override
	public void receiveData(Request req) {
		// v0 test commands
		if (req.getCommand().equals(Constants.FEEDER_FLIP_DIVERTER_COMMAND)) {
			flipDiverter();
		} else if (req.getCommand().equals(Constants.FEEDER_RECEIVED_BIN_COMMAND)) {
			partGraphics = new PartGraphics(PartType.B);
			binGraphics = new BinGraphics(partGraphics, 10);
			receiveBin(binGraphics);
		} else if (req.getCommand().equals(Constants.FEEDER_MOVE_TO_DIVERTER_COMMAND)) {
			PartGraphics part = binGraphics.getPart();
			
			partList.add(part);
			
			movePartToDiverter(part);
		} else if (req.getCommand().equals(Constants.FEEDER_MOVE_TO_LANE_COMMAND)) {
			// partList.remove(0);
			
		}
	}
}
