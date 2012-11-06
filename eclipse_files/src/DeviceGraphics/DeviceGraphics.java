package DeviceGraphics;

import Networking.Request;
import Networking.Server;
import Utils.Location;

/**
 * The abstract class that supports all other DeviceGraphics.
 * 
 * @author Peter Zhang
 */
public interface DeviceGraphics {
	public abstract void receiveData(Request req);
}
