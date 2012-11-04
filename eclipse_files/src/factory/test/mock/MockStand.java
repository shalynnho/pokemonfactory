package factory.test.mock;

import factory.data.Kit;
import factory.interfaces.Stand;

/**
 * Mock stand. Messages received simply add an entry to the mock agent's log.
 * @author dpaje
 */
public class MockStand extends MockAgent implements Stand {

	public EventLog log;

	public MockStand(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgMakeKits(int numKits) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgKitAssembled(Kit k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgHereIsKit(Kit k, int dest) {
		log.add(new LoggedEvent("Received message msgHereIsKit"));

	}

	@Override
	public void msgShippedKit() {
		log.add(new LoggedEvent("Received message msgShippedKit"));

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

}