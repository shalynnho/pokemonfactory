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
		
		// test flip diverter command
		JButton testButton = new JButton("Flip Diverter");
		testButton.addActionListener(new NetworkingButtonListener("flipdiv", Constants.FEEDER_TARGET, writer));
		panel.add(testButton);
		
		// test bin on feeder
		JButton haveBin = new JButton("Get Bin");
		haveBin.addActionListener(new NetworkingButtonListener("getbin", Constants.FEEDER_TARGET, writer));
		panel.add(haveBin);
		
		// test feed parts to div
		JButton feedDiv = new JButton("Feed Diverter");
		feedDiv.addActionListener(new NetworkingButtonListener("feeddiverter", Constants.FEEDER_TARGET, writer));
		panel.add(feedDiv);
		
		// test feed parts to lane
		JButton feedLane = new JButton("Feed Lane");
		feedLane.addActionListener(new NetworkingButtonListener("feedlane", Constants.FEEDER_TARGET, writer));
		panel.add(feedLane);
		
		timer = new Timer(Constants.TIMER_DELAY, this);
		timer.start();
	}
	
	public void initDevices() {
		// example:
		addDevice(Constants.LANE_TARGET+":"+0, new LaneGraphicsDisplay(this, new Location(200, 100), 0));
		addDevice(Constants.LANE_TARGET+":"+1, new LaneGraphicsDisplay(this, new Location(200, 150), 1));
		addDevice(Constants.FEEDER_TARGET, new FeederGraphicsDisplay(this, new Location(600, 100)));
	}
	
	@Override
	public void receiveData(Request req) {
		devices.get(req.getTarget()).receiveData(req);
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
