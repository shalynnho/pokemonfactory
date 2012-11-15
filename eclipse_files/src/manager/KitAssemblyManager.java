package manager;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import DeviceGraphicsDisplay.DeviceGraphicsDisplay;
import Networking.Client;
import Networking.Request;
import Utils.Constants;

public class KitAssemblyManager extends Client implements ActionListener {
	// Window dimensions
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	
	// Create a timer
	private Timer timer;
	
	/**
	 * Constructor
	 */
	public KitAssemblyManager() {
		super();
		clientName = Constants.GANTRY_ROBOT_MNGR_CLIENT;
		
		initStreams();
		initGUI();
		initDevices();
	}

	/**
	 * Forward network requests to devices processing
	 * @param req incoming request
	 */
	public void receiveData(Request req) {
		devices.get(req.getTarget()).receiveData(req);
	}
	
	/**
	 * Initialize the GUI and start the timer.
	 */
	public void initGUI() {
		timer = new Timer(Constants.TIMER_DELAY, this);
		timer.start();
	}
	
	/**
	 * Initialize the devices
	 */
	public void initDevices() {
		// TODO - add KitAssemblyManager devices here
	}
	
	/**
	 * Main method sets up the JFrame
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Client.setUpJFrame(frame, WINDOW_WIDTH, WINDOW_HEIGHT, "Kit Assembly Managers");
		
		FactoryProductionManager mngr = new FactoryProductionManager();
		frame.add(mngr);
		mngr.setVisible(true);
		frame.validate();
	}
	
	/**
	 * This function handles painting of graphics
	 */
	public void paintComponent(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;
		g.drawImage(Constants.CLIENT_BG_IMAGE, 0, 0, this);
		
		for(DeviceGraphicsDisplay device : devices.values()) {
			device.draw(this, g);
		}
	}

	/**
	 * This function handles action events.
	 */
	public void actionPerformed(ActionEvent ae) {
		repaint();
	}
}
