package agent.test.mock;

import agent.CameraAgent;
import agent.LaneAgent;
import agent.data.Part;
import agent.data.PartType;
import agent.interfaces.Nest;
import DeviceGraphics.DeviceGraphics;

/**
 * Mock nest
 * @author Daniel Paje
 */
public class MockNest extends MockAgent implements Nest {

	public EventLog log;

	public MockNest(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void msgHereIsPartType(PartType type) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received message msgHereIsPartType"));
	}

	@Override
	public void msgHereIsPart(Part p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgTakingPart(Part p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgDoneTakingParts() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgReceivePartDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgGivePartToPartsRobotDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPurgingDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getParts(PartType requestedType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveToPosition(Part part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nestFull() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateParts() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLane(LaneAgent lane) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCamera(CameraAgent camera) {
		// TODO Auto-generated method stub
		
	}

	public void setGraphicalRepresentation(DeviceGraphics nest) {
		// TODO Auto-generated method stub
		
	}

}
