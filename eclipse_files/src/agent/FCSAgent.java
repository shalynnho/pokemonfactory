package agent;

import java.util.ArrayList;

import DeviceGraphics.DeviceGraphics;
import agent.data.Bin;
import agent.data.PartType;
import agent.interfaces.Conveyor;
import agent.interfaces.FCS;
import agent.interfaces.Gantry;
import agent.interfaces.Nest;
import agent.interfaces.PartsRobot;
import agent.interfaces.Stand;
import factory.Order;

/**
 * Unused in V0
 * @author Daniel Paje, Michael Gendotti
 */
public class FCSAgent extends Agent implements FCS {
	
	private Stand stand;    
	private PartsRobot partsRobot;    
	private Gantry gantry;    
	private ArrayList<Nest> nests;    
	private Conveyor conveyor;
	private myState state;    
	private ArrayList<Order> orders;  
	
	//private FCSGraphics fcsGraphics;
	
	private final String name;

	public enum myState {PENDING, STARTED, LOADED};    
	public enum orderState {PENDING, ORDERED, CANCEL};
	
	public FCSAgent(String name){
		super();
		this.name=name;
		this.nests=new ArrayList<Nest>();
		this.orders=new ArrayList<Order>();
	}
	
	public FCSAgent(){
		super();
		this.name="FCS";
	}

	@Override
	public void msgAddKitsToQueue(ArrayList<PartType> parts,int numOfKits){    
	    orders.add(new Order(parts,numOfKits));    
	}    
	
	@Override
	public void msgStopMakingKit(ArrayList<PartType> parts){    
	    for(Order o: orders){    
	        if(o.parts.equals(parts)){
	        	o.cancel=true;;    
	        }
	    }    
	}
	
	@Override
	public void msgStartProduction(){    
	    state=myState.STARTED;    
	}    

	@Override
	public void msgOrderFinished(){  
		for(Order o:orders){
			if(o.state==orderState.ORDERED){
				orders.remove(o);
				break;
			}
		}
	    state=myState.STARTED;    
	}    

	@Override
	public boolean pickAndExecuteAnAction(){
		if(state==myState.STARTED){    
		    if(!orders.isEmpty()){   
		    	for(Order o:orders){
					if(o.cancel){
						cancelOrder(o);
						return true;
					}
				}
		        for(Order o:orders){    
		            if(o.state==orderState.PENDING){    
		                placeOrder(o);  
		                return true;
		            }    
		        }    
		    }    
		}
		return false;
	}
	
	public void placeOrder(Order o){    
	    o.state=orderState.ORDERED;    
	    state=myState.LOADED;    
	    
	    conveyor.msgHereIsKitConfiguration(o.parts);
	    stand.msgMakeKits(o.numberOfKits);    
	    
	    partsRobot.msgHereIsKitConfiguration(o.parts);   
	    
	    for(int i=0;i<o.parts.size();i++)    
	    {    
	    	gantry.msgHereIsBinConfig(new Bin(o.parts.get(i),i+1));  
	    }  
	    
	    for(int i=0;i<o.parts.size();i++){    
	        nests.get(i).msgHereIsPartType(o.parts.get(i));    
	    }       
	}    
	
	public void cancelOrder(Order o){
		if(o.state==orderState.ORDERED){
			//stand.msgStopMakingTheseKits(o.parts);
			orders.remove(o);
		} else {
			orders.remove(o);
		}
	}

	public void setStand(Stand stand){
		this.stand=stand;
	}
	
	public void setPartsRobot(PartsRobot partsRobot){
		this.partsRobot=partsRobot;
	}
	
	public void setGantry(Gantry gantry){
		this.gantry=gantry;
	}
	
	public void setConveyor(Conveyor conveyor){
		this.conveyor=conveyor;
	}
	
	public void setNest(Nest nest){
		this.nests.add(nest);
	}
	
	public void setNests(ArrayList<Nest> nests){
		this.nests=nests;
	}
	
	public Stand getStand(){
		return stand;
	}
	
	public PartsRobot getPartsRobot(){
		return partsRobot;
	}
	
	public Gantry getGantry(){
		return gantry;
	}
	
	public Conveyor getConveyor(){
		return conveyor;
	}
	
	public ArrayList<Nest> getNests(){
		return nests;
	}
	
	public String getName(){
		return name;
	}

	@Override
	public void setGraphicalRepresentation(DeviceGraphics dg) {
		//fcsGraphics=(fcsGraphics) dg;
	}
	
	public DeviceGraphics getGraphicalRepresentation() {
		//return fcsGraphics;
		return null;
	}
	
	public ArrayList<Order> getOrders(){
		return orders;
	}

}
