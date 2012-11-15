package DeviceGraphics;

import java.util.ArrayList;
import java.util.TreeMap;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import agent.Agent;
import agent.KitRobotAgent;
import agent.data.Kit;

public class KitRobotGraphics implements GraphicsInterfaces.KitRobotGraphics,
		DeviceGraphics {

	private Kit kit;
	private final Server server;

	// KitGraphics[] positions;

	ArrayList<KitGraphics> kitsOnKitRobot;

	TreeMap<String, KitGraphics> kitPositions;

	KitRobotAgent kitRobotAgent;

	public KitRobotGraphics(Server s, Agent kra) {
		kitPositions = new TreeMap<String, KitGraphics>();
		initKitPositions();
		server = s;
		kitRobotAgent = (KitRobotAgent) kra;
	}

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

	public void addKit(KitGraphics kg) {
		kitsOnKitRobot.add(kg);
	}

	@Override
	public void msgPlaceKitInInspectionArea(KitGraphics kit) {

		/*
		 * Integer tempI = Search(kit); if (tempI.equals(new Integer(3))) {
		 * server.sendData(new Request("moveKitInLocation1ToInspection",
		 * Constants.KIT_ROBOT_TARGET, null)); positions[2] = kit; } else if
		 * (tempI == 4) { server.sendData(new
		 * Request("moveKitInLocation2ToInspection", Constants.KIT_ROBOT_TARGET,
		 * null)); positions[2] = kit; } else {
		 * System.out.println("Kit isn't in one of the locations"); }
		 */

		for (String kitGraphicsKey : kitPositions.keySet()) {
			KitGraphics tempKitGraphics = (KitGraphics)kitPositions.get(kitGraphicsKey);
			if (tempKitGraphics == null) {
				System.out.println(this.toString() + " tempKitGraphics null");
				System.out.println(this.toString() + kitGraphicsKey);
			}
			if (tempKitGraphics==kit) {
				kitPositions.put(kitGraphicsKey, null);
				if (kitGraphicsKey.equals(Constants.KIT_LOCATION1)) {
				} else {
					server.sendData(new Request(
							Constants.KIT_ROBOT_DISPLAY_PICKS_LOCATION2_TO_INSPECTION,
							Constants.KIT_ROBOT_TARGET, null));
				}
			}

		}
		server.sendData(new Request(
				Constants.KIT_ROBOT_DISPLAY_PICKS_LOCATION1_TO_INSPECTION,
				Constants.KIT_ROBOT_TARGET, null));
		
		kitPositions.put(Constants.KIT_INSPECTION_AREA, kit);


		// TODO Auto-generated method stub
	}

	@Override
	public void msgPlaceKitOnConveyor() {
		kitPositions.put(Constants.KIT_INSPECTION_AREA, null);
		server.sendData(new Request(
				Constants.KIT_ROBOT_DISPLAY_PICKS_INSPECTION_TO_GOOD_CONVEYOR,
				Constants.KIT_ROBOT_TARGET, null));

		// server.sendData(new
		// Request(Constants.CONVEYOR_RECEIVES_KIT_ROBOT_PICK_COMMAND,
		// Constants.KIT_ROBOT_TARGET, null ));
		// TODO Auto-generated method stub
	}

	@Override
	public void receiveData(Request req) {
		String target = req.getTarget();
		String command = req.getCommand();
		Object object = req.getData();

		if (command.equals(Constants.KIT_ROBOT_LOGIC_PICKS_CONVEYOR_TO_LOCATION1)) {
			
			msgPlaceKitOnStand(null, 1);
		} 
		else if(command.equals(Constants.KIT_ROBOT_LOGIC_PICKS_LOCATION1_TO_INSPECTION))
		{
			msgPlaceKitInInspectionArea(new KitGraphics(server));
		
		}
		else if(command.equals(Constants.KIT_ROBOT_LOGIC_PICKS_INSPECTION_TO_GOOD_CONVEYOR))
		{
			msgPlaceKitOnConveyor();
		} 
		else if (command
				.equals(Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND)) {
			server.sendData(new Request(Constants.CONVEYOR_RECEIVE_KIT_COMMAND,
					Constants.CONVEYOR_TARGET, null));
		} else if (command.equals(Constants.KIT_ROBOT_ON_STAND_DONE)) {
			System.out.println("placekitonStandDone sent");
			kitRobotAgent.msgPlaceKitOnStandDone();
		} else if (command.equals(Constants.KIT_ROBOT_ON_CONVEYOR_DONE)) {
			System.out.println("placekitonconveyordone sent");
			kitRobotAgent.msgPlaceKitOnConveyorDone();
		}

		// else if(command.equals())

		// if()
		// TODO Auto-generated method stub
	}

	public void msgPlaceKitOnStand1(KitGraphics kit) {
		// TODO Auto-generated method stub

		kitPositions.put(Constants.KIT_LOCATION1, kit);
		server.sendData(new Request(
				Constants.KIT_ROBOT_DISPLAY_PICKS_CONVEYOR_TO_LOCATION1,
				Constants.KIT_ROBOT_TARGET, null));

	}

	public void msgPlaceKitOnStand2(KitGraphics kit) {
		// TODO Auto-generated method stub

		kitPositions.put(Constants.KIT_LOCATION2, kit);
		server.sendData(new Request(Constants.KIT_ROBOT_DISPLAY_PICKS_CONVEYOR_TO_LOCATION2,
				Constants.KIT_ROBOT_TARGET, null));
	}
	@Override
	public void msgPlaceKitOnStand(KitGraphics kit, int location) {
		if (location == 1) {
			msgPlaceKitOnStand1(kit);
			/*server.sendData(new Request(
					Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND,
					Constants.CONVEYOR_TARGET, null));
		*/
		} else if (location == 2) {
			msgPlaceKitOnStand2(kit);
			/*server.sendData(new Request(
					Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND,
					Constants.CONVEYOR_TARGET, null));
			*/
		}

	}

}
