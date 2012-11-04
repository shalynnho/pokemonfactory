package factory.interfaces;

import factory.data.Part;
import factory.data.PartType;

public interface Nest {

	//MESSAGES
	public abstract void msgHereIsPartType(PartType type);

	public abstract void msgHereIsPart(Part p);

	public abstract void msgTakingPart(Part p);

	public abstract void msgDoneTakingParts();

	public abstract void msgReceivePartDone();

	public abstract void msgGivePartToPartsRobotDone();

	public abstract void msgPurgingDone();

	public abstract boolean pickAndExecuteAnAction();

	//ACTIONS
	public abstract void getParts(PartType requestedType);

	public abstract void moveToPosition(Part part);

	public abstract void nestFull();

	public abstract void updateParts();

}