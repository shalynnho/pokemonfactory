package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.Agent;
import agent.FeederAgent;
import agent.data.Bin;

public class GantryGraphics implements DeviceGraphics {
	
	Bin heldBin; // Bin that gantry is carrying
	private Server server;
	ArrayList<BinGraphics> initialBins;
	
	Boolean removeState = false;
	Boolean receiveState = false;
	Boolean dropState = false;
	
	Agent gantryAgent;

	public GantryGraphics(Server s, Agent ga) {
		heldBin = null;
		server = s;
		gantryAgent = ga;
		
		/*// TODO Find out correct name for constant array list
		for (int i = 0; i < Constants.ARRAY_LIST_OF_PART_TYPES.size(); i ++) {
			initialBins.add(new BinGraphics(new Bin(Constants.ARRAY_LIST_OF_PART_TYPES.get(i)), i));
		}*/
	}
	
	// get bin from feeder
	public void receiveBin(Bin newBin, FeederAgent feeder) {
		heldBin = newBin;
		server.sendData(new Request(Constants.GANTRY_ROBOT_GET_BIN_COMMAND, Constants.GANTRY_ROBOT_TARGET, heldBin));
		moveTo(feeder.feederGUI.getLocation());
		receiveState = true;
	}
	
	// take off screen
	public void removeBin(Bin newBin) {
		heldBin = newBin;
		moveTo (heldBin.binGraphics.getLocation());
		removeState = true;
	}
	
	// drop bin into feeder
	public void dropBin (Bin newBin, FeederAgent feeder) {
		heldBin = newBin;
		server.sendData(new Request(Constants.GANTRY_ROBOT_DROP_BIN_COMMAND, Constants.GANTRY_ROBOT_TARGET, heldBin));
		moveTo(feeder.feederGUI.getLocation());
		dropState = true;
	}
		
	
	private void moveTo (Location newLocation) {
		server.sendData(new Request(Constants.GANTRY_ROBOT_MOVE_TO_LOC_COMMAND, Constants.GANTRY_ROBOT_TARGET, newLocation));
	}
	
	@Override
	public void receiveData(Request req) {
		if (req.getCommand() == Constants.GANTRY_ROBOT_DONE_MOVE) {
			if (receiveState) {
				gantryAgent.msgReceiveBinDone(heldBin);
			}
	}

}
