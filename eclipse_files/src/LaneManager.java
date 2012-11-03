import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

import DeviceGraphicsDisplay.FeederGraphicsDisplay;
import Networking.Client;
import Networking.Request;
import Utils.Constants;


public class LaneManager extends Client {
	// Temp values. Feel free to change
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	
	ArrayList<FeederGraphicsDisplay> feeders = new ArrayList<FeederGraphicsDisplay>();
	Image bgImage;
	
	public LaneManager() {
		clientName = Constants.LANE_MNGR_CLIENT;
		//initStreams();
		
		JLabel label = new JLabel("Lane Manager");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("SansSerif", Font.PLAIN, 40));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVisible(true);
		add(label);
		this.setVisible(true);
		
		//bgImage = Toolkit.getDefaultToolkit().createImage(("src/images/bg.jpeg"));
		
		try{
		bgImage = ImageIO.read(new File("src/images/bg.jpeg"));
		}
		catch(IOException e){
			System.out.println("Wat?");
		}
	}
	
	@Override
	public void receiveData(Request req) {
		if(req.getTarget().contains(Constants.FEEDER_TARGET)) {
			int targetID = Integer.parseInt(req.getTarget().substring(req.getTarget().indexOf(':')+1));
			feeders.get(targetID).receiveData(req);
		}
	}

	public static void main(String[] args) {
		LaneManager mngr = new LaneManager();
		mngr.setBackground(Color.BLACK);
		mngr.setTitle("Factory Project - Lane Manager");
		mngr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mngr.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		mngr.setVisible(true);
	}
	
	@Override
	public void paint(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;
		
		g.drawImage(bgImage, 0, 0, this);
		//super.paint(gg);
		//Needed to comment this out in order for bg to show.
	}
	
	private class LaneManagerButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//TODO: change the command.
			writer.sendData(new Request("Some command", Constants.SERVER_TARGET, null));
		}
	}
}
