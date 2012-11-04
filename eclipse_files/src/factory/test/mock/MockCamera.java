package factory.test.mock;

import java.util.List;

import DeviceGraphics.PartGraphics;
import factory.NestAgent;
import factory.data.Kit;
import factory.interfaces.Camera;
import factory.interfaces.Nest;

/**
 * Mock camera. Messages received simply add an entry to the mock agent's log.
 * @author dpaje
 */
public class MockCamera extends MockAgent implements Camera {

	public EventLog log;

	public MockCamera(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgInspectKit(Kit kit) {
		log.add(new LoggedEvent("Received message msgInspectKit"));

	}

	@Override
	public void msgIAmFull(NestAgent nest) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgTakePictureNestDone(List<PartGraphics> parts, Nest nest) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgTakePictureKitDone(boolean done) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

}
