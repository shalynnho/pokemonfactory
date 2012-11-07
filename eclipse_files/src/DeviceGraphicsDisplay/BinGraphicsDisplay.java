package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;

import agent.data.PartType;


import Networking.Request;
import Utils.Constants;
import Utils.Location;

public class BinGraphicsDisplay extends DeviceGraphicsDisplay {
		
	// TODO temp fullBin image name. change later
	// TODO need an empty bin image name
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
			g.drawImage(Constants.BIN_IMAGE, binLocation.getX(), binLocation.getY(), c);
		else
			g.drawImage(emptyBin, binLocation.getX(), binLocation.getY(), c);
	}
	
	public void setFull (Boolean full) {
		isFull = full;
	}

	public void receiveData(Request req) {
		
	}
}
