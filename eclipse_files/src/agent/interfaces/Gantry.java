package agent.interfaces;

import agent.FeederAgent;
import agent.data.Bin;
import agent.data.PartType;

public interface Gantry {

	public abstract void msgHereIsBinConfig(Bin bin);

	public abstract void msgINeedParts(PartType type, FeederAgent feeder);

	public abstract void msgReceiveBinDone(Bin bin);

	public abstract void msgDropBinDone(Bin bin);

	public abstract void msgRemoveBinDone(Bin bin);

	public abstract boolean pickAndExecuteAnAction();

	public abstract void moveToFeeder(Bin bin, FeederAgent feeder);

	public abstract void fillFeeder(Bin bin, FeederAgent feeder);

	public abstract void discardBin(Bin bin);

}