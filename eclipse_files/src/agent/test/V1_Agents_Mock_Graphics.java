package agent.test;

import java.util.ArrayList;

import agent.CameraAgent;
import agent.ConveyorAgent;
import agent.FCSAgent;
import agent.FeederAgent;
import agent.GantryAgent;
import agent.KitRobotAgent;
import agent.LaneAgent;
import agent.NestAgent;
import agent.PartsRobotAgent;
import agent.StandAgent;
import agent.test.mock.MockGraphics;
import factory.KitConfig;
import factory.Order;
import factory.PartType;

public class V1_Agents_Mock_Graphics {

	public static void main(String[] args) {
		GantryAgent gantry = new GantryAgent("Gantry Agent");
		ArrayList<FeederAgent> feeders = new ArrayList<FeederAgent>();
		for (int i = 0; i < 4; i++) {
			feeders.add(new FeederAgent("Feeder Agent " + i));
		}
		ArrayList<LaneAgent> lanes = new ArrayList<LaneAgent>();
		for (int i = 0; i < 8; i++) {
			lanes.add(new LaneAgent("Lane Agent " + i));
		}
		ArrayList<NestAgent> nests = new ArrayList<NestAgent>();
		for (int i = 0; i < 8; i++) {
			nests.add(new NestAgent("Nest Agent " + i));
		}
		PartsRobotAgent partsRobot = new PartsRobotAgent("Parts Robot Agent");
		CameraAgent camera = new CameraAgent("Camera Agent");
		StandAgent stand = new StandAgent("Stand Agent");
		KitRobotAgent kitRobot = new KitRobotAgent("Kit Robot Agent");
		ConveyorAgent conveyor = new ConveyorAgent("Conveyor Agent");
		FCSAgent fcs = new FCSAgent("FCS Agent");

		for (int i = 0; i < 8; i++) {
			feeders.get(i / 2).setGantry(gantry);
			feeders.get(i / 2).setLane(lanes.get(i));
			lanes.get(i).setFeeder(feeders.get(i / 2));
			lanes.get(i).setNest(nests.get(i));
			nests.get(i).setLane(lanes.get(i));
			camera.setNest(nests.get(i));
			fcs.setNest(nests.get(i));
		}
		conveyor.setFcs(fcs);
		conveyor.setKitrobot(kitRobot);
		if(conveyor.getFcs()==null){
			System.out.println("Conveyor fcs null");
		}
		if(conveyor.getKitrobot()==null){
			System.out.println("Conveyor kitrobot null");
		} else {
			System.out.println("Conveyor kitrobot not null");
		}
		camera.setKitRobot(kitRobot);
		stand.setFCS(fcs);
		stand.setKitrobot(kitRobot);
		stand.setPartsRobot(partsRobot);
		kitRobot.setCamera(camera);
		kitRobot.setStand(stand);
		kitRobot.setConveyor(conveyor);
		fcs.setConveyor(conveyor);
		fcs.setGantry(gantry);
		fcs.setPartsRobot(partsRobot);
		fcs.setStand(stand);

		MockGraphics mg = new MockGraphics("Mock Graphics");
		
		mg.setCamera(camera);
		mg.setConveyor(conveyor);
		//mg.setFeeder(feeder);
		mg.setGantry(gantry);
		mg.setKitrobot(kitRobot);
		//mg.setLane(lane);
		//mg.setNest(nest);
		mg.setPartsrobot(partsRobot);

		gantry.setGraphicalRepresentation(mg);
		for (int i = 0; i < 4; i++) {
			feeders.get(i).setGraphicalRepresentation(mg);
		}
		for (int i = 0; i < 8; i++) {
			lanes.get(i).setGraphicalRepresentation(mg);
		}
		for (int i = 0; i < 8; i++) {
			nests.get(i).setGraphicalRepresentation(mg);
		}
		partsRobot.setGraphicalRepresentation(mg);
		camera.setGraphicalRepresentation(mg);
		stand.setGraphicalRepresentation(mg);
		kitRobot.setGraphicalRepresentation(mg);
		conveyor.setGraphicalRepresentation(mg);
		fcs.setGraphicalRepresentation(mg);

		KitConfig kg = new KitConfig("Kit config");
		kg.addItem(new PartType("A"), 1);
		kg.addItem(new PartType("B"), 1);
		kg.addItem(new PartType("C"), 1);
		kg.addItem(new PartType("D"), 1);
		kg.addItem(new PartType("E"), 1);
		kg.addItem(new PartType("F"), 1);
		kg.addItem(new PartType("G"), 1);
		kg.addItem(new PartType("H"), 1);

		gantry.startThread();
		for (int i = 0; i < 4; i++) {
			feeders.get(i).startThread();
		}
		for (int i = 0; i < 8; i++) {
			lanes.get(i).startThread();
		}
		for (int i = 0; i < 8; i++) {
			nests.get(i).startThread();
		}
		mg.startThread();
		partsRobot.startThread();
		camera.startThread();
		stand.startThread();
		kitRobot.startThread();
		conveyor.startThread();
		fcs.startThread();

		fcs.msgStartProduction();
		fcs.msgAddKitsToQueue(new Order(kg, 1));
	}

}
