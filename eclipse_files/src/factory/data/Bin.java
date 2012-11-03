package factory.data;

import java.util.ArrayList;

import factory.FeederAgent;

public class Bin {
	public DeviceGraphics.BinGraphics bin;
	public PartType type;
	public FeederAgent feeder;
	public enum BinStatus {
		FULL, MOVING, OVER_FEEDER, FILLING_FEEDER, EMPTY, DISCARDING
	};
	public BinStatus binState = BinStatus.FULL;
}
