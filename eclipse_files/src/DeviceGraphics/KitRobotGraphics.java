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
	
	KitGraphics[] positions;
	
	ArrayList<KitGraphics> kitsOnKitRobot;
	
	public KitRobotGraphics(Server s)
	{
		
		positions=new KitGraphics[4];
		
		for(int i=0; i<4; i++)
		{
			positions[i]=new KitGraphics();
		}
		
		location = new Location(0,0);
		server=s;	
	}
	
	public Integer Search(KitGraphics kit){
		for(Integer i=0; i<4;i++)
		{
			if(kit.equals(positions))
			{
				return i;
			}
			
		}
		
		return 0;
	}
	
	public void addKit(KitGraphics kg){
		kitsOnKitRobot.add(kg);
	} 
	
	public void giveKitToConveyor(KitGraphics kg){
		server.sendData(new Request("GiveKitToConveyor",Constants.CONVEYOR_TARGET, null));
		kitsOnKitRobot.remove(kg);
	}
	

	@Override
	public void msgPlaceKitInInspectionArea(KitGraphics kit) {
	Integer tempI = Search(kit);
		
		if(tempI.equals(new Integer(3)))
		{
			server.sendData(new Request("moveKitInLocation1ToInspection", Constants.KIT_ROBOT_TARGET,  null));
			positions[2]=kit;
			
		}
		else if(tempI==4)
		{
			server.sendData(new Request("moveKitInLocation2ToInspection", Constants.KIT_ROBOT_TARGET, null));
			positions[2]=kit;
		}
		else
		{
			System.out.println("Kit isn't in one of the locations");
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void msgPlaceKitOnConveyor() {
		positions[1]=positions[2];
		positions[2]=null;
		server.sendData(new Request("moveKitToConveyor", Constants.KIT_ROBOT_TARGET, null ));
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveData(Request req) {
		String target=req.getTarget();
		String command=req.getCommand();
		Object object=req.getData();
		
		//if()
		// TODO Auto-generated method stub
	}

	@Override
	public void msgPlaceKitOnStand1(KitGraphics kit) {
		// TODO Auto-generated method stub
		positions[3]=kit;
		server.sendData(new Request("moveKitToStand1", Constants.KIT_ROBOT_TARGET, null));	
	}

	@Override
	public void msgPlaceKitOnStand2(KitGraphics kit) {
		// TODO Auto-generated method stub
		positions[4]=kit;
		server.sendData(new Request("moveKitToStand2", Constants.KIT_ROBOT_TARGET, null));
	}
	
	
}

