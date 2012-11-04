package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import factory.data.Kit;

public class KitRobotGraphics extends DeviceGraphics implements GraphicsInterfaces.KitRobotGraphics {

	private Kit kit;
	private Location location;
	private Server server;
	
	public KitRobotGraphics(Server s)
	{
		location = new Location(0,0);
		server=s;
		
	}
	@Override	
	public void msgPlaceKitOnStand(KitGraphics kit, int location) {
		server.sendData(new Request("moveKitToStand", Constants.KIT_ROBOT_TARGET, null));
		// TODO Auto-generated method stub
		
	}
	
	
	

	@Override
	public void msgPlaceKitInInspectionArea(KitGraphics kit) {
		server.sendData(new Request("moveKitToInspection", Constants.KIT_ROBOT_TARGET,  null));
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPlaceKitOnConveyor() {
		server.sendData(new Request("moveKitToConveyor", Constants.KIT_ROBOT_TARGET, null ));
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveData(Request req) {
		// TODO Auto-generated method stub
		
	}
	
	
}

