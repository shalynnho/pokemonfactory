package DeviceGraphicsDisplay;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JComponent;

import agent.data.PartType;


import Networking.*;
import Utils.Constants;
import Utils.Location;

public class PartsRobotDisplay extends DeviceGraphicsDisplay {
	
	private static Image partsRobotImage;
	private static Image armImage;
	
	
	private ArrayList<PartGraphicsDisplay> partArrayGraphics;
	
	private Location loc, kitloc;
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
	private boolean nest1, nest2, gohome, gokit;
	private boolean arm1, arm2, arm3, arm4;
	private boolean pickup, givekit;
	
	private Location partStartLoc1,partStartLoc2,partStartLoc3,partStartLoc4;
	
	private int I;
	
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
		gohome = false;
		gokit = false;
		
		partStartLoc1 = new Location(currentLocation.getX(),currentLocation.getY());//new Location(initialLocation.getX()+30, initialLocation.getY()+30);
		//partStartLoc1.setX(currentLocation.getX()+30);
		//partStartLoc1.setY(currentLocation.getY());
		partStartLoc2 = new Location(currentLocation.getX()+30,currentLocation.getY());
		partStartLoc3 = new Location(currentLocation.getX(),currentLocation.getY()+30);
		partStartLoc4 = new Location(currentLocation.getX()+30,currentLocation.getY()+30);
		
		arm1 = false;
		arm2 = false;
		arm3 = false;
		arm4 = false;
		
		I = 0;
		
