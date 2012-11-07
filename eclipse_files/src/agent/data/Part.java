package agent.data;

import DeviceGraphics.PartGraphics;

public class Part {

	public PartGraphics partGraphics;
	public PartType type;
	public boolean isGood;
	public boolean up;

	/*public Part() {
		isGood = true;
		up=true;
	}*/

	public Part(PartType type) {
		partGraphics= new PartGraphics(type);
		this.type = type;
		isGood = true;
		up=true;
	}
	
	public void flipDirection() {
		up=!up;
	}

	
}
