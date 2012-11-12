package DeviceGraphicsDisplay;

import java.awt.Graphics2D;

import javax.swing.JComponent;

import Networking.Request;
import Utils.Location;
import factory.PartType;

/**
 * 
 * @author Shalynn Ho
 *
 */
public class StandGraphicsDisplay extends DeviceGraphicsDisplay {
	
	// the kit that is currently on the stand
	private KitGraphicsDisplay kit;
	private boolean isEmpty;
	
	
	private StandGraphicsDisplay() {
		isEmpty = true;
	}
	
	public void giveKit(KitGraphicsDisplay kgd) {
		
	}
	
	public void receiveKit() {
		
	}
	
	public void receivePart() {
		
	}


	@Override
	public void draw(JComponent c, Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveData(Request req) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocation(Location newLocation) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
