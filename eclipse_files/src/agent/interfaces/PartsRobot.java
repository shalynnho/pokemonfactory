package agent.interfaces;

import java.util.List;

import agent.data.Kit;
import agent.data.Part;
import agent.data.PartType;


public interface PartsRobot {

	public abstract void msgHereIsKitConfiguration(List<PartType> config);

	public abstract void msgHereAreGoodParts(Nest n, List<Part> goodparts);

	public abstract void msgUseThisKit(Kit k);

	public abstract void msgPickUpPartDone();

	public abstract void msgGivePartToKitDone();

	public abstract boolean pickAndExecuteAnAction();

}
