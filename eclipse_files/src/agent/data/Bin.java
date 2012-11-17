package agent.data;

import factory.PartType;
import DeviceGraphics.BinGraphics;
import agent.FeederAgent;

public class Bin {
	public BinGraphics binGraphics;
	public Part part;
	public FeederAgent feeder;

	public enum BinStatus {
		PENDING, FULL, MOVING, OVER_FEEDER, FILLING_FEEDER, EMPTY, DISCARDING
	};

	public BinStatus binState = BinStatus.PENDING;

	public Bin() {

	}
	
	public Bin(Part part, int BinNum) {
		binGraphics= new BinGraphics(this,BinNum);
		this.part = part;
	}

	public Bin(PartType type, int BinNum) {
		binGraphics= new BinGraphics(this,BinNum);
		this.part = new Part(type);
	}
}
