package DeviceGraphics;

import java.util.ArrayList;
import Networking.Request;
import Networking.Server;


//INCOMPLETE
/*
 *Author: Matt Zecchini
*/



public class StandGraphics implements DeviceGraphics{
	
	//This ArrayList keeps track of each kitting stand. There will be three stands: Inspection,
	//Stand 1, and Stand 2. 
	ArrayList<KitGraphics> standPositions = new ArrayList<KitGraphics>();
	
	public StandGraphics(){
		
	}
	
	public void receivePart(PartGraphics part){
		//Part Robot places part on kit/kitting stand, this method passes this part 
	}
	
	public void receiveKit(KitGraphics kg){
		
	}
	
	public KitGraphics giveKit(KitGraphics kg){
		//once kit is full, use this function to give it to the Kit Robot 
		return kg;
	}

	public void receiveData(Request req){
		
	}
	
}
