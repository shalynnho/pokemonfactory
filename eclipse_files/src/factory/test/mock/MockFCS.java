package factory.test.mock;

/**
 * Mock FCS. Messages received simply add an entry to the mock agent's log.
 * NOTE: There is no interface for this agent yet.
 * @author dpaje
 */
public class MockFCS extends MockAgent {

	public EventLog log;

	public MockFCS(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

}
