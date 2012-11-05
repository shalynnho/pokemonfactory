package factory.test;

import factory.CameraAgent;
import factory.ConveyorAgent;
import factory.FCSAgent;
import factory.KitRobotAgent;
import factory.StandAgent;
import factory.test.mock.MockGraphics;

/**
 * Tests the kitting area (Kit robot, conveyor, stand) Messages are manually
 * sent from classes not part of this area of the cell. These messages assume
 * the other agents/graphics objects completed successfully.
 * @author dpaje
 */
public class v0KittingAreaTest {
	static ConveyorAgent conveyor;
	static CameraAgent camera;
	static KitRobotAgent kitrobot;
	static StandAgent stand;
	static FCSAgent fcs;
	static MockGraphics mockgraphics;

	public v0KittingAreaTest() {

		conveyor = new ConveyorAgent("conveyor");
		camera = new CameraAgent();
		kitrobot = new KitRobotAgent("kitrobot");
		// partsrobot = new PartsRobotAgent();
		stand = new StandAgent("stand");
		fcs = new FCSAgent();

		mockgraphics = new MockGraphics("mockgraphics");

		stand.setKitRobot(kitrobot);
		// stand.setPartsRobot(partsrobot);
		stand.setFCS(fcs);

		kitrobot.setCamera(camera);
		kitrobot.setConveyor(conveyor);
		kitrobot.setStand(stand);
		// kitrobot.setGraphicalRepresentation(mockgraphics.getKitrobotgraphics());
		kitrobot.setMockGraphics(mockgraphics);

		conveyor.setKitRobot(kitrobot);
		// conveyor.setGraphicalRepresentation(mockgraphics.getConveyorgraphics());
		conveyor.setMockgraphics(mockgraphics);

		mockgraphics.setConveyor(conveyor);
		mockgraphics.setKitrobot(kitrobot);

		conveyor.startThread();
		kitrobot.startThread();
		stand.startThread();
		mockgraphics.startThread();
	}

	public static ConveyorAgent getConveyor() {
		return conveyor;
	}

	public static void setConveyor(ConveyorAgent conveyor) {
		v0KittingAreaTest.conveyor = conveyor;
	}

	public static KitRobotAgent getKitrobot() {
		return kitrobot;
	}

	public static void setKitrobot(KitRobotAgent kitrobot) {
		v0KittingAreaTest.kitrobot = kitrobot;
	}

	public static StandAgent getStand() {
		return stand;
	}

	public static void setStand(StandAgent stand) {
		v0KittingAreaTest.stand = stand;
	}

	public static FCSAgent getFcs() {
		return fcs;
	}

	public static void setFcs(FCSAgent fcs) {
		v0KittingAreaTest.fcs = fcs;
	}

	public static MockGraphics getMockgraphics() {
		return mockgraphics;
	}

	public static void setMockgraphics(MockGraphics mockgraphics) {
		v0KittingAreaTest.mockgraphics = mockgraphics;
	}

	public static void main(String[] args) {
		v0KittingAreaTest test = new v0KittingAreaTest();
		System.out.println("Starting kit area test");
		test.getStand().msgMakeKits(5);
	}

}