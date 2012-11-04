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

	List<PartType> requestList = new ArrayList<PartType>();
	PartType currentPartType;
    List<MyPart> currentParts = new ArrayList<MyPart>();
    int count = 0;  
    int full = 9;   
    boolean takingParts = false; 
    
    NestGraphics guiNest;
	
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
			}
		}
		for(MyPart currentPart : currentParts) {
			if(currentPart.status == NestStatus.IN_NEST) {
				moveToPosition(currentPart.part);
			}
		}
		if(count == full) {
			nestFull();
		}
		if(takingParts == true) {
			updateParts();
		}
		return false;
	}

	//ACTIONS
	public void getParts(PartType requestedType) {   
        count++;
		lane.msgINeedPart(requestedType);  
        stateChanged();
	}    
    public void moveToPosition(Part part) {  
        //GUINest.receivePart(part);
    	for(MyPart currentPart : currentParts) {
    		if(currentPart.part == part)
    			currentPart.status = NestStatus.IN_NEST_POSITION;
    	}  
        stateChanged();
    }
    public void nestFull() {    
        camera.msgIAmFull(this);  
        takingParts = true; 
        stateChanged();
    }
    public void updateParts() { 
        //GUINest.updatePartsList(); 
    }
	
}
