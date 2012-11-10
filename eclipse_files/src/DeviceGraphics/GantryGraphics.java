package DeviceGraphics;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.data.Bin;

public class GantryGraphics implements DeviceGraphics {
	
	BinGraphics heldBin; // Bin that gantry is carrying
	private Server server;

	public GantryGraphics(Server s) {
		heldBin = null;
		server = s;
	}
	
	public void receiveBin(Bin newBin) {
		heldBin = newBin.binGraphics;
		server.sendData(new Request(Constants.GANTRY_ROBOT_GET_BIN_COMMAND, Constants.GANTRY_ROBOT_TARGET, heldBin));
	}
	
	public void removeBin(Bin newBin) {
		heldBin = newBin.binGraphics;
		moveTo (Constants.BIN_STORAGE_LOC);
	}
	
	public void moveTo (Location newLocation) {
		server.sendData(new Request(Constants.GANTRY_ROBOT_MOVE_TO_LOC_COMMAND, Constants.GANTRY_ROBOT_TARGET, newLocation));
	}
	
	@Override
	public void receiveData(Request req) {
		// TODO Auto-generated method stub
		
	}

}
