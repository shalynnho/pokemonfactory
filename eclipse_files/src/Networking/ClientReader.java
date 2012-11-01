package Networking;

import java.net.Socket;

/**
 * Variant of StreamReader, used by the Server. 
 *  
 * @author Peter Zhang
 */
public class ClientReader extends StreamReader {

	private Server server;
	
	public ClientReader(Socket soc, Server s) {
		super(soc);
		server = s;
	}

	@Override
	public void receiveData(Request req) {
		server.receiveData(req);
	}
	
}
