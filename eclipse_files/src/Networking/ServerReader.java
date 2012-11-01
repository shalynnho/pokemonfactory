package Networking;

import java.net.Socket;

/**
 * Variant of StreamReader, used by clients. 
 *  
 * @author Peter Zhang
 */
public class ServerReader extends StreamReader {

	private Client client;
	
	public ServerReader(Socket s, Client c) {
		super(s);
		client = c;
	}

	@Override
	public void receiveData(Request req) {
		client.receiveData(req);
	}
	
}
