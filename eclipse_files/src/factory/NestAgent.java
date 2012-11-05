package factory;

import java.util.ArrayList;
import java.util.List;

import factory.LaneAgent.LaneStatus;
import factory.data.Part;
import factory.data.PartType;
import factory.interfaces.Nest;
import DeviceGraphics.NestGraphics;
import agent.Agent;

public class NestAgent extends Agent implements Nest {

	public List<PartType> requestList = new ArrayList<PartType>();
	PartType currentPartType;
    List<MyPart> currentParts = new ArrayList<MyPart>();
    int count = 0;  
    int full = 9;   
    boolean takingParts = false; 
    
    public NestGraphics guiNest;
	
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
		IN_NEST,IN_NEST_POSITION
	};
    
	public NestAgent(String name) {
    	super();
    	
    	this.name = name;
    }
	
	//MESSAGES
	public void msgHereIsPartType(PartType type) { 
        //GUINest.purge();
		currentPartType = type;
        requestList.clear();    
        requestList.add(type);   
        stateChanged();
    }    
    public void msgHereIsPart(Part p) {     
        currentParts.add(new MyPart(p));    
        stateChanged(); 
    }   
    public void msgTakingPart(Part p) {    
        //GUINest.givePartToPartsRobot(p);    
        currentParts.remove(p);
        count --;
        stateChanged(); 
    }
    public void msgDoneTakingParts() { 
        takingParts = false;    
        stateChanged(); 
    }
    public void msgReceivePartDone() {}
    public void msgGivePartToPartsRobotDone() {}
    public void msgPurgingDone() {}
	
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		for(PartType requestedPart : requestList) {
			if(count < full) {
				getParts(requestedPart);
				return true;
			}
		}
		for(MyPart currentPart : currentParts) {
			if(currentPart.status == NestStatus.IN_NEST) {
				moveToPosition(currentPart.part);
				return true;
			}
		}
		if(count == full) {
			nestFull();
			return true;
		}
		if(takingParts == true) {
			updateParts();
			return true;
		}
		return false;
	}

	//ACTIONS
	public void getParts(PartType requestedType) {   
        print("Telling lane it need a part and incrementing count");
		count++;
		lane.msgINeedPart(requestedType);  
        stateChanged();
	}    
    public void moveToPosition(Part part) {
    	print("Moving part to proper nest location");
        //GUINest.receivePart(part);
    	for(MyPart currentPart : currentParts) {
    		if(currentPart.part == part)
    			currentPart.status = NestStatus.IN_NEST_POSITION;
    	}  
        stateChanged();
    }
    public void nestFull() {  
    	print("Telling camera that this nest is full");
        camera.msgIAmFull(this);  
        takingParts = true; 
        stateChanged();
    }
    public void updateParts() { 
        //GUINest.updatePartsList(); 
    }
    
    
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
