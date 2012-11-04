package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;

/**
 * Contains display components of ConveyorGraphics object
 * 
 * @author neetugeo
 */

public class ConveyorGraphicsDisplay extends DeviceGraphicsDisplay {

	Location location;
	ArrayList<Location> conveyorLines;
	int velocity;
	Client client;
	
	
	public ConveyorGraphicsDisplay(Client cli, Location loc) {
		location = loc;
		client = cli;
		conveyorLines = new ArrayList<Location>();
		for (int i = 0; i < 10; i++){
			conveyorLines.add(new Location(location.getX(), i*40));   //creating an array list of conveyor line locations for painting
		}
		velocity = 1;
	}
	
	public void setLocation(Location newLocation) {
		location = newLocation;		
	}
	
	public void draw(JComponent c, Graphics2D g2){
		g2.drawImage(Constants.CONVEYOR_IMAGE, location.getX(), location.getY(), c);
		for(int i = 0; i < conveyorLines.size(); i++){
			g2.drawImage(Constants.CONVEYOR_LINES_IMAGE, conveyorLines.get(i).getX(), conveyorLines.get(i).getY(), c);
			conveyorMove(i);
		}
	}
	
	/**
	 * A conveyor lines movement function. Created to lessen the clutter in draw.
	 * 
	 * @param i
	 */
	public void conveyorMove(int i) {
		if(conveyorLines.get(i).getY() < 383){                                  //if bottom of black conveyor line is less than this y position
			conveyorLines.get(i).setY(conveyorLines.get(i).getY() + velocity);  //when a conveyor is done being painted, move the location for next repaint
		    }
			else if(conveyorLines.get(i).getY() >= 383){                        //if bottom of black conveyor line is greater than or equal to this y position
				conveyorLines.get(i).setY(0);
			}
	}
	
	/**
	 * Function created to change the velocity of the conveyor
	 * 
	 * @param i
	 */
	public void setVelocity(int i) {
		velocity = i;
	}

	@Override
	public void receiveData(Request req) {
		String command = req.getCommand();
		String target = req.getTarget();
		Object object = req.getData();
		
		if (command.equals(Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND)) {
			if (object != null) {
				KitGraphicsDisplay kgd = (KitGraphicsDisplay)object;
				if (kgd.kitLocation.getY() >= 200) {                          // let it be known that 200 is the stop value
					velocity = 0;                                             // conveyor stops for kit pick up
				}                                                             // Kit robot should look for KIT location, not some point on conveyor             
			}
		}
		
		else if (command.equals(Constants.CONVEYOR_CHANGE_VELOCITY_COMMAND)) {
			//must take in int somehow
		}
		
		else if (command.equals(Constants.CONVEYOR_RECEIVE_KIT_COMMAND)) {
			if(object != null) {
				KitGraphicsDisplay kgd = (KitGraphicsDisplay)object;
				kgd.setLocation(new Location(0,300));
				
				// NOTE: eventually need to make a function for conveyorgraphics display or kitgraphics display
				//       that tells the server or relevant party that a kit has left the factory so conveyor
				//       can get rid of the reference.
			}
		}
	}
}
