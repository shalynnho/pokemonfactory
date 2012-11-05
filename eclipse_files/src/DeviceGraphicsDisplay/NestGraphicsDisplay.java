package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import Networking.Request;
import Utils.Location;

/**
 * @author vanshjain
 * 
 */


public class NestGraphicsDisplay extends DeviceGraphicsDisplay {
	
	private static Image nestImg;
	
	// max number of parts this Nest holds
	private static final int MAX_PARTS=8;
	// x-coordinate of the Nest
	private static final int NEST_X=119;
	// y-coordinate of the Nest
	private static int NEST_Y;
	// width and height of the nest
	private static final int NEST_WIDTH=45; 
	private static final int NEST_HEIGHT=80;
	// width and height of a part
	private static final int PART_WIDTH=20, PART_HEIGHT=21;
	
	// images of an empty nest (top/bottom)
	private static ImageIcon emptyNestImg1, emptyNestImg2;
	
	// image of partXX in 1 unit of nest
	// need 1 for every possible part
	private static ImageIcon partXXNestImg;
	
	// the LaneManager (client) which talks to the Server
	private LaneManager laneManager;
	// the id of this nest
	private int nestID;
	// true if spot is filled, false if not
	private ArrayList<Boolean> nestSpots;

	public NestGraphicsDisplay(LaneManager lm, int id) {
		laneManager = lm;
		nestID = id;
		nestImg = Toolkit.getDefaultToolkit().getImage("src/images/Nest.png");
		if(nestID==0){
			NEST_Y=100;
		}
		else
			NEST_Y=175;
		
	}
	
	public void requestData(Request r) {
		
	}
	
	public void receivePart(PartGraphicsDisplay p) {
		
	}
	
	public void givePartToPartsRobot(PartGraphicsDisplay) {
		
	}
	
	public void purge() {
		
	}
	
	private void movePartsUp() {
		
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	@Override
	public void draw(JComponent c, Graphics2D g) {
		// TODO Auto-generated method stub
		
		g.drawImage(nestImg, NEST_X, NEST_Y, c)
		
	}


	@Override
	public void receiveData(Request req) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setLocation(Location newLocation) {
		// TODO Auto-generated method stub
		
	}

}
