package Networking;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerWriter {
	private Socket socket;
	private ObjectOutputStream oos;	
	
	public ServerWriter(Socket s){
		socket = s;
		try {
			// Create the input stream for reading from the server
			oos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("ServerWriter: got stream");
		} catch (Exception e) {
			System.out.println("ServerWriter: Stream init fail");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void sendData(Request req) {
		try {
			System.out.println("ServerWriter: requesting for \" " + req + " \"");
			oos.writeObject(req);
			oos.flush();
			oos.reset();
		} catch (Exception e) {
			System.out.println("ServerWriter: request fail");
			e.printStackTrace();
		}
	}
}
