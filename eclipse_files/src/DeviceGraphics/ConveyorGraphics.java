package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.StandAgent;

/**
 * Contains the logic for the Conveyor object 
 * 
 * @author neetugeo
 */

public class ConveyorGraphics implements GraphicsInterfaces.ConveyorGraphics, DeviceGraphics {

	private ArrayList<KitGraphics> kitsOnConveyor; // all kits on conveyor
	private ArrayList<KitGraphics> kitsToLeave;
	private Location location;
	private Server server;
	private int velocity;
	
	
	public ConveyorGraphics(Server s){
		location = new Location(0,0);
		kitsOnConveyor = new ArrayList<KitGraphics>();
		kitsToLeave = new ArrayList<KitGraphics>();
		server = s;
		velocity = 1;
	} 
	
	public void bringEmptyKit(KitGraphics kg){
		kitsOnConveyor.add(kg);
		server.sendData(Constants.CONVEYOR_MAKE_NEW_KIT_COMMAND, Constants.CONVEYOR_TARGET, null);
	} 

	public void giveKitToKitRobot(){
		
		//sending the kit to be taken away to KitRobotGraphics
		server.sendData(new Request(Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND, Constants.CONVEYOR_TARGET, null));  //temporary command name until Kit Robot finalized
		//server.sendData(new Request("GetThisKit", ))
		kitsOnConveyor.remove(0);
	} 

	/**
	 * send a completed kit off-screen
	 *
	 * @param kit - a kit must be received from KitRobot before sending it away
	 */
	public void receiveKit(KitGraphics kg){
		kitsOnConveyor.add(kg);
	}
	
	public void receiveData(Request r){
		String target = r.getTarget();
		String command = r.getCommand();
		Object object = r.getData();
		
     
		if(target.equals(Constants.CONVEYOR_TARGET)) {
			if(command.equals(Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND)) {
				//parsing object to kit object
				
				giveKitToKitRobot();	
			}			
			else if (command.equals(Constants.CONVEYOR_RECEIVE_KIT_COMMAND)) {
				System.out.println("Conveyor receives signal from kit");	
				kitsToLeave.add(new KitGraphics(server));
				server.sendData(new Request(Constants.CONVEYOR_RECEIVE_KIT_COMMAND, Constants.CONVEYOR_TARGET, null));
			
			} else if (command.equals(Constants.CONVEYOR_CHANGE_VELOCITY_COMMAND)) {
				//need to somehow send an integer to change the velocity
			} else if (command.equals(Constants.CONVEYOR_SEND_ANIMATION_COMMAND)) {
				//still not quite sure how to implement this yet
			} else if (command.equals(Constants.CONVEYOR_MAKE_NEW_KIT_COMMAND)) {
				//bringEmptyKit(new KitGraphics(server));
				//server.sendData(new Request(Constants.CONVEYOR_MAKE_NEW_KIT_COMMAND, Constants.CONVEYOR_TARGET, null));
				StandAgent stand = (StandAgent)server.agents.get("Stand");
				stand.msgMakeKits(1);
			}
		}
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
	
}
