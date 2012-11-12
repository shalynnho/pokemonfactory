package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;

/**
 * Contains display components of ConveyorGraphics object
 * @author neetugeo
 */

public class ConveyorGraphicsDisplay extends DeviceGraphicsDisplay {

	Location locationIn, locationOut;
	ArrayList<Location> conveyorLines;
	ArrayList<Location> exitLines;
	ArrayList<KitGraphicsDisplay> kitsOnConveyor;
	ArrayList<KitGraphicsDisplay> kitsToLeave;
	int velocity;
	Client client;
	boolean pickMe;

	public ConveyorGraphicsDisplay(Client cli) {
		locationIn = Constants.CONVEYOR_LOC; // location for input lane
		locationOut = new Location(locationIn.getX(), locationIn.getY() + 400); // location
																	// for exit
																	// lane,
																	// based off
																	// of input
																	// lane
		client = cli;
		conveyorLines = new ArrayList<Location>();
		for (int i = 0; i < 10; i++) {
			conveyorLines.add(new Location(locationIn.getX(), i * 40)); // creating
																		// an
																		// array
																		// list
																		// of
																		// conveyor
																		// line
																		// locations
																		// for
																		// painting
		}
		exitLines = new ArrayList<Location>();
		for (int i = 0; i < 20; i++) {
			exitLines.add(new Location(i * 40, locationOut.getY()));
		}
		velocity = 5;
		kitsOnConveyor = new ArrayList<KitGraphicsDisplay>();
		kitsToLeave = new ArrayList<KitGraphicsDisplay>();
		pickMe = true;
	}

	@Override
	public void setLocation(Location newLocation) {
		location = newLocation;
	}

	public void newKit() {
		print("Making new kit");
		KitGraphicsDisplay temp = new KitGraphicsDisplay(client);
		temp.setLocation(new Location(0, 0));
		kitsOnConveyor.add(temp);
	}

	public void giveKitAway() {
		System.out.println("Give it away");
		kitsOnConveyor.remove(0);
		velocity = 5;
		pickMe = true;
	}

	public void sendOut() {
		kitsToLeave.remove(0);
	}

	public void newExitKit() {
		KitGraphicsDisplay temp = new KitGraphicsDisplay(client);
		temp.setLocation(new Location(0,400));
		kitsToLeave.add(temp);
	}

	public void animationDone(Request r) {
		client.sendData(r);
	}

	@Override
	public void draw(JComponent c, Graphics2D g2) {
		g2.drawImage(Constants.CONVEYOR_IMAGE, locationIn.getX(),
				locationIn.getY(), c);

		for (int i = 0; i < conveyorLines.size(); i++) {
			g2.drawImage(Constants.CONVEYOR_LINES_IMAGE, conveyorLines.get(i)
					.getX(), conveyorLines.get(i).getY(), c);
			moveIn(i);
		}

		g2.drawImage(Constants.EXIT_IMAGE, locationOut.getX(),
				locationOut.getY(), c);

		for (int i = 0; i < exitLines.size(); i++) {
			g2.drawImage(Constants.EXIT_LINES_IMAGE, exitLines.get(i).getX(),
					exitLines.get(i).getY(), c);
			moveOut(i);
		}

		for (int j = 0; j < kitsOnConveyor.size(); j++) {
			if (kitsOnConveyor.get(0).getLocation().getY() < 200) {
				KitGraphicsDisplay tempKit = kitsOnConveyor.get(j);
				tempKit.draw(c, g2);
				Location temp = tempKit.getLocation();
				tempKit.setLocation(new Location(temp.getX(), temp.getY()
						+ velocity));
			} else if (kitsOnConveyor.get(0).getLocation().getY() >= 200){
				velocity = 0;
				KitGraphicsDisplay tempKit = kitsOnConveyor.get(j);
				tempKit.draw(c, g2);
				Location temp = tempKit.getLocation();
				tempKit.setLocation(new Location(temp.getX(), temp.getY()
						+ velocity));
				if (pickMe == true) {
				animationDone(new Request(
						Constants.CONVEYOR_MAKE_NEW_KIT_COMMAND
								+ Constants.DONE_SUFFIX,
						Constants.CONVEYOR_TARGET, null));
				
				//second test message
				animationDone(new Request(
						Constants.CONVEYOR_MAKE_NEW_KIT_COMMAND
								+ Constants.DONE_SUFFIX,
						Constants.CONVEYOR_TARGET, null));
				
				pickMe = false;
				}
			}
		}

		for (int i = 0; i < kitsToLeave.size(); i++) {
			KitGraphicsDisplay tempKit = kitsToLeave.get(i);
			tempKit.draw(c, g2);
			if (tempKit.getLocation().getX() == 800) {
				animationDone(new Request(
						Constants.CONVEYOR_RECEIVE_KIT_COMMAND
								+ Constants.DONE_SUFFIX,
						Constants.CONVEYOR_TARGET, null));
			}
			Location temp = tempKit.getLocation();
			tempKit.setLocation(new Location(temp.getX() + velocity, temp
					.getY()));
		}
	}

	/**
	 * A conveyor lines movement function. Created to lessen the clutter in
	 * draw.
	 * @param i
	 */
	public void moveIn(int i) {
		// if bottom of black conveyor line is less than this y position
		if (conveyorLines.get(i).getY() < 385) {
			// when a conveyor is done being painted, move the location for next
			// repaint
			conveyorLines.get(i).setY(conveyorLines.get(i).getY() + velocity);
		} else if (conveyorLines.get(i).getY() >= 385) {
			// if bottom of black conveyor line is greater than or equal to this
			// y position
			conveyorLines.get(i).setY(0);
		}
	}

	public void moveOut(int i) {
		if (exitLines.get(i).getX() < 800) {
			exitLines.get(i).setX(exitLines.get(i).getX() + 5);
		} else if (exitLines.get(i).getX() >= 793) {
			exitLines.get(i).setX(0);
		}
	}

	/**
	 * Function created to change the velocity of the conveyor
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
			giveKitAway();
		} else if (command.equals(Constants.CONVEYOR_MAKE_NEW_KIT_COMMAND)) {
			newKit();
		} else if (command.equals(Constants.CONVEYOR_CHANGE_VELOCITY_COMMAND)) {
			// must take in int somehow
		} else if (command.equals(Constants.CONVEYOR_RECEIVE_KIT_COMMAND)) {
			newExitKit();
		}
	}
}
