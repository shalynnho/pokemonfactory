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
import factory.FeederAgent;
import factory.GantryAgent;
import factory.KitRobotAgent;
import factory.LaneAgent;
import factory.NestAgent;
import factory.PartsRobotAgent;

;

public class MockGraphics extends Agent implements CameraGraphics,
		ConveyorGraphics, FeederGraphics, GantryGraphics, KitRobotGraphics,
		LaneGraphics, NestGraphics, PartsRobotGraphics {

	Timer timer;
	CameraAgent camera;
	ConveyorAgent conveyor;
	FeederAgent feeder;
	GantryAgent gantry;
	KitRobotAgent kitrobot;
	LaneAgent lane;
	NestAgent nest;
	PartsRobotAgent partsrobot;

	public MockGraphics(String name) {
		super();

		camera = new CameraAgent();
		conveyor = new ConveyorAgent("conveyor");
		feeder = new FeederAgent("feeder");
		gantry = new GantryAgent("gantry");
		kitrobot = new KitRobotAgent("kitrobot");
		lane = new LaneAgent("lane");
		nest = new NestAgent();
		partsrobot = new PartsRobotAgent();

		camera.startThread();
		conveyor.startThread();
		feeder.startThread();
		gantry.startThread();
		kitrobot.startThread();
		lane.startThread();
		nest.startThread();
		partsrobot.startThread();
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

}
