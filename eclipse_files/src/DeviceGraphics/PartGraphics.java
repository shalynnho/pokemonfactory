package DeviceGraphics;

import Utils.Location;
import agent.data.PartType;

public class PartGraphics {
	PartType partType;
	Location partLocation;

	// PartType????
	public PartGraphics(PartType type) {
		partType = type;
	}

	public void setLocation(Location newLocation) {
		partLocation = newLocation;
	}

	public Location getLocation() {
		return partLocation;
	}

	public PartType getPartType() {
		return partType;
	}

}
