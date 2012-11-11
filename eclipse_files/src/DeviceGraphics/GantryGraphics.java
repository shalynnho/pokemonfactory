package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.data.Bin;

public class GantryGraphics implements DeviceGraphics {
	
	BinGraphics heldBin; // Bin that gantry is carrying
	private Server server;
	ArrayList<BinGraphics> initialBins;

	public GantryGraphics(Server s) {
		heldBin = null;
		server = s;
		
		// TODO Find out correct name for constant array list
		for (int i = 0; i < Constants.ARRAY_LIST_OF_PART_TYPES.size(); i ++) {
			initialBins.add(new BinGraphics(new Bin(Constants.ARRAY_LIST_OF_PART_TYPES.get(i)), i));
		}
	}
	
	public void receiveBin(Bin newBin) {
		heldBin = newBin.binGraphics;
		server.sendData(new Request(Constants.GANTRY_ROBOT_GET_BIN_COMMAND, Constants.GANTRY_ROBOT_TARGET, heldBin));
	}
	
	public void removeBin(Bin newBin) {
		heldBin = newBin.binGraphics;
		moveTo (Constants.BIN_STORAGE_LOC);
	}
	
	public void dropBin (Bin newBin) {
		heldBin = newBin.binGraphics;
		server.sendData(new Request(Constants.GANTRY_ROBOT_DROP_BIN_COMMAND, Constants.GANTRY_ROBOT_TARGET, heldBin));
	}
		
	
	public void moveTo (Location newLocation) {
		server.sendData(new Request(Constants.GANTRY_ROBOT_MOVE_TO_LOC_COMMAND, Constants.GANTRY_ROBOT_TARGET, newLocation));
	}
	
	@Override
	public void receiveData(Request req) {
		// TODO Auto-generated method stub
		
	}

}
