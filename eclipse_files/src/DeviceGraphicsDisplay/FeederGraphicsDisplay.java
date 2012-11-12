package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;
import agent.data.PartType;

/**
 * This class handles drawing of the feeder and diverter.
 * @author Harry Trieu, Aaron Harris
 *
 */

public class FeederGraphicsDisplay extends DeviceGraphicsDisplay {
	// this will store a reference to the client
	private Client client;
	// feeder image dimensions
	private static final int FEEDER_HEIGHT = 120;
	private static final int FEEDER_WIDTH = 120;
	// true if the diverter is pointing to the top lane
	private boolean diverterTop;
	// true if a bin has been received
	private boolean haveBin;
	// location of the feeder
	private Location feederLocation;
	// a BinGraphicsDisplay object
	private BinGraphicsDisplay bgd;
	// ID of the feeder
	private int feederID;

	/**
	 * constructor
	 */
	public FeederGraphicsDisplay(Client cli, int id) {
		// store a reference to the client
		client = cli;	
		// diverter initially points to the top lane
		diverterTop = true;
		// we don't have a bin to start with
		haveBin = false;
		// set the feeder's ID
		feederID = id;
		// generate the feeder's location based on its ID
		feederLocation = new Location(500, id*2 + 150);
		
		// force an initial repaint to display feeder and diverter
		// client.repaint();
		
		// set the feeder's default location
		// feederLocation = loc;
	}
	
	/**
	 * This function handles drawing of feeder components.
	 */
	public void draw(JComponent c, Graphics2D g) {
		g.drawImage(Constants.FEEDER_IMAGE, feederLocation.getX(), feederLocation.getY(), c);
		
		if (diverterTop) {
			g.drawImage(Constants.LANE_LED_IMAGE, feederLocation.getX()+32, feederLocation.getY()+7, c);
		}
		
		if (haveBin) {
			bgd.draw(c, g);
		}
	}
	
	/**
	 * Inherited method.
	 */
	public void setLocation(Location newLocation) {
		// unused for feeder
	}
	
	/**
	 * Display bin being received.
	 */
	public void receiveBin() {
		// TODO adjust bin location later
		bgd = new BinGraphicsDisplay(new Location(feederLocation.getX() + FEEDER_WIDTH - 50, feederLocation.getY() + FEEDER_HEIGHT/2), PartType.B);
		bgd.setFull(true);
		haveBin = true;
	}

	/**
	 * Process requests sent to the device.
	 */
	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.FEEDER_FLIP_DIVERTER_COMMAND)) {
			diverterTop = !diverterTop;
			client.sendData(new Request(Constants.FEEDER_FLIP_DIVERTER_COMMAND + Constants.DONE_SUFFIX, Constants.FEEDER_TARGET + feederID , null));
		} else if (req.getCommand().equals(Constants.FEEDER_RECEIVED_BIN_COMMAND)) {
			receiveBin();
			// TODO figure out how to interface with gantry
			haveBin = true;
			client.sendData(new Request(Constants.FEEDER_RECEIVED_BIN_COMMAND + Constants.DONE_SUFFIX, Constants.FEEDER_TARGET + feederID , null));
		} else if (req.getCommand().equals(Constants.FEEDER_PURGE_BIN_COMMAND)) {
			// TODO future: move bin to purge area
			
			if (haveBin) {
				bgd.setFull(false);
				haveBin = false;
			}
			
			client.sendData(new Request(Constants.FEEDER_PURGE_BIN_COMMAND + Constants.DONE_SUFFIX, Constants.FEEDER_TARGET + feederID , null));
		}
	}
}
