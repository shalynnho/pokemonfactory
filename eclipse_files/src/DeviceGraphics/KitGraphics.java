package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.data.PartType;


public class KitGraphics implements DeviceGraphics {
	
	ArrayList<PartGraphics> parts = new ArrayList<PartGraphics>(); // parts currently in the kit
	ArrayList<PartType> partTypes; // part types required to make kit
	Location kitLocation;
	
	Boolean isFull; //Says whether or not the kit is full
	Server server;
	
	public KitGraphics (Server server) {
		this.server = server;
		isFull = false;
	}
	
	
	/**
	 * set the part types required to build kit
	 * 
	 * @param kitDesign - parts required to build the kit
	 */
	public void setPartTypes(ArrayList<PartType> kitDesign) {
		partTypes = kitDesign;
	}
	
	
	public void addPart (PartGraphics newPart) {
		parts.add(newPart);
		
		if ((parts.size() % 2) == 1) {
			newPart.getLocation().setX(kitLocation.getX() + 5);
			newPart.getLocation().setY(kitLocation.getY() + (20 * (parts.size() -1) / 2));
		}
		else {
			newPart.getLocation().setX(kitLocation.getX() + 34);
			newPart.getLocation().setY(kitLocation.getY() + (20 * parts.size() / 2));
		}		
		
		if (parts.size() == 8) {
			parts.clear();
		}
	}
	
	
	public void setLocation (Location newLocation) {
		kitLocation = newLocation;
	}
	
	
	public Location getLocation () {
		return kitLocation;
	}
	
	//If true, set isFull boolean to true
	public void setFull (Boolean full) {
		isFull = full;
	}
	
	public Boolean getFull () {
		return isFull;
	}
	
	public void receivePart(PartGraphics part) {
		System.out.println("hello2");
		addPart(part);
		server.sendData(new Request(Constants.KIT_UPDATE_PARTS_LIST_COMMAND, Constants.KIT_TARGET, parts));
	}

	@Override
	public void receiveData(Request req) {
		if (req.getCommand().equals("Testing")) {
			addPart(new PartGraphics (PartType.A));
		}
	}
	
}
