package manager;

import javax.swing.Timer;

import Networking.Client;
import Networking.Request;
import Utils.Constants;

public class KitAssemblyManager extends Client {
	// change this later
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;

	private Timer timer;
	
	public KitAssemblyManager() {
		super();
		clientName = Constants.GANTRY_ROBOT_MNGR_CLIENT;
	}

	@Override
	public void receiveData(Request req) {
		// TODO Auto-generated method stub
		
	}
}
