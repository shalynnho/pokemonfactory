package Utils;

import java.io.Serializable;

import factory.PartType;

public class PartData implements Serializable {
    Location loc;
    PartType pt;
    Location kitloc;
    boolean isBad;

    public PartData(Location l, PartType p) {
	loc = l;
	pt = p;
    }

    public PartData(Location l) {
	kitloc = l;
    }

    public Location getKitLocation() {
	return kitloc;
    }

    public Location getLocation() {
	return loc;
    }

    public PartType getPartType() {
	return pt;
    }

    public boolean getBad() {
	return isBad;
    }
}
