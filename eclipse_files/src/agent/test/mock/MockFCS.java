package agent.test.mock;

import java.util.ArrayList;

import agent.data.PartType;
import agent.interfaces.FCS;

/**
 * Mock FCS. Messages received simply add an entry to the mock agent's log.
 * @author Daniel Paje
 */
public class MockFCS extends MockAgent implements FCS {

	public EventLog log;

	public MockFCS(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgOrderFinished() {
		log.add(new LoggedEvent("Order finished"));
	}

	@Override
	public void msgAddKitsToQueue(ArrayList<PartType> parts, int numOfKits) {		
	}

	@Override
	public void msgStopMakingKit(ArrayList<PartType> parts) {		
	}

	@Override
	public void msgStartProduction() {
	}

}
