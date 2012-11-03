package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Variant of StreamReader, used by clients. 
 *  
 * @author Peter Zhang
 */
public class ServerReader extends StreamReader {

	private Client client;
	
	public ServerReader(Socket s, Client c) throws IOException {
		super(s);
		client = c;
	}
	
	public ServerReader(ObjectInputStream ois, Client c) {
		super(ois);
		client = c;
	}

	@Override
	public void receiveData(Request req) {
		client.receiveData(req);
	}
	
}
