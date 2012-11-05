package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;

import factory.data.PartType;

import Networking.Request;
import Utils.Location;

public class BinGraphicsDisplay extends DeviceGraphicsDisplay {
	
	//NEED IMAGE NAMES
	
	// TODO temp fullBin image name. change later
	Image fullBin = Toolkit.getDefaultToolkit().getImage("src/images/samplebin.png");
	Image emptyBin = Toolkit.getDefaultToolkit().getImage("PUT IMAGE NAME HERE");
	
	Location binLocation;
	PartType partType;
	
	Boolean isFull;
	
	public BinGraphicsDisplay (Location newLocation, PartType pt) {
		binLocation = newLocation;
		partType = pt;
	}
	
	public void setLocation (Location newLocation) {
		binLocation = newLocation;
	}
	
	public Location getLocation () {
		return binLocation;
	}
	
	public PartType getPartType() {
		return partType;
	}
	
	public void draw (JComponent c, Graphics2D g) {
		if (isFull)
			g.drawImage(fullBin, binLocation.getX(), binLocation.getY(), c);
		else
			g.drawImage(emptyBin, binLocation.getX(), binLocation.getY(), c);
	}
	
	public void setFull (Boolean full) {
		isFull = full;
	}

	public void receiveData(Request req) {
		
	}
}
