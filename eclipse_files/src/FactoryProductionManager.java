import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

import DeviceGraphicsDisplay.CameraGraphicsDisplay;
import DeviceGraphicsDisplay.ConveyorGraphicsDisplay;
import DeviceGraphicsDisplay.DeviceGraphicsDisplay;
import DeviceGraphicsDisplay.FeederGraphicsDisplay;
import DeviceGraphicsDisplay.KitGraphicsDisplay;
import DeviceGraphicsDisplay.KitRobotGraphicsDisplay;
import DeviceGraphicsDisplay.LaneGraphicsDisplay;
import DeviceGraphicsDisplay.NestGraphicsDisplay;
import DeviceGraphicsDisplay.PartsRobotDisplay;
import GUI.FactoryProductionMgrGUI;
import Networking.Client;
import Networking.Request;
import Utils.Constants;
import factory.KitConfig;
import factory.Order;


public class FactoryProductionManager extends Client implements ActionListener {
	// WINDOW DIMENSIONS
	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 700;
	
	private FactoryProductionMgrGUI fpmGUI;
	
	private Timer timer;
	
	/**
	 * Constructor
	 */
	public FactoryProductionManager() {
		super();
		clientName = Constants.FACTORY_PROD_MNGR_CLIENT;
		
		initStreams();
		initGUI();
		initDevices();
	}
	
	/**
	 * Initialize the GUI and start the timer.
	 */
	public void initGUI() {
		fpmGUI = new FactoryProductionMgrGUI(WINDOW_HEIGHT);
		
		add(fpmGUI, BorderLayout.EAST);
		fpmGUI.setVisible(true);
		
		//TODO: Add FPM GUI stuff here.
			//	GUI Panel to select and order kits. Adjacent to graphics panel?
			// 	NetworkingButtonListener
		
		timer = new Timer(Constants.TIMER_DELAY, this);
		timer.start();
	}
	
	/**
	 * Initialize the devices
	 */
	public void initDevices() {
		// TODO: adjust LOCATIONS of each device
		
		for (int i = 0; i < Constants.LANE_COUNT; i++) {
			addDevice(Constants.LANE_TARGET + i, new LaneGraphicsDisplay(this, i));
		}
	
		for (int i = 0; i < Constants.NEST_COUNT; i++) {
			addDevice(Constants.NEST_TARGET + i, new NestGraphicsDisplay(this, i));
		}

		for (int i = 0; i < Constants.FEEDER_COUNT; i++) {
			addDevice(Constants.FEEDER_TARGET + i, new FeederGraphicsDisplay(this, i));
		}

		addDevice(Constants.CONVEYOR_TARGET, new ConveyorGraphicsDisplay(this));
		
		addDevice(Constants.KIT_ROBOT_TARGET, new KitRobotGraphicsDisplay(this));
		
		addDevice(Constants.KIT_TARGET, new KitGraphicsDisplay());
		
		addDevice(Constants.CAMERA_TARGET, new CameraGraphicsDisplay(this));
		
		addDevice(Constants.PARTS_ROBOT_TARGET, new PartsRobotDisplay(this));
		
	}
	
	@Override
	public void receiveData(Request req) {
		if (req.getTarget().equals(Constants.ALL_TARGET)) {
			if (req.getCommand().equals(Constants.FCS_UPDATE_KITS)) {
				fpmGUI.updateKitConfigs((ArrayList<KitConfig>)req.getData());
			} else if (req.getCommand().equals(Constants.FCS_UPDATE_ORDERS)) {
				fpmGUI.updateOrders((ArrayList<Order>)req.getData());
			}
		} else {
			devices.get(req.getTarget()).receiveData(req);
		}
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
