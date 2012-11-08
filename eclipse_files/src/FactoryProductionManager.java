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

import DeviceGraphicsDisplay.*;
import GUI.NetworkingButtonListener;
import GUI.OverlayPanel;
import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;


public class FactoryProductionManager extends Client implements ActionListener {
	// WINDOW DIMENSIONS
	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 600;
	
	private Timer timer;
	
	
	public FactoryProductionManager() {
		super();
		clientName = Constants.FACTORY_PROD_MNGR_CLIENT;
		
		initStreams();
		initGUI();
		initDevices();
	}
	
	public void initGUI() {
		JLabel label = new JLabel("Factory Production Manager");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("SansSerif", Font.PLAIN, 40));
		label.setHorizontalAlignment(JLabel.CENTER);
		add(label);
		
		OverlayPanel panel = new OverlayPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setVisible(true);
		
		//TODO: Add FPM GUI stuff here.
			//	GUI Panel to select and order kits. Adjacent to graphics panel?
			// 	NetworkingButtonListener
		
		timer = new Timer(Constants.TIMER_DELAY, this);
		timer.start();
	}
	
	public void initDevices() {
		// TODO: add ALL devices
		// TODO: adjust LOCATIONS of each device
		
		for (int i = 0; i < Constants.LANE_COUNT; i++) {
			addDevice(Constants.LANE_TARGET + i, new LaneGraphicsDisplay(this, i));
		}
	
		for (int i = 0; i < Constants.NEST_COUNT; i++) {
			addDevice(Constants.NEST_TARGET + i, new NestGraphicsDisplay(this, i));
		}

		// TODO: i'd rather use a for loop to initialize this
			// Harry, if we store the start locations as constants, could you adjust your FGD
			// code to set the start location based on the ID assigned here
		addDevice(Constants.FEEDER_TARGET + 0, new FeederGraphicsDisplay(0, this, Constants.FEEDER0_LOC));
//		addDevice(Constants.FEEDER_TARGET + 1, new FeederGraphicsDisplay(1, this, new Location(600, 100)));
//		addDevice(Constants.FEEDER_TARGET + 2, new FeederGraphicsDisplay(2, this, new Location(600, 100)));
//		addDevice(Constants.FEEDER_TARGET + 3, new FeederGraphicsDisplay(3, this, new Location(600, 100)));

		// TODO: uncomment when conveyor doesn't break everything anymore
//		addDevice(Constants.CONVEYOR_TARGET, new ConveyorGraphicsDisplay(this, Constants.CONVEYOR_LOC));
		
		addDevice(Constants.KIT_ROBOT_TARGET, new KitRobotGraphicsDisplay(this, Constants.KIT_ROBOT_LOC));
		
		addDevice(Constants.KIT_TARGET, new KitGraphicsDisplay(this, Constants.KIT_LOC));
		
		addDevice(Constants.CAMERA_TARGET, new CameraGraphicsDisplay(this, Constants.CAMERA_LOC));
		
		addDevice(Constants.PARTS_ROBOT_TARGET, new PartsRobotDisplay(this, Constants.PARTS_ROBOT_LOC));
		
	}
	
	@Override
	public void receiveData(Request req) {
		devices.get(req.getTarget()).receiveData(req);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Client.setUpJFrame(frame, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		FactoryProductionManager mngr = new FactoryProductionManager();
		frame.add(mngr);
		mngr.setVisible(true);
		frame.validate();
	}
	
	@Override
	public void paintComponent(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;
		g.drawImage(Constants.CLIENT_BG_IMAGE, 0, 0, this);
		
		for(DeviceGraphicsDisplay device : devices.values()) {
			device.draw(this, g);
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}

}
