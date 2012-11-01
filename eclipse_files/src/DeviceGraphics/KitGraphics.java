package DeviceGraphics;

import java.util.ArrayList;

import Utils.Location;

import factory.data.PartType;

public class KitGraphics {
	
	ArrayList<PartGraphics> parts; // parts currently in the kit
	ArrayList<PartType> partTypes; // part types required to make kit
	Location kitLocation;

	// ***********
	public void KitGraphics () {
	
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
	}
	
	
	public void setLocation (Location newLocation) {
		kitLocation = newLocation;
	}
	
	
	public Location getLocation () {
		return kitLocation;
	}
}
