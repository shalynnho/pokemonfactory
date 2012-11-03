package DeviceGraphicsDisplay;

import java.awt.Graphics2D;

import javax.swing.JFrame;

import Utils.Location;

public abstract class DeviceGraphicsDisplay {

	public abstract void draw (JFrame myJFrame, Graphics2D g);
		
	public abstract void setLocation (Location newLocation);
}
