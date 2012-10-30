package factory.interfaces;

import factory.data.Kit;

public interface Conveyor {

	public abstract void msgNeedKit();

	public abstract void msgTakeKitAway(Kit k);

	public abstract void msgBringEmptyKitDone();

	public abstract void msgGiveKitToKitRobotDone();

	public abstract void msgReceiveKitDone();

	public abstract String getName();

	public abstract boolean pickAndExecuteAnAction();

}
