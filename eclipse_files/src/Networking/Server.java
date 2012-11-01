package Networking;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
			ss = new ServerSocket(6889);
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
			if (req.getTarget() == "server" && req.getCommand() == "identify") {
				String identity = (String) req.getData();
				if (identity.equals("kitRobot")) {
					kitRobotReader = new ClientReader(s, this);
					kitRobotWriter = new StreamWriter(s);
					new Thread(kitRobotReader).start();
				} else if (identity.equals("partsRobot")) {
					partsRobotReader = new ClientReader(s, this);
					partsRobotWriter = new StreamWriter(s);
					new Thread(partsRobotReader). start();
				} else if (identity.equals("lane")) {
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
		
		if (target.contains("conveyor")) {
			sendDataToConveyor(req);
		} else if (target.contains("kitRobot")) {
			sendDataToKitRobot(req);
		} else if (target.contains("partsRobot")) {
			sendDataToPartsRobot(req);
		} else if (target.contains("nest")) {
			sendDataToNest(req);
		} else if (target.contains("camera")) {
			sendDataToCamera(req);
		} else if (target.contains("lane")) {
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
