
package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;

public class KitRobotGraphicsDisplay extends DeviceGraphicsDisplay {
	// double x,y;

	// Rectangle2D.Double rectangle;
	Rectangle2D.Double rectangle1;
	JLabel imageLabel;

	// Positions
	public enum Position {
		conveyorPosition, goodConveyorPosition, inspectionPosition, location1Position, location2Position;
	}

	Position position;

	// Commands
	public enum Command {
		moveToConveyor,moveToGoodConveyor, moveToInspectionStand, moveToLocation1, moveToLocation2
	};

	Command moveToInitialPosition;
	Command moveToPosition;
	Command moveToFinalPosition;
	
	
	boolean initialJob;
	boolean finalJob;
	boolean jobIsDone;

	int degreeStep;
	public void setDegreeStep(int degreeStep) {
		this.degreeStep = degreeStep;
	}

	int currentDegree;
	int finalDegree;
	int degreeCountDown;

	int rotationAxisX;
	int rotationAxisY;

	int kitRobotPositionX;
	int kitRobotPositionY;
	AffineTransform trans;
	
	Client kitRobotClient;
	Location location;
	ArrayList<KitGraphicsDisplay> kits = new ArrayList<KitGraphicsDisplay>();
	KitGraphicsDisplay currentKit = new KitGraphicsDisplay(client);

	// just for v0

	public KitRobotGraphicsDisplay(Client cli) {

		// super();
		location = Constants.KIT_ROBOT_LOC;
		kitRobotClient = cli;

		moveToInitialPosition = Command.moveToInspectionStand;
		moveToFinalPosition = Command.moveToConveyor;
		position = Position.conveyorPosition;
		initialJob = false;
		finalJob = false;
		jobIsDone = true;
		degreeStep = 1;
		currentDegree = 0;
		finalDegree = 0;

		trans = new AffineTransform();

		// image =new
		// ImageIcon(this.getClass().getResource("/resource/Square.jpg"));
		rotationAxisX = 25;
		rotationAxisY = 25;
		kitRobotPositionX = 195;
		kitRobotPositionY = 215;

		trans.translate(kitRobotPositionX, kitRobotPositionY);
		rectangle1 = new Rectangle2D.Double(0, 0, 600, 400);

	}

	public void changeKit(KitGraphicsDisplay k) {
		kits.add(k);
	}

	public void removeKit(KitGraphicsDisplay k) {
		kits.remove(k);
	}

	public void placeKitOnStand(int i) {
		if (i == 1) {
			ConveyorToLocation1();
		} else if (i == 2) {
			ConveyorToLocation2();
		}
	}

	public void setCommands(Command initialCommand, Command finalCommand){
		jobIsDone=false;
		initialJob=true;
		this.moveToInitialPosition=initialCommand;
		this.moveToFinalPosition=finalCommand;
	}
	public void InspectionToGoodConveyor(){
		setCommands(Command.moveToInspectionStand, Command.moveToGoodConveyor);
		moveToInitialOrFinal();
	}
	public void ConveyorToLocation1() {
		setCommands(Command.moveToConveyor, Command.moveToLocation1);
		moveToInitialOrFinal();
	}

	public void ConveyorToLocation2() {
		setCommands(Command.moveToConveyor, Command.moveToLocation2);
		moveToInitialOrFinal();
	}

	public void Location1ToInspectionStand() {
		setCommands(Command.moveToLocation1,Command.moveToInspectionStand);
		moveToInitialOrFinal();
	}

	public void Location2ToInspectionStand() {
		setCommands(Command.moveToLocation2, Command.moveToInspectionStand);
		moveToInitialOrFinal();
	}

	public void InspectionStandToGoodConveyor() {
		setCommands(Command.moveToInspectionStand, Command.moveToGoodConveyor);
		moveToInitialOrFinal();
	}

	public void Location1ToLocation2() {
		setCommands(Command.moveToLocation1,Command.moveToLocation2);
		moveToInitialOrFinal();
	}
	
