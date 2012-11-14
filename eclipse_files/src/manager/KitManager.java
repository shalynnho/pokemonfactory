package manager;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import manager.panel.KitManagerPanel;

import factory.KitConfig;
import factory.Order;

import Networking.Client;
import Networking.Request;
import Utils.Constants;

/**
 * This class handles creation, change, and deletion of kits.
 * The GUI portion of this class is contained within KitManagerPanel class.
 * @author Harry Trieu
 *
 */
public class KitManager extends Client {
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	
	private KitManagerPanel kmPanel;
	
	/**
	 * Constructor
	 */
	public KitManager() {
		super();
		clientName = Constants.KIT_MNGR_CLIENT;
		
		initStreams();
		initGUI();
	}

	/**
	 * This function parses requests sent to the KitManager client.
	 */
	public void receiveData(Request req) {
		if (req.getTarget().equals(Constants.ALL_TARGET)) {
			if (req.getCommand().equals(Constants.FCS_UPDATE_PARTS)) {
				// TODO Panel must handle updating of parts.
			} else if (req.getCommand().equals(Constants.FCS_UPDATE_KITS)) {
				// TODO Panel must handle updating of kits.
				// TODO Does it care if kits are updated? It makes the change.
			}
		} else {
			System.out.println("KitManager received a request not addressed to: " + req.getTarget());
			System.out.println("PartsManager cannot parse this request.");
		}		
	}

	/**
	 * This function creates the GUI panel and adds it to the frame.
	 */
	public void initGUI() {
		// may have to pass in reference to this class
		kmPanel = new KitManagerPanel();
		
		add(kmPanel, BorderLayout.CENTER);
		kmPanel.setVisible(true);
	}
	
	/**
	 * The main function sets up the JFrame.
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Client.setUpJFrame(frame, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		KitManager km = new KitManager();
		frame.add(km);
		km.setVisible(true);
		frame.validate();
	}
}
