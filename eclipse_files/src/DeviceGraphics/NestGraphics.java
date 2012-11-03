package DeviceGraphics;

import Networking.*;
import GraphicsInterfaces.*;
import Utils.*;
import factory.data.*;

import java.util.*;

/**
 * This class represents the graphics logic for a nest.
 * 
 * @author Shalynn Ho
 *
 */
public class NestGraphics extends DeviceGraphics implements GraphicsInterfaces.NestGraphics {
	// max number of parts this Nest holds
	private static final int MAX_PARTS;
	
	// instructions to display graphics will be sent through the server
	private Server server;
	// the ID of this Nest
	private int nestID;
	// the PartsRobot
	private PartsRobotGraphics partsRobot;
	
	// Location of upper left corner of this nest
	private Location location;
	// dynamically stores the parts currently in the Nest
	private ArrayList<PartGraphics> partsInNest;
	
	// true during Nest purge cycle, can't receive parts
	private boolean purging;
	// true if nest is full, can't receive parts
	private boolean isFull;
	// true if spot is filled, false if not
	private ArrayList<Boolean> nestSpots;
	
	public NestGraphics(Server s, int nid, PartsRobotGraphics pr) {
		server = s;
		nestID = nid;
		partsRobot = pr;
		
		partsInNest = new ArrayList<PartGraphics>();
		nestSpots = new ArrayList<Boolean>();
	}

	/**
	 * @param - 
	 */
	public void receivePart(Part p) {
		PartGraphics pg = p.part;

		
	}
	
	/**
	 * @param
	 */
	public void givePartToPartsRobot(Part p) {
		PartGraphics pg = p.part;

	}
	
	/**
	 * 
	 */
	public void purge() {
		
	}
	
	/**
	 * 
	 * @param r
	 */
	public void receiveData(Request r) {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isFull() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean allPartsUnanalyzed() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean allPartsGood() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean allPartsBad() {
		
	}
	
	/**
	 * 
	 */
	public Location getLocation() {
		
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<PartGraphics> getPartsInNest() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<PartGraphics,Boolean> getQualityOfParts() {
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
