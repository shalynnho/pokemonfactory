package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Networking.Request;
import Networking.StreamWriter;

public class NetworkingButtonListener implements ActionListener {
	
	private Request req;
	private StreamWriter writer;
	
	public NetworkingButtonListener(Request req, StreamWriter writer) {
		this.req = req;
		this.writer = writer;
	}
	
	public NetworkingButtonListener(String command, String target, StreamWriter writer) {
		this(new Request(command, target, null), writer);
	}
	
	public NetworkingButtonListener(String command, String target, Object data, StreamWriter writer) {
		this(new Request(command, target, data), writer);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		writer.sendData(req);
	}

}
