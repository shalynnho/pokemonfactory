package DeviceGraphicsDisplay;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;
import factory.PartType;

public class PartsRobotDisplay extends DeviceGraphicsDisplay {
	
	private static Image partsRobotImage;
	private static ArrayList<Image> armImage1;
	private static ArrayList<Image> armImage2;
	private static ArrayList<Image> armImage3;
	private static ArrayList<Image> armImage4;
	
	
	private ArrayList<PartGraphicsDisplay> partArrayGraphics;
	
	private Location loc, kitloc;
	private Location initialLocation;
	private Location currentLocation;
	private Location armLocation;
	
	
	private Client partsRobotClient;
	
	private boolean rotate;
	private boolean home;
	private boolean pickup, givekit;
	
	private ArrayList<Location> partStartLoc;
	private ArrayList<Location> armLoc;
	private int I;
	
	public PartsRobotDisplay(Client prc){
		partsRobotClient = prc;
		
		initialLocation = Constants.PARTS_ROBOT_LOC; //new Location(250,450);
		currentLocation = initialLocation;
		armLocation = currentLocation;
		
		partsRobotImage = Toolkit.getDefaultToolkit().getImage("src/images/Square.jpg");
		armImage1 = new ArrayList<Image>();
		armImage2 = new ArrayList<Image>();
		armImage3 = new ArrayList<Image>();
		armImage4 = new ArrayList<Image>();
		int j;
		for (j=0; j<3; j++){
			if(j<2){
				armImage1.add(Toolkit.getDefaultToolkit().getImage("src/image/Arm.png"));
				armImage2.add(Toolkit.getDefaultToolkit().getImage("src/image/Arm.png"));
				armImage3.add(Toolkit.getDefaultToolkit().getImage("src/image/Arm.png"));
				armImage4.add(Toolkit.getDefaultToolkit().getImage("src/image/Arm.png"));
			}
			else{
				armImage1.add(Toolkit.getDefaultToolkit().getImage("src/image/Arm2.png"));
				armImage2.add(Toolkit.getDefaultToolkit().getImage("src/image/Arm2.png"));
				armImage3.add(Toolkit.getDefaultToolkit().getImage("src/image/Arm2.png"));
				armImage4.add(Toolkit.getDefaultToolkit().getImage("src/image/Arm2.png"));
				
			}
		}
		
		partArrayGraphics = new ArrayList<PartGraphicsDisplay>();
		
		rotate = false;
		home = true;
		pickup = false;
		givekit = false;
		
		partStartLoc = new ArrayList<Location>();
		partStartLoc.add( new Location(currentLocation.getX(),currentLocation.getY()));
		partStartLoc.add( new Location(currentLocation.getX()+30,currentLocation.getY()));
		partStartLoc.add( new Location(currentLocation.getX(),currentLocation.getY()+30));
		partStartLoc.add( new Location(currentLocation.getX()+30,currentLocation.getY()+30));
		
		armLoc = new ArrayList<Location>();
		armLoc.add(new Location(armLocation.getX()+60,armLocation.getY()));
		armLoc.add(new Location(armLocation.getX()+60,armLocation.getY()+30));
		armLoc.add(new Location(armLocation.getX()+60,armLocation.getY()+60));
		armLoc.add(new Location(armLocation.getX()+60,armLocation.getY()+90));
		
		I = 0;
			
	}
	
