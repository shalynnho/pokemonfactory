import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import GUI.OverlayPanel;
import Networking.Client;
import Networking.Request;
import Utils.Constants;


public class KitRobotManager extends Client{
	// Temp values. Feel free to change
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	
	public KitRobotManager() {
		clientName = Constants.KIT_ROBOT_MNGR_CLIENT;
		// initStreams();
		
		JLabel label = new JLabel("Kit Robot Manager");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("SansSerif", Font.PLAIN, 40));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVisible(true);
		add(label);
		
		OverlayPanel panel = new OverlayPanel();
		panel.add(new JLabel("hello"));
		add(panel, BorderLayout.SOUTH);
		panel.setVisible(true);
	}
	
	@Override
	public void receiveData(Request req) {
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Client.setUpJFrame(frame, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		KitRobotManager mngr = new KitRobotManager();
		frame.add(mngr);
		mngr.setVisible(true);
		frame.validate();
	}
	
	@Override
	public void paintComponent(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;
		
		g.drawImage(Constants.CLIENT_BG_IMAGE, 0, 0, this);
	}
	
	private class KitRobotManagerButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//TODO: change the command.
			writer.sendData(new Request("Some command", Constants.SERVER_TARGET, null));
		}
	}
}
