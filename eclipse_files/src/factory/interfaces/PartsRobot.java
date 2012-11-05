package factory.interfaces;

import java.util.*;

import DeviceGraphics.PartGraphics;

import factory.data.*;


public interface PartsRobot {

	public abstract void msgHereIsKitConfiguration(List<PartType> config);
	public abstract void msgHereAreGoodParts(Nest n,List<Part> goodparts);
	public abstract void msgUseThisKit(Kit k);
	public abstract void msgPickUpPartDone();
	public abstract void msgGivePartToKitDone();
	public abstract boolean pickAndExecuteAnAction();

}
