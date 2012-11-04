package factory.test.mock;


public class MockFCS extends MockAgent {

	public EventLog log;

	public MockFCS(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

}
