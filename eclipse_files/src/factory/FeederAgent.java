package factory;

import java.util.ArrayList;
import java.util.List;

import factory.data.Part;
import factory.data.PartType;
import factory.interfaces.Feeder;
import factory.interfaces.Lane;
import agent.Agent;

public class FeederAgent extends Agent implements Feeder {
	public List<PartType> requestList = new ArrayList<PartType>();     
    public List<MyPart> currentParts = new ArrayList<MyPart>();
    
    private GantryAgent gantry;
    private LaneAgent lane;
    
    String name;
    
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
    	
    	this.name = name;
    	
    }
    
    public enum FeederStatus {
		IN_FEEDER,IN_DIVERTER, END_DIVERTER
	};
    
    public void msgINeedPart(PartType type) {      
        requestList.add(type);       
        stateChanged(); 
    }       
    public void msgHereAreParts(Part p) {   
    	//Changing this to PartType in V1, then feeder will just generate a new part whenever you need one 
    	//per Prof W. saying bins carry thousands of parts in class
        currentParts.add(new MyPart(p));        
        stateChanged(); 
    }       
    public void msgGivePartToDiverterDone(Part part) {      
        for(MyPart currentPart:currentParts){
        	if(currentPart.part==part)
        		currentPart.status = FeederStatus.END_DIVERTER;     
        }
        stateChanged(); 
    }       
    public void msgGivePartToLaneDone(Part part) {      
        stateChanged(); 
    }
    
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		for(PartType requestedType : requestList) {
			getParts(requestedType);
			return true;
		}
		for(MyPart currentPart:currentParts) {
			if(currentPart.status==FeederStatus.END_DIVERTER) {
				giveToLane(currentPart.part);
				return true;
			}
		}
		for(MyPart currentPart:currentParts) {
			if(currentPart.status==FeederStatus.IN_FEEDER) {
				giveToDiverter(currentPart.part);
				return true;
			}
		}
		return false;
	}
	
	public void getParts(PartType requestedType) {
		print("Telling gantry that it needs parts");
        gantry.msgINeedParts(requestedType);
        requestList.remove(requestedType);
        stateChanged(); 
    }
	
    public void giveToDiverter(Part part) {  
    	print("Giving parts to diverter");
        //GUIDiverter.face(part.laneOrientation);
    	//SEMAPHORE GOES HERE
        //GUIFeeder.givePartToDiverter(part); 
        for(MyPart currentPart:currentParts) {
        	if(currentPart.part==part)
        		currentPart.status = FeederStatus.IN_DIVERTER;   
        }
        stateChanged(); 
    }   
    public void giveToLane(Part part) {  
    	print("Giving part to lane");
        lane.msgHereIsPart(part);  
        //GUIDiverter.givePartToLane(part);
    	for(MyPart currentPart:currentParts) {
        	if(currentPart.part==part) {
        		currentParts.remove(currentPart);
        		return;
        	}
    	}
        stateChanged();
    }
	
    //GETTERS AND SETTERS
    public String getName() {
    	return name;
    }
    public void setGantry(GantryAgent gantry) {
    	this.gantry = gantry;
    }
    public void setLane(LaneAgent lane) {
    	this.lane = lane;
    }
    
}
