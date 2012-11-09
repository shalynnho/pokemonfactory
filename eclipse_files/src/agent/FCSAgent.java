package agent;

import DeviceGraphics.DeviceGraphics;
import agent.interfaces.FCS;

/**
 * Unused in V0
 * @author Daniel Paje
 */
public class FCSAgent extends Agent implements FCS {

	@Override
	public void msgOrderFinished() {
		print("Received msgOrderFinished");
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGraphicalRepresentation(DeviceGraphics dg) {

	}

}