		pickup = false;
		givekit = false;
				
	}
	
	public void draw(JComponent c, Graphics2D g){
		if(pickup){
			for (int i = 0; i < 5; i++){
				if(currentLocation.getX()<loc.getX()){
				currentLocation.incrementX(1);
				partStartLoc1.setX(currentLocation.getX());
				partStartLoc2.setX(currentLocation.getX()+30);
				partStartLoc3.setX(currentLocation.getX());
				partStartLoc4.setX(currentLocation.getX()+30);
				} else if(currentLocation.getX()>loc.getX()){
					currentLocation.incrementX(-1);
					partStartLoc1.setX(currentLocation.getX());
					partStartLoc2.setX(currentLocation.getX()+30);
					partStartLoc3.setX(currentLocation.getX());
					partStartLoc4.setX(currentLocation.getX()+30);
					}
				else if(currentLocation.getY()>loc.getY()){
				currentLocation.incrementY(-1);
				partStartLoc1.setY(currentLocation.getY());
				partStartLoc2.setY(currentLocation.getY());
				partStartLoc3.setY(currentLocation.getY()+30);
				partStartLoc4.setY(currentLocation.getY()+30);
				}else if(currentLocation.getY()<loc.getY()){
					currentLocation.incrementY(1);
					partStartLoc1.setY(currentLocation.getY());
					partStartLoc2.setY(currentLocation.getY());
					partStartLoc3.setY(currentLocation.getY()+30);
					partStartLoc4.setY(currentLocation.getY()+30);
					}
				g.drawImage(partsRobotImage, currentLocation.getX(), currentLocation.getY(), c);
				if(currentLocation.getX() == loc.getX() && currentLocation.getY() == loc.getY()){
					System.out.println("at parts location");
					pickUpPart();
				}
			    
			}
			/*int i;
			for (i=0;i<100;i++){
			g.drawImage(partsRobotImage, initialLocation.getX()+i, initialLocation.getY(), c);
			
			}
			int j;
			for (j=0;j<100;j++)
				g.drawImage(partsRobotImage, initialLocation.getX(), initialLocation.getY()-j, c);
		*/
		/*else if(nest2){
			for (int i = 0; i < 5; i++){
				if(currentLocation.getX()<550){
				currentLocation.incrementX(1);
				partStartLoc1.setX(currentLocation.getX());
				partStartLoc2.setX(currentLocation.getX()+30);
				partStartLoc3.setX(currentLocation.getX());
				partStartLoc4.setX(currentLocation.getX()+30);
				}
				else if(currentLocation.getY()>175){
				currentLocation.incrementY(-1);
				partStartLoc1.setY(currentLocation.getY());
				partStartLoc2.setY(currentLocation.getY());
				partStartLoc3.setY(currentLocation.getY()+30);
				partStartLoc4.setY(currentLocation.getY()+30);
				}
				else if(currentLocation.getY()<175){
					currentLocation.incrementY(1);
					partStartLoc1.setY(currentLocation.getY());
					partStartLoc2.setY(currentLocation.getY());
					partStartLoc3.setY(currentLocation.getY()+30);
					partStartLoc4.setY(currentLocation.getY()+30);
				}
				g.drawImage(partsRobotImage, currentLocation.getX(), currentLocation.getY(), c);
			}*/
		}else if (givekit) {
			for (int i = 0; i < 5; i++){
				if(currentLocation.getX()>kitloc.getX()){
				currentLocation.incrementX(-1);
				partStartLoc1.setX(currentLocation.getX());
				partStartLoc2.setX(currentLocation.getX()+30);
				partStartLoc3.setX(currentLocation.getX());
				partStartLoc4.setX(currentLocation.getX()+30);
				}
				else if(currentLocation.getY()>kitloc.getY()){
				currentLocation.incrementY(-1);
				partStartLoc1.setY(currentLocation.getY());
				partStartLoc2.setY(currentLocation.getY());
				partStartLoc3.setY(currentLocation.getY()+30);
				partStartLoc4.setY(currentLocation.getY()+30);
				}
				else if(currentLocation.getY()<kitloc.getY()){
					currentLocation.incrementY(1);
					partStartLoc1.setY(currentLocation.getY());
					partStartLoc2.setY(currentLocation.getY());
					partStartLoc3.setY(currentLocation.getY()+30);
					partStartLoc4.setY(currentLocation.getY()+30);
				}
				
				g.drawImage(partsRobotImage, currentLocation.getX(), currentLocation.getY(), c);
				
			}
			if (currentLocation.getX() == kitloc.getX() && currentLocation.getY() == kitloc.getY()){
				givePart();
			}
		} else if (gohome) {
			for (int i = 0; i < 5; i++){
				if(currentLocation.getY()<450){
					currentLocation.incrementY(1);
					partStartLoc1.setY(currentLocation.getY());
					partStartLoc2.setY(currentLocation.getY());
					partStartLoc3.setY(currentLocation.getY()+30);
					partStartLoc4.setY(currentLocation.getY()+30);
				}
				else if(currentLocation.getX()>250){
				currentLocation.incrementX(-1);
				partStartLoc1.setX(currentLocation.getX());
				partStartLoc2.setX(currentLocation.getX()+30);
				partStartLoc3.setX(currentLocation.getX());
				partStartLoc4.setX(currentLocation.getX()+30);
				}
				else if(currentLocation.getX()<250){
				currentLocation.incrementX(1);
				partStartLoc1.setX(currentLocation.getX());
				partStartLoc2.setX(currentLocation.getX()+30);
				partStartLoc3.setX(currentLocation.getX());
				partStartLoc4.setX(currentLocation.getX()+30);
				}
				
				
				g.drawImage(partsRobotImage, currentLocation.getX(), currentLocation.getY(), c);
			}
		}else if(!nest1 && !nest2){
		
			g.drawImage(partsRobotImage, initialLocation.getX(), initialLocation.getY(), c);
		}
		
		for (int i = 0; i < partArrayGraphics.size(); i++){
			PartGraphicsDisplay pgd = partArrayGraphics.get(i);
			pgd.draw(c, g );
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
		gohome = false;
		gokit = false;
		
	}
	
	public void goToNest2(){
		
		nest2 = true;
		nest1 = !nest2;
		gohome = false;
		gokit = false;
		
	}
	
	public void goHome(){
		nest1 = false;
		nest2 = false;
		gohome = true;
		gokit = false;
	}
	
	public void goKit(){
		nest1 = false;
		nest2 = false;
		gohome = false;
		gokit = true;
		System.out.println("gokit = true;");
	}
	public void pickUp(){
		pickup = true;
		givekit = false;
	}
	
	public void giveKit(){
		givekit = true;
	}
	public void pickUpPart(){
		//partArrayGraphics.add(pgd);
		PartType partType = PartType.B;
		PartGraphicsDisplay pgd = new PartGraphicsDisplay(partType);
		
		if (!arm1){
			arm1 = true;
			pgd.setLocation(partStartLoc1);
			partArrayGraphics.add(pgd);
			I++;
			System.out.println("picked up part1");
			partsRobotClient.sendData(new Request(
				    Constants.PARTS_ROBOT_RECEIVE_PART_COMMAND + Constants.DONE_SUFFIX, 
				    Constants.PARTS_ROBOT_TARGET,
				    null));
			pickup = false;
		}
		else if (!arm2){
			arm2 = true;
			pgd.setLocation(partStartLoc2);
			partArrayGraphics.add(pgd);
			I++;
			System.out.println("picked up part2");
			partsRobotClient.sendData(new Request(
				    Constants.PARTS_ROBOT_RECEIVE_PART_COMMAND + Constants.DONE_SUFFIX, 
				    Constants.PARTS_ROBOT_TARGET,
				    null));
			pickup = false;
		}
		else if (!arm3){
			arm3 = true;
			pgd.setLocation(partStartLoc3);
			partArrayGraphics.add(pgd);
			I++;
			System.out.println("picked up part3");
			partsRobotClient.sendData(new Request(
				    Constants.PARTS_ROBOT_RECEIVE_PART_COMMAND + Constants.DONE_SUFFIX, 
				    Constants.PARTS_ROBOT_TARGET,
				    null));
			pickup = false;
		}
		else if (!arm4){
			arm4 = true;
			pgd.setLocation(partStartLoc4);
			partArrayGraphics.add(pgd);
			I++;
			System.out.println("picked up part4");
			partsRobotClient.sendData(new Request(
				    Constants.PARTS_ROBOT_RECEIVE_PART_COMMAND + Constants.DONE_SUFFIX, 
				    Constants.PARTS_ROBOT_TARGET,
				    null));
			pickup = false;
		}
		else
			System.out.println("Can't pick up more parts.");
	}
	
	public void givePart(){
		
		if (arm4){
			arm4 = false;
			I--;
			partArrayGraphics.remove(I);
			System.out.println("gave part4");
			partsRobotClient.sendData(new Request(
				    Constants.PARTS_ROBOT_GIVE_PART_COMMAND + Constants.DONE_SUFFIX, 
				    Constants.PARTS_ROBOT_TARGET,
				    null));
		}
		else if (arm3){
			arm3 = false;
			I--;
			partArrayGraphics.remove(I);
			System.out.println("gave part3");
			partsRobotClient.sendData(new Request(
				    Constants.PARTS_ROBOT_GIVE_PART_COMMAND + Constants.DONE_SUFFIX, 
				    Constants.PARTS_ROBOT_TARGET,
				    null));
		}
		else if (arm2){
			arm2 = false;
			I--;
			partArrayGraphics.remove(I);
			System.out.println("gave part2");
			partsRobotClient.sendData(new Request(
				    Constants.PARTS_ROBOT_GIVE_PART_COMMAND + Constants.DONE_SUFFIX, 
				    Constants.PARTS_ROBOT_TARGET,
				    null));
		}
		else if (arm1){
			arm1 = false;
			I--;
			partArrayGraphics.remove(I);
			System.out.println("gave part1");
			partsRobotClient.sendData(new Request(
				    Constants.PARTS_ROBOT_GIVE_PART_COMMAND + Constants.DONE_SUFFIX, 
				    Constants.PARTS_ROBOT_TARGET,
				    null));
		}
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
			System.out.println("got to nest1");
		} else if (r.getCommand().equals(Constants.PARTS_ROBOT_MOVE_TO_NEST2_COMMAND)) {
			goToNest2();
		} else if (r.getCommand().equals(Constants.PARTS_ROBOT_GO_HOME_COMMAND)){
			goHome();
			System.out.println("GOHOME");
		} else if (r.getCommand().equals(Constants.PARTS_ROBOT_GO_KIT_COMMAND)){
			goKit();
			System.out.println("gokit");
		}else if (r.getCommand().equals(Constants.PARTS_ROBOT_GIVE_COMMAND)){
			kitloc = (Location) r.getData();
			giveKit();
			/*if(!arm4)
				partArrayGraphics.remove(3);
			else if (!arm3)
				partArrayGraphics.remove(2);
			else if (!arm2)
				partArrayGraphics.remove(1);
			else if (!arm1)
				partArrayGraphics.remove(0);*/
			
			
		}else if (r.getCommand().equals(Constants.PARTS_ROBOT_PICKUP_COMMAND)){
			loc = (Location) r.getData();
			pickUp();
			System.out.println("before pick up");
			
			//PartType partType = (PartType) r.getData();
			/*PartType partType = PartType.B;
			PartGraphicsDisplay pgd = new PartGraphicsDisplay(partType);
			if(arm4){
				pgd.setLocation(partStartLoc4);
				
			}
			else if (arm3){
				pgd.setLocation(partStartLoc3);
				
			}
			else if (arm2){
				pgd.setLocation(partStartLoc2);
				
			}
			else if (arm1){
				pgd.setLocation(partStartLoc1);
				
			}
			partArrayGraphics.add(pgd);*/
			
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
