package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;
import agent.data.Bin;

public class GantryGraphicsDisplay extends DeviceGraphicsDisplay {
	
	Location currentLocation;
	Location destinationLocation;
	
	ArrayList<BinGraphicsDisplay> binList;
	
	boolean isBinHeld = false;
	boolean isMoving = false;
	
	BinGraphicsDisplay heldBin;
	
	Bin tempBin;
	
	Client client;

	public GantryGraphicsDisplay (Client c) {
		currentLocation = Constants.GANTRY_ROBOT_LOC;
		destinationLocation = currentLocation;
		binList = new ArrayList<BinGraphicsDisplay>();
		client = c;
		
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
			}
			else if(currentLocation.getX() > Constants.GANTRY_ROBOT_LOC.getX()) {
				currentLocation.incrementX(-5);
			}
		}
		
		//If robot is in initial X, move to correct Y
		if(currentLocation.getX() == Constants.GANTRY_ROBOT_LOC.getX()) {
			if(currentLocation.getY() < destinationLocation.getY()) {
				currentLocation.incrementY(5);
			}
			if(currentLocation.getY() > destinationLocation.getY()) {
				currentLocation.incrementY(-5);
			}
		}
		
		//If robot is at correct Y, move to correct X
		if (currentLocation.getY() == destinationLocation.getY()) {
			if(currentLocation.getX() < destinationLocation.getX()) {
				currentLocation.incrementX(5);
			}
			else if(currentLocation.getX() > destinationLocation.getX()) {
				currentLocation.incrementX(-5);
			}
		
			if(currentLocation.getX() == destinationLocation.getX() && isMoving == true) {
				client.sendData(new Request(Constants.GANTRY_ROBOT_DONE_MOVE, Constants.GANTRY_ROBOT_TARGET, null));
				isMoving = false;
			}
		}
		
			for (int i = 0; i < binList.size(); i ++) {
				binList.get(i).draw(c, g);
			}
			
		if (isBinHeld) {
			heldBin.setLocation(currentLocation);
		}
		g.drawImage(Constants.GANTRY_ROBOT_IMAGE, currentLocation.getX(), currentLocation.getY(), c);
	}

	@Override
	public void receiveData(Request req) {
		if (req.getCommand() == Constants.GANTRY_ROBOT_GET_BIN_COMMAND) {
			tempBin = (Bin) req.getData();
			heldBin = new BinGraphicsDisplay(currentLocation, tempBin.part.type);
			heldBin.setFull(tempBin.binGraphics.getFull());
			isBinHeld = true;
			tempBin = null;
		}
		else if (req.getCommand() == Constants.GANTRY_ROBOT_MOVE_TO_LOC_COMMAND) {
			destinationLocation = (Location) req.getData();
			isMoving = true;
		}
		else if (req.getCommand() == Constants.GANTRY_ROBOT_DROP_BIN_COMMAND) {
			heldBin = null;
			isBinHeld = false;
		}
		else if (req.getCommand() == Constants.GANTRY_ROBOT_ADD_NEW_BIN) {
			tempBin = (Bin) req.getData();
			binList.add( new BinGraphicsDisplay(tempBin.binGraphics.getInitialLocation(), tempBin.part.type));
			isBinHeld = true;
			tempBin = null;
		}
	}

	@Override
	public void setLocation(Location newLocation) {
		destinationLocation = newLocation;
		
	}

}
