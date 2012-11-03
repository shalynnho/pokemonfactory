package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

import Utils.Location;

public class KitGraphicsDisplay extends DeviceGraphicsDisplay {
	
	Location kitLocation;
	
	int xCoordinate;
	int yCoordinate;
	
	//NEED IMAGE NAMES
	Image kitImage = Toolkit.getDefaultToolkit().getImage("PUT IMAGE NAME HERE");

	
	public void setLocation (Location newLocation) {
		kitLocation = newLocation;
		xCoordinate = kitLocation.getX();
		yCoordinate = kitLocation.getY();
	}

	public void draw(JFrame myJFrame, Graphics2D g) {
		g.drawImage(kitImage, xCoordinate, yCoordinate, myJFrame);
		
	}

	
}
