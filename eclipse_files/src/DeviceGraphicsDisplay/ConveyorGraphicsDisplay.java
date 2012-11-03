package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import Utils.Location;

public class ConveyorGraphicsDisplay {

	Location location;
	Image conveyorImage;
	
	public void setLocation(Location newLocation){
		location = newLocation;
		try{
			conveyorImage = ImageIO.read(new File("src/images/Conveyor.jpg"));
			}
			catch(IOException e){
				
			}
	}
	
	public void draw(JFrame jf, Graphics2D g2){
		g2.drawImage(conveyorImage, location.getX(), location.getY(), jf);
	}
}
