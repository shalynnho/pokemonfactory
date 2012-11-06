package factory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import agent.Agent;
import factory.data.Part;
import factory.data.PartType;
import factory.interfaces.Lane;

/**
 * Lane delivers parts to the nest
 * @author Arjun Bhargava
 */
public class LaneAgent extends Agent implements Lane {

	public List<PartType> requestList = new ArrayList<PartType>();
	public List<MyPart> currentParts = new ArrayList<MyPart>();

	String name;
	
	Semaphore animation = new Semaphore(1, true);

	public class MyPart {
		Part part;
		LaneStatus status;

		public MyPart(Part p) {
			part = p;
			status = LaneStatus.BEGINNING_LANE;
		}
	}

	public enum LaneStatus {
		BEGINNING_LANE, IN_LANE, END_LANE
	};

	FeederAgent feeder;
	NestAgent nest;

	public LaneAgent(String name) {
		super();

		this.name = name;
	}

	@Override
	public void msgINeedPart(PartType type) {
		requestList.add(type);
		stateChanged();
	}

	@Override
	public void msgHereIsPart(Part p) {
		currentParts.add(new MyPart(p));
		stateChanged();
	}

	@Override
	public void msgReceivePartDone(Part part) {
		//animation.release(); //This message is never sent to the LaneGraphics
		stateChanged();
	}

	@Override
	public void msgGivePartToNestDone(Part part) {
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
		for (MyPart part : currentParts) {
			if (part.status == LaneStatus.BEGINNING_LANE) {
				giveToNest(part.part);
				return true;
			}
		}
		return false;
	}

	@Override
	public void getParts(PartType requestedType) {
		print("Telling Feeder that it needs a part");
		feeder.msgINeedPart(requestedType);
		requestList.remove(requestedType);
		stateChanged();
	}

	@Override
	public void giveToNest(Part part) {
		print("Giving part to Nest");
		
		// GUILane.givePartToNest(part);
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		nest.msgHereIsPart(part);
		for (MyPart currentPart : currentParts) {
			if (currentPart.part == part) {
				currentParts.remove(currentPart);
				return;
			}
		}
		stateChanged();
	}

	@Override
	public String getName() {
		return name;
	}

	public void setFeeder(FeederAgent feeder) {
		this.feeder = feeder;
	}

	public void setNest(NestAgent nest) {
		this.nest = nest;
	}

}
