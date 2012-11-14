package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;
import factory.PartType;

/**
 * @author Vansh Jain, Shalynn Ho, Harry Trieu
 * 
 */
public class NestGraphicsDisplay extends DeviceGraphicsDisplay {	
	// max number of parts this Nest holds
	private static final int MAX_PARTS = 8;
	// width and height of the nest
	private static final int NEST_WIDTH = 75; 
	private static final int NEST_HEIGHT = 70;
	// width and height of a part
	private static final int PART_WIDTH = 20, PART_HEIGHT = 21;
	
	// the LaneManager (client) which talks to the Server
	private Client client;
	// the id of this nest
	private int nestID;
	
	// location of the nest
	private Location nestLocation;
	
	private boolean animate;
	
	//boolean if the nest is full
	private boolean isFull;
	// dynamically stores the parts currently in the Nest
	private ArrayList<PartGraphicsDisplay> partsInNest = new ArrayList<PartGraphicsDisplay>();

	/**
	 * Default constructor
	 */
	public NestGraphicsDisplay(Client c, int id) {
		client = c;
		nestID = id;
		animate =false; 
		isFull = true;
		nestLocation = new Location(485 - NEST_WIDTH, 45 + nestID * 75);
		
//		 TODO may need to remove temp later today - cannot create a random PartType
		for (int i = 0; i < 8; i++) {
			PartGraphicsDisplay temp = new PartGraphicsDisplay(Constants.DEFAULT_PARTTYPES.get(0));
			if(i < 4) {
				temp.setLocation(new Location((nestLocation.getX()+i*20),(nestLocation.getY()+1)));
			} else {
				temp.setLocation(new Location((nestLocation.getX()+(i-4)*20),(nestLocation.getY()+23))); 
			}
			partsInNest.add(temp);
		}
	}
	
	public void receivePart(PartGraphicsDisplay p) {
		
	}
	
//	public void givePartToPartsRobot(PartGraphicsDisplay) {		
//	}
	
	public void purge() {
		
	}
	
	private void movePartsUp() {
		
	}

	/**
	 * Handles drawing of NestGraphicsDisplay objects
	 */
	public void draw(JComponent c, Graphics2D g) {		
		g.drawImage(Constants.NEST_IMAGE, nestLocation.getX(), nestLocation.getY(), c);
		for(PartGraphicsDisplay part : partsInNest) {
			part.draw(c,g);
		}
	}

	/**
	 * Processes requests targeted at the NestGraphicsDisplay
	 */
	public void receiveData(Request req) {
		
		if (req.getCommand().equals(Constants.NEST_RECEIVE_PART_COMMAND)) {
			if(partsInNest.size() >= MAX_PARTS) {
				// TODO should this be a message back to the server?
					// NOTE: according to the agents, this should never happen anyway
				System.out.println("Nest is full");
			} else {
				PartType type = (PartType) req.getData();
				PartGraphicsDisplay temp = new PartGraphicsDisplay(type);
				addPartToCorrectLocation(temp, partsInNest.size());
				//choosing the correct location of the part
				partsInNest.add(temp);
			}
			client.sendData(new Request(Constants.NEST_RECEIVE_PART_COMMAND + Constants.DONE_SUFFIX, Constants.NEST_TARGET+nestID, null));
			
		} else if (req.getCommand().equals(Constants.NEST_GIVE_TO_PART_ROBOT_COMMAND)) {
			partsInNest.remove(0);	// TODO: later might need to animate this
			updateLocationOfParts(partsInNest);
			client.sendData(new Request(Constants.NEST_GIVE_TO_PART_ROBOT_COMMAND + Constants.DONE_SUFFIX, Constants.NEST_TARGET+nestID, null));
		
		} else if (req.getCommand().equals(Constants.NEST_PURGE_COMMAND)) {
			//TODO: (later) animate parts out of nest
			for(int i = 0; i < partsInNest.size(); i++){
				partsInNest.remove(0);
			}
			client.sendData(new Request(Constants.NEST_PURGE_COMMAND + Constants.DONE_SUFFIX, Constants.NEST_TARGET+nestID, null));
		}	
	}

	/**
	 * Allows another object to set the NestGraphicsDisplay location
	 */
	public void setLocation(Location newLocation) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * add part to the correct location
	 * @param temp
	 * @param i
	 */
	public void addPartToCorrectLocation(PartGraphicsDisplay temp, int i){
		if(i < 4) {
			temp.setLocation(new Location((nestLocation.getX()+i*20),(nestLocation.getY()+1)));
		} else {
			temp.setLocation(new Location((nestLocation.getX()+(i-4)*20),(nestLocation.getY()+23))); 
		}
	}
	
	/**
	 * update location of the parts
	 * @param x
	 */
	public void updateLocationOfParts(ArrayList<PartGraphicsDisplay> x){
		for(int i=0; i<x.size(); i++){
			addPartToCorrectLocation(x.get(i),i);
		}
	}
	
}
