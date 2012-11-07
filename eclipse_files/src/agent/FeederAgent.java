package agent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import agent.data.Part;
import agent.data.PartType;
import agent.interfaces.Feeder;

import DeviceGraphics.DeviceGraphics;
import GraphicsInterfaces.FeederGraphics;

/**
 * Feeder receives parts from gantry and feeds the lanes
 * @author Arjun Bhargava
 */
public class FeederAgent extends Agent implements Feeder {
	public List<PartType> requestList = new ArrayList<PartType>();
	public PartType currentType;
	public List<MyPart> currentParts = new ArrayList<MyPart>();

	private GantryAgent gantry;
	private LaneAgent lane;
	private LaneAgent lane1;
	private PartType type1;
	private LaneAgent lane2;
	private PartType type2;
	private FeederGraphics feederGUI;
	
	private boolean currentOrientation = true;

	String name;
	
	public Semaphore animation = new Semaphore(0, true);

	public class MyPart {
		Part part;
		FeederStatus status;

		public MyPart(Part p) {
			part = p;
			status = FeederStatus.IN_FEEDER;
		}
	}

	public FeederAgent(String name) {
		super();
		lane = lane1;
		this.name = name;

	}

	public enum FeederStatus {
		IN_FEEDER, IN_DIVERTER, END_DIVERTER
	};

	@Override
	public void msgINeedPart(PartType type) {
		if(requestList.size() == 0 && currentType != type) {
			requestList.add(type);
			currentType = type;
		}
		else {
			currentParts.add(new MyPart(new Part(type)));
		}
		stateChanged();
	}

	@Override
	public void msgHereAreParts(Part p) {
		// Changing this to PartType in V1, then feeder will just generate a new
		// part whenever you need one
		// per Prof W. saying bins carry thousands of parts in class
		currentParts.add(new MyPart(p));
		stateChanged();
	}

	@Override
	public void msgGivePartToDiverterDone(Part part) {
		for (MyPart currentPart : currentParts) {
			if (currentPart.part == part) {
				currentPart.status = FeederStatus.END_DIVERTER;
			}
		}
		animation.release();
		stateChanged();
	}

	@Override
	public void msgGivePartToLaneDone(Part part) {
		animation.release();
		stateChanged();
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		for (PartType requestedType : requestList) {
			getParts(requestedType);
			return true;
		}
		for (MyPart currentPart : currentParts) {
			if (currentPart.status == FeederStatus.END_DIVERTER) {
				giveToLane(currentPart.part);
				return true;
			}
		}
		for (MyPart currentPart : currentParts) {
			if (currentPart.status == FeederStatus.IN_FEEDER) {
				giveToDiverter(currentPart.part);
				return true;
			}
		}
		return false;
	}

	@Override
	public void getParts(PartType requestedType) {
		print("Telling gantry that it needs parts");
		gantry.msgINeedParts(requestedType);
		requestList.remove(requestedType);
		stateChanged();
	}

	@Override
	public void giveToDiverter(Part part) {
		print("Giving parts to diverter");
		if(feederGUI !=null) {
			if(part.up != currentOrientation) {
				if(lane == lane1)
					lane = lane2;
				else
					lane=lane1;
				currentOrientation = part.up;
				feederGUI.flipDiverter();
			}
		}
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// SEMAPHORE GOES HERE
		if(feederGUI !=null) {
			feederGUI.movePartToDiverter(part.partGraphics);
		}
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (MyPart currentPart : currentParts) {
			if (currentPart.part == part) {
				currentPart.status = FeederStatus.IN_DIVERTER;
			}
		}
		stateChanged();
	}

	@Override
	public void giveToLane(Part part) {
		print("Giving part to lane");
		if(feederGUI !=null) {
			feederGUI.movePartToLane(part.partGraphics);
		}
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lane.msgHereIsPart(part);
		for (MyPart currentPart : currentParts) {
			if (currentPart.part == part) {
				currentParts.remove(currentPart);
				return;
			}
		}
		stateChanged();
	}

	// GETTERS AND SETTERS
	public void setGraphicalRepresentation(DeviceGraphics feeder) {
		this.feederGUI = (FeederGraphics)feeder;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setGantry(GantryAgent gantry) {
		this.gantry = gantry;
	}

	@Override
	public void setLane(LaneAgent lane) {
		this.lane1 = lane;
		this.lane = lane;
	}

}
