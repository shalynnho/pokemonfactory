package agent.interfaces;

import factory.PartType;
import agent.CameraAgent;
import agent.LaneAgent;
import agent.data.Part;
import GraphicsInterfaces.NestGraphics;

public interface Nest {

	public NestGraphics guiNest = null;

	//MESSAGES
	public abstract void msgHereIsPartType(PartType type);

	public abstract void msgHereIsPart(Part p);

	public abstract void msgTakingPart(Part p);

	public abstract void msgDoneTakingParts();

	public abstract void msgReceivePartDone();

	public abstract void msgGivePartToPartsRobotDone();

	public abstract void msgPurgingDone();

	public abstract boolean pickAndExecuteAnAction();

}