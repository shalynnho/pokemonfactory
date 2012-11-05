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
	ArrayList<KitGraphicsDisplay> kitsOnConveyor;
	int velocity;
	int counter;
	Client client;
	
	
	public ConveyorGraphicsDisplay(Client cli, Location loc) {
		location = loc;
		client = cli;
		conveyorLines = new ArrayList<Location>();
		for (int i = 0; i < 10; i++){
			conveyorLines.add(new Location(location.getX(), i*40));   //creating an array list of conveyor line locations for painting
		}
		velocity = 1;
		counter = 0;
		kitsOnConveyor = new ArrayList<KitGraphicsDisplay>();
	}
	
	public void setLocation(Location newLocation) {
		location = newLocation;		
	}
	
	public void newKit() {
			KitGraphicsDisplay temp = new KitGraphicsDisplay();
			temp.setLocation(new Location(0,0));
			kitsOnConveyor.add(temp);
	}
	
	public void draw(JComponent c, Graphics2D g2){
		g2.drawImage(Constants.CONVEYOR_IMAGE, location.getX(), location.getY(), c);
		counter++;
		for(int i = 0; i < conveyorLines.size(); i++){
			g2.drawImage(Constants.CONVEYOR_LINES_IMAGE, conveyorLines.get(i).getX(), conveyorLines.get(i).getY(), c);
			conveyorMove(i);
		}
		
		for(int j = 0; j < kitsOnConveyor.size(); j++) {
			if (kitsOnConveyor.get(0).kitLocation.getY() < 200) {
				KitGraphicsDisplay tempKit = kitsOnConveyor.get(j);
				tempKit.draw(c,g2);
				Location temp = tempKit.kitLocation;
				tempKit.setLocation(new Location(temp.getX(), temp.getY() + velocity));
			} else {
				velocity = 0;
			}
		}
	}
	
	/**
	 * A conveyor lines movement function. Created to lessen the clutter in draw.
	 * 
	 * @param i
	 */
	public void conveyorMove(int i) {
		if(conveyorLines.get(i).getY() < 380){                                  //if bottom of black conveyor line is less than this y position
			conveyorLines.get(i).setY(conveyorLines.get(i).getY() + velocity);  //when a conveyor is done being painted, move the location for next repaint
		    }
			else if(conveyorLines.get(i).getY() >= 380){                        //if bottom of black conveyor line is greater than or equal to this y position
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
				kitsOnConveyor.get(0).kitLocation.getY();             
		} else if (command.equals(Constants.CONVEYOR_MAKE_NEW_KIT_COMMAND)) {
			newKit();
		} else if (command.equals(Constants.CONVEYOR_CHANGE_VELOCITY_COMMAND)) {
			//must take in int somehow
		} else if (command.equals(Constants.CONVEYOR_RECEIVE_KIT_COMMAND)) {
			//make this later
		}
	}
}
