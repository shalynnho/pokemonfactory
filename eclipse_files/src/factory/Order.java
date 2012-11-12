package factory;

import java.util.ArrayList;

import agent.FCSAgent.orderState;

public class Order{    
	public orderState state;    
	public ArrayList<PartType> parts;    
	public int numberOfKits;  
	public boolean cancel;
	
	public Order(ArrayList<PartType> parts, int numKits){    
		this.state=orderState.PENDING;    
		this.parts=parts;    
		numberOfKits=numKits;
		cancel=false;
	}    
}    