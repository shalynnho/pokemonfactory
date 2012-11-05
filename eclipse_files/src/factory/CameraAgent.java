package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import GraphicsInterfaces.CameraGraphics;
import agent.Agent;
import factory.data.Kit;
import factory.data.Part;
import factory.data.PartType;
import factory.interfaces.Camera;
import factory.interfaces.Nest;

/**
 * Camera is responsible for inspecting full nests and assembled kits.
 * @author Ross Newman
 */
public class CameraAgent extends Agent implements Camera {

	String name;

	public CameraAgent(String name) {
		super();
		this.name = name;
	}

	public List<MyNest> nests = Collections
			.synchronizedList(new ArrayList<MyNest>());
	private final List<MyKit> kits = Collections
			.synchronizedList(new ArrayList<MyKit>());
	public CameraGraphics guiCamera;

	public KitRobotAgent kitRobot;
	public PartsRobotAgent partRobot;
	private NestAgent nest;

	public enum NestStatus {
		NOT_READY, READY, PHOTOGRAPHED
	};

	private enum KitStatus {
		NOT_READY, DONE, MESSAGED, PICTURE_BEING_TAKEN
	};

	private class MyKit {
		Kit kit;
		KitStatus ks;
		boolean kitDone;

		MyKit(Kit k) {
			kit = k;
			ks = KitStatus.NOT_READY;
			kitDone = false;
		}
	}

	public class MyNest {
		Nest nest;
		PartType type;
		List<Part> Parts;
		public NestStatus state;

		MyNest(Nest nest, PartType type) {
			this.nest = nest;
			this.type = type;
			this.state = NestStatus.NOT_READY;
		}
	}

	/********** MESSAGES ************/
	/**
	 * 
	 */
	@Override
	public void msgInspectKit(Kit kit) {
		kits.add(new MyKit(kit));
		stateChanged();
	}

	@Override
	public void msgIAmFull(NestAgent n) {
		MyNest nest = new MyNest(n, n.currentPartType);
		nests.add(nest);
		stateChanged();
	}

	@Override
	public void msgTakePictureNestDone(List<Part> parts, Nest nest) {
		synchronized (nests) {
			for (MyNest n : nests) {
				if (n.nest == nest) {
					n.Parts = parts;
					n.state = NestStatus.PHOTOGRAPHED;
					break;
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgTakePictureKitDone(Kit k, boolean done) {
		for (MyKit kit : kits) {
			if (k.equals(kit)) {
				kit.kitDone = done;
				kit.ks = KitStatus.DONE;
			}
		}
		stateChanged();
	}

	/*********** SCHEDULER **************/
	@Override
	public boolean pickAndExecuteAnAction() {
		synchronized (nests) {
			for (MyNest n : nests) {
				if (n.state == NestStatus.NOT_READY) {
					takePictureOfNest(n);
					return true;
				} else if (n.state == NestStatus.PHOTOGRAPHED) {
					tellPartsRobot(n);
					return true;
				}
			}
		}

		synchronized (kits) {
			for (MyKit k : kits) {
				if (k != null && k.ks == KitStatus.NOT_READY) {
					takePictureOfKit(k);
					return true;
				}
			}
			for (MyKit k : kits) {
				if (k != null && k.ks == KitStatus.DONE) {
					tellKitRobot(k);
					return true;
				}
			}
		}
		return false;
	}

	/*********** ACTIONS *******************/
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
		List<Part> goodParts = new ArrayList<Part>();
		for (Part part : n.Parts) {
			if (part.isGood) {
				goodParts.add(part);
			}
		}
		print("good parts count: " + goodParts.size());
		partRobot.msgHereAreGoodParts(n.nest, goodParts);
		nests.remove(n);
		stateChanged();

	}

	private void takePictureOfNest(MyNest n) {
		// guiCamera.takeNestPhoto(n.nest.guiNest);
		n.state = NestStatus.READY;
		stateChanged();
	}

	public void setNest(NestAgent nest) {
		this.nest = nest;
	}

	public void setPartsRobot(PartsRobotAgent parts) {
		this.partRobot = parts;

	}

}
