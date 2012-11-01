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
	
	ServerSocket ss;
	Socket s;
	
	// V0 Config
	private ClientReader kitRobotReader;
	private StreamWriter kitRobotWriter;
	
	private ClientReader partsRobotReader;
	private StreamWriter partsRobotWriter;
	
	private ClientReader laneReader;
	private StreamWriter laneWriter;
	
	
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
				if (identity.equals(Constants.KIT_ROBOT_CLIENT)) {
					kitRobotReader = new ClientReader(s, this);
					kitRobotWriter = new StreamWriter(s);
					new Thread(kitRobotReader).start();
				} else if (identity.equals(Constants.PARTS_ROBOT_CLIENT)) {
					partsRobotReader = new ClientReader(s, this);
					partsRobotWriter = new StreamWriter(s);
					new Thread(partsRobotReader). start();
				} else if (identity.equals(Constants.LANE_CLIENT)) {
					laneReader = new ClientReader(s, this);
					laneWriter = new StreamWriter(s);
					new Thread(laneReader). start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void receiveData(Request req) {
		String target = req.getTarget();
		
		if(target.equals("server")) {
			
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
		kitRobotWriter.sendData(req);
	}
	
	private void sendDataToKitRobot(Request req) {
		kitRobotWriter.sendData(req);
	}
	
	private void sendDataToPartsRobot(Request req) {
		partsRobotWriter.sendData(req);
	}
	
	private void sendDataToNest(Request req) {
		partsRobotWriter.sendData(req);
	}
	
	private void sendDataToCamera(Request req) {
		partsRobotWriter.sendData(req);
	}
	
	private void sendDataToLane(Request req) {
		laneWriter.sendData(req);
	}
	
	public static void main(String[] args) {
		Server server = new Server();
	}
}
