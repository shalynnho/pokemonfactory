package agent.test.mock;

import agent.data.Bin;
import agent.data.PartType;
import agent.interfaces.Gantry;

/**
 * Mock Gantry
 * @author Daniel Paje
 */
public class MockGantry extends MockAgent implements Gantry {

	public EventLog log;

	public MockGantry(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void msgHereIsBinConfig(Bin bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgINeedParts(PartType type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgreceiveBinDone(Bin bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgdropBinDone(Bin bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgremoveBinDone(Bin bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveToFeeder(Bin bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillFeeder(Bin bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void discardBin(Bin bin) {
		// TODO Auto-generated method stub

	}

}
