package agent.interfaces;

import agent.NestAgent;
import agent.data.Kit;

public interface Camera {

	public abstract void msgInspectKit(Kit kit);

	public abstract void msgIAmFull(Nest nest);

	/**
	 * For v0, nests will never have bad parts.
	 * @param nest nest that was photographed.
	 */
	public abstract void msgTakePictureNestDone(NestAgent nest);

	public abstract void msgTakePictureKitDone(Kit kit, boolean done);

	public abstract boolean pickAndExecuteAnAction();
}
