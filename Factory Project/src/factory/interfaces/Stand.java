package factory.interfaces;

import factory.data.Kit;

public interface Stand {

	public abstract void msgShippedKit();

	public abstract void msgKitAssembled(Kit k);

	public abstract void msgMakeKits(int numKits);

	public abstract void msgHereIsKit(Kit k);

	public abstract String getName();

	public abstract boolean pickAndExecuteAnAction();

}
