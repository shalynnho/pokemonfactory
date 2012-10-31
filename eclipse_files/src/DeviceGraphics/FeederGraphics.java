package DeviceGraphics;

import Utils.Location;
import Networking.Server;

public class FeederGraphics extends DeviceGraphics  {
	static final int partsLowThreshold = 5;

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
		
	}
	
	void receiveBin(BinGraphics bg) {
		
	}
	
	void purgeBin(BinGraphics bg) {
		
	}
	
	boolean isPartLow() {
		if (partsRemaining < partsLowThreshold)
			return (true);
		else
			return (false);
	}
	
	void movePartToLane() {
		
	}
	
	void movePartToDiverter(PartGraphics pg) {
		
	}
	
	void flipDiverter() {
		
	}
	
	

}
