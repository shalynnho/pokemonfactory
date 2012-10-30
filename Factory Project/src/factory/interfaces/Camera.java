package factory.interfaces;

import java.util.List;

import factory.Kit;
import factory.NestAgent;

public interface Camera {

	public abstract void msgInspectKit(Kit kit);
	public abstract void msgIAmFull(NestAgent nest);
	public abstract void msgTakePictureNestDone(List<GUIPart> parts, NestAgent nest);
	public abstract void msgTakePictureKitDone(boolean done);
	
	public abstract boolean pickAndExecuteAnAction();
}
