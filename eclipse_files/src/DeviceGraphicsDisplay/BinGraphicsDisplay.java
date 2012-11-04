package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;

import Networking.Request;
import Utils.Location;

public class BinGraphicsDisplay extends DeviceGraphicsDisplay {
	
	//NEED IMAGE NAMES
	Image fullBin = Toolkit.getDefaultToolkit().getImage("PUT IMAGE NAME HERE");
	Image emptyBin = Toolkit.getDefaultToolkit().getImage("PUT IMAGE NAME HERE");
	
	Location binLocation;
	
	Boolean isFull;
	
	public BinGraphicsDisplay (Location newLocation) {
		binLocation = newLocation;
	}
	
	public void setLocation (Location newLocation) {
		binLocation = newLocation;
	}
	
	public Location getLocation () {
		return binLocation;
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
