package agent.interfaces;

import agent.GantryAgent;
import agent.LaneAgent;
import agent.data.Part;
import agent.data.PartType;
import GraphicsInterfaces.FeederGraphics;

public interface Feeder {

	public abstract void msgINeedPart(PartType type);

	public abstract void msgHereAreParts(Part p);

	public abstract void msgGivePartToDiverterDone(Part part);

	public abstract void msgGivePartToLaneDone(Part part);

	public abstract boolean pickAndExecuteAnAction();

	public abstract void getParts(PartType requestedType);

	public abstract void giveToDiverter(Part part);

	public abstract void giveToLane(Part part);

	//GETTERS AND SETTERS
	public abstract void setGantry(GantryAgent gantry);

	public abstract void setLane(LaneAgent lane);

}