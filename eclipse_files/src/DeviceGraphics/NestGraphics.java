package DeviceGraphics;

import Networking.*;
import GraphicsInterfaces.*;
import Utils.*;

import java.util.*;

public class NestGraphics extends DeviceGraphics {
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
	
	public NestGraphics(Server s, int id, PartsRobotGraphics p) {
		server = s;
		nestID = id;
		partsRobot = p;
		
		partsInNest = new ArrayList<PartGraphics>();
		nestSpots = new ArrayList<Boolean>();
	}

	public void receivePart(PartGraphics p) {
		
	}
	
	public void givePartToPartsRobot(PartGraphics p) {
		
	}
	
	public void purge() {
		
	}
	
	public void receiveData(Request r) {
		
	}
	
	public boolean isFull() {
		
	}
	
	public boolean allPartsUnanalyzed() {
		
	}
	
	public boolean allPartsGood() {
		
	}
	
	public boolean allPartsBad() {
		
	}
	
	public Location getLocation() {
		
	}

	public ArrayList<PartGraphics> getPartsInNest() {
		
	}
	
	public Map<PartGraphics,Boolean> getQualityOfParts() {
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
