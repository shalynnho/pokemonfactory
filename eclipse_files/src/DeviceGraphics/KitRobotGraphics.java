package DeviceGraphics;

import java.util.TreeMap;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.Agent;
import agent.KitRobotAgent;
import agent.StandAgent;

public class KitRobotGraphics implements GraphicsInterfaces.KitRobotGraphics,
		DeviceGraphics {

	private final Server server;


	TreeMap<String, KitGraphics> kitPositions; // keeps track of where the kits are

	KitRobotAgent kitRobotAgent; 
	StandAgent standAgent; // for testing

	KitGraphics testKit1; // for testing
	KitGraphics testKit2; // for testing

	Location inspectionLocation;
	Location location1;
	Location location2;
	
	public KitRobotGraphics(Server s, Agent kra, Agent sta) {
		kitPositions = new TreeMap<String, KitGraphics>();
		initKitPositions();
		server = s;
		kitRobotAgent = (KitRobotAgent) kra;
		standAgent = (StandAgent) sta;
		testKit1 = new KitGraphics(server);
		testKit2 = new KitGraphics(server);
		inspectionLocation = new Location(240, 100);
		location1 = new Location(280, 200);
		location2= new Location(240, 300);
	}

	/*
	 * initializes the Treemap of kits.
	 */
	public void initKitPositions() {

		KitGraphics tempKitGraphics = new KitGraphics(server);
		kitPositions.put(Constants.KIT_INITIAL, tempKitGraphics);
		tempKitGraphics = new KitGraphics(server);
		kitPositions.put(Constants.KIT_INSPECTION_AREA, tempKitGraphics);
		tempKitGraphics = new KitGraphics(server);
		kitPositions.put(Constants.KIT_LOCATION1, tempKitGraphics);
		tempKitGraphics = new KitGraphics(server);
		kitPositions.put(Constants.KIT_LOCATION2, tempKitGraphics);

	}
	/*
	 * sends message to KitRobotGraphicsDisplay to move a kit to inspection area
	 * (non-Javadoc)
	 * @see GraphicsInterfaces.KitRobotGraphics#msgPlaceKitInInspectionArea(DeviceGraphics.KitGraphics)
	 *	passes the kit that will move to the inspection area
	 */
	public void msgPlaceKitInInspectionArea(KitGraphics kit) {
		for (String kitGraphicsKey : kitPositions.keySet()) {
			KitGraphics tempKitGraphics = kitPositions.get(kitGraphicsKey);
			if (tempKitGraphics == null) {
				System.out.println(this.toString() + " tempKitGraphics null");
				System.out.println(this.toString() + kitGraphicsKey);
			}
			if (tempKitGraphics == kit) {
				
				kitPositions.put(kitGraphicsKey, null);
				if (kitGraphicsKey.equals(Constants.KIT_LOCATION1)) {
					tempKitGraphics.setPosition(5);
					kitPositions.put(Constants.KIT_INSPECTION_AREA, tempKitGraphics);
					server.sendData(new Request(
							Constants.KIT_ROBOT_DISPLAY_PICKS_LOCATION1_TO_INSPECTION,
							Constants.KIT_ROBOT_TARGET, null));
				} else {
					tempKitGraphics.setPosition(5);
					kitPositions.put(Constants.KIT_INSPECTION_AREA, tempKitGraphics);
					server.sendData(new Request(
							Constants.KIT_ROBOT_DISPLAY_PICKS_LOCATION2_TO_INSPECTION,
							Constants.KIT_ROBOT_TARGET, null));
				}
			}

		}

		kitPositions.put(Constants.KIT_INSPECTION_AREA, kit);

	}

	/*
	 * messages KitRobotGraphicsDisplay to move a kit to the GoodConveyor
	 * (non-Javadoc)
	 * @see GraphicsInterfaces.KitRobotGraphics#msgPlaceKitOnConveyor()
	 */
	public void msgPlaceKitOnConveyor() {
		kitPositions.get(Constants.KIT_INSPECTION_AREA).setPosition(5);
		kitPositions.put(Constants.KIT_INSPECTION_AREA, null);
		server.sendData(new Request(
				Constants.KIT_ROBOT_DISPLAY_PICKS_INSPECTION_TO_GOOD_CONVEYOR,
				Constants.KIT_ROBOT_TARGET, null));
	}

	public void msgPlaceKitOnStand1(KitGraphics kit) {
		kit.setLocation(location1);
		kit.setPosition(4);
		kitPositions.put(Constants.KIT_LOCATION1, kit);
		server.sendData(new Request(
				Constants.KIT_ROBOT_DISPLAY_PICKS_CONVEYOR_TO_LOCATION1,
				Constants.KIT_ROBOT_TARGET, null));

	}

	public void msgPlaceKitOnStand2(KitGraphics kit) {
		kit.setLocation(location2);
		kit.setPosition(3);
		kitPositions.put(Constants.KIT_LOCATION2, kit);
		server.sendData(new Request(
				Constants.KIT_ROBOT_DISPLAY_PICKS_CONVEYOR_TO_LOCATION2,
				Constants.KIT_ROBOT_TARGET, null));
	}
	
	/*
	 * Moves the kit to a stand location
	 * (non-Javadoc)
	 * @see GraphicsInterfaces.KitRobotGraphics#msgPlaceKitOnStand(DeviceGraphics.KitGraphics, int)
	 */
	public void msgPlaceKitOnStand(KitGraphics kit, int location) {
		if (location == 1) {
			msgPlaceKitOnStand1(kit);
		} else if (location == 2) {
			msgPlaceKitOnStand2(kit);
		}

	}
	
	/*
	 * receives request that will send commands based on the request
	 * @see DeviceGraphics.DeviceGraphics#receiveData(Networking.Request)
	 *	request is a class that has a command target and data
	 */
	public void receiveData(Request req) {
		String target = req.getTarget();
		String command = req.getCommand();
		Object object = req.getData();

		if (command
				.equals(Constants.KIT_ROBOT_LOGIC_PICKS_CONVEYOR_TO_LOCATION1)) {

			msgPlaceKitOnStand(testKit1, 1);
		} else if (command
				.equals(Constants.KIT_ROBOT_LOGIC_PICKS_CONVEYOR_TO_LOCATION2)) {

			msgPlaceKitOnStand(testKit2, 2);
		} else if (command
				.equals(Constants.KIT_ROBOT_LOGIC_PICKS_LOCATION1_TO_INSPECTION)) {
			msgPlaceKitInInspectionArea(testKit1);

		} else if (command
				.equals(Constants.KIT_ROBOT_LOGIC_PICKS_LOCATION2_TO_INSPECTION)) {
			msgPlaceKitInInspectionArea(testKit2);
		} else if (command
				.equals(Constants.KIT_ROBOT_LOGIC_PICKS_INSPECTION_TO_GOOD_CONVEYOR)) {
			msgPlaceKitOnConveyor();
		} else if (command
				.equals(Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND)) {
			server.sendData(new Request(Constants.CONVEYOR_RECEIVE_KIT_COMMAND,
					Constants.CONVEYOR_TARGET, null));
		} else if (command.equals(Constants.KIT_ROBOT_AGENT_RECEIVES_KIT1_DONE)) {
			// standAgent.fakeKitCompletion(kitPositions.get(Constants.KIT_LOCATION1));
		} else if (command.equals(Constants.KIT_ROBOT_AGENT_RECEIVES_KIT2_DONE)) {
			// standAgent.fakeKitCompletion(kitPositions.get(Constants.KIT_LOCATION2));
		} else if (command.equals(Constants.KIT_ROBOT_AGENT_RECEIVES_KIT_INSPECTED)) {
			// Hack for KitRobotManager
			kitRobotAgent.msgKitPassedInspection();
		} else if (command.equals(Constants.KIT_ROBOT_ON_STAND1_DONE)) {
			kitRobotAgent.msgPlaceKitOnStandDone();
		} else if (command.equals(Constants.KIT_ROBOT_ON_STAND2_DONE)) {
			kitRobotAgent.msgPlaceKitOnStandDone();
		} else if (command.equals(Constants.KIT_ROBOT_ON_CONVEYOR_DONE)) {
			kitRobotAgent.msgPlaceKitOnConveyorDone();
		} else if (command.equals(Constants.KIT_ROBOT_ON_INSPECTION_DONE)) {
			kitRobotAgent.msgPlaceKitInInspectionAreaDone();
		} else if (command.equals(Constants.KIT_RECEIVES_PART)) {
			
			PartGraphics testPart = new PartGraphics(Constants.DEFAULT_PARTTYPES.get(1));
			kitPositions.get(Constants.KIT_LOCATION1).receivePart(testPart);
		}
	}

}
