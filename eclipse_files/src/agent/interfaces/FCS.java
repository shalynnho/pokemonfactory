package agent.interfaces;

import java.util.ArrayList;

import agent.data.PartType;

public interface FCS {

	public abstract void msgAddKitsToQueue(ArrayList<PartType> parts,int numOfKits);
	
	public abstract void msgStopMakingKit(ArrayList<PartType> parts);
	
	public abstract void msgStartProduction();
	
	public abstract void msgOrderFinished();
	
}
