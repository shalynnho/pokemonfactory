package DeviceGraphics;

import factory.PartType;
import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.Agent;
import agent.FeederAgent;

/**
 * This class handles the logic for the feeder animation.
 * @author Harry Trieu
 *
 */
public class FeederGraphics implements GraphicsInterfaces.FeederGraphics, DeviceGraphics {
	// a reference to the server
	private Server server;
	// the feeder's unique ID
	private int feederID;
	// a reference to the FeederAgent
	private FeederAgent feederAgent;
	// true if the diverter is pointing to the top lane
	private boolean diverterTop;
	// a bin
	private BinGraphics binGraphics;
	// location of the feeder
	private Location feederLocation;
	
	/**
	 * This is the constructor.
	 * @param id the unique ID of the feeder (there will be 4 feeders so we need to uniquely identify them)
	 * @param myServer a reference to the Server
	 */
	public FeederGraphics(int id, Server myServer, Agent a) {
		feederID = id;
		
		// System.out.println("Feeder created! ID: " + feederID);
		
		server = myServer;
		feederAgent = (FeederAgent)a;
		feederLocation = new Location(Constants.FEEDER_LOC);
		feederLocation.incrementY(id*Constants.FEEDER_Y_STEP);
		
		// System.out.println("Feeder ID: " + feederID + " | X: " + feederLocation.getX() + " | Y: " + feederLocation.getY());
		
		// diverter defaults to the top lane
		diverterTop = true;
	}
	
	/**
	 * This function receives a bin.
	 * @param bg BinGraphics passed in by the Agent
	 */
	public void receiveBin(BinGraphics bg) {
		binGraphics = bg;
		PartType type = bg.getPart().getPartType();
		server.sendData(new Request(Constants.FEEDER_RECEIVED_BIN_COMMAND, Constants.FEEDER_TARGET + feederID, type));
		
		System.out.println("[FEEDER]: Received a bin.");
	}
	
	/**
	 * This function purges the bin.
	 * @param bg
	 */
	public void purgeBin(BinGraphics bg) {
		bg.setFull(false);
		server.sendData(new Request(Constants.FEEDER_PURGE_BIN_COMMAND, Constants.FEEDER_TARGET + feederID, null));
		
		System.out.println("[FEEDER]: Bin purged.");
	}
	
	/**
	 * This function flips the diverter.
	 */
	public void flipDiverter() {
		diverterTop = !diverterTop;
		server.sendData(new Request(Constants.FEEDER_FLIP_DIVERTER_COMMAND, Constants.FEEDER_TARGET + feederID, null));
		
		System.out.println("[FEEDER]: Diverter flipping.");
	}
	
	/**
	 * Returns the location of the feeder.
	 */
	public Location getLocation() {
		return feederLocation;
	}

	/**
	 * This function handles requests sent to the server
	 */
	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.FEEDER_RECEIVED_BIN_COMMAND + Constants.DONE_SUFFIX)) {
			feederAgent.msgReceiveBinDone(binGraphics.getBin());
		} else if (req.getCommand().equals(Constants.FEEDER_PURGE_BIN_COMMAND + Constants.DONE_SUFFIX)) {
			feederAgent.msgPurgeBinDone(binGraphics.getBin());
		} else if (req.getCommand().equals(Constants.FEEDER_FLIP_DIVERTER_COMMAND + Constants.DONE_SUFFIX)) {
			feederAgent.msgFlipDiverterDone();
		}
	}
}
