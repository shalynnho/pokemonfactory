package factory.test;

import factory.CameraAgent;
import factory.ConveyorAgent;
import factory.FCSAgent;
import factory.FeederAgent;
import factory.GantryAgent;
import factory.KitRobotAgent;
import factory.LaneAgent;
import factory.NestAgent;
import factory.PartsRobotAgent;
import factory.StandAgent;
import factory.interfaces.Feeder;
import factory.interfaces.Gantry;
import factory.interfaces.Lane;
import factory.interfaces.Nest;
import factory.test.mock.MockGraphics;

public class v0KittingAreaTest {
	static CameraAgent camera;
	static ConveyorAgent conveyor;
	static Feeder feeder;
	static Gantry gantry;
	static KitRobotAgent kitrobot;
	static Lane lane;
	static Nest nest;
	static PartsRobotAgent partsrobot;
	static StandAgent stand;
	static FCSAgent fcs;
	static MockGraphics mockgraphics;

	public v0KittingAreaTest() {
		camera = new CameraAgent();
		conveyor = new ConveyorAgent("conveyor");
		feeder = new FeederAgent("feeder");
		gantry = new GantryAgent("gantry");
		kitrobot = new KitRobotAgent("kitrobot");
		lane = new LaneAgent("lane");
		nest = new NestAgent();
		partsrobot = new PartsRobotAgent();
		stand = new StandAgent("stand");
		fcs = new FCSAgent();

		mockgraphics = new MockGraphics("mockgraphics");

		stand.setKitRobot(kitrobot);
		stand.setPartsRobot(partsrobot);

		kitrobot.setCamera(camera);
		kitrobot.setConveyor(conveyor);
		kitrobot.setStand(stand);
		// kitrobot.setGraphicalRepresentation(mockgraphics.getKitrobotgraphics());
		kitrobot.setMockGraphics(mockgraphics);

		conveyor.setKitRobot(kitrobot);
		// conveyor.setGraphicalRepresentation(mockgraphics.getConveyorgraphics());
		conveyor.setMockgraphics(mockgraphics);

		conveyor.startThread();
		kitrobot.startThread();
		stand.startThread();
		mockgraphics.startThread();
	}

	public static CameraAgent getCamera() {
		return camera;
	}

	public static void setCamera(CameraAgent camera) {
		v0KittingAreaTest.camera = camera;
	}

	public static ConveyorAgent getConveyor() {
		return conveyor;
	}

	public static void setConveyor(ConveyorAgent conveyor) {
		v0KittingAreaTest.conveyor = conveyor;
	}

	public static Feeder getFeeder() {
		return feeder;
	}

	public static void setFeeder(Feeder feeder) {
		v0KittingAreaTest.feeder = feeder;
	}

	public static Gantry getGantry() {
		return gantry;
	}

	public static void setGantry(Gantry gantry) {
		v0KittingAreaTest.gantry = gantry;
	}

	public static KitRobotAgent getKitrobot() {
		return kitrobot;
	}

	public static void setKitrobot(KitRobotAgent kitrobot) {
		v0KittingAreaTest.kitrobot = kitrobot;
	}

	public static Lane getLane() {
		return lane;
	}

	public static void setLane(Lane lane) {
		v0KittingAreaTest.lane = lane;
	}

	public static Nest getNest() {
		return nest;
	}

	public static void setNest(Nest nest) {
		v0KittingAreaTest.nest = nest;
	}

	public static PartsRobotAgent getPartsrobot() {
		return partsrobot;
	}

	public static void setPartsrobot(PartsRobotAgent partsrobot) {
		v0KittingAreaTest.partsrobot = partsrobot;
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

		test.getStand().msgMakeKits(100);
	}
}