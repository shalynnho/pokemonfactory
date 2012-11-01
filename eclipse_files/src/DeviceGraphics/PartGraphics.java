package DeviceGraphics;

import Utils.Location;
import factory.data.PartType;

public class PartGraphics {
	PartType partType;
	Location partLocation;
	
	// PartType????
	public void PartGraphics (PartType type) {
		partType = type;
	}
	
	
	public void setLocation (Location newLocation) {
		partLocation = newLocation;
	}
	
	
	public Location getLocation () {
		return partLocation;
	}
	
	public PartType getPartType () {
		return partType;
	}
	
}
