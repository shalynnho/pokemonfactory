package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;

public class FeederGraphicsDisplay extends DeviceGraphicsDisplay {
	Client client;
	
	private static final int FEEDER_HEIGHT = 120;
	private static final int FEEDER_WIDTH = 120;
	private static final int DIVERTER_HEIGHT = 40;
	private static final int DIVERTER_WIDTH = 120;
	
	private Image diverterImage;
	private Image feederImage;
	
	Location feederLocation;
	Location diverterLocation;
	
	/**
	 * constructor
	 */
	public FeederGraphicsDisplay(Client cli, Location loc) {
		client = cli;
		
		diverterImage = Toolkit.getDefaultToolkit().getImage("images/diverter.png");
		feederImage = Toolkit.getDefaultToolkit().getImage("images/feeder.png");
		
		feederLocation = loc; // set the feeder's location
		diverterLocation = new Location(feederLocation.getX()-15, feederLocation.getY()+((FEEDER_HEIGHT/2)+(DIVERTER_HEIGHT)/2)); // set the diverter's location
		
		// rotate diverter to default to top lane
		
		client.repaint();
	}
	
	@Override
	public void draw(JComponent c, Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(feederImage, feederLocation.getX(), feederLocation.getY(), c);
		g.drawImage(diverterImage, diverterLocation.getX(), diverterLocation.getY(), c);
		
		
	}

	@Override
	public void setLocation(Location newLocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.FEEDER_FLIP_DIVERTER_COMMAND)) {
			// TODO diverterImage.rotate
		}
	}

}
