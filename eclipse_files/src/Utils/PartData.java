package Utils;


import java.io.Serializable;

import factory.PartType;

public class PartData implements Serializable{

	Location loc;
	PartType pt;
	
	public PartData(Location l, PartType p){
		loc = l;
		pt = p;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public PartType getPartType(){
		return pt;
	}
}
