package factory.test.mock;

import java.util.List;

import factory.data.Kit;
import factory.data.Part;
import factory.data.PartType;
import factory.interfaces.Nest;
import factory.interfaces.PartsRobot;

/**
 * Mock partsrobot. Messages received simply add an entry to the mock agent's
 * log.
 * @author Daniel Paje
 */
public class MockPartsRobot extends MockAgent implements PartsRobot {

	public EventLog log;

	public MockPartsRobot(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgHereIsKitConfiguration(List<PartType> config) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgUseThisKit(Kit k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPickUpPartDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgGivePartToKitDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgHereAreGoodParts(Nest n, List<Part> goodparts) {
		// TODO Auto-generated method stub

	}

}
