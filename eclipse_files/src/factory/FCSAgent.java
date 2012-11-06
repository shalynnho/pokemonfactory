package factory;

import DeviceGraphics.DeviceGraphics;
import agent.Agent;
import factory.interfaces.FCS;

/**
 * Unused in V0
 * @author Daniel Paje
 */
public class FCSAgent extends Agent implements FCS {

	@Override
	public void msgOrderFinished() {
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setGraphicalRepresentation(DeviceGraphics dg) {
		
	}

}
