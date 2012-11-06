package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import DeviceGraphics.PartsRobotGraphics;
import agent.Agent;
import factory.data.Kit;
import factory.data.Part;
import factory.data.PartType;
import factory.interfaces.Nest;
import factory.interfaces.PartsRobot;
import factory.interfaces.Stand;

// import factory.StandAgent.MyKit;

/**
 * Parts robot picks parts from nests and places them in kits
 * @author Ross Newman
 */
public class PartsRobotAgent extends Agent implements PartsRobot {

	public PartsRobotAgent(String name) {
		super();

		this.name = name;
		for (int i = 0; i < 4; i++) {
			this.Arms.add(new Arm());
		}
	}

	String name;

	private class MyKit {
		Kit kit;
		MyKitStatus MKS;
		List<PartType> config = new ArrayList<PartType>();

		MyKit(Kit k, List<PartType> newConfig) {
			kit = k;
			config = newConfig;
			MKS = MyKitStatus.NotDone;
		}
	}

	private enum MyKitStatus {
		NotDone, Done
	};

	public class Arm {
		Part part;
		ArmStatus AS;

		public Arm() {
			part = null;
			AS = ArmStatus.Empty;
		}
	}

	private enum ArmStatus {
		Empty, Full
	};

	private List<PartType> KitConfig = Collections.synchronizedList(new ArrayList<PartType>());
	private final List<MyKit> MyKits = Collections.synchronizedList(new ArrayList<MyKit>());;
	public Map<Nest, List<Part>> GoodParts = new HashMap<Nest, List<Part>>();
	public List<Arm> Arms = Collections.synchronizedList(new ArrayList<Arm>());

	List<Kit> KitsOnStand;
	List<Nest> nests;

	Stand stand;
	PartsRobotGraphics guiPartsRobot;

	public Semaphore Animation = new Semaphore(1, true);
	public Semaphore AccessKit = new Semaphore(1, true);

	/***** MESSAGES ***************************************/
	/**
	 * Changes the configuration for the kits From FCS
	 */
	@Override
	public void msgHereIsKitConfiguration(List<PartType> config) {
		KitConfig = config;
		stateChanged();
	}

	/**
	 * From Camera
	 */
	@Override
	public void msgHereAreGoodParts(Nest n, List<Part> goodParts) {
		GoodParts.put(n, goodParts);
		stateChanged();
	}

	/**
	 * From Kit Robot
	 */
	@Override
	public void msgUseThisKit(Kit k) {
		MyKit mk = new MyKit(k, KitConfig);
		MyKits.add(mk);
		stateChanged();
	}

	/**
	 * Releases animation semaphore after a part is picked up, so that a new
	 * animation may be run by GUI From GUI
	 */
	@Override
	public void msgPickUpPartDone() {
		Animation.release();
		stateChanged();
	}

	/**
	 * Releases animation semaphore after a part is given to kit, so that a new
	 * animation may be run by GUI From GUI
	 */
	@Override
	public void msgGivePartToKitDone() {
		Animation.release();
		stateChanged();
	}

	/**************** SCHEDULER ***********************/

	@Override
	public boolean pickAndExecuteAnAction() {
		
		//Checks if a kit is done and inspects it if it is
		if (MyKits.size() > 0) {
			print("Have kits");
			for (MyKit mk : MyKits) {
				if (mk.MKS == MyKitStatus.Done) {
					RequestInspection(mk);
					return true;
				} 
			}
		}
		
		//Checks if there is an empty arm, if there is it fills it with a good part that the kit needs
		if(IsAnyArmEmpty()){ 
			for (Nest nest : GoodParts.keySet()) { 
				for (Part part : GoodParts.get(nests)) { //Going through all the good parts
					for (MyKit mk : MyKits) { 
						if(mk.kit.needPart(part)){ //Checking if the good part is needed by either kit
							for (Arm arm : Arms) {
								if (arm.AS == ArmStatus.Empty) { //Find the empty arm 
									PickUpPart(arm,part,nest);
									return true;
								}
							}
						}
					}
				}
			}
		}
		
		//Checks if any arm is holding a part and places it if there is one
		for (Arm arm : Arms) {
			if (arm.AS == ArmStatus.Full) {
				PlacePart(arm);
				return true;
			}
		}
		print("returning false");
		return false;
	}

	/********** ACTIONS **************/

	private void PickUpPart(Arm arm, Part part, Nest nest) {

		print("Picking up part");
		GoodParts.remove(nest);
		
		//Tells the graphics to pickup the part
		//guiPartsRobot.pickUpPart(part.part);
		try {
			Animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Only takes 1 part from a nest at a time
		nest.msgTakingPart(part);											
		nest.msgDoneTakingParts();
		
		stateChanged();
	}

	private void PlacePart(Arm arm) {
		for (MyKit mk : MyKits) {
			if (mk.kit.needPart(arm.part)) {
				
				// guiPartsRobot.givePartToKit(mk.kit.kit);
				try {
					Animation.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Tells the kit it has the part now
				mk.kit.parts.add(arm.part);
				mk.kit.partsExpected.remove(arm.part);
				arm.part = null;
				
				//Checks if the kit is done
				CheckMyKit(mk);
				
				break;
			}
		}
		stateChanged();
	}

	private void CheckMyKit(MyKit mk) {
		if (mk.kit.partsExpected.size() == 0) {
			mk.MKS = MyKitStatus.Done;
		}
		// stateChanged();
	}

	private void RequestInspection(MyKit mk) {
		stand.msgKitAssembled(mk.kit);
		MyKits.remove(mk);
		stateChanged();

	}
	
	//Helper methods
	
	//Checks if any of the arms are empty 
	private boolean IsAnyArmEmpty(){
		for (Arm a : Arms) {
			if (a.AS == ArmStatus.Empty) {
				return true;
			}
		}
		return false;
	}

}
