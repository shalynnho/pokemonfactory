package DeviceGraphics;

import til.Location;
import Networking.Server;

public class FeederGraphics extends DeviceGraphics  {
	Server server;
	Location location;
	int feederID;
	int partsFed;
	// BinGraphics binGraphics;
	// boolean partLow;
	
	/**
	 * 
	 * @param id
	 * @param myServer
	 */
	FeederGraphics(int id, Server myServer) {
		id = feederID;
		server = myServer;
		partsFed = 0;
		
		// TODO edit location coordinates later
		location = new Location(200, 100*feederID);
		
		// TODO send message to FeederGraphicsDisplay
		
	}
	
	void movePartToLane(Part) {
		
	}
	
	void flipDiverter() {
		
	}
	
	

}
