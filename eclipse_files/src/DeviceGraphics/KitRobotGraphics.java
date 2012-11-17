package DeviceGraphics;

import java.util.ArrayList;
import java.util.TreeMap;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import agent.Agent;
import agent.KitRobotAgent;
import agent.StandAgent;
import agent.data.Kit;

public class KitRobotGraphics implements GraphicsInterfaces.KitRobotGraphics,
		DeviceGraphics {

	private Kit kit;
	private final Server server;

	// KitGraphics[] positions;


	TreeMap<String, KitGraphics> kitPositions; // keeps track of where the kits are

	KitRobotAgent kitRobotAgent;
	StandAgent standAgent;

	KitGraphics testKit1;
	KitGraphics testKit2;

	public KitRobotGraphics(Server s, Agent kra, Agent sta) {
		kitPositions = new TreeMap<String, KitGraphics>();
		initKitPositions();
		server = s;
		kitRobotAgent = (KitRobotAgent) kra;
		standAgent = (StandAgent) sta;
		testKit1 = new KitGraphics(server);
		testKit2 = new KitGraphics(server);
	}

	/**
	 * Initializes the Treemap of kits.
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
					server.sendData(new Request(
							Constants.KIT_ROBOT_DISPLAY_PICKS_LOCATION1_TO_INSPECTION,
							Constants.KIT_ROBOT_TARGET, null));
				} else {
					server.sendData(new Request(
							Constants.KIT_ROBOT_DISPLAY_PICKS_LOCATION2_TO_INSPECTION,
							Constants.KIT_ROBOT_TARGET, null));
				}
			}

		}

		kitPositions.put(Constants.KIT_INSPECTION_AREA, kit);

		// TODO Auto-generated method stub
	}

	/*
	 * messages KitRobotGraphicsDisplay to move a kit to the GoodConveyor
	 * (non-Javadoc)
	 * @see GraphicsInterfaces.KitRobotGraphics#msgPlaceKitOnConveyor()
	 */
	public void msgPlaceKitOnConveyor() {
		kitPositions.put(Constants.KIT_INSPECTION_AREA, null);
		server.sendData(new Request(
				Constants.KIT_ROBOT_DISPLAY_PICKS_INSPECTION_TO_GOOD_CONVEYOR,
				Constants.KIT_ROBOT_TARGET, null));
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
			standAgent.fakeKitCompletion(kitPositions
					.get(Constants.KIT_LOCATION1));
		} else if (command.equals(Constants.KIT_ROBOT_AGENT_RECEIVES_KIT2_DONE)) {

			standAgent.fakeKitCompletion(kitPositions
					.get(Constants.KIT_LOCATION2));
		}
		if (command.equals(Constants.KIT_ROBOT_AGENT_RECEIVES_KIT_INSPECTED)) {
			// Hack for KitRobotManager
			kitRobotAgent.msgKitPassedInspection();
		}

		else if (command.equals(Constants.KIT_ROBOT_ON_STAND_DONE)) {
			System.out.println("placekitonStandDone sent");
			// testing
			kitRobotAgent.msgPlaceKitOnStandDone();
		} else if (command.equals(Constants.KIT_ROBOT_ON_CONVEYOR_DONE)) {
			System.out.println("placekitonconveyordone sent");
			kitRobotAgent.msgPlaceKitOnConveyorDone();
		} else if (command.equals(Constants.KIT_ROBOT_ON_INSPECTION_DONE)) {
			System.out.println("placekitoninspection sent");
			kitRobotAgent.msgPlaceKitInInspectionAreaDone();
		}
	}

	public void msgPlaceKitOnStand1(KitGraphics kit) {
		kitPositions.put(Constants.KIT_LOCATION1, kit);
		server.sendData(new Request(
				Constants.KIT_ROBOT_DISPLAY_PICKS_CONVEYOR_TO_LOCATION1,
				Constants.KIT_ROBOT_TARGET, null));

	}

	public void msgPlaceKitOnStand2(KitGraphics kit) {
		// TODO Auto-generated method stub

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

}
