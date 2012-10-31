package GraphicsInterfaces;

import DeviceGraphics.PartGraphics;

public interface LaneGraphics {

	public abstract void receivePart(PartGraphics part);
	public abstract void givePartToNest(PartGraphics part);
	public abstract void purge();
	
}
