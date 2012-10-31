package Networking;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * A Runnable object that maintains connection with the Server, and relaying the message to the client.
 * 
 * @author Peter Zhang
 */
public class ServerReader implements Runnable{
	private Socket socket;
	private Client client;
	private ObjectInputStream ois;	
	
	public ServerReader(Socket s, Client c){
		socket = s;
		client = c;
		try {
			// Create the input stream for reading from the server
			ois = new ObjectInputStream(socket.getInputStream());
			System.out.println("ServerReader: got stream");
		} catch (Exception e) {
			System.out.println("ServerReader: Stream init fail");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Request req = (Request) ois.readObject();
				System.out.println("ServerReader: received request = " + req.getCommand());
				client.receiveData(req);
			} catch (Exception e) {
				System.out.println("ServerReader: Cannot read data from Server");		
				break;
			}
		}
	}

}
