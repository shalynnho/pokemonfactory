package manager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

import manager.panel.FactoryProductionManagerPanel;
import DeviceGraphicsDisplay.*;
import Networking.*;
import Utils.Constants;
import factory.KitConfig;
import factory.Order;


public class FactoryProductionManager extends Client implements ActionListener {
	// WINDOW DIMENSIONS
	private static final int WINDOW_WIDTH = 1200;
	private static final int WINDOW_HEIGHT = 700;
	
	private FactoryProductionManagerPanel fpmPanel;
	
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
		fpmPanel = new FactoryProductionManagerPanel(this, WINDOW_HEIGHT);
		
		add(fpmPanel, BorderLayout.EAST);
		fpmPanel.setVisible(true);
		
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
		
		for (int i = 0; i < Constants.STAND_COUNT; i++) {
			addDevice(Constants.STAND_TARGET + i, new StandGraphicsDisplay(this, i));
		}

		addDevice(Constants.CONVEYOR_TARGET, new ConveyorGraphicsDisplay(this));
		
		addDevice(Constants.KIT_ROBOT_TARGET, new KitRobotGraphicsDisplay(this));
		
		addDevice(Constants.CAMERA_TARGET, new CameraGraphicsDisplay(this));
		
		addDevice(Constants.PARTS_ROBOT_TARGET, new PartsRobotDisplay(this));
		
		addDevice(Constants.GANTRY_ROBOT_TARGET, new GantryGraphicsDisplay(this));
		
	}
	
	@Override
	public void receiveData(Request req) {
		if (req.getTarget().equals(Constants.ALL_TARGET)) {
			if (req.getCommand().equals(Constants.FCS_UPDATE_KITS)) {
				fpmPanel.updateKitConfigs((ArrayList<KitConfig>)req.getData());
			} else if (req.getCommand().equals(Constants.FCS_UPDATE_ORDERS)) {
				fpmPanel.updateOrders((ArrayList<Order>)req.getData());
			}
		} else {
			devices.get(req.getTarget()).receiveData(req);
		}
	}
	
	public void createOrder(Order o) {
		this.sendData(new Request(Constants.FCS_ADD_ORDER, Constants.FCS_TARGET, o));
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
