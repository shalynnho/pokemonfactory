package Utils;

import java.io.Serializable;

import factory.PartType;

public class PartData implements Serializable {
    Location loc;
    PartType pt;
    Location kitloc;
    boolean isBad;
    int Arm;

    public PartData(Location l, PartType p, int arm) {
	loc = l;
	pt = p;
	Arm = arm;
    }

    public PartData(Location l, int arm) {
	kitloc = l;
	Arm = arm; 
    }
    
    public PartData(PartType p, boolean isBad) {
    	pt = p;
    	this.isBad=isBad;
    }
    
    public PartData(PartType parttype, int arm) {
    	pt = parttype;
    	Arm = arm;
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
    
    public int getArm() {
    	return Arm;
    }
    
}
