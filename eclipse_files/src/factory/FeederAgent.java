package factory;

import java.util.ArrayList;
import java.util.List;

import factory.data.Part;
import factory.data.PartType;
import factory.interfaces.Feeder;
import factory.interfaces.Lane;
import agent.Agent;

public class FeederAgent extends Agent implements Feeder {
	List<PartType> requestList = new ArrayList<PartType>();     
    List<MyPart> currentParts = new ArrayList<MyPart>();
    
    private GantryAgent gantry;
    private Lane lane;
    
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
		}
		for(MyPart currentPart:currentParts) {
			if(currentPart.status==FeederStatus.IN_DIVERTER) {
				giveToDiverter(currentPart.part);
			}
		}
		for(MyPart currentPart:currentParts) {
			if(currentPart.status==FeederStatus.END_DIVERTER) {
				giveToLane(currentPart.part);
			}
		}
		return false;
	}
	
	public void getParts(PartType requestedType) {   
        gantry.msgINeedParts(requestedType);
        requestList.remove(requestedType);
        stateChanged(); 
    }
	
    public void giveToDiverter(Part part) {  
        //GUIDiverter.face(part.laneOrientation); 
        //GUIFeeder.givePartToDiverter(part); 
        for(MyPart currentPart:currentParts) {
        	if(currentPart.part==part)
        		currentPart.status = FeederStatus.IN_DIVERTER;   
        }
        stateChanged(); 
    }   
    public void giveToLane(Part part) {  
        //lane.msgHereIsPart(part);  
        //GUIDiverter.givePartToLane(part);   
    	for(MyPart currentPart:currentParts) {
        	if(currentPart.part==part)
        		currentParts.remove(currentPart);
    	}
        stateChanged();
    }
	
    //GETTERS AND SETTERS
    public void setGantry(GantryAgent gantry) {
    	this.gantry = gantry;
    }
    public void setLane(Lane lane) {
    	this.lane = lane;
    }
    
}
