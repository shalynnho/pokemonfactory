package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.Agent;
import agent.KitRobotAgent;
import agent.data.Kit;

public class KitRobotGraphics implements GraphicsInterfaces.KitRobotGraphics,
		DeviceGraphics {

	private Kit kit;
	private Server server;

	KitGraphics[] positions;

	ArrayList<KitGraphics> kitsOnKitRobot;

	KitRobotAgent kitRobotAgent;

	public KitRobotGraphics(Server s, Agent kra) {
		// qw
		positions = new KitGraphics[4];

		for (int i = 0; i < 4; i++) {
			positions[i] = new KitGraphics(s);
			positions[i] = new KitGraphics(server);
		}

		server = s;

		kitRobotAgent = (KitRobotAgent) kra;
	}

	public Integer Search(KitGraphics kit) {
		for (Integer i = 0; i < 4; i++) {
			if (kit.equals(positions)) {
				return i;
			}

		}

		return 0;
	}

	public void addKit(KitGraphics kg) {
		kitsOnKitRobot.add(kg);
	}

	public void giveKitToConveyor(KitGraphics kg) {
		server.sendData(new Request("GiveKitToConveyor",
				Constants.CONVEYOR_TARGET, null));
		kitsOnKitRobot.remove(kg);
	}

	@Override
	public void msgPlaceKitInInspectionArea(KitGraphics kit) {
		
		/*Integer tempI = Search(kit);

		if (tempI.equals(new Integer(3))) {
			server.sendData(new Request("moveKitInLocation1ToInspection",
					Constants.KIT_ROBOT_TARGET, null));
			positions[2] = kit;

		} else if (tempI == 4) {
			server.sendData(new Request("moveKitInLocation2ToInspection",
					Constants.KIT_ROBOT_TARGET, null));

			positions[2] = kit;
		} else {
			System.out.println("Kit isn't in one of the locations");
		}
		*/
		
		kitRobotAgent.msgPlaceKitInInspectionAreaDone();
		// TODO Auto-generated method stub
	}

	@Override
	public void msgPlaceKitOnConveyor() {

		server.sendData(new Request(
				Constants.KIT_ROBOT_DISPLAY_PICKS_LOCATION1_TO_CONVEYOR,
				Constants.KIT_ROBOT_TARGET, null));

		positions[1] = positions[2];
		positions[2] = null;
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

		if (command.equals(Constants.KIT_ROBOT_LOGIC_PICKS_CONVEYOR)) {
			
			msgPlaceKitOnStand(null, 1);

		} else if (command
				.equals(Constants.KIT_ROBOT_LOGIC_PICKS_lOCATION1_TO_CONVEYOR)) {

			msgPlaceKitOnConveyor();
			// server.sendData(new
			// Request(Constants.CONVEYOR_RECEIVE_KIT_COMMAND,
			// Constants.CONVEYOR_TARGET,null));
		} else if (command
				.equals(Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND)) {
			server.sendData(new Request(Constants.CONVEYOR_RECEIVE_KIT_COMMAND,
					Constants.CONVEYOR_TARGET, null));
		}
		else if(command.equals(Constants.KIT_ROBOT_ON_STAND_DONE))
		{
			System.out.println("placekitonStandDone sent");
			kitRobotAgent.msgPlaceKitOnStandDone();
		}
		else if (command.equals(Constants.KIT_ROBOT_ON_CONVEYOR_DONE))
		{
			System.out.println("placekitonconveyordone sent");
			kitRobotAgent.msgPlaceKitOnConveyorDone();
		}

		// else if(command.equals())

		// if()
		// TODO Auto-generated method stub
	}

	public void msgPlaceKitOnStand1(KitGraphics kit) {
		// TODO Auto-generated method stub
		positions[3] = kit;
		server.sendData(new Request(Constants.KIT_ROBOT_DISPLAY_PICKS_CONVEYOR,
				Constants.KIT_ROBOT_TARGET, null));
	}

	public void sendMessageBack(Server s) {
		s.sendData(new Request(Constants.CONVEYOR_RECEIVE_KIT_COMMAND,
				Constants.CONVEYOR_TARGET, null));
	}

	public void msgPlaceKitOnStand2(KitGraphics kit) {
		// TODO Auto-generated method stub
		positions[4] = kit;
		server.sendData(new Request("moveKitToStand2",
				Constants.KIT_ROBOT_TARGET, null));
	}

	@Override
	public void msgPlaceKitOnStand(KitGraphics kit, int location) {
		if (location == 1) {
			msgPlaceKitOnStand1(kit);
			server.sendData(new Request(
					Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND,
					Constants.CONVEYOR_TARGET, null));
		} else if (location == 2) {
			msgPlaceKitOnStand2(kit);
		}

	}

}
