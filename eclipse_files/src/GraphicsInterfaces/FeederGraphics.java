package GraphicsInterfaces;

import DeviceGraphics.BinGraphics;
import DeviceGraphics.PartGraphics;

public interface FeederGraphics {

	public abstract void receiveBin(BinGraphics bin);
	public abstract void purgeBin(BinGraphics bin);
	public abstract void movePartToDiverter(PartGraphics part);
	public abstract void flipDiverter();
	public abstract void movePartToLane(PartGraphics part);
	
}
