package factory.test.mock;

import java.util.Timer;
import java.util.TimerTask;

import DeviceGraphics.BinGraphics;
import DeviceGraphics.KitGraphics;
import DeviceGraphics.PartGraphics;
import GraphicsInterfaces.CameraGraphics;
import GraphicsInterfaces.ConveyorGraphics;
import GraphicsInterfaces.FeederGraphics;
import GraphicsInterfaces.GantryGraphics;
import GraphicsInterfaces.KitRobotGraphics;
import GraphicsInterfaces.LaneGraphics;
import GraphicsInterfaces.NestGraphics;
import GraphicsInterfaces.PartsRobotGraphics;
import agent.Agent;
import factory.CameraAgent;
import factory.ConveyorAgent;
import factory.KitRobotAgent;
import factory.PartsRobotAgent;
import factory.interfaces.Feeder;
import factory.interfaces.Gantry;
import factory.interfaces.Lane;
import factory.interfaces.Nest;

;

public class MockGraphics extends Agent implements CameraGraphics,
		ConveyorGraphics, FeederGraphics, GantryGraphics, KitRobotGraphics,
		LaneGraphics, NestGraphics, PartsRobotGraphics {

	Timer timer;

	// Agents
	CameraAgent camera;
	ConveyorAgent conveyor;
	Feeder feeder;
	Gantry gantry;
	KitRobotAgent kitrobot;
	Lane lane;
	Nest nest;
	PartsRobotAgent partsrobot;

	// Graphics
	KitRobotGraphics kitrobotgraphics;
	ConveyorGraphics conveyorgraphics;

	public MockGraphics(String name) {
		super();

<<<<<<< HEAD
		// Set server to null
		// kitrobotgraphics = new DeviceGraphics.KitRobotGraphics(null);
		conveyorgraphics = new DeviceGraphics.ConveyorGraphics(null);
=======
		camera = new CameraAgent();
		conveyor = new ConveyorAgent("conveyor");
		feeder = new FeederAgent("feeder");
		gantry = new GantryAgent("gantry");
		kitrobot = new KitRobotAgent("kitrobot");
		lane = new LaneAgent("lane");
		nest = new NestAgent("nest");
		partsrobot = new PartsRobotAgent();

		camera.startThread();
		conveyor.startThread();
		feeder.startThread();
		gantry.startThread();
		kitrobot.startThread();
		lane.startThread();
		nest.startThread();
		partsrobot.startThread();
>>>>>>> Updated NestAgent - Just need to add the graphics messages. Updated a
	}

	@Override
	public void pickUpPart(PartGraphics part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void givePartToKit(KitGraphics kit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void givePartToPartsRobot(PartGraphics part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receivePart(PartGraphics part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void givePartToNest(PartGraphics part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void purge() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPlaceKitOnStand(KitGraphics kit, int location) {
		System.out
				.println("KitRobotGraphics received message msgPlaceKitOnStand");
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out
						.println("KitRobotGraphics sending message msgPlaceKitOnStandDone() to KitRobot after 3000ms");
				kitrobot.msgPlaceKitOnStandDone();
			}
		}, 3000);

	}

	@Override
	public void msgPlaceKitInInspectionArea(KitGraphics kit) {
		System.out
				.println("KitRobotGraphics received message placeKitInInspectionArea");
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out
						.println("KitRobotGraphics sending message placeKitInInspectionAreaDone() to KitRobot after 3000ms");
				kitrobot.msgPlaceKitOnStandDone();
			}
		}, 3000);
	}

	@Override
	public void msgPlaceKitOnConveyor() {
		System.out
				.println("KitRobotGraphics received message msgPlaceKitOnConveyor");

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out
						.println("KitRobotGraphics sending message placeKitOnConveyorDone() to KitRobot after 3000ms");
				kitrobot.msgPlaceKitOnStandDone();
			}
		}, 3000);
	}

	@Override
	public void dropBin(BinGraphics bin, FeederGraphics feeder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBin(BinGraphics bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveBin(BinGraphics bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void purgeBin(BinGraphics bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void movePartToDiverter(PartGraphics part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void flipDiverter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void movePartToLane(PartGraphics part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgBringEmptyKit(KitGraphics kit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgGiveKitToKitRobot(KitGraphics kit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgReceiveKit(KitGraphics kit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeNestPhoto(NestGraphics nest1, NestGraphics nest2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeKitPhoto(KitGraphics kit) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * Accessors and mutators
	 */

	public CameraAgent getCamera() {
		return camera;
	}

	public void setCamera(CameraAgent camera) {
		this.camera = camera;
	}

	public ConveyorAgent getConveyor() {
		return conveyor;
	}

	public void setConveyor(ConveyorAgent conveyor) {
		this.conveyor = conveyor;
	}

	public Feeder getFeeder() {
		return feeder;
	}

	public void setFeeder(Feeder feeder) {
		this.feeder = feeder;
	}

	public Gantry getGantry() {
		return gantry;
	}

	public void setGantry(Gantry gantry) {
		this.gantry = gantry;
	}

	public KitRobotAgent getKitrobot() {
		return kitrobot;
	}

	public void setKitrobot(KitRobotAgent kitrobot) {
		this.kitrobot = kitrobot;
	}

	public Lane getLane() {
		return lane;
	}

	public void setLane(Lane lane) {
		this.lane = lane;
	}

	public Nest getNest() {
		return nest;
	}

	public void setNest(Nest nest) {
		this.nest = nest;
	}

	public PartsRobotAgent getPartsrobot() {
		return partsrobot;
	}

	public void setPartsrobot(PartsRobotAgent partsrobot) {
		this.partsrobot = partsrobot;
	}

	public KitRobotGraphics getKitrobotgraphics() {
		return kitrobotgraphics;
	}

	public void setKitrobotgraphics(KitRobotGraphics kitrobotgraphics) {
		this.kitrobotgraphics = kitrobotgraphics;
	}

	public ConveyorGraphics getConveyorgraphics() {
		return conveyorgraphics;
	}

	public void setConveyorgraphics(ConveyorGraphics conveyorgraphics) {
		this.conveyorgraphics = conveyorgraphics;
	}

}
