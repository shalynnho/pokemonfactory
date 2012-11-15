package manager;
import java.util.ArrayList;

import javax.swing.JFrame;

import manager.panel.PartsManagerPanelV2;
import Networking.Client;
import Networking.Request;
import Utils.Constants;
import factory.PartType;

/**
 * This class handles creation, change, and deletion of parts.
 * The GUI portion of this class is contained within the PartsManagerGUI class.
 * @author Harry Trieu
 *
 */
public class PartsManager extends Client {
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	
	private PartsManagerPanelV2 pmPanel;

	/**
	 * Constructor
	 */
	public PartsManager() {
		super();
		clientName = Constants.PARTS_MNGR_CLIENT;
		
		initStreams();
		initGUI();
	}
	
	/**
	 * This function processes network requests.
	 * @param req the request to be processed
	 */
	public void receiveData(Request req) {
		if (req.getTarget().equals(Constants.ALL_TARGET)) {
			if (req.getCommand().equals(Constants.FCS_UPDATE_PARTS)) {
				pmPanel.updatePartTypes((ArrayList<PartType>)req.getData());
			}
		} else {
			System.out.println("PartsManager received a request addressed to: " + req.getTarget());
			System.out.println("PartsManager cannot parse this request.");
		}
	}
	
	public void createPart(PartType pt) {
		
	}
	
	public void editPart(PartType pt) {
		
	}
	
	public void deletePart(PartType pt) {
		
	}
	
	/**
	 * This function initializes the GUI panel.
	 */
	public void initGUI() {
		// may have to pass in reference to this class
		pmPanel = new PartsManagerPanelV2(this);
		
		add(pmPanel);
		pmPanel.setVisible(true);
	}
	
	/**
	 * This main initializes the JFrame.
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Client.setUpJFrame(frame, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		PartsManager pm = new PartsManager();
		frame.add(pm);
		pm.setVisible(true);
		frame.validate();
	}
}
