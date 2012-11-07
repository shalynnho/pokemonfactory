package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.Agent;
import agent.PartsRobotAgent;


//import factory.data.Kit;



public class PartsRobotGraphics implements GraphicsInterfaces.PartsRobotGraphics, DeviceGraphics {

	Location initialLocation; // initial location of robot
	Location currentLocation; // current location of robot
	boolean arm1, arm2, arm3, arm4; // whether the arm is full, initialized empty
	ArrayList<PartGraphics> partArray; // an array of parts that allocates memory for 4 parts
	KitGraphics kit;
	int i;
	boolean nest1, nest2;
	private Server server;
	private PartsRobotAgent partsRobotAgent;
	
	public PartsRobotGraphics(Server s, Agent a ) {
		partsRobotAgent = (PartsRobotAgent)a;
		initialLocation = new Location(250,450);
		currentLocation = initialLocation;
		arm1 = false;
		arm2 = false;
		arm3 = false;
		arm4 = false;
		partArray = new ArrayList<PartGraphics>();
		i = 0;
		nest1 = false;
		nest2 = false;
		server = s;
	}
	
	public void goToNest1(){
		nest1 = true;
		nest2 = false;
		server.sendData(new Request(Constants.PARTS_ROBOT_MOVE_TO_NEST1_COMMAND, Constants.PARTS_ROBOT_TARGET, null));
	}
	
	public void goToNest2(){
		nest1 = false;
		nest2 = true;
		server.sendData(new Request(Constants.PARTS_ROBOT_MOVE_TO_NEST2_COMMAND, Constants.PARTS_ROBOT_TARGET, null));
	}
	/*public void pickUpPart(Part part){
		//currentLocation = location;
		//Animation(currentLocation, 10);
		PartGraphics pg = part.partGraphics;
		partArray.add(pg);
		rotateArm();
		server.sendData(new Request(Constants.PARTS_ROBOT_PICKUP_COMMAND, Constants.PARTS_ROBOT_TARGET, null));
		/**
		 * pickup from nests
		 * goes to a location to pick up a part
		 * be able to hold 4 parts at a time
		 
	}
	public void pickUpPart(){
		server.sendData(new Request(Constants.PARTS_ROBOT_PICKUP_COMMAND, Constants.PARTS_ROBOT_TARGET, null));
		System.out.println("pickup");
	}*/
	public void pickUpPart(PartGraphics pg) {
		// TODO Auto-generated method stub
		partArray.add(pg);
		rotateArm();
		
		// V0 hack
		Location tempLoc = new Location(550, 100);
		server.sendData(new Request(Constants.PARTS_ROBOT_PICKUP_COMMAND, Constants.PARTS_ROBOT_TARGET, tempLoc));
		//pg.setLocation()
	}
	
	/*public void givePartToKit(PartGraphics part,KitGraphics k){
		//Animation(k.location, 10);
		partArray.remove(part);
		derotateArm();
		server.sendData(new Request(Constants.PARTS_ROBOT_GIVE_COMMAND, Constants.PARTS_ROBOT_TARGET, null));
		/**
		 * gives the part to the kit
		 * goes to a location to give a part
		 * puts part in a specific location inside the kit
		 
	}*/
	public void givesPartToKit(){
		server.sendData(new Request(Constants.PARTS_ROBOT_GIVE_COMMAND, Constants.PARTS_ROBOT_TARGET, null));
	}
	public void goesToKit(){
		System.out.println("kit");
	server.sendData(new Request(Constants.PARTS_ROBOT_GO_KIT_COMMAND, Constants.PARTS_ROBOT_TARGET, null));
	}
	
	public void rotateArm(){
		if (!isFullArm1())
			arm1 = true;
		else if (!isFullArm2())
			arm2 = true;
		else if (!isFullArm3())
			arm3 = true;
		else if (!isFullArm4())
			arm4 = true;
		i++;
		
		/**
		 * rotates the arm
		 */
	}
	
	public void derotateArm(){
		if (isFullArm4())
			arm4 = false;
		else if (isFullArm3())
			arm3 = false;
		else if (isFullArm2())
			arm2 = false;
		else if (isFullArm1())
			arm1 = false;
		
		i--;
	}
	
	 
	public boolean isFullArm1(){
		return arm1;
		/**
		 *  returns true if arm1 is full
		 */
	}
	
	
	public boolean isFullArm2(){
		return arm2;
		/**
		 *  returns true if arm2 is full
		 */
	}
	
	public boolean isFullArm3(){
		return arm3;
		/**
		 *  returns true if arm3 is full
		 */
	}
	
	public boolean isFullArm4(){
		return arm4;
		/**
		 * returns true if arm4 is full
		 */
	}
	
	public void goHome(){
		//Animation(initialLocation, 10);
		currentLocation = initialLocation;
		/**
		 * depends on specific graphics implementation
		 * sends the robot back to the initial location
		 * makes sure that the robot doesn't collide
		 * then send message that the action has been performed(either part is picked up or it's given to a kit)
		 */
		System.out.println("gohome");
		server.sendData(new Request(Constants.PARTS_ROBOT_GO_HOME_COMMAND, Constants.PARTS_ROBOT_TARGET, null));
	}
	
	public Location getLocation(){
		return currentLocation;
		/**
		 * returns the current Location
		 */
	}
	
	public void receiveData(Request req) {
		/*if (req.getCommand().equals(Constants.PARTS_ROBOT_MOVE_TO_NEST1_COMMAND)){
			System.out.println("got to NEST1");
			goToNest1();
			//System.out.println("got to NEST1");
		} else if (req.getCommand().equals(Constants.PARTS_ROBOT_MOVE_TO_NEST2_COMMAND)){
			goToNest2();
		} else if(req.getCommand().equals(Constants.PARTS_ROBOT_PICKUP_COMMAND)){
			pickUpPart();
		} else if(req.getCommand().equals(Constants.PARTS_ROBOT_GO_KIT_COMMAND)){
			goesToKit();
			System.out.println("gives part to kit");
		} else if(req.getCommand().equals(Constants.PARTS_ROBOT_GO_HOME_COMMAND)){
			goHome();
			System.out.println("gohome");
		} else if(req.getCommand().equals(Constants.PARTS_ROBOT_GIVE_COMMAND)){
			givesPartToKit();
		} else */if (req.equals(Constants.PARTS_ROBOT_RECEIVE_PART_COMMAND + Constants.DONE_SUFFIX)) {
		    partsRobotAgent.msgPickUpPartDone();
		} else if (req.equals(Constants.PARTS_ROBOT_GIVE_COMMAND + Constants.DONE_SUFFIX)) {
		    partsRobotAgent.msgGivePartToKitDone();
		}
	}

	
	
	@Override
	public void givePartToKit(PartGraphics part, KitGraphics kit) {
		// TODO Auto-generated method stub
		for(PartGraphics p : partArray)
		{
			if (p == part)
				partArray.remove(p);
		}
		server.sendData(new Request(Constants.PARTS_ROBOT_GIVE_COMMAND, Constants.PARTS_ROBOT_TARGET, kit.getLocation()));
		
		
	}	
	
}
