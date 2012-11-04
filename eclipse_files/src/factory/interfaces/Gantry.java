package factory.interfaces;

import factory.data.Bin;
import factory.data.PartType;

public interface Gantry {

	public abstract void msgHereIsBinConfig(Bin bin);

	public abstract void msgINeedParts(PartType type);

	public abstract void msgreceiveBinDone(Bin bin);

	public abstract void msgdropBinDone(Bin bin);

	public abstract void msgremoveBinDone(Bin bin);

	public abstract boolean pickAndExecuteAnAction();

	public abstract void moveToFeeder(Bin bin);

	public abstract void fillFeeder(Bin bin);

	public abstract void discardBin(Bin bin);

}