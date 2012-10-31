package GraphicsInterfaces;

import DeviceGraphics.BinGraphics;

public interface GantryGraphics {

	public abstract void receiveBin(BinGraphics bin);
	public abstract void dropBin(BinGraphics bin, FeederGraphics feeder);
	public abstract void removeBin(BinGraphics bin);
	
}
