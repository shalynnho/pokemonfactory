package Networking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedHashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import DeviceGraphicsDisplay.DeviceGraphicsDisplay;
import Utils.Constants;

/**
 * Abstract class that all managers extend from.
 * 
 * @author Peter Zhang
 */
public abstract class Client extends JPanel{
	
	private Socket socket;
	protected ServerReader reader;
	protected StreamWriter writer;
	
	/**
	 * To identify client with server. 
	 */
	protected String clientName;
	
	/**
	 * To store devices based on device target.
	 */
	protected LinkedHashMap<String, DeviceGraphicsDisplay> devices = new LinkedHashMap<String, DeviceGraphicsDisplay>();
	
	protected Client() {
		setLayout(new BorderLayout());
	}
	
	/**
	 * This is called by ServerReaders' receiveData(Object), taking in a Request variable casted from ObjectInput.
	 * Must be implemented by the Manager subclasses so to parse the Request variable accordingly.
	 */
	public abstract void receiveData(Request req);
	
	public void initStreams() {
		try {
		    socket = new Socket("localhost", Constants.SERVER_PORT);
		    System.out.println("Client: connected to the server");
		    
		    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		    
		    writer = new StreamWriter(oos);
		    reader = new ServerReader(ois, this);
		    new Thread(reader).start();
		    System.out.println("Client: streams ready");
		} catch (Exception e) {
		    System.out.println("Client: got an exception initing streams" + e.getMessage() );
		    e.printStackTrace();
		    System.exit(1);
		}
		
		// establish client identity with server
		writer.sendData(new Request(Constants.IDENTIFY_COMMAND, Constants.SERVER_TARGET, clientName));
	}
	
	public static void setUpJFrame(JFrame frame, int width, int height) {
		frame.setBackground(Color.BLACK);
		frame.setTitle("Factory Project - Lane Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	public void addDevice(String target, DeviceGraphicsDisplay device) {
		devices.put(target, device);
	}
}
