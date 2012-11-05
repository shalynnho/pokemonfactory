package factory.test.mock;

import DeviceGraphics.KitGraphics;
import GraphicsInterfaces.ConveyorGraphics;

/**
 * Mock graphical representation of the conveyor.
 * @author Daniel Paje
 */
public class MockConveyorGraphics extends MockAgent implements ConveyorGraphics {

	public EventLog log;

	public MockConveyorGraphics(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgBringEmptyKit(KitGraphics kit) {
		log.add(new LoggedEvent("Received message msgBringEmptyKit"));

	}

	@Override
	public void msgGiveKitToKitRobot(KitGraphics kit) {
		log.add(new LoggedEvent("Received message msgGiveKitToKitRobot"));

	}

	@Override
	public void msgReceiveKit(KitGraphics kit) {
		log.add(new LoggedEvent("Received message msgReceiveKit"));

	}

}
