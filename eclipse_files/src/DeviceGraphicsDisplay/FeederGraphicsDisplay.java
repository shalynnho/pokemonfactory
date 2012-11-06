package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;
import factory.data.PartType;

/**
 * This class handles drawing of the feeder and diverter.
 * @author Harry Trieu, Aaron Harris
 *
 */

public class FeederGraphicsDisplay extends DeviceGraphicsDisplay {
	// this will store a reference to the client
	private Client client;
	// part image dimensions
	private static final int FEEDER_HEIGHT = 120;
	private static final int FEEDER_WIDTH = 120;
	// true if the diverter is pointing to the top lane
	private boolean diverterTop;
	// true if a bin has been received
	private boolean haveBin;
	// location of the feeder
	private Location feederLocation;
	
	// v0 stuff
	private BinGraphicsDisplay bgd; 
	private ArrayList<PartGraphicsDisplay> partGDList = new ArrayList<PartGraphicsDisplay>();

	/**
	 * constructor
	 */
	public FeederGraphicsDisplay(Client cli, Location loc) {
		// store a reference to the client
		client = cli;
		
		// set the feeder's default location
		feederLocation = loc;
		
		// diverter initially points to the top lane
		diverterTop = true;
		
		haveBin = false;
		
		// force an initial repaint to display feeder and diverter
		client.repaint();
	}
	
	/**
	 * this function handles drawing
	 */
	public void draw(JComponent c, Graphics2D g) {
		g.drawImage(Constants.FEEDER_IMAGE, feederLocation.getX(), feederLocation.getY(), c);
		
		if (diverterTop) {
			g.drawImage(Constants.LANE_LED_IMAGE, feederLocation.getX()+20, feederLocation.getY()+20, c);
		}
		
		if (haveBin) {
			bgd.draw(c, g);
		}
	}
	
	@Override
	public void setLocation(Location newLocation) {
		// unused for feeder
	}

	@Override
	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.FEEDER_FLIP_DIVERTER_COMMAND)) {
			diverterTop = !diverterTop;
			
		} else if (req.getCommand().equals(Constants.FEEDER_RECEIVED_BIN_COMMAND)) {
			// TODO fix bin coordinates
			
			bgd = new BinGraphicsDisplay(new Location(feederLocation.getX() + FEEDER_WIDTH - 50, feederLocation.getY() + FEEDER_HEIGHT/2), PartType.B);
			bgd.setFull(true);
			haveBin = true;
			
		} else if (req.getCommand().equals(Constants.FEEDER_PURGE_BIN_COMMAND)) {
			// TODO future: move bin to purge area
			
			if (haveBin) {
				bgd.setFull(false); // could be problematic if called when bin has not been received
				haveBin = false;
			}
			
		} else if (req.getCommand().equals(Constants.FEEDER_MOVE_TO_DIVERTER_COMMAND)) {
			PartGraphicsDisplay part = new PartGraphicsDisplay(bgd.getPartType());
			
			// where the part starts
			// part.setLocation(startingPartLocation);
			// partGDList.add(part);
			
			client.sendData(new Request(Constants.FEEDER_MOVE_TO_DIVERTER_COMMAND + Constants.DONE_SUFFIX, Constants.FEEDER_TARGET, null));
		} else if (req.getCommand().equals(Constants.FEEDER_MOVE_TO_LANE_COMMAND)) {
			
			client.sendData(new Request(Constants.FEEDER_MOVE_TO_LANE_COMMAND + Constants.DONE_SUFFIX, Constants.FEEDER_TARGET, null));
		}
	}
}
