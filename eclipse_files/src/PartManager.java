import javax.swing.JFrame;

import Networking.Client;
import Networking.Request;

public class PartManager extends Client {
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;

	public PartManager() {
		
	}
	
	@Override
	public void receiveData(Request req) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Client.setUpJFrame(frame, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		PartManager partMngr = new PartManager();
		frame.add(partMngr);
		partMngr.setVisible(true);
		frame.validate();
	}
}
