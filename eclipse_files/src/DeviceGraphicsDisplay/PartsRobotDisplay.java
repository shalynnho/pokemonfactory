package DeviceGraphicsDisplay;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JComponent;

import Networking.*;
import Utils.Constants;
import Utils.Location;

public class PartsRobotDisplay extends DeviceGraphicsDisplay {
	
	private static Image partsRobotImage;
	private static Image armImage;
	
	private ArrayList<PartGraphicsDisplay> partArrayGraphics;
	
	private Location initialLocation;
	private Location currentLocation;
	private Location arm1InitialLocation;
	private Location arm2InitialLocation;
	private Location arm3InitialLocation;
	private Location arm4InitialLocation;
	private Location arm1CurrentLocation;
	private Location arm2CurrentLocation;
	private Location arm3CurrentLocation;
	private Location arm4CurrentLocation;
	
	private Client partsRobotClient;
	
	private boolean rotate;
	private boolean nest1, nest2;
	
	public PartsRobotDisplay(Client prc, Location loc){
		partsRobotClient = prc;
		
		initialLocation = loc; //new Location(250,450);
		currentLocation = initialLocation;
		
		arm1InitialLocation = new Location(325, 495);
		arm2InitialLocation = new Location(286, 450);
		arm3InitialLocation = new Location(250, 495);
		arm4InitialLocation = new Location(286, 540);
		arm1CurrentLocation = arm1InitialLocation;
		arm2CurrentLocation = arm2InitialLocation;
		arm3CurrentLocation = arm3InitialLocation;
		arm4CurrentLocation = arm4InitialLocation;
		
		partsRobotImage = Toolkit.getDefaultToolkit().getImage("src/images/Square.jpg");
		armImage = Toolkit.getDefaultToolkit().getImage("src/image/Arm.png");
		partArrayGraphics = new ArrayList<PartGraphicsDisplay>();
		
		rotate = false;
		nest1 = false;
		nest2 = false;
	}
	
	public void draw(JComponent c, Graphics2D g){
		if(nest1){
			//for (i=0;i<95;i++)
			g.drawImage(partsRobotImage, 300, 300, c);
		}
		else if(nest2){
			//g.drawImage(partsRobotImage, 340, 300, c);
		}else if(!nest1 && !nest2){
			g.drawImage(partsRobotImage, initialLocation.getX(), initialLocation.getY(), c);
		}
		/*AffineTransform originalTransform = g.getTransform();
		if(getRotate()){
			
		}
			//how to rotate on axis
		int i = 0;*/
		
	}
	public void goToNest1(){
		nest1 = true;
		nest2 = !nest1;
		
	}
	
	public void goToNest2(){
		
		nest2 = true;
		nest1 = !nest2;
		
	}
	

	
	public void pickUpPart(PartGraphicsDisplay pgd){
		partArrayGraphics.add(pgd);
	}
	
	/*public void givePartToKit(PartGraphicsDisplay part){
		partArrayGraphics.remove();
	}*/
	
	public void rotateArm(){
		rotate = true;
	}
	
	public boolean getRotate(){
		return rotate;
	}
	
	public void movePartsRobot(){
		
	}

	

	@Override
	public void receiveData(Request r) {
		if (r.getCommand().equals(Constants.PARTS_ROBOT_MOVE_TO_NEST1_COMMAND)) {
			goToNest1();
		} else if (r.getCommand().equals(Constants.PARTS_ROBOT_MOVE_TO_NEST2_COMMAND)) {
			goToNest2();
		}
	}

	@Override
	public void setLocation(Location newLocation) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
