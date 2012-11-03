package DeviceGraphicsDisplay;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import Utils.Location;

public class ConveyorGraphicsDisplay {

	Location location;
	Image conveyorImage = Toolkit.getDefaultToolkit().getImage("Conveyor.jpg");
	
	public void setLocation(Location newLocation){
		location = newLocation;
	}
	
	public void draw(JFrame jf, Graphics2D g2){
		g2.drawImage(conveyorImage, location.getX(), location.getY(), jf);
	}
}
