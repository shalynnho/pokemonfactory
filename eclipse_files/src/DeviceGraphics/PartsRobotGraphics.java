package DeviceGraphics;

/**
 * The parts robot will be used to move 
 * @author vanshjain
 *
 */

public class PartsRobotGraphics {

	Location initialLocation; // initial location of robot
	Location currentLocation; // current location of robot
	boolean arm1, arm2, arm3, arm4; // whether the arm is full, initialized empty
	ArrayList<Part> partArray; // an array of parts that allocates memory for 4 parts
	
	public void pickUpPart(Part part, Location location){
		/**
		 * pickup from nests
		 * goes to a location to pick up a part
		 * be able to hold 4 parts at a time
		 */
	}
	
	public void givePartToKit(Kit, int){
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
	
	
	 
	public  boolean isFullArm1(arm1){
		/**
		 *  returns true if arm1 is full
		 */
	}
	
	public boolean isFullArm2(arm2){
		/**
		 *  returns true if arm2 is full
		 */
	}
	
	public boolean isFullArm3(arm3){
		/**
		 *  returns true if arm3 is full
		 */
	}
	
	public boolean isFullArm4(arm4){
		/**
		 * returns true if arm4 is full
		 */
	}
	
	public void goHome(){
		/**
		 * depends on specific graphics implementation
		 * sends the robot back to the initial location
		 * makes sure that the robot doesn't collide
		 * then send message that the action has been performed(either part is picked up or it's given to a kit)
		 */
	}
	
	public Location getLocation(){
		/**
		 * returns the current Location
		 */
	}
	
	
}
