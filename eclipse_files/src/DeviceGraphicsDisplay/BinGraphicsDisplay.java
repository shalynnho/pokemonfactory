package DeviceGraphicsDisplay;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

import Utils.Location;

public class BinGraphicsDisplay extends DeviceGraphicsDisplay {
	
	//NEED IMAGE NAMES
	Image fullBin = Toolkit.getDefaultToolkit().getImage("PUT IMAGE NAME HERE");
	Image emptyBin = Toolkit.getDefaultToolkit().getImage("PUT IMAGE NAME HERE");
	
	Location binLocation;
	
	int xCoordinate;
	int yCoordinate;
	
	Boolean isFull;
	
	public void setLocation (Location newLocation) {
		binLocation = newLocation;
		xCoordinate = binLocation.getX();
		yCoordinate = binLocation.getY();
	}
	
	public void draw (JFrame myJFrame, Graphics2D g) {
		if (isFull)
			g.drawImage(fullBin, xCoordinate, yCoordinate, myJFrame);
		else
			g.drawImage(emptyBin, xCoordinate, yCoordinate, myJFrame);
	}
	
	public void setFull (Boolean full) {
		isFull = full;
	}
}
