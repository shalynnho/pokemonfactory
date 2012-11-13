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

import DeviceGraphicsDisplay.*;
import GUI.NetworkingButtonListener;
import GUI.OverlayPanel;
import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;


public class LaneManager extends Client implements ActionListener{
	// Temp values. Feel free to change
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	
	private Timer timer;
	
	public LaneManager() {
		super();
		clientName = Constants.LANE_MNGR_CLIENT;
		
		initStreams();
		initGUI();
		initDevices();
	}
	
	public void initGUI() {
		JLabel label = new JLabel("Lane Manager");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("SansSerif", Font.PLAIN, 40));
		label.setHorizontalAlignment(JLabel.CENTER);
		add(label);
		
		OverlayPanel panel = new OverlayPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setVisible(true);
		
		// TODO: scrap all of this as we will not need buttons in V1
		
		// test feed parts to div
		// JButton feedDiv = new JButton("Feed Diverter");
		// feedDiv.addActionListener(new NetworkingButtonListener(Constants.FEEDER_MOVE_TO_DIVERTER_COMMAND, Constants.FEEDER_TARGET, writer));
		// panel.add(feedDiv);
		
		// test feed parts to lane
		// JButton feedLane = new JButton("Feed Lane");
		// feedLane.addActionListener(new NetworkingButtonListener(Constants.FEEDER_MOVE_TO_LANE_COMMAND, Constants.FEEDER_TARGET, writer));
		// panel.add(feedLane);

		// test flip diverter command
		/*JButton testButton = new JButton("Flip Diverter");
		testButton.addActionListener(new NetworkingButtonListener(Constants.FEEDER_FLIP_DIVERTER_COMMAND, Constants.FEEDER_TARGET, writer));
		panel.add(testButton);*/
		
		// test bin on feeder
		JButton haveBin = new JButton("Get Bin");
		haveBin.addActionListener(new NetworkingButtonListener("TESTING_FEEDER", Constants.FEEDER_TARGET + 0, writer));
		haveBin.addActionListener(new NetworkingButtonListener("TESTING_LANE", Constants.LANE_TARGET + 0, writer));
		haveBin.addActionListener(new NetworkingButtonListener("TESTING_LANE", Constants.LANE_TARGET + 1, writer));
		panel.add(haveBin);
		
		/*// test lane 0 receive part
		JButton laneRecPart0 = new JButton("Send Part Top");
		laneRecPart0.addActionListener(new NetworkingButtonListener(Constants.LANE_RECEIVE_PART_COMMAND, Constants.LANE_TARGET+":"+0, writer));
		panel.add(laneRecPart0);
		
		// test lane 1 receive part
		JButton laneRecPart1 = new JButton("Send Part Bottom");
		laneRecPart1.addActionListener(new NetworkingButtonListener(Constants.LANE_RECEIVE_PART_COMMAND, Constants.LANE_TARGET+":"+1, writer));
		panel.add(laneRecPart1);*/
		
		timer = new Timer(Constants.TIMER_DELAY, this);
		timer.start();
	}
	
	public void initDevices() {
		// TODO add all devices (4) feeders, (8) lanes
		
		addDevice(Constants.LANE_TARGET + 0, new LaneGraphicsDisplay(this, new Location(199, 100), 0));
		addDevice(Constants.LANE_TARGET + 1, new LaneGraphicsDisplay(this, new Location(199, 175), 1));
		addDevice(Constants.FEEDER_TARGET + 0, new FeederGraphicsDisplay(this, 0));
	}
	
	@Override
	public void receiveData(Request req) {
		if (req.getTarget().equals(Constants.ALL_TARGET)) {
			// TODO code to overwrite ArrayList with req.getData()
		} else {
			devices.get(req.getTarget()).receiveData(req);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Client.setUpJFrame(frame, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		LaneManager mngr = new LaneManager();
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
