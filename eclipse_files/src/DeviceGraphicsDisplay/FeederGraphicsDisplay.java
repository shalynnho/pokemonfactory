package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;

/**
 * This class handles drawing of the feeder and diverter.
 * @author Harry Trieu
 *
 */

public class FeederGraphicsDisplay extends DeviceGraphicsDisplay {
	Client client; // this will store a reference to the client
	
	private static final int FEEDER_HEIGHT = 120;
	private static final int FEEDER_WIDTH = 120;
	private static final int DIVERTER_HEIGHT = 40;
	private static final int DIVERTER_WIDTH = 120;
	
	private Image diverterImage; // image of the diverter
	private Image feederImage; // image of the feeder
	
	private boolean diverterPosition;
	
	Location feederLocation; // location of the feeder
	Location diverterLocation; // location of the diverter
	
	/**
	 * constructor
	 */
	public FeederGraphicsDisplay(Client cli, Location loc) {
		client = cli; // store a reference to the client
		
		diverterImage = Toolkit.getDefaultToolkit().getImage("src/images/Diverter.png"); // set the path of the diverter image
		feederImage = Toolkit.getDefaultToolkit().getImage("src/images/Feeder.png"); // set the path of the feeder image
		
		feederLocation = loc; // set the feeder's location
		diverterLocation = new Location(feederLocation.getX()-90, feederLocation.getY()+(FEEDER_HEIGHT/2)-(DIVERTER_HEIGHT/2)); // set the diverter's location
		
		diverterPosition = true;
		
		// TODO rotate diverter to default to top lane
				
		client.repaint();
	}
	
	@Override
	public void draw(JComponent c, Graphics2D g) {
		AffineTransform originalTransform = g.getTransform();
		
		g.rotate(0.09, diverterLocation.getX(), diverterLocation.getY());
		g.drawImage(diverterImage, diverterLocation.getX(), diverterLocation.getY(), c);
		
		g.setTransform(originalTransform);
		
		g.drawImage(feederImage, feederLocation.getX(), feederLocation.getY(), c);
		
		
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
