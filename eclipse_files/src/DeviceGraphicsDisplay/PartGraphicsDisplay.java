package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JComponent;

import Networking.Request;
import Utils.Constants;
import Utils.Location;
import factory.PartType;

public class PartGraphicsDisplay extends DeviceGraphicsDisplay {
	Location partLocation;
	
	//NEED IMAGE NAMES
	Image partImage;

	public PartGraphicsDisplay (PartType pt) {
		//String imageName = pt.toString();
		
		// v0 hardcoded image name for testing
	}
	
	public void setLocation (Location newLocation) {
		partLocation = newLocation;
	}

	public void draw(JComponent c, Graphics2D g) {
		g.drawImage(Constants.PART_IMAGE, partLocation.getX(), partLocation.getY(), c);
	}

	public Location getLocation () {
		return partLocation;
	}
	
	public void receiveData(Request req) {
	}
	
}
