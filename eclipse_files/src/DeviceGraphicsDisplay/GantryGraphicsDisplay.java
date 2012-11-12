package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.data.Bin;

public class GantryGraphicsDisplay extends DeviceGraphicsDisplay {
	
	Location currentLocation;
	Location destinationLocation;
	
	ArrayList<BinGraphicsDisplay> initialBins;
	static int initialBinsXLocation = 500; //TODO find correct number
	static int initialBinsYLocation = 50; //TODO find correct number
	
	BinGraphicsDisplay heldBin;
	
	Bin tempBin;
	
	Server server;

	public GantryGraphicsDisplay (Server s) {
		currentLocation = Constants.GANTRY_ROBOT_LOC;
		server = s;
		
		/*// TODO Find out correct name for constant array list
		for (int i = 0; i < Constants.ARRAY_LIST_OF_PART_TYPES.size(); i ++){
			initialBins.add(new BinGraphicsDisplay(new Location (initialBinsXLocation, initialBinsYLocation + i * 50), Constants.ARRAY_LIST_OF_PART_TYPES.get(i)));
		}*/
	}
	
	@Override
	public void draw(JComponent c, Graphics2D g) {
		
		// If robot is at incorrect Y location, first move bot to inital X location
		if (currentLocation.getY() != destinationLocation.getY()) {
			if(currentLocation.getX() < Constants.GANTRY_ROBOT_LOC.getX()) {
				currentLocation.incrementX(5);
				if (heldBin != null) {
					heldBin.setLocation(currentLocation);
				}
			}
			else if(currentLocation.getX() > Constants.GANTRY_ROBOT_LOC.getX()) {
				currentLocation.incrementX(-5);
				if (heldBin != null) {
					heldBin.setLocation(currentLocation);
				}
			}
		}
		
		//If robot is in initial X, move to correct Y
		if(currentLocation.getX() == Constants.GANTRY_ROBOT_LOC.getX()) {
			if(currentLocation.getY() < destinationLocation.getY()) {
				currentLocation.incrementY(5);
				if (heldBin != null) {
					heldBin.setLocation(currentLocation);
				}
			}
			if(currentLocation.getY() > destinationLocation.getY()) {
				currentLocation.incrementY(-5);
				if (heldBin != null) {
					heldBin.setLocation(currentLocation);
				}
			}
		}
		
		//If robot is at correct Y, move to correct X
		if (currentLocation.getY() == destinationLocation.getY()) {
			if(currentLocation.getX() < destinationLocation.getX()) {
				currentLocation.incrementX(5);
				if (heldBin != null) {
					heldBin.setLocation(currentLocation);
				}
			}
			else if(currentLocation.getX() > destinationLocation.getX()) {
				currentLocation.incrementX(-5);
				if (heldBin != null) {
					heldBin.setLocation(currentLocation);
				}
			}
		
			if(currentLocation.getX() == destinationLocation.getX()) {
				server.sendData(new Request(Constants.GANTRY_ROBOT_DONE_MOVE, Constants.GANTRY_ROBOT_TARGET, null));
			}
		}
		heldBin.draw(c, g);
	}

	@Override
	public void receiveData(Request req) {
		if (req.getCommand() == Constants.GANTRY_ROBOT_GET_BIN_COMMAND) {
			tempBin = (Bin) req.getData();
			heldBin = new BinGraphicsDisplay(currentLocation, tempBin.part.type);
			heldBin.setFull(tempBin.binGraphics.getFull());
		}
		else if (req.getCommand() == Constants.GANTRY_ROBOT_MOVE_TO_LOC_COMMAND) {
			destinationLocation = (Location) req.getData();
		}
		else if (req.getCommand() == Constants.GANTRY_ROBOT_DROP_BIN_COMMAND) {
			heldBin = null;
		}
	}

	@Override
	public void setLocation(Location newLocation) {
		destinationLocation = newLocation;
		
	}

}
