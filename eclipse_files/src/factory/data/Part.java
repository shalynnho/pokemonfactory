package factory.data;

import DeviceGraphics.PartGraphics;

public class Part {

	public PartGraphics part;
	public PartType type;
	public boolean isGood = true;
	
	public Part(){
		
	}
	
	public Part(PartType type){
		this.type=type;
	}

	
	
}
