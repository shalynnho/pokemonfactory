package factory.test.mock;

import factory.interfaces.FCS;

/**
 * Mock FCS. Messages received simply add an entry to the mock agent's log.
 * NOTE: There is no interface for this agent yet.
 * @author dpaje
 */
public class MockFCS extends MockAgent implements FCS {

	public EventLog log;

	public MockFCS(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgOrderFinished() {
		// TODO Auto-generated method stub

	}

}
