package DeviceGraphics;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.Agent;
import agent.CameraAgent;

/**
 * Server-side Camera object
 * 
 * @author Peter Zhang
 */
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
		// TODO: replace null with 
		// location = nest1.getLocation();
		server.sendData(new Request(Constants.CAMERA_TAKE_NEST_PHOTO_COMMAND, Constants.CAMERA_TARGET, null));
	}

	@Override
	public void takeKitPhoto(KitGraphics kit) {
		// TODO: replace null with 
		// location = kit.getLocation();
		server.sendData(new Request(Constants.CAMERA_TAKE_KIT_PHOTO_COMMAND, Constants.CAMERA_TARGET, null));
	}
	
	@Override
	public void receiveData(Request req) {
		if(req.getCommand().equals(Constants.CAMERA_TAKE_NEST_PHOTO_COMMAND)) {
			takeNestPhoto(null);
		} else if(req.getCommand().equals(Constants.CAMERA_TAKE_NEST_PHOTO_COMMAND)) {
			takeKitPhoto(null);
		}
	}

}
