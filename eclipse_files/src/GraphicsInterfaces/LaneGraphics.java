package GraphicsInterfaces;

import agent.data.Part;
import DeviceGraphics.PartGraphics;

public interface LaneGraphics {

	public abstract void receivePart(Part part);
	public abstract void givePartToNest(Part part);
	public abstract void purge();
	
}
