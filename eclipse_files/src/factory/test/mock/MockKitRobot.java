package factory.test.mock;

import factory.data.Kit;
import factory.interfaces.KitRobot;

public class MockKitRobot extends MockAgent implements KitRobot {

	public EventLog log;

	public MockKitRobot(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgHereIsKit(Kit k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgNeedKit(int standLocation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgMoveKitToInspectionArea(Kit k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgKitPassedInspection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPlaceKitOnConveyorDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPlaceKitInInspectionAreaDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPlaceKitOnStandDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

}
