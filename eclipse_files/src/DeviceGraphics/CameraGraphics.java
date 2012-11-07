package DeviceGraphics;

import Networking.Request;
import Networking.Server;
import Utils.Location;
import agent.Agent;
import factory.CameraAgent;

public class CameraGraphics implements DeviceGraphics, GraphicsInterfaces.CameraGraphics {
	
	private Location location;
	
	private Server server;
	private CameraAgent agent;

	public CameraGraphics(Server myServer, Agent a) {
		server = myServer;
		agent = (CameraAgent)a;
		
		location = new Location(100, 100);
	}
	
	@Override
	public void takeNestPhoto(GraphicsInterfaces.NestGraphics nest1) {
		nest1.getLocation()
	}

	@Override
	public void takeKitPhoto(KitGraphics kit) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void receiveData(Request req) {
		
	}

}
