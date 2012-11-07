package agent.interfaces;

import agent.data.Part;
import agent.data.PartType;
import DeviceGraphics.PartGraphics;
import GraphicsInterfaces.LaneGraphics;

public interface Lane {

	public abstract void msgINeedPart(PartType type);

	public abstract void msgHereIsPart(Part p);

	public abstract void msgReceivePartDone(PartGraphics part);

	public abstract void msgGivePartToNestDone(PartGraphics part);

	public abstract boolean pickAndExecuteAnAction();

	public abstract void getParts(PartType requestedType);

	public abstract void giveToNest(Part part);

}