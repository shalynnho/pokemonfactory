package DeviceGraphics;

import factory.data.Part;

/**
 * The parts robot will be used to move 
 * @author vanshjain
 *
 */

public class PartsRobotGraphics {

	Location initialLocation; // initial location of robot
	Location currentLocation; // current location of robot
	boolean arm1, arm2, arm3, arm4; // whether the arm is full,Parttialized empty
	ArrayList<Part> partArray; // an array of parts that allocates memory for 4 parts
	KitGraphics kit;
	
	public PartsRobotGraphics {
		initialLocation = new Location();
		currentLocation = initialLocation;
		arm1 = false;
		arm2 = false;
		arm3 = false;
		arm4 = false;
		partArray = new ArrayList<Part>();
	}
	
	
	public void pickUpPart(Part part, Location location){
		currentLocation = location;
		Animation(currentLocation, 10);
		partArray.add(part);
		if (!isFullArm1)
			arm1 = true;
		else if (!isFullArm2)
			arm2 = true;
		else if (!isFullArm3)
			arm3 = true;
		else if (!isFullArm4)
			arm4 = true;	
		/**
		 * pickup from nests
		 * goes to a location to pick up a part
		 * be able to hold 4 parts at a time
		 */
	}
	
	public void givePartToKit(Kit, int){
		Animation(Kit.location, 10);
		while (isFullArm1){
			partArray.remove(add);
			if (isFullArm4)
				arm4 = false;
			else if (isFullArm3)
				arm3 = false;
			else if (isFullArm2)
				arm2 = false;
			else if (isFullArm1)
				arm1 = false;
		}
		
		goHome();
		/**
		 * gives the part to the kit
		 * goes to a location to give a part
		 * puts part in a specific location inside the kit
		 */
	}
	
	public void rotateArm(){
		/**
		 * rotates the arm
		 */
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
		Animation(initialLocation, 10);
		currentLocation = initialLocation;
		/**
		 * depends on specific graphics implementation
		 * sends the robot back to the initial location
		 * makes sure that the robot doesn't collide
		 * then send message that the action has been performed(either part is picked up or it's given to a kit)
		 */
	}
	
	public Location getLocation(){
		return currentLocation;
		/**
		 * returns the current Location
		 */
	}
	
	
}
