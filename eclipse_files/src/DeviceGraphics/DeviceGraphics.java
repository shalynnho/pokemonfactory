package DeviceGraphics;

import Networking.Request;
import Networking.Server;
import Utils.Location;

/**
 * The abstract class that supports all other DeviceGraphics.
 * 
 * @author Peter Zhang
 */
public abstract class DeviceGraphics {
	private Server server;
	private Location location;
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public abstract void receiveData(Request req);
}
