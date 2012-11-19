package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JComponent;

import Networking.Request;
import Utils.Location;
import factory.PartType;

public class PartGraphicsDisplay extends DeviceGraphicsDisplay {
	Location partLocation;
	PartType partType;
	
	private Image partImage;

	public PartGraphicsDisplay (PartType pt) {
		partType = pt;
		partImage = partType.getImage();
	}
	
	public void setLocation (Location newLocation) {
		partLocation = new Location(newLocation);
	}

	public void draw(JComponent c, Graphics2D g) {
		g.drawImage(partImage, partLocation.getX(), partLocation.getY(), c);
	}
	
	public void drawWithOffset(JComponent c, Graphics2D g, int offset) {
		g.drawImage(partImage, partLocation.getX() + offset, partLocation.getY(), c);
	}

	public Location getLocation () {
		return partLocation;
	}
	
	public PartType getPartType() {
		return partType;
	}
	
	public void receiveData(Request req) {
	}
	
}
