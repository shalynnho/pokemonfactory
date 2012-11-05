package factory.test.mock;

import DeviceGraphics.KitGraphics;
import GraphicsInterfaces.KitRobotGraphics;

/**
 * Mock graphical representation for the kitrobot. Messages received simply add
 * an entry to the mock agent's log.
 * @author Daniel Paje
 */
public class MockKitRobotGraphics extends MockAgent implements KitRobotGraphics {

	public EventLog log;

	public MockKitRobotGraphics(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgPlaceKitOnStand(KitGraphics kit, int location) {
		log.add(new LoggedEvent("Received message msgPlaceKitOnStand"));

	}

	@Override
	public void msgPlaceKitInInspectionArea(KitGraphics kit) {
		log.add(new LoggedEvent("Received message msgPlaceKitInInspectionArea"));

	}

	@Override
	public void msgPlaceKitOnConveyor() {
		log.add(new LoggedEvent("Received message msgPlaceKitOnConveyor"));

	}

}
