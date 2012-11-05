package factory;

import java.util.ArrayList;
import java.util.List;

import factory.FeederAgent.FeederStatus;
import factory.data.Part;
import factory.data.PartType;
import factory.interfaces.Feeder;
import factory.interfaces.Lane;
import agent.Agent;

public class LaneAgent extends Agent implements Lane {
	
	public List<PartType> requestList = new ArrayList<PartType>(); 
    public List<MyPart> currentParts = new ArrayList<MyPart>();
	
    String name;
    
    public class MyPart {
    	Part part;
    	LaneStatus status;
    	
    	public MyPart(Part p) {
    		part = p;
    		status = LaneStatus.BEGINNING_LANE;
    	}
    }
    
    public enum LaneStatus {
		BEGINNING_LANE,IN_LANE, END_LANE
	};
    
    FeederAgent feeder;
    NestAgent nest;
    
    public LaneAgent(String name) {
    	super();
    	
    	this.name = name;
    }
    
    public void msgINeedPart(PartType type) { 
        requestList.add(type);   
        stateChanged(); 
    }   
    public void msgHereIsPart(Part p) {      
        currentParts.add(new MyPart(p));    
        stateChanged(); 
    }   
    public void msgReceivePartDone(Part part) { 
        stateChanged(); 
    }   
    public void msgGivePartToNestDone(Part part) {    
        stateChanged(); 
    }

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		for(PartType requestedType : requestList) {
			getParts(requestedType);
			return true;
		}
		for(MyPart part: currentParts) {
			if(part.status==LaneStatus.BEGINNING_LANE) {
				giveToNest(part.part);
				return true;
			}
		}
		return false;
	}

	public void getParts(PartType requestedType) { 
		print("Telling Feeder that it needs a part");
        feeder.msgINeedPart(requestedType);
        requestList.remove(requestedType);
        stateChanged(); 
    }   
    public void giveToNest(Part part) {
    	print("Giving part to Nest");
        //GUILane.givePartToNest(part);  
        nest.msgHereIsPart(part);
    	for(MyPart currentPart : currentParts) {
    		if(currentPart.part == part) {
    			currentParts.remove(currentPart);
    			return;
    		}
    	}
        stateChanged(); 
    }
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
