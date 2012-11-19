package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.util.ArrayList;

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
	
	// locations to take pictures from
	private ArrayList<Location> locs = new ArrayList<Location>();
	
	public CameraGraphicsDisplay(Client c) {
		//TODO: No need for location
		client = c;
		location = Constants.CAMERA_LOC;
	}

	@Override
	public void draw(JComponent c, Graphics2D g) {
	    	if(flashOn >= 3) {
	    	    flashOn--;
	    	} else if(flashOn >= 0) {
			for(Location loc : locs) {
				g.drawImage(Constants.CAMERA_IMAGE, loc.getX() + client.getOffset(), loc.getY(), c);
			}
			flashOn--;
		} else {
			locs.clear();
		}
	}

	@Override
	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.CAMERA_TAKE_NEST_PHOTO_COMMAND)) {
			locs = (ArrayList<Location>) req.getData();
			flashOn = 10;
		} else if (req.getCommand().equals(Constants.CAMERA_TAKE_NEST_PHOTO_COMMAND)) {
			locs.add((Location) req.getData());
			flashOn = 10;
		}
	}

	@Override
	public void setLocation(Location newLocation) {
		location = newLocation;
	}

}