	public void draw(JComponent c, Graphics2D g){
		
		if(pickup){
			
				if(currentLocation.getX()<loc.getX()){
					currentLocation.incrementX(5);
					partStartLoc.get(0).setX(currentLocation.getX());
					partStartLoc.get(1).setX(currentLocation.getX()+30);
					partStartLoc.get(2).setX(currentLocation.getX());
					partStartLoc.get(3).setX(currentLocation.getX()+30);
				
				} else if(currentLocation.getX()>loc.getX()){
					currentLocation.incrementX(-5);
					partStartLoc.get(0).setX(currentLocation.getX());
					partStartLoc.get(1).setX(currentLocation.getX()+30);
					partStartLoc.get(2).setX(currentLocation.getX());
					partStartLoc.get(3).setX(currentLocation.getX()+30);
					}
				else if(currentLocation.getY()>loc.getY()){
					currentLocation.incrementY(-5);
					partStartLoc.get(0).setY(currentLocation.getY());
					partStartLoc.get(1).setY(currentLocation.getY());
					partStartLoc.get(2).setY(currentLocation.getY()+30);
					partStartLoc.get(3).setY(currentLocation.getY()+30);
				}else if(currentLocation.getY()<loc.getY()){
					currentLocation.incrementY(5);
					partStartLoc.get(0).setY(currentLocation.getY());
					partStartLoc.get(1).setY(currentLocation.getY());
					partStartLoc.get(2).setY(currentLocation.getY()+30);
					partStartLoc.get(3).setY(currentLocation.getY()+30);
					}
				g.drawImage(partsRobotImage, currentLocation.getX(), currentLocation.getY(), c);
				int k;
				for (k=0;k<3;k++){
					g.drawImage(armImage1.get(k),armLoc.get(0).getX(), armLoc.get(0).getY(), c);
					g.drawImage(armImage2.get(k),armLoc.get(1).getX(), armLoc.get(1).getY(), c);
					g.drawImage(armImage3.get(k),armLoc.get(2).getX(), armLoc.get(2).getY(), c);
					g.drawImage(armImage4.get(k),armLoc.get(3).getX(), armLoc.get(3).getY(), c);
				}
				if(currentLocation.getX() == loc.getX()-30 && currentLocation.getY() == loc.getY()){
					System.out.println("at parts location");
					Location tempLoc = armLoc.get(I-1);
					while(armLoc.get(I-1).getX() != loc.getX()){
						extendArm();
						if(I==1)
							g.drawImage(armImage1.get(1),armLoc.get(0).getX(),armLoc.get(0).getY(), c);
						else if (I==2)
							g.drawImage(armImage2.get(1),armLoc.get(1).getX(),armLoc.get(1).getY(), c);
						else if (I==3)
							g.drawImage(armImage3.get(1),armLoc.get(2).getX(),armLoc.get(2).getY(), c);
						else if (I==4)
							g.drawImage(armImage4.get(1),armLoc.get(3).getX(),armLoc.get(3).getY(), c);
					}
					if(armLocation.getX() == loc.getX() && armLocation.getY() == loc.getY()){
						pickUpPart();
						while(armLoc.get(I-1).getX() != tempLoc.getX()){
							retractArm();
							if(I==1)
								g.drawImage(armImage1.get(1),armLoc.get(0).getX(),armLoc.get(0).getY(), c);
							else if (I==2)
								g.drawImage(armImage2.get(1),armLoc.get(1).getX(),armLoc.get(1).getY(), c);
							else if (I==3)
								g.drawImage(armImage3.get(1),armLoc.get(2).getX(),armLoc.get(2).getY(), c);
							else if (I==4)
								g.drawImage(armImage4.get(1),armLoc.get(3).getX(),armLoc.get(3).getY(), c);

						}
					}
				}
				
				
		}else if (givekit) {
			for (int i = 0; i < 5; i++){
				if(currentLocation.getX()>kitloc.getX()){
					currentLocation.incrementX(-1);
					partStartLoc.get(0).setX(currentLocation.getX());
					partStartLoc.get(1).setX(currentLocation.getX()+30);
					partStartLoc.get(2).setX(currentLocation.getX());
					partStartLoc.get(3).setX(currentLocation.getX()+30);
				}
				else if(currentLocation.getY()>kitloc.getY()){
					currentLocation.incrementY(-1);
					partStartLoc.get(0).setY(currentLocation.getY());
					partStartLoc.get(1).setY(currentLocation.getY());
					partStartLoc.get(2).setY(currentLocation.getY()+30);
					partStartLoc.get(3).setY(currentLocation.getY()+30);
				}
				else if(currentLocation.getY()<kitloc.getY()){
					currentLocation.incrementY(1);
					partStartLoc.get(0).setY(currentLocation.getY());
					partStartLoc.get(1).setY(currentLocation.getY());
					partStartLoc.get(2).setY(currentLocation.getY()+30);
					partStartLoc.get(3).setY(currentLocation.getY()+30);
				}
				
				g.drawImage(partsRobotImage, currentLocation.getX(), currentLocation.getY(), c);
				int k;
				for (k=0;k<3;k++){
					g.drawImage(armImage1.get(k),armLocation.getX()+60, armLocation.getY(), c);
					g.drawImage(armImage2.get(k),armLocation.getX()+60, armLocation.getY(), c);
					g.drawImage(armImage3.get(k),armLocation.getX()+60, armLocation.getY(), c);
					g.drawImage(armImage4.get(k),armLocation.getX()+60, armLocation.getY(), c);
				}
			}
			if (currentLocation.getX() == kitloc.getX()+30 && currentLocation.getY() == kitloc.getY()){
				Location tempLoc = armLoc.get(I-1);
				while(armLoc.get(I-1).getX() != kitloc.getX()){
					retractArm();
					if(I==1)
						g.drawImage(armImage1.get(1),armLoc.get(0).getX(),armLoc.get(0).getY(), c);
					else if (I==2)
						g.drawImage(armImage2.get(1),armLoc.get(1).getX(),armLoc.get(1).getY(), c);
					else if (I==3)
						g.drawImage(armImage3.get(1),armLoc.get(2).getX(),armLoc.get(2).getY(), c);
					else if (I==4)
						g.drawImage(armImage4.get(1),armLoc.get(3).getX(),armLoc.get(3).getY(), c);
				}
				if (armLoc.get(I-1).getX() == kitloc.getX()){
					givePart();
					while(armLocation.getX() != tempLoc.getX()){
						extendArm();
						if(I==1)
							g.drawImage(armImage1.get(1),armLoc.get(0).getX(),armLoc.get(0).getY(), c);
						else if (I==2)
							g.drawImage(armImage2.get(1),armLoc.get(1).getX(),armLoc.get(1).getY(), c);
						else if (I==3)
							g.drawImage(armImage3.get(1),armLoc.get(2).getX(),armLoc.get(2).getY(), c);
						else if (I==4)
							g.drawImage(armImage4.get(1),armLoc.get(3).getX(),armLoc.get(3).getY(), c);}
				}
			}
		} else if (!givekit || !pickup) {
			for (int i = 0; i < 5; i++){
				if(currentLocation.getY()<450){
					currentLocation.incrementY(1);
					partStartLoc.get(0).setY(currentLocation.getY());
					partStartLoc.get(1).setY(currentLocation.getY());
					partStartLoc.get(2).setY(currentLocation.getY()+30);
					partStartLoc.get(3).setY(currentLocation.getY()+30);
				}
				else if(currentLocation.getX()>250){
					currentLocation.incrementX(-1);
					partStartLoc.get(0).setX(currentLocation.getX());
					partStartLoc.get(1).setX(currentLocation.getX()+30);
					partStartLoc.get(2).setX(currentLocation.getX());
					partStartLoc.get(3).setX(currentLocation.getX()+30);
				}
				else if(currentLocation.getX()<250){
					currentLocation.incrementX(1);
					partStartLoc.get(0).setX(currentLocation.getX());
					partStartLoc.get(1).setX(currentLocation.getX()+30);
					partStartLoc.get(2).setX(currentLocation.getX());
					partStartLoc.get(3).setX(currentLocation.getX()+30);
				}
				
				
				g.drawImage(partsRobotImage, currentLocation.getX(), currentLocation.getY(), c);
				int k;
				for (k=0;k<3;k++){
					g.drawImage(armImage1.get(k),armLocation.getX()+60, armLocation.getY(), c);
					g.drawImage(armImage2.get(k),armLocation.getX()+60, armLocation.getY(), c);
					g.drawImage(armImage3.get(k),armLocation.getX()+60, armLocation.getY(), c);
					g.drawImage(armImage4.get(k),armLocation.getX()+60, armLocation.getY(), c);
				}
			}
		}else if(home){
		
			g.drawImage(partsRobotImage, initialLocation.getX(), initialLocation.getY(), c);
			int i;
			for (i=0;i<3;i++){
				g.drawImage(armImage1.get(i),initialLocation.getX()+60, initialLocation.getY(), c);
				g.drawImage(armImage2.get(i),initialLocation.getX()+60, initialLocation.getY(), c);
				g.drawImage(armImage3.get(i),initialLocation.getX()+60, initialLocation.getY(), c);
				g.drawImage(armImage4.get(i),initialLocation.getX()+60, initialLocation.getY(), c);
			}
		}
		
		for (int i = 0; i < partArrayGraphics.size(); i++){
			PartGraphicsDisplay pgd = partArrayGraphics.get(i);
			pgd.draw(c, g );
		}
	
	}
	
