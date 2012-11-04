package factory;

import java.util.*;
import java.util.concurrent.Semaphore;

import factory.data.*;
import factory.interfaces.Nest;
import factory.interfaces.PartsRobot;
import factory.interfaces.Stand;
import DeviceGraphics.PartGraphics;
import DeviceGraphics.PartsRobotGraphics;
import agent.Agent;

public class PartsRobotAgent extends Agent implements PartsRobot {

	private class MyKit{
		Kit kit;
		MyKitStatus MKS;
		List<PartType> config = new ArrayList<PartType>();
		MyKit(Kit k, List<PartType> newConfig){
			kit = k;
			config = newConfig;
			MKS = MyKitStatus.NotDone;
		}
	}
	private enum MyKitStatus{NotDone,Done};
	private class Arm {
		Part part;
		ArmStatus AS;
	}
	private enum ArmStatus{Empty,Full};
	
	private List<PartType> KitConfig = Collections.synchronizedList(new ArrayList<PartType>());
	private List<MyKit> MyKits = Collections.synchronizedList(new ArrayList<MyKit>());;
	private Map<Nest,List<PartGraphics>> GoodParts;
	private List<Arm> Arms = Collections.synchronizedList(new ArrayList<Arm>());
	
	List<Kit> KitsOnStand;
	Stand stand;
	PartsRobotGraphics guiPartsRobot;
	
	public Semaphore Animation = new Semaphore(1,true);
	public Semaphore AccessKit = new Semaphore(1,true);
	
	/*****MESSAGES***************************************/
	/**
	 * Changes the configuration for the kits
	 * From FCS
	 */
	public void msgHereIsKitConfiguration(List<PartType> config) {
		KitConfig = config;
		stateChanged();
	}
	/**
	 * 
	 * From Camera
	 */
	public void msgHereAreGoodParts(Nest n, List<PartGraphics> parts) {
		GoodParts.put(n, parts);
		stateChanged();
	}


	/**
	 * 
	 * From Kit Robot
	 */
	public void msgUseThisKit(Kit k) {
		MyKit mk = new MyKit(k, KitConfig);
		MyKits.add(mk);
		stateChanged();
	}
	/**
	 * Releases animation semaphore after a part is picked up, so that a new animation may be run by GUI
	 * From GUI
	 */
	public void msgPickUpPartDone() {
		Animation.release();
		stateChanged();
	}

	/**
	 * Releases animation semaphore after a part is given to kit, so that a new animation may be run by GUI
	 * From GUI
	 */
	public void msgGivePartToKitDone() {
		Animation.release();
		stateChanged();
	}
	
	/****************SCHEDULER***********************/
	 
	public boolean pickAndExecuteAnAction() {
		if(MyKits.size() > 0){
			for(MyKit mk : MyKits)
			{
				if(mk.MKS == MyKitStatus.Done)
				{
					RequestInspection(mk);
					return true;
				}
				else 
				{
					for(Arm a : Arms)
					{
						if(a.AS == ArmStatus.Full)
						{
							PlaceParts();
						}
					}
				}
			}
		}
		if(GoodParts.size() > 0)
		{
			for(Arm a : Arms)
			{
				if(a.AS == ArmStatus.Empty)
				{
					PickUpPart(a);
				}
			}
			
		}
		return false;
	}
	
	/**********ACTIONS**************/
	
	//Wot?
	private void PickUpPart(Arm a) {
		Part pickUpPart;
		int nestIndex;
		if(MyKits.size() < 1)
		{
			if(Arms.isEmpty())
			{
				nestIndex = GoodParts.first();
				List<Part> available = GoodParts.get(nestIndex);
				pickUpPart = available.get(0);
			} else {
				for(Part p : GoodParts)
			}
		}
		stateChanged();
		
	}
	
	private void PlaceParts() {
		for(MyKit mk : MyKits)
		{
			for(Arm a : Arms)
			{
				Part p = a.part;
				if(mk.kit.isNeeded(a.part)){
					mk.kit.parts.put(p);
					mk.kit.partsExpected.remove(p);
					a.part = null;
					Animation.acquire();
					guiPartsRobot.givePartToKit(Part,mk.kit);
				}
			}
			CheckMyKit(mk);
		}
		stateChanged();
	}
	private void CheckMyKit(MyKit mk) {
		if(mk.kit.partsExpected.size() == 0){
			mk.MKS = MyKitStatus.Done;
		}
		stateChanged();
	}
	private void RequestInspection(MyKit mk) {
		Stand.msgKitIsDone(mk.kit);
		MyKits.remove(mk);
		stateChanged();
		
	}


	
}
