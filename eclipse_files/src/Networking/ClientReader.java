package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Variant of StreamReader, used by the Server. 
 *  
 * @author Peter Zhang
 */
public class ClientReader extends StreamReader {

	private Server server;
	
	public ClientReader(Socket soc, Server s) throws IOException {
		super(soc);
		server = s;
	}
	
	public ClientReader(ObjectInputStream ois, Server s) {
		super(ois);
		server = s;
	}

	@Override
	public void receiveData(Request req) {
		server.receiveData(req);
	}
	
}