	public void pickUp(){
		home = false;
		pickup = true;
		givekit = false;
	}
	
	public void giveKit(){
		home = false;
		givekit = true;
	}
	public void pickUpPart(){
		//partArrayGraphics.add(pgd);
		PartType partType = Constants.DEFAULT_PARTTYPES.get(0);
		PartGraphicsDisplay pgd = new PartGraphicsDisplay(partType);
		
		if (I<4){
			pgd.setLocation(partStartLoc.get(I));
			partArrayGraphics.add(pgd);
			I++;
			pickup = false;
			partsRobotClient.sendData(new Request(
				    Constants.PARTS_ROBOT_RECEIVE_PART_COMMAND + Constants.DONE_SUFFIX, 
				    Constants.PARTS_ROBOT_TARGET,
				    null));
		}
		else
			System.out.println("Can't pick up more parts.");
	}
	
	public void givePart(){
		
		if(I>0 && I<=4){
			I--;
			partArrayGraphics.remove(I);
			partsRobotClient.sendData(new Request(
			    Constants.PARTS_ROBOT_GIVE_COMMAND + Constants.DONE_SUFFIX, 
			    Constants.PARTS_ROBOT_TARGET,
			    null));
		}
		givekit = false;
		
	}
	
	public void extendArm(){
		
			//if(armLocation.getX() != loc.getX()){
		
		if(I==1)
			armLoc.get(0).incrementX(1);
		else if(I==2)
			armLoc.get(1).incrementX(1);
		else if(I==3)
			armLoc.get(2).incrementX(1);
		else if(I==4)
			armLoc.get(3).incrementX(1);
				
		
	}
	
	public void retractArm(){
		
		//if(armLocation.getX() == loc.getX()){
		if(I==1)
			armLoc.get(0).incrementX(-1);
		else if(I==2)
			armLoc.get(1).incrementX(-1);
		else if(I==3)
			armLoc.get(2).incrementX(-1);
		else if(I==4)
			armLoc.get(3).incrementX(-1);
		
	}
	
	public void rotateArm(){
		rotate = true;
	}
	
	public boolean getRotate(){
		return rotate;
	}
	
	public void receiveData(Request r) {
		if (r.getCommand().equals(Constants.PARTS_ROBOT_GIVE_COMMAND)){
			kitloc = (Location) r.getData();
			giveKit();
		}else if (r.getCommand().equals(Constants.PARTS_ROBOT_PICKUP_COMMAND)){
			loc = (Location) r.getData();
			pickUp();
			System.out.println("before pick up");
		}
	}

	@Override
	public void setLocation(Location newLocation) {
		// TODO Auto-generated method stub
		
	}

}
