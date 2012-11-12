package factory;

import java.util.ArrayList;

public class Order{    
	public orderState state;    
	public ArrayList<PartType> parts;    
	public int numberOfKits;  
	public boolean cancel;
	
	public static enum orderState {PENDING, ORDERED, CANCEL};
	
	public Order(ArrayList<PartType> parts, int numKits){    
		this.state = orderState.PENDING;    
		this.parts=parts;    
		numberOfKits=numKits;
		cancel=false;
	}    
}    