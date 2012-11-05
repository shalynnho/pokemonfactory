package factory.test.mock;

import factory.GantryAgent;
import factory.LaneAgent;
import factory.data.Part;
import factory.data.PartType;
import factory.interfaces.Feeder;

/**
 * Mock feeder.
 * @author Daniel Paje
 */
public class MockFeeder extends MockAgent implements Feeder {

	public EventLog log;

	public MockFeeder(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void msgINeedPart(PartType type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgHereAreParts(Part p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgGivePartToDiverterDone(Part part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgGivePartToLaneDone(Part part) {
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
	public void giveToDiverter(Part part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveToLane(Part part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGantry(GantryAgent gantry) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLane(LaneAgent lane) {
		// TODO Auto-generated method stub

	}

}
