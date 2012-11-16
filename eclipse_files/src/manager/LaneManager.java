package manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import manager.util.NetworkingButtonListener;
import manager.util.OverlayPanel;

import DeviceGraphicsDisplay.*;
import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;

public class LaneManager extends Client implements ActionListener{
	// Temp values. Feel free to change
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 700;
	
	// DISPLAY OFFSET, feel free to change
	private static final int OFFSET = -300;
	
	// Create a new timer
	private Timer timer;
	
	/**
	 * Constructor
	 */
	public LaneManager() {
		super();
		clientName = Constants.LANE_MNGR_CLIENT;
		
		initStreams();
		initGUI();
		initDevices();
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
		// TODO add all devices (4) feeders, (8) lanes

		for (int i = 0; i < Constants.LANE_COUNT; i++) {
			addDevice(Constants.LANE_TARGET + i, new LaneGraphicsDisplay(this, i));
		}
	
		for (int i = 0; i < Constants.NEST_COUNT; i++) {
			addDevice(Constants.NEST_TARGET + i, new NestGraphicsDisplay(this, i));
		}

		for (int i = 0; i < Constants.FEEDER_COUNT; i++) {
			addDevice(Constants.FEEDER_TARGET + i, new FeederGraphicsDisplay(this, i));
		}
		
		addDevice(Constants.CAMERA_TARGET, new CameraGraphicsDisplay(this));

	}
	
	/**
	 * Forward network requests to devices processing
	 * @param req incoming request
	 */
	public void receiveData(Request req) {
		devices.get(req.getTarget()).receiveData(req);
	}

	/**
	 * 
	 * @return display offset
	 */
	public int getOffset() {
		return OFFSET;
	}
	
	/**
	 * Main method sets up the JFrame
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Client.setUpJFrame(frame, WINDOW_WIDTH, WINDOW_HEIGHT, "Lane Manager");
		
		LaneManager mngr = new LaneManager();
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
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
}
