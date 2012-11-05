package DeviceGraphicsDisplay;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JComponent;

import Networking.*;
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
	
	public PartsRobotDisplay(Client prc){
		partsRobotClient = prc;
		
		initialLocation = new Location(250,450);
		currentLocation = initialLocation;
		
		arm1InitialLocation = new Location(325, 495);
		arm2InitialLocation = new Location(286, 450);
		arm3InitialLocation = new Location(250, 495);
		arm4InitialLocation = new Location(286, 540);
		arm1CurrentLocation = arm1InitialLocation;
		arm2CurrentLocation = arm2InitialLocation;
		arm3CurrentLocation = arm3InitialLocation;
		arm4CurrentLocation = arm4InitialLocation;
		
		partsRobotImage = Toolkit.getDefaultToolkit().getImage("src/images/PartsRobot.png");
		armImage = Toolkit.getDefaultToolkit().getImage("src/image/Arm.png");
		partArrayGraphics = new ArrayList<PartGraphicsDisplay>();
		
		rotate = false;
	}
	
	public void paintComponent(JComponent c, Graphics2D g){
		g.drawImage(partsRobotImage, initialLocation.getX(), initialLocation.getY(), c);
		AffineTransform originalTransform = g.getTransform();
		if(getRotate()){
			
		}
			//how to rotate on axis
		
		if(goToNest1()){
			g.drawImage(partsRobotImage, 340, 210, c);
		}
		else if(goToNest2()){
			g.drawImage(partsRobotImage, 340, 300, c);
		}
	}
	public boolean goToNest1(){
		
		return true;
	}
	
	public boolean goToNest2(){
		
		return true;
	}
	
	public void requestData(Request r){
		
	}
	
	public void pickUpPart(PartGraphicsDisplay pgd){
		partArrayGraphics.add(pgd);
	}
	
	public void givePartToKit(PartGraphicsDisplay part){
		partArrayGraphics.remove();
	}
	
	public void rotateArm(){
		rotate = true;
	}
	
	public boolean getRotate(){
		return rotate;
	}
	
	public void movePartsRobot(){
		
	}

	@Override
	public void draw(JComponent c, Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveData(Request req) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocation(Location newLocation) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
