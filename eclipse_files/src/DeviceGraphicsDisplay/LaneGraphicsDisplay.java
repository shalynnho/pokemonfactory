package DeviceGraphicsDisplay;

import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * 
 * @author Shalynn Ho
 *
 */
public class LaneGraphicsDisplay extends DeviceGraphicsDisplay {
	// max number of parts that can be on a Lane
	private static final int MAX_PARTS;
	// horizontal length of the Lane image
	private static final int IMG_LENGTH;
	// x-coordinates of beginning and end of lane
	private static final int LANE_BEG_X, LANE_END_X;
	// width and height of the part
	private static final int PART_WIDTH, PART_HEIGHT;
	
	// the LaneManager (client) which talks to the Server
	private LaneManager laneManager;
	
	// stores static ImageIcon emptyLane1, emptyLane2
	private static ArrayList<ImageIcon> emptyLaneImg;
	// stores static ImageIcon partXXLane1, partXXLane where XX is the part type
	private static ArrayList<ImageIcon> partXXImg;
	
	// the ID of this Lane
	private int laneID;
	// dynamically stores images of Lane
	private ArrayList<ImageIcon> imgsOnLane;
	// true if Lane is on
	private boolean laneOn;
	
	
	public LaneGraphicsDisplay(LaneManager laneManager, int laneID) {
		
	}
	
	public void paintComponent(Graphics2D g2) {
		
	}
	
	public void receiveData(Request r) {
		
	}
	
	public void receivePart(PartGraphicsDisplay p) {
		
	}
	
	public void givePartToNest(PartGraphicsDisplay p) {
		
	}
	
	public void purge() {
		
	}
	
	public void setAmplitude(int amp) {
		
	}
	
	public void toggleSwitch(boolean on) {
		
	}
	
	private void movePartDownLane() {
		
	}

	private void lineUpParts() {
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
