package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;

/**
 * Contains the logic for the Conveyor object 
 * 
 * @author neetugeo
 */

public class ConveyorGraphics extends DeviceGraphics implements GraphicsInterfaces.ConveyorGraphics {

	private ArrayList<KitGraphics> kitsOnConveyor; // all kits on conveyor
	private Location location;
	private Server server;
	        
	public ConveyorGraphics(Server s){
		location = new Location(0,0);
		kitsOnConveyor = new ArrayList<KitGraphics>();
		server = s;
	} 
	
	public void bringEmptyKit(KitGraphics kg){
		kitsOnConveyor.add(kg);
	} 

	public void giveKitToKitRobot(KitGraphics kg){
		kg.setFull(true);
		server.sendData(new Request(Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND, Constants.CONVEYOR_TARGET, kg));
		//sending the kit to be taken away to KitRobotGraphics
		server.sendData(new Request(Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND, Constants.KIT_ROBOT_TARGET, kg)); 
		kitsOnConveyor.remove(kg);
	} 

	/**
	 * send a completed kit off-screen
	 *
	 * @param kit - a kit must be received from KitRobot before sending it away
	 */
	public void receiveKit(KitGraphics kg){
		//server.sendData(new Request(Constants.CONVEYOR_RECEIVE_KIT_COMMAND, Constants.CONVEYOR_TARGET, kg));
		kitsOnConveyor.add(kg);
	}
	
	public void receiveData(Request r){
     String target = r.getTarget();
     String command = r.getCommand();
     Object object = r.getData();
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
