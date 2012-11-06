package factory.data;

import DeviceGraphics.PartGraphics;

public class Part {

	public PartGraphics part;
	public PartType type;
	public boolean isGood;
	public boolean up;

	public Part() {
		isGood = true;
		up=true;
	}

	public Part(PartType type) {
		this.type = type;
		isGood = true;
		up=true;
	}
	
	public void flipDirection() {
		up=!up;
	}

}
