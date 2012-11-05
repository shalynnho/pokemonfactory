package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Utils.Location;

import factory.data.PartType;

public class KitGraphics extends DeviceGraphics {
	
	ArrayList<PartGraphics> parts; // parts currently in the kit
	ArrayList<PartType> partTypes; // part types required to make kit
	Location kitLocation;
	
	Boolean isFull; //Says whether or not the kit is full

	// ***********
	public KitGraphics () {
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


	@Override
	public void receiveData(Request req) {
		if (req.getCommand().equals("Testing")) {
			addPart(new PartGraphics (PartType.A));
		}
		
	}
	
}
