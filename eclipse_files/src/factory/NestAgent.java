package factory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import DeviceGraphics.NestGraphics;
import agent.Agent;
import factory.data.Part;
import factory.data.PartType;
import factory.interfaces.Nest;

/**
 * Nests hold parts
 * @author Arjun Bhargava
 */
public class NestAgent extends Agent implements Nest {

	public List<PartType> requestList = new ArrayList<PartType>();
	PartType currentPartType;
	public List<MyPart> currentParts = new ArrayList<MyPart>();
	public int count = 0;
	public int countRequest = 0;
	int full = 9;
	public boolean takingParts = false;

	public NestGraphics guiNest;
	
	Semaphore animation = new Semaphore(0, true);

	String name;

	LaneAgent lane;
	CameraAgent camera;

	public class MyPart {
		Part part;
		NestStatus status;

		public MyPart(Part p) {
			part = p;
			status = NestStatus.IN_NEST;
		}
	}

	public enum NestStatus {
		IN_NEST, IN_NEST_POSITION
	};

	public NestAgent(String name) {
		super();

		this.name = name;
	}

	// MESSAGES
	@Override
	public void msgHereIsPartType(PartType type) {
		// GUINest.purge();
		currentPartType = type;
		countRequest = 0;
		count = 0;
		requestList.clear();
		requestList.add(type);
		stateChanged();
	}

	@Override
	public void msgHereIsPart(Part p) {
		count++;
		currentParts.add(new MyPart(p));
		stateChanged();
	}

	@Override
	public void msgTakingPart(Part p) {
		// GUINest.givePartToPartsRobot(p);
		for (MyPart part : currentParts) {
			if (part.part == p) {
				currentParts.remove(part);
				return;
			}
		}
		count--;
		countRequest--;
		stateChanged();
	}

	@Override
	public void msgDoneTakingParts() {
		takingParts = false;
		stateChanged();
	}

	@Override
	public void msgReceivePartDone() {
		animation.release();
		stateChanged();
	}

	@Override
	public void msgGivePartToPartsRobotDone() {
		///animation.release(); //This message is never sent
		stateChanged();
	}
 
	@Override
	public void msgPurgingDone() {
		animation.release();
		stateChanged();
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		for (PartType requestedPart : requestList) {
			if (countRequest < full) {
				getParts(requestedPart);
				return true;
			}
		}
		for (MyPart currentPart : currentParts) {
			if (currentPart.status == NestStatus.IN_NEST) {
				moveToPosition(currentPart.part);
				return true;
			}
		}
		if (count == full) {
			nestFull();
			return true;
		}
		if (takingParts == true) {
			updateParts();
			return true;
		}
		return false;
	}

	// ACTIONS
	@Override
	public void getParts(PartType requestedType) {
		print("Telling lane it need a part and incrementing count");
		countRequest++;
		lane.msgINeedPart(requestedType);
		stateChanged();
	}

	@Override
	public void moveToPosition(Part part) {
		print("Moving part to proper nest location");
		
		// GUINest.receivePart(part);
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (MyPart currentPart : currentParts) {
			if (currentPart.part == part) {
				currentPart.status = NestStatus.IN_NEST_POSITION;
			}
		}
		stateChanged();
	}

	@Override
	public void nestFull() {
		print("Telling camera that this nest is full");
		camera.msgIAmFull(this);
		takingParts = true;
		stateChanged();
	}

	@Override
	public void updateParts() {
		// GUINest.updatePartsList();
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stateChanged();
	}

	@Override
	public String getName() {
		return name;
	}

	public void setLane(LaneAgent lane) {
		this.lane = lane;
	}

	public void setCamera(CameraAgent camera) {
		this.camera = camera;
	}

}
