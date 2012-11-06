package factory.interfaces;

import java.util.List;

import DeviceGraphics.PartGraphics;

import factory.data.Kit;
import factory.data.Part;
import factory.NestAgent;

public interface Camera {

	public abstract void msgInspectKit(Kit kit);
	public abstract void msgIAmFull(Nest nest);
	public abstract void msgTakePictureNestDone(List<Part> parts, Nest nest);
	public abstract void msgTakePictureKitDone(Kit kit, boolean done);
	
	public abstract boolean pickAndExecuteAnAction();
}
