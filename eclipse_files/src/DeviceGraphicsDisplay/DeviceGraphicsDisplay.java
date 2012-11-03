package DeviceGraphicsDisplay;

import java.awt.Graphics2D;

import javax.swing.JFrame;

import Networking.Client;
import Networking.Request;
import Utils.Location;

/**
 * An abstract class that every DeviceGraphicsDisplay extends from.
 * 
 * @author Peter Zhang
 */
public abstract class DeviceGraphicsDisplay {
	
	Client client;
	Location location;

	/**
	 * Override this method to draw out your component.
	 */
	public abstract void draw (JFrame myJFrame, Graphics2D g);
	
	/**
	 * Override this method so to process any requests sent from Server. Will be called by Client as Requests arrive.
	 */
	public abstract void receiveData(Request req);
	
	public abstract void setLocation (Location newLocation);
}
