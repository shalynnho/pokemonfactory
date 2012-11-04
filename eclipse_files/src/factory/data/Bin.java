package factory.data;

import java.util.ArrayList;

import factory.FeederAgent;
import factory.interfaces.Feeder;

public class Bin {
	public DeviceGraphics.BinGraphics bin;
	public Part part;
	public FeederAgent feeder;
	public enum BinStatus {
		FULL, MOVING, OVER_FEEDER, FILLING_FEEDER, EMPTY, DISCARDING
	};
	public BinStatus binState = BinStatus.FULL;
}
