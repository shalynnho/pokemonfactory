package GraphicsInterfaces;

import DeviceGraphics.PartGraphics;
import factory.data.Part;

public interface LaneGraphics {

	public abstract void receivePart(Part part);
	public abstract void givePartToNest(Part part);
	public abstract void purge();
	
}
