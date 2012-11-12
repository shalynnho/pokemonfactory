package agent.interfaces;

import java.util.List;

import factory.KitConfig;

import agent.data.Kit;
import agent.data.Part;


public interface PartsRobot {

	public abstract void msgHereIsKitConfiguration(KitConfig config);

	public abstract void msgHereAreGoodParts(Nest n, List<Part> goodparts);

	public abstract void msgUseThisKit(Kit k);

	public abstract void msgPickUpPartDone();

	public abstract void msgGivePartToKitDone();

	public abstract boolean pickAndExecuteAnAction();

}
