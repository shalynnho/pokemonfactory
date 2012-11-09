package agent.interfaces;

import agent.data.Bin;
import agent.data.PartType;

public interface Gantry {

	public abstract void msgHereIsBinConfig(Bin bin);

	public abstract void msgINeedParts(PartType type);

	public abstract void msgReceiveBinDone(Bin bin);

	public abstract void msgDropBinDone(Bin bin);

	public abstract void msgRemoveBinDone(Bin bin);

	public abstract boolean pickAndExecuteAnAction();

	public abstract void moveToFeeder(Bin bin);

	public abstract void fillFeeder(Bin bin);

	public abstract void discardBin(Bin bin);

}