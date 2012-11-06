package factory.test.mock;

import java.util.ArrayList;

import factory.data.Kit;
import factory.data.PartType;
import factory.interfaces.Conveyor;

/**
 * Mock conveyor. Messages received simply add an entry to the mock agent's log.
 * @author Daniel Paje
 */
public class MockConveyor extends MockAgent implements Conveyor {

	public EventLog log;

	public MockConveyor(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgNeedKit() {
		log.add(new LoggedEvent("Received message msgNeedKit"));
	}

	@Override
	public void msgTakeKitAway(Kit k) {
		log.add(new LoggedEvent("Received message msgTakeKitAway"));

	}

	@Override
	public void msgBringEmptyKitDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgGiveKitToKitRobotDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgReceiveKitDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgHereIsKitConfiguration(ArrayList<PartType> config) {
		// TODO Auto-generated method stub

	}

}
