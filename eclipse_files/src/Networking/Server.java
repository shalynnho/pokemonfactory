package Networking;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Utils.Constants;

/**
 * The Server is the "middleman" between Agents and the GUI clients. 
 * This is where constructors of the different Agents will be called, 
 * as well as establishing connections with the GUI clients.
 * 
 * @author Peter Zhang
 *
 */
public class Server {
	private ServerSocket ss;
	private Socket s;
	
	// V0 Config
	private ClientReader kitRobotMngrReader;
	private StreamWriter kitRobotMngrWriter;
	
	private ClientReader partsRobotMngrReader;
	private StreamWriter partsRobotMngrWriter;
	
	private ClientReader laneMngrReader;
	private StreamWriter laneMngrWriter;
	
	public Server() {
		try {
			ss = new ServerSocket(Constants.SERVER_PORT);
		} catch (Exception e) {
			System.out.println("Server: cannot init server socket");
			e.printStackTrace();
			System.exit(0);
		}
		
		while (true) {
			try {
				s = ss.accept();
				identifyClient(s);
			} catch (Exception e) {
				System.out.println("Server: got an exception" + e.getMessage());
			}
		}
	}
	
	/**
	 * Organize incoming streams according to the first message that we receive
	 */
	private void identifyClient(Socket s) {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(s.getInputStream());
			Request req = (Request) ois.readObject();
			if (req.getTarget() == Constants.SERVER_TARGET && req.getCommand() == Constants.IDENTIFY_COMMAND) {
				String identity = (String) req.getData();
				if (identity.equals(Constants.KIT_ROBOT_MNGR_CLIENT)) {
					kitRobotMngrReader = new ClientReader(s, this);
					kitRobotMngrWriter = new StreamWriter(s);
					new Thread(kitRobotMngrReader).start();
				} else if (identity.equals(Constants.PARTS_ROBOT_MNGR_CLIENT)) {
					partsRobotMngrReader = new ClientReader(s, this);
					partsRobotMngrWriter = new StreamWriter(s);
					new Thread(partsRobotMngrReader). start();
				} else if (identity.equals(Constants.LANE_MNGR_CLIENT)) {
					laneMngrReader = new ClientReader(s, this);
					laneMngrWriter = new StreamWriter(s);
					new Thread(laneMngrReader). start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void receiveData(Request req) {
		String target = req.getTarget();
		
		if(target.equals(Constants.SERVER_TARGET)) {
			
		}
	}
	
	public void sendData(Request req) {
		String target = req.getTarget();
		
		if (target.contains(Constants.CONVEYOR_TARGET)) {
			sendDataToConveyor(req);
		} else if (target.contains(Constants.KIT_ROBOT_TARGET)) {
			sendDataToKitRobot(req);
		} else if (target.contains(Constants.PARTS_ROBOT_TARGET)) {
			sendDataToPartsRobot(req);
		} else if (target.contains(Constants.NEST_TARGET)) {
			sendDataToNest(req);
		} else if (target.contains(Constants.CAMERA_TARGET)) {
			sendDataToCamera(req);
		} else if (target.contains(Constants.LANE_TARGET)) {
			sendDataToLane(req);
		}
	}
	
	private void sendDataToConveyor(Request req) {
		kitRobotMngrWriter.sendData(req);
	}
	
	private void sendDataToKitRobot(Request req) {
		kitRobotMngrWriter.sendData(req);
	}
	
	private void sendDataToPartsRobot(Request req) {
		partsRobotMngrWriter.sendData(req);
	}
	
	private void sendDataToNest(Request req) {
		partsRobotMngrWriter.sendData(req);
	}
	
	private void sendDataToCamera(Request req) {
		partsRobotMngrWriter.sendData(req);
	}
	
	private void sendDataToLane(Request req) {
		laneMngrWriter.sendData(req);
	}
	
	public static void main(String[] args) {
		Server server = new Server();
	}
}
