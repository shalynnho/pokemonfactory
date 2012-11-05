package DeviceGraphicsDisplay;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JComponent;

import factory.data.PartType;

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
	private boolean nest1, nest2, gohome, gokit;
	private boolean arm1, arm2, arm3, arm4;
	
	private Location partStartLoc1,partStartLoc2,partStartLoc3,partStartLoc4;
	
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
		
		partStartLoc1 = new Location(290,470);
		partStartLoc2 = new Location(286,450);
		partStartLoc3 = new Location(250,495);
		partStartLoc4 = new Location(286,540);
		
		arm1 = false;
		arm2 = false;
		arm3 = false;
		arm4 = false;
				
	}
	
	public void draw(JComponent c, Graphics2D g){
		if(nest1){
			for (int i = 0; i < 5; i++){
				if(initialLocation.getX()<550)
				initialLocation.incrementX(1);
				else if(initialLocation.getY()>100)
				initialLocation.incrementY(-1);
				g.drawImage(partsRobotImage, initialLocation.getX(), initialLocation.getY(), c);
			}
			/*int i;
			for (i=0;i<100;i++){
			g.drawImage(partsRobotImage, initialLocation.getX()+i, initialLocation.getY(), c);
			
			}
			int j;
			for (j=0;j<100;j++)
				g.drawImage(partsRobotImage, initialLocation.getX(), initialLocation.getY()-j, c);
		*/}
		else if(nest2){
			for (int i = 0; i < 5; i++){
				if(initialLocation.getX()<550)
				initialLocation.incrementX(1);
				else if(initialLocation.getY()>175)
				initialLocation.incrementY(-1);
				else if(initialLocation.getY()<175)
					initialLocation.incrementY(1);
				g.drawImage(partsRobotImage, initialLocation.getX(), initialLocation.getY(), c);
			}
		}else if (gokit) {
			for (int i = 0; i < 5; i++){
				if(initialLocation.getX()>100)
				initialLocation.incrementX(-1);
				else if(initialLocation.getY()>230)
				initialLocation.incrementY(-1);
				else if(initialLocation.getY()<230)
					initialLocation.incrementY(1);
				
				g.drawImage(partsRobotImage, initialLocation.getX(), initialLocation.getY(), c);
			}
		}else if (gohome){
			for (int i = 0; i < 5; i++){
				if(initialLocation.getY()<450)
					initialLocation.incrementY(1);
				else if(initialLocation.getX()>250)
				initialLocation.incrementX(-1);
				else if(initialLocation.getX()<250)
				initialLocation.incrementX(1);
				
				
				g.drawImage(partsRobotImage, initialLocation.getX(), initialLocation.getY(), c);
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
	
	public void pickUpPart(){
		//partArrayGraphics.add(pgd);
		if (!arm1){
			arm1 = true;
			
		}
		else if (!arm2)
			arm2 = true;
		else if (!arm3)
			arm3 = true;
		else if (!arm4)
			arm4 = true;
		else
			System.out.println("Can't pick up more parts.");
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
		}else if (r.getCommand().equals(Constants.PARTS_ROBOT_PICKUP_COMMAND)){
			pickUpPart();
			//PartType partType = (PartType) r.getData();
			PartType partType = PartType.B;
			PartGraphicsDisplay pgd = new PartGraphicsDisplay(partType);
			if(arm1)
				pgd.setLocation(partStartLoc1);
			else if (arm2)
				pgd.setLocation(partStartLoc2);
			else if (arm3)
				pgd.setLocation(partStartLoc3);
			else if (arm4)
				pgd.setLocation(partStartLoc4);
			partArrayGraphics.add(pgd);
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
