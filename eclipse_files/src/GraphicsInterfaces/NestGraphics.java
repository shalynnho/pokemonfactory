package GraphicsInterfaces;

import DeviceGraphics.PartGraphics;

public interface NestGraphics {

	public abstract void receivePart(PartGraphics part);
	public abstract void givePartToPartsRobot(PartGraphics part);
	public abstract void purge();
	
}
