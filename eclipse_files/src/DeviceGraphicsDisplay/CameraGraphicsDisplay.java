package DeviceGraphicsDisplay;

import java.awt.Graphics2D;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;

/**
 * Client-side Camera object
 * 
 * @author Peter Zhang
 */
public class CameraGraphicsDisplay extends DeviceGraphicsDisplay{
	
	private int flashOn = -1;
	
	public CameraGraphicsDisplay(Client c, Location loc) {
		client = c;
		location = loc;
	}

	@Override
	public void draw(JComponent c, Graphics2D g) {
		if(flashOn >= 0) {
			g.drawImage(Constants.CAMERA_IMAGE, location.getX(), location.getY(), c);
			flashOn--;
		}
	}

	@Override
	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.CAMERA_TAKE_NEST_PHOTO_COMMAND)) {
			location = new Location(50, 50); 
			flashOn = 3;
		} else if (req.getCommand().equals(Constants.CAMERA_TAKE_NEST_PHOTO_COMMAND)) {
			location = new Location(100, 100);
			flashOn = 3;
		}
	}

	@Override
	public void setLocation(Location newLocation) {
		location = newLocation;
	}

}
