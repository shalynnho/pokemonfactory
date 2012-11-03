import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Networking.Client;
import Networking.Request;
import Utils.Constants;


public class LaneManager extends Client {
	// Temp values. Feel free to change
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	
	public LaneManager() {
		clientName = Constants.LANE_MNGR_CLIENT;
		initStreams();
		
		JLabel label = new JLabel("Lane Manager");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("SansSerif", Font.PLAIN, 40));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVisible(true);
		add(label);
	}
	
	@Override
	public void receiveData(Request req) {
	}

	public static void main(String[] args) {
		LaneManager mngr = new LaneManager();
		mngr.setBackground(Color.BLACK);
		mngr.setTitle("Factory Project - Lane Manager");
		mngr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mngr.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		mngr.setVisible(true);
	}
	
	private class LaneManagerButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//TODO: change the command.
			writer.sendData(new Request("Some command", Constants.SERVER_TARGET, null));
		}
	}
}
