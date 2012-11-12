package factory;

import java.io.Serializable;


public class Order implements Serializable{    
	public orderState state;    
	public KitConfig kitConfig;    
	public int numberOfKits;  
	public boolean cancel;
	
	public static enum orderState {PENDING, ORDERED, CANCEL};
	
	public Order(KitConfig kc, int numKits){    
		this.state = orderState.PENDING;    
		this.kitConfig = kc;    
		numberOfKits = numKits;
		cancel = false;
	}    
}    