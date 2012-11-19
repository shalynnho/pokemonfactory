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
	

	// Positions
	public enum Position {
		conveyorPosition, goodConveyorPosition, inspectionPosition, location1Position, location2Position;
	}

	Position position;

	// Commands
	public enum Command {
		moveToConveyor, moveToGoodConveyor, moveToInspectionStand, moveToLocation1, moveToLocation2
	};

	Command moveToInitialPosition;	//initial command
	Command moveToFinalPosition;	//final command
	Command moveToPosition; 		//current command
	
	boolean initialJob;
	boolean finalJob;
	boolean jobIsDone;

	int degreeStep;
	public void setDegreeStep(int degreeStep) {
		this.degreeStep = degreeStep;
	}

	int degreeCountDown;

	double rotationAxisX;
	double rotationAxisY;

	double kitRobotPositionX;
	double kitRobotPositionY;
	
	AffineTransform trans;

	Client kitRobotClient;
	Location location;
	ArrayList<KitGraphicsDisplay> kits = new ArrayList<KitGraphicsDisplay>();
	KitGraphicsDisplay currentKit; 
	
	public KitRobotGraphicsDisplay(Client cli) {

		location = Constants.KIT_ROBOT_LOC;
		kitRobotClient = cli;

		moveToInitialPosition = Command.moveToInspectionStand;
		moveToFinalPosition = Command.moveToConveyor;
		position = Position.conveyorPosition;
		initialJob = false;
		finalJob = false;
		jobIsDone = true;
		degreeStep = Constants.KIT_ROBOT_DEGREE_STEP;
		trans = new AffineTransform();

		rotationAxisX = Constants.KIT_ROBOT_ROTATION_AXIS_LOC.getXDouble() ;
		rotationAxisY = Constants.KIT_ROBOT_ROTATION_AXIS_LOC.getYDouble();
		kitRobotPositionX = Constants.KIT_ROBOT_LOC.getXDouble() + kitRobotClient.getOffset();
		kitRobotPositionY = Constants.KIT_ROBOT_LOC.getYDouble();

		currentKit= new KitGraphicsDisplay();
		trans.translate(kitRobotPositionX, kitRobotPositionY);

	}

	public void setCommands(Command initialCommand, Command finalCommand) {
		jobIsDone = false;
		initialJob = true;
		this.moveToInitialPosition = initialCommand;
		this.moveToFinalPosition = finalCommand;
	}
	
	//begin paths
	public void InspectionToGoodConveyor() {
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
		setCommands(Command.moveToLocation1, Command.moveToInspectionStand);
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
		setCommands(Command.moveToLocation1, Command.moveToLocation2);
		moveToInitialOrFinal();
	}
	
	//end paths
	public void setPositiveDegreeStep() {
		setDegreeStep(Constants.KIT_ROBOT_DEGREE_STEP);
	}

	public void setNegativeDegreeStep() {
		setDegreeStep(-Constants.KIT_ROBOT_DEGREE_STEP);
	}
	
	/*
	 * sets the rotation configurations based on the commands
	 */
	public void moveToInitialOrFinal() {

		if (initialJob) {
			moveToPosition = moveToInitialPosition;
		} else if (finalJob) {
			moveToPosition = moveToFinalPosition;
		}

		if (position.equals(Position.conveyorPosition)) {
			if (moveToPosition.equals(Command.moveToInspectionStand)) {
				setRotationConfigurations(135, Position.inspectionPosition);
			} else if (moveToPosition.equals(Command.moveToLocation1)) {
				setRotationConfigurations(180, Position.location1Position);
			} else if (moveToPosition.equals(Command.moveToLocation2)) {
				setRotationConfigurations(225, Position.location2Position);
			} else if (moveToPosition.equals(Command.moveToGoodConveyor)) {
				setRotationConfigurations(45, Position.goodConveyorPosition);
			} else {
				setRotationConfigurations(0, Position.conveyorPosition);
			}
		} else if (position.equals(Position.inspectionPosition)) {
			if (moveToPosition.equals(Command.moveToLocation1)) {
				setRotationConfigurations(45, Position.location1Position);
			} else if (moveToPosition.equals(Command.moveToLocation2)) {
				setRotationConfigurations(90, Position.location2Position);
			} else if (moveToPosition.equals(Command.moveToConveyor)) {
				setRotationConfigurations(-135, Position.conveyorPosition);
			} else if (moveToPosition.equals(Command.moveToGoodConveyor)) {
				setRotationConfigurations(-90, Position.goodConveyorPosition);
			} else {
				setRotationConfigurations(0, Position.inspectionPosition);
			}
		} else if (position.equals(Position.location1Position)) {
			if (moveToPosition.equals(Command.moveToLocation2)) {
				setRotationConfigurations(45, Position.location2Position);
			} else if (moveToPosition.equals(Command.moveToConveyor)) {
				setRotationConfigurations(180, Position.conveyorPosition);
			} else if (moveToPosition.equals(Command.moveToInspectionStand)) {
				setRotationConfigurations(-45, Position.inspectionPosition);
			} else if (moveToPosition.equals(Command.moveToGoodConveyor)) {
				setRotationConfigurations(-135, Position.goodConveyorPosition);
			} else {
				setRotationConfigurations(0, Position.location1Position);
			}
		} else if (position.equals(Position.location2Position)) {
			if (moveToPosition.equals(Command.moveToConveyor)) {
				setRotationConfigurations(135, Position.conveyorPosition);
			} else if (moveToPosition.equals(Command.moveToInspectionStand)) {
				setRotationConfigurations(-90, Position.inspectionPosition);
			} else if (moveToPosition.equals(Command.moveToLocation1)) {
				setRotationConfigurations(-45, Position.location1Position);
			} else if (moveToPosition.equals(Command.moveToGoodConveyor)) {
				setRotationConfigurations(180, Position.goodConveyorPosition);
			} else {
				setRotationConfigurations(0, Position.location2Position);
			}
		} else if (position.equals(Position.goodConveyorPosition)) {
			if (moveToPosition.equals(Command.moveToConveyor)) {
				setRotationConfigurations(-45, Position.conveyorPosition);
			} else if (moveToPosition.equals(Command.moveToInspectionStand)) {
				setRotationConfigurations(90, Position.inspectionPosition);
			} else if (moveToPosition.equals(Command.moveToLocation1)) {
				setRotationConfigurations(135, Position.location1Position);
			} else if (moveToPosition.equals(Command.moveToLocation2)) {
				setRotationConfigurations(180, Position.location2Position);
			} else {
				setRotationConfigurations(0, Position.goodConveyorPosition);
			}
		}

	}
	
	/**
	 * set the rotation configurations.
	 * degreeCountdown-increments till it reaches 0. designates how much to rotate
	 * position- sets the position in the statemachine logic
	 */
	public void setRotationConfigurations(int degreeCountDown, Position position) {
		this.degreeCountDown = degreeCountDown;
		if (this.degreeCountDown >= 0) {
			setPositiveDegreeStep();
		} else {
			setNegativeDegreeStep();
		}

		this.position = position;
	}

	/**
	 * sends the done messages when the degreecountdown reaches 0
	 * the done messages are based on what position the kit robot reaches
	 */
	public void sendDoneMessages() {
		if (position.equals(Position.location1Position)
				|| position.equals(Position.location2Position)) {
			kitRobotClient.sendData(new Request(
					Constants.KIT_ROBOT_ON_STAND_DONE,
					Constants.KIT_ROBOT_TARGET, null));
		} else if (position.equals(Position.goodConveyorPosition)) {
			kits.remove(currentKit);
			kitRobotClient.sendData(new Request(
					Constants.KIT_ROBOT_ON_CONVEYOR_DONE,
					Constants.KIT_ROBOT_TARGET, null));
		} else if (position.equals(Position.inspectionPosition)) {
			kitRobotClient.sendData(new Request(
					Constants.KIT_ROBOT_ON_INSPECTION_DONE,
					Constants.KIT_ROBOT_TARGET, null));
		}
	}
	/**
	 * changes which job it's doing based on the booleans
	 */
	public void checkDegrees() {

		if (degreeCountDown == 0) {
			if (initialJob) {
				initialJob = false;
				finalJob = true;
				moveToInitialOrFinal();
				currentKit.startRotating();
			} else if (finalJob) {
				finalJob = false;
				jobIsDone = true;
				sendDoneMessages();
			}
		}
	}

	public void setKitConfigurations(KitGraphicsDisplay kit,
			int kitDegreeCountDown, int position) {
		currentKit = kit;
		currentKit.setDegreeCountDown(kitDegreeCountDown);
		currentKit.setPosition(position);
	}

	/*
	 * receives data and does stuff based on the request command
	 * (non-Javadoc)
	 * @see DeviceGraphicsDisplay.DeviceGraphicsDisplay#receiveData(Networking.Request)
	 */
	public void receiveData(Request req) {
		String command = req.getCommand();
		String target = req.getTarget();
		Object obj = req.getData();

		if (target.equals(Constants.KIT_ROBOT_TARGET)) {
			if (command
					.equals(Constants.KIT_ROBOT_DISPLAY_PICKS_CONVEYOR_TO_LOCATION1)) {

				KitGraphicsDisplay tempKit = new KitGraphicsDisplay();
				tempKit.setTranslation(kitRobotClient.getOffset());
				setKitConfigurations(tempKit, 180, 5);
				kits.add(currentKit);
				ConveyorToLocation1();
			} else if (command
					.equals(Constants.KIT_ROBOT_DISPLAY_PICKS_CONVEYOR_TO_LOCATION2)) {
				KitGraphicsDisplay tempKit = new KitGraphicsDisplay();
				tempKit.setTranslation(kitRobotClient.getOffset());
				setKitConfigurations(tempKit, 225, 6);
				kits.add(currentKit);
				ConveyorToLocation2();
			} else if (command
					.equals(Constants.KIT_ROBOT_DISPLAY_PICKS_INSPECTION_TO_GOOD_CONVEYOR)) {
				for (int i = 0; i < kits.size(); i++) {
					if (kits.get(i).getPosition() == 4) {
						setKitConfigurations(kits.get(i), -90, 2);
					}
				}
				InspectionToGoodConveyor();
			} else if (command
					.equals(Constants.KIT_ROBOT_DISPLAY_PICKS_LOCATION1_TO_INSPECTION)) {

				for (int i = 0; i < kits.size(); i++) {
					if (kits.get(i).getPosition() == 5) {
						setKitConfigurations(kits.get(i), -45, 4);
					}
				}
				Location1ToInspectionStand();
			} else if (command
					.equals(Constants.KIT_ROBOT_DISPLAY_PICKS_LOCATION2_TO_INSPECTION)) {
				for (int i = 0; i < kits.size(); i++) {
					if (kits.get(i).getPosition() == 6) {
						setKitConfigurations(kits.get(i), -90, 4);
					}
				}
				Location2ToInspectionStand();
			}

		}

	}
	/**
	 * rotates the kit robot if you are doing a job
	 */
	public void doJob() {
		if (!jobIsDone) {
			trans.rotate(Math.toRadians(degreeStep), rotationAxisX,
					rotationAxisY);
			degreeCountDown -= degreeStep;
			// System.out.println("currentDegree: " + currentDegree);
		}
	}

	/*
	 * draws the kit robot and kit
	 * (non-Javadoc)
	 * @see DeviceGraphicsDisplay.DeviceGraphicsDisplay#draw(javax.swing.JComponent, java.awt.Graphics2D)
	 */
	public void draw(JComponent c, Graphics2D g) {
		checkDegrees();
		doJob();
		drawtheKits(c, g);
		g.drawImage(Constants.KIT_ROBOT_IMAGE, trans, null);
	}
	/*
	 * draws the kits in the kit robot area
	 */
	public void drawtheKits(JComponent c, Graphics2D g) {
		for (int i = 0; i < kits.size(); i++) {

			kits.get(i).drawRotate(c, g);
		}
	}

	@Override
	public void setLocation(Location newLocation) {
		location = newLocation;
	}

}