	public void setPositiveDegreeStep(){
		setDegreeStep(1);
	}
	
	public void setNegativeDegreeStep(){
		setDegreeStep(-1);
	}

	public void moveToInitialOrFinal() {
		
		if(initialJob)
		{
			moveToPosition=moveToInitialPosition;
		}
		else if(finalJob)
		{
			moveToPosition=moveToFinalPosition;
		}
		
		if (position.equals(Position.conveyorPosition) ) {
			if (moveToPosition.equals(Command.moveToInspectionStand)) {
			    setRotationConfigurations(135,Position.inspectionPosition);
			} else if (moveToPosition.equals(Command.moveToLocation1)) {
				setRotationConfigurations(180, Position.location1Position);
			} else if (moveToPosition.equals(Command.moveToLocation2)) {
				setRotationConfigurations(225, Position.location2Position);
			} else if (moveToPosition.equals(Command.moveToGoodConveyor)) {
			    setRotationConfigurations(45, Position.goodConveyorPosition);
			}
			else {
				setRotationConfigurations(0,Position.conveyorPosition);
			}
		} else if (position.equals(Position.inspectionPosition)) {
			if (moveToPosition.equals(Command.moveToLocation1) ) {
				setRotationConfigurations(45, Position.location1Position);
			} else if (moveToPosition.equals(Command.moveToLocation2)) {
				setRotationConfigurations(90, Position.location2Position);
			} else if (moveToPosition.equals(Command.moveToConveyor)) {
				setRotationConfigurations(-135, Position.conveyorPosition);
			} else if (moveToPosition.equals(Command.moveToGoodConveyor)){
				setRotationConfigurations(-90, Position.goodConveyorPosition);
			} else {
				setRotationConfigurations(0,Position.inspectionPosition);
			}
		} else if (position.equals(Position.location1Position)) {
			if (moveToPosition.equals(Command.moveToLocation2)) {
				setRotationConfigurations(45, Position.location2Position);
			} else if (moveToPosition.equals(Command.moveToConveyor)) {
				setRotationConfigurations(180, Position.conveyorPosition);
			} else if (moveToPosition
					.equals(Command.moveToInspectionStand)) {
				setRotationConfigurations(-45, Position.inspectionPosition);
			} else if(moveToPosition.equals(Command.moveToGoodConveyor))
			{
				setRotationConfigurations(-135,Position.goodConveyorPosition);
			} else {
				setRotationConfigurations(0,Position.location1Position);
			}
		} else if (position.equals(Position.location2Position)) {
			if (moveToPosition.equals(Command.moveToConveyor)) {
				setRotationConfigurations(135, Position.conveyorPosition);
			} else if (moveToPosition
					.equals(Command.moveToInspectionStand)) {
				setRotationConfigurations(-90, Position.inspectionPosition);
			} else if (moveToPosition.equals(Command.moveToLocation1)) {
				setRotationConfigurations(-45, Position.location1Position);
			} else if(moveToPosition.equals(Command.moveToGoodConveyor)){
				setRotationConfigurations(180, Position.goodConveyorPosition);
			}
			else {
				setRotationConfigurations(0,Position.location2Position);
			}
		}
	}
	public void setRotationConfigurations(int degreeCountDown, Position position){
	this.degreeCountDown = degreeCountDown;
	if(this.degreeCountDown>=0)
	{
		setPositiveDegreeStep();
	}
	else {
		setNegativeDegreeStep();
	}
	
	this.position = position;
	}
	public void checkDegrees() {

		if (degreeCountDown == 0) {

			if (initialJob) {
				initialJob = false;
				finalJob = true;
				currentDegree = 0;
				System.out.println("Passed through initial job");

				if (position.equals(Position.conveyorPosition)
						&& moveToInitialPosition.equals(Command.moveToConveyor)) {

					System.out.println("Sending to conveyor");

				}

				moveToInitialOrFinal();
				currentKit.startRotating();
			} else if (finalJob) {
				/*if (position.equals(Position.location1Position)||position.equals(Position.location2Position)) {
					kitRobotClient.sendData(new Request(Constants.KIT_ROBOT_ON_STAND_DONE, Constants.KIT_ROBOT_TARGET,null));
				}
				else if(position.equals(Position.goodConveyorPosition)){
					kitRobotClient.sendData(new Request(Constants.KIT_ROBOT_ON_CONVEYOR_DONE, Constants.KIT_ROBOT_TARGET, null));
				}
				else if(position.equals(Position.inspectionPosition)){
					kitRobotClient.sendData(new Request(Constants.KIT_ROBOT_ON_INSPECTION_DONE,Constants.KIT_ROBOT_TARGET,null));
				}
					*/
					
				finalJob = false;
				jobIsDone = true;
				currentDegree = 0;

			}
		}
	}

