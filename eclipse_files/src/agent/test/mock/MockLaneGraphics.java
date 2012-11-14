package agent.test.mock;

import DeviceGraphics.DeviceGraphics;
import DeviceGraphics.PartGraphics;
import GraphicsInterfaces.LaneGraphics;
import Networking.Request;
import agent.Agent;

public class MockLaneGraphics extends Agent implements DeviceGraphics, LaneGraphics{

	@Override
	public void receivePart(PartGraphics part) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void givePartToNest(PartGraphics part) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void purge() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveData(Request req) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGraphicalRepresentation(DeviceGraphics dg) {
		// TODO Auto-generated method stub
		
	}

}
