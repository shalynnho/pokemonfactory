package DeviceGraphicsDisplay;

import java.awt.Graphics2D;

import javax.swing.JComponent;

import Networking.Request;
import Utils.Constants;
import Utils.Location;

public class GantryGraphicsDisplay extends DeviceGraphicsDisplay {
	
	Location currentLocation;
	Location destinationLocation;
	
	BinGraphicsDisplay heldBin;

	public GantryGraphicsDisplay () {
		currentLocation = Constants.GANTRY_ROBOT_LOC;
	}
	
	@Override
	public void draw(JComponent c, Graphics2D g) {
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

	@Override
	public void receiveData(Request req) {
		if (req.getCommand() == Constants.GANTRY_ROBOT_GET_BIN_COMMAND) {
			// FIGURE OUT HOW TO DO THIS PART
		}
		else if (req.getCommand() == Constants.GANTRY_ROBOT_MOVE_TO_LOC_COMMAND) {
			destinationLocation = (Location) req.getData();
		}
	}

	@Override
	public void setLocation(Location newLocation) {
		destinationLocation = newLocation;
		
	}

}
