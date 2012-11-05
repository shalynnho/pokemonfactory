package GraphicsInterfaces;

import DeviceGraphics.KitGraphics;

public interface KitRobotGraphics {

	public abstract void msgPlaceKitOnStand1(KitGraphics kit);
	public abstract void msgPlaceKitOnStand2(KitGraphics kit);
	public abstract void msgPlaceKitInInspectionArea(KitGraphics kit);
	public abstract void msgPlaceKitOnConveyor();
	
}
