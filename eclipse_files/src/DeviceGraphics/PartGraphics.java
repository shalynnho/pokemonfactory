package DeviceGraphics;

import java.io.Serializable;

import factory.PartType;

import Utils.Location;

public class PartGraphics implements Serializable {
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
