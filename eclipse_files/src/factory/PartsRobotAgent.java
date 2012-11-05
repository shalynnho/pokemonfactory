package factory;

import java.util.ArrayList;
import java.util.Collections;
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
			AS = null;
		}
	}

	private enum ArmStatus {
		Empty, Full
	};

	private List<PartType> KitConfig = Collections
			.synchronizedList(new ArrayList<PartType>());
	private final List<MyKit> MyKits = Collections
			.synchronizedList(new ArrayList<MyKit>());;
	public Map<Nest, List<Part>> GoodParts;
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
	public void msgHereAreGoodParts(Nest n, List<Part> goodParts2) {
		GoodParts.put(n, goodParts2);
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
		if (MyKits.size() > 0) {
			for (MyKit mk : MyKits) {
				if (mk.MKS == MyKitStatus.Done) {
					RequestInspection(mk);
					return true;
				} else {
					for (Arm a : Arms) {
						if (a.AS == ArmStatus.Full) {
							PlaceParts();
						}
					}
				}
			}
		}
		if (GoodParts.size() > 0) {
			for (Arm a : Arms) {
				if (a.AS == ArmStatus.Empty) {
					PickUpPart(a);
				}
			}

		}
		return false;
	}

	/********** ACTIONS **************/

	private void PickUpPart(Arm a) {
		Part pickUpPart = null;

		loop: for (MyKit mk : MyKits) {
			for (Nest nests : GoodParts.keySet()) {
				for (Part p : GoodParts.get(nests)) {
					if (mk.kit.needPart(p)) {
						nests.msgTakingPart(p);/*
												 * try { Animation.acquire(); }
												 * catch (InterruptedException
												 * e) { e.printStackTrace(); }
												 */

						nests.msgDoneTakingParts();
						stateChanged();
					}
				}
			}
		}

	}

	private void PlaceParts() {
		for (MyKit mk : MyKits) {
			for (Arm a : Arms) {
				Part p = a.part;
				if (mk.kit.needPart(a.part)) {
					mk.kit.parts.add(p);
					mk.kit.partsExpected.remove(p.type);
					a.part = null;
					try {
						Animation.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// guiPartsRobot.givePartToKit(mk.kit.kit);
				}
			}
			CheckMyKit(mk);
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

}
