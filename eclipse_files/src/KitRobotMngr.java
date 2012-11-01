import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Networking.Client;
import Networking.Request;


public class KitRobotMngr extends Client{
	// Temp values. Feel free to change
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	
	public KitRobotMngr() {
		JLabel label = new JLabel("Kit Robot Manager");
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
		KitRobotMngr mngr = new KitRobotMngr();
		mngr.setBackground(Color.BLACK);
		mngr.setTitle("Factory Project - Kit Robot Manager");
		mngr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mngr.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		mngr.setVisible(true);
	}
}
