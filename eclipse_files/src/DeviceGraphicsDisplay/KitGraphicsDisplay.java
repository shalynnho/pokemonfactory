package DeviceGraphicsDisplay;

import java.awt.Graphics2D;

import javax.swing.JComponent;

import Networking.Request;
import Utils.Constants;
import Utils.Location;

public class KitGraphicsDisplay extends DeviceGraphicsDisplay {
	
	Location kitLocation;

	public KitGraphicsDisplay () {
		
	}

	public void setLocation (Location newLocation) {
		kitLocation = newLocation;
	}
	
	public Location getLocation () {
		return kitLocation;
	}

	public void draw(JComponent c, Graphics2D g) {
		g.drawImage(Constants.KIT_IMAGE, kitLocation.getX(), kitLocation.getY(), c);
		
	}

	public void receiveData(Request req) {
		
	}

	
}
