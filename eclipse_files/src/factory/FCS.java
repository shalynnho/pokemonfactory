package factory;

import java.util.ArrayList;

import Networking.Request;
import Utils.Constants;
import agent.FCSAgent;

/**
 * Factory Control System. Hub for GUI controls to the factory operations. 
 * 
 * @author Peter Zhang
 */
public class FCS {
	private ArrayList<Order> orders = new ArrayList<Order>();
	private ArrayList<PartType> partTypes = (ArrayList<PartType>) Constants.DEFAULT_PARTTYPES.clone();
	
	FCSAgent agent;
	
	public FCS(FCSAgent a) {
		agent = a;
	}

	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.FCS_NEW_PART)) {
			
		} else if (req.getCommand().equals(Constants.FCS_NEW_KIT)) {
			
		} else if (req.getCommand().equals(Constants.FCS_ADD_KIT_TO_QUEUE)) {
			
		} else if (req.getCommand().equals(Constants.FCS_START_PRODUCTION)) {
			
		} else if (req.getCommand().equals(Constants.FCS_STOP_KIT)) {
			
		} else if (req.getCommand().equals(Constants.FCS_STOP_PRODUCTION)) {
			
		}
	}
	
}
