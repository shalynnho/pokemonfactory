package DeviceGraphics;

import Networking.Request;
import Networking.Server;

import Utils.Constants;
import Utils.Location;

public class FeederGraphics extends DeviceGraphics  {
	static final int PARTS_LOW_THRESHOLD = 2;

	private Server server;
	private Location location;
	private int feederID;
	private int partsFed;
	private int partsRemaining;
	// BinGraphics binGraphics;
	// boolean partLow;
	
	/**
	 * 
	 * @param id the unique ID of the feeder (there will be 4 feeders so we need to uniquely identify them)
	 * @param myServer a reference to the Server
	 */
	public FeederGraphics(int id, Server myServer) {
		id = feederID;
		server = myServer;
		partsFed = 0;

		// partsRemaining
		
		// TODO edit location coordinates later
		location = new Location(200, 100*feederID);
		
		// TODO send message to FeederGraphicsDisplay
		server.sendData(new Request(Constants.FEEDER_INIT_GRAPHICS, Constants.FEEDER_TARGET, location));
		
	}
	
	void receiveBin(BinGraphics bg) {
		
	}
	
	void purgeBin(BinGraphics bg) {
		
	}
	
	boolean isPartLow() {
		return (partsRemaining < PARTS_LOW_THRESHOLD);
	}
	
	void movePartToLane() {
		
	}
	
	void movePartToDiverter(PartGraphics pg) {
		
	}
	
	void flipDiverter() {
		
	}
	
	

}