	@Override
	public void receiveData(Request req) {
		String command = req.getCommand();
		String target = req.getTarget();
		Object obj = req.getData();
		
		if (target.equals(Constants.KIT_ROBOT_TARGET)) {
			if (command.equals(Constants.KIT_ROBOT_DISPLAY_PICKS_CONVEYOR_TO_LOCATION1)) {
				
				KitGraphicsDisplay tempKit = new KitGraphicsDisplay(client);
				tempKit.setPosition(5);
				currentKit = tempKit;
				currentKit.setDegreeCountDown(180);
				kits.add(tempKit);
				ConveyorToLocation1();
			}
			else if(command.equals(Constants.KIT_ROBOT_DISPLAY_PICKS_CONVEYOR_TO_LOCATION2))
			{
				KitGraphicsDisplay tempKit = new KitGraphicsDisplay(client);
				tempKit.setPosition(6);
				currentKit= tempKit;
				currentKit.setDegreeCountDown(225);
				kits.add(tempKit);
				ConveyorToLocation2();
			}
			else if (command.equals(Constants.KIT_ROBOT_DISPLAY_PICKS_INSPECTION_TO_GOOD_CONVEYOR)) {

				for (int i = 0; i < kits.size(); i++) {
					if (kits.get(i).getPosition() == 4) {
						currentKit = kits.get(i);
						currentKit.setDegreeCountDown(-90);
						kits.get(i).setPosition(2);
					}
				}
				InspectionToGoodConveyor();
			} else if (command.equals(Constants.KIT_ROBOT_DISPLAY_PICKS_LOCATION1_TO_INSPECTION)) {
				
				for (int i = 0; i < kits.size(); i++) {
					if (kits.get(i).getPosition() == 5) {
						System.out.println("kit is configured");
						currentKit = kits.get(i);
						currentKit.setDegreeCountDown(-45);
						kits.get(i).setPosition(4);
					}
				}
				Location1ToInspectionStand();
			} else if (command.equals(Constants.KIT_ROBOT_DISPLAY_PICKS_LOCATION2_TO_INSPECTION)) {
				for (int i = 0; i < kits.size(); i++) {
					if (kits.get(i).getPosition() == 6) {
						currentKit = kits.get(i);
						currentKit.setDegreeCountDown(-90);
						kits.get(i).setPosition(4);
					}
				}
				Location2ToInspectionStand();
			} 

		}

	}

	public void doJob() {
		if (!jobIsDone) {
			trans.rotate(Math.toRadians(degreeStep), rotationAxisX,
					rotationAxisY);
			degreeCountDown-=degreeStep;
			// System.out.println("currentDegree: " + currentDegree);
		}
	}


	@Override
	public void draw(JComponent c, Graphics2D g) {
		checkDegrees();
		doJob();

		for (int i = 0; i < kits.size(); i++) {

			kits.get(i).drawRotate(c, g);
		}

		g.drawImage(Constants.KIT_ROBOT_IMAGE, trans, null);

	}

	@Override
	public void setLocation(Location newLocation) {
		location = newLocation;
		// TODO Auto-generated method stub
	}

}
