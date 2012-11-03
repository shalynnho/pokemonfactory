package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

import Utils.Location;

public abstract class PartGraphicsDisplay extends DeviceGraphicsDisplay {
	Location partLocation;
	
	int xCoordinate;
	int yCoordinate;
	
	//NEED IMAGE NAMES
	Image partImage = Toolkit.getDefaultToolkit().getImage("PUT IMAGE NAME HERE");

	
	public void setLocation (Location newLocation) {
		partLocation = newLocation;
		xCoordinate = partLocation.getX();
		yCoordinate = partLocation.getY();
	}

	public void draw(JFrame myJFrame, Graphics2D g) {
		g.drawImage(partImage, xCoordinate, yCoordinate, myJFrame);
		
}
