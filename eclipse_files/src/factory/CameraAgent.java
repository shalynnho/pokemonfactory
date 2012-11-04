package factory;

import java.util.*;

import factory.data.Kit;
import factory.data.PartType;
import factory.interfaces.Camera;
import factory.interfaces.Nest;
import DeviceGraphics.PartGraphics;
import GraphicsInterfaces.CameraGraphics;
import agent.Agent;

public class CameraAgent extends Agent implements Camera {
	
	private List<MyNest> nests = Collections.synchronizedList(new ArrayList<MyNest>());
 	private List<MyKit> kits = Collections.synchronizedList(new ArrayList<MyKit>());
	public CameraGraphics guiCamera;
	
	public KitRobotAgent kitRobot;
	public PartsRobotAgent partRobot;
	
	private enum NestStatus {NOT_READY,READY,PHOTOGRAPHED};
	private enum KitStatus {NOT_READY,DONE,MESSAGED, PICTURE_BEING_TAKEN};
	
	private class MyKit{
		Kit kit;
		KitStatus ks;
		boolean kitDone;
		MyKit(Kit k){
			kit = k;
			ks = KitStatus.NOT_READY;
			kitDone = false;
		}
	}
	private class MyNest{
		Nest nest;
		PartType type;
		List<PartGraphics> guiParts;
		NestStatus state;
		MyNest(Nest nest, PartType type){
			this.nest = nest;
			this.type = type;
			this.state = NestStatus.NOT_READY;
		}
	}
	
	/**********MESSAGES************/
	/**
	 * 
	 */
	public void msgInspectKit(Kit kit) {
		kits.add(new MyKit(kit));
		stateChanged();		
	}

	public void msgIAmFull(NestAgent n) {
		MyNest nest = new MyNest(n, n.currentPartType);
		nests.add(nest);
		stateChanged();		
	}
	
	public void msgTakePictureNestDone(List<PartGraphics> parts, Nest nest) {
		for(MyNest n: nests)
		{
			if(n.nest == nest)
			{
				n.guiParts = parts;
				n.state = NestStatus.PHOTOGRAPHED;
				break;
			}
		}
		stateChanged();
	}

	public void msgTakePictureKitDone(Kit k, boolean done) {
		for(MyKit kit: kits){
			if(k.equals(kit)){
				kit.kitDone = done;
				kit.ks = KitStatus.DONE;
			}
		}
		stateChanged();
	}
	
	/***********SCHEDULER**************/
	public boolean pickAndExecuteAnAction() {
		for(MyNest n: nests){
			if(n.state == NestStatus.NOT_READY)
			{
				takePictureOfNest(n);
			}
		}
		for(MyNest n: nests)
		{
			if(n.state == NestStatus.PHOTOGRAPHED)
			{
				tellPartsRobot(n);
			}
		}
		for(MyKit k: kits)
		{
			if(k != null && k.ks == KitStatus.NOT_READY){
				takePictureOfKit(k);
			}
		}
		for(MyKit k: kits)
		{
			if(k != null && k.ks == KitStatus.DONE){
				tellKitRobot(k);
			}
		}
		return false;
	}

	/***********ACTIONS*******************/
	private void tellKitRobot(MyKit k) {
		k.ks = KitStatus.MESSAGED;
		kitRobot.msgKitPassedInspection();
		stateChanged();
	}

	private void takePictureOfKit(MyKit kit) {
		kit.ks = KitStatus.PICTURE_BEING_TAKEN;
		guiCamera.takeKitPhoto(kit.kit.kit);
		stateChanged();		
	}

	private void tellPartsRobot(MyNest n) {
		List<PartGraphics> goodParts = new ArrayList<PartGraphics>();
		for(PartGraphics part: n.guiParts)
		{
			if(part.isGood())
			{
				goodParts.add(part);
			}
		}
		partRobot.msgHereAreGoodParts(n.nest,goodParts);
		nests.remove(n);
		stateChanged();
		
	}

	private void takePictureOfNest(MyNest n) {
		guiCamera.takeNestPhoto(n.nest.);
		n.state = NestStatus.READY;
		stateChanged();		
	}

	

}
