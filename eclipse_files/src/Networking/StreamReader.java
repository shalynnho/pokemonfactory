package Networking;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Abstract class, so that client/server side can have their own implementation on how the data is handled.
 * 
 * @author Peter Zhang
 */
public abstract class StreamReader implements Runnable{
	private Socket socket;
	private ObjectInputStream ois;	
	
	public StreamReader(Socket s){
		socket = s;
		try {
			// Create the input stream for reading from the server
			ois = new ObjectInputStream(socket.getInputStream());
			System.out.println("StreamReader: got stream");
		} catch (Exception e) {
			System.out.println("StreamReader: Stream init fail");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Request req = (Request) ois.readObject();
				System.out.println("StreamReader: received request = " + req.getCommand());
				receiveData(req);
			} catch (Exception e) {
				System.out.println("StreamReader: Cannot read data from Server");		
				break;
			}
		}
	}
	
	/**
	 * To be implemented by either ServerReader and ClientReader, specifying target terminal to receive the request. 
	 */
	public abstract void receiveData(Request req);

}
