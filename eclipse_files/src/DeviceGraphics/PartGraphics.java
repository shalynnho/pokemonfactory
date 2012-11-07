package DeviceGraphics;

import java.io.Serializable;

import Utils.Location;
import agent.data.PartType;

public class PartGraphics implements Serializable {
	PartType partType;
	Location partLocation;
	
	// PartType????
	public PartGraphics (PartType type) {
		System.out.println("made part with part type");
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
