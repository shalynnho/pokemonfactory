package agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import DeviceGraphics.DeviceGraphics;
import DeviceGraphics.KitGraphics;
import GraphicsInterfaces.CameraGraphics;
import GraphicsInterfaces.NestGraphics;
import agent.NestAgent.MyPart;
import agent.data.Kit;
import agent.data.Part;
import agent.interfaces.Camera;
import agent.interfaces.Nest;

/**
 * Camera is responsible for inspecting full nests and assembled kits.
 * 
 * @author Ross Newman, Michael Gendotti
 */
public class CameraAgent extends Agent implements Camera {

    private final String name;

    public List<MyNest> nests = Collections.synchronizedList(new ArrayList<MyNest>());
    public MyKit mk;

    public CameraGraphics guiCamera;
    private final Timer timer;

    public KitRobotAgent kitRobot;
    public PartsRobotAgent partRobot;

    Semaphore animation = new Semaphore(0, true);

    public enum NestStatus {
	NOT_READY, READY, PHOTOGRAPHING, PHOTOGRAPHED
    };

    public enum KitStatus {
	NOT_READY, DONE, PICTURE_BEING_TAKEN, FAILED
    };

    public class MyKit {
	public Kit kit;
	public KitStatus ks;
	public boolean kitDone;

	public MyKit(Kit k) {
	    kit = k;
	    ks = KitStatus.NOT_READY;
	    kitDone = false;
	}
    }

    public class MyNest {
	public NestAgent nest;
	public NestStatus state;
	public int numFilledSnapshot = 0;

	public MyNest(NestAgent nest) {
	    this.nest = nest;
	    this.state = NestStatus.NOT_READY;
	}
    }

    public CameraAgent(String name) {
	super();
	this.name = name;

	timer = new Timer();
	timer.scheduleAtFixedRate(new TimerTask() {
	    // Fires every 3.001 seconds.
	    @Override
	    public void run() {
		print("Waking up");
		stateChanged();
	    }
	}, System.currentTimeMillis(), 3000);
    }

    /*
     * Messages
     */
    @Override
    public void msgInspectKit(Kit kit) {
	print("Received msgInspectKit");
	mk = new MyKit(kit);
	stateChanged();

    }

    // Should no longer be called in v2. Change to timer.
    public void msgIAmFull(Nest nest) {
	synchronized (nests) {

	    print("Received msgIAmFull from nest with type " + ((NestAgent) nest).currentPartType);
	    for (MyNest n : nests) {
		if (n.nest == nest) {
		    n.state = NestStatus.READY;
		    break;
		}
	    }
	}
	stateChanged();

    }

    @Override
    public void msgResetSelf() {
	print("Reseting Self");
	synchronized (nests) {
	    for (MyNest n : nests) {
		n.state = NestStatus.NOT_READY;
	    }
	}
	mk = null;
	// stateChanged();
    }

    @Override
    public void msgTakePictureNestDone(NestGraphics nest, boolean done, NestGraphics nest2, boolean done2) {
	print("Received msgTakePictureNestDone from graphics");
	boolean found1 = false;
	boolean found2 = false;
	synchronized (nests) {
	    for (MyNest n : nests) {
		if (n.nest.nestGraphics == nest) {
		    // In v0 all parts are good parts
		    print("nest " + nests.indexOf(n) + " photographed");
		    n.numFilledSnapshot = n.nest.currentParts.size();
		    n.state = NestStatus.PHOTOGRAPHED;
		    if (found2) {
			break;
		    }
		    found1 = true;
		}
		if (n.nest.nestGraphics == nest2) {
		    // In v0 all parts are good parts
		    print("nest " + nests.indexOf(n) + " photographed");
		    n.numFilledSnapshot = n.nest.currentParts.size();
		    n.state = NestStatus.PHOTOGRAPHED;
		    if (found1) {
			break;
		    }
		    found2 = true;
		}
	    }
	}
	animation.release();
	stateChanged();
    }

    @Override
    public void msgTakePictureKitDone(KitGraphics k, boolean done) {
	print("Received msgTakePictureKitDone from graphics");
	mk.kitDone = done;
	// if (k.badKitConfig != null) {
	// print("Kit Inspection passed.");
	    mk.ks = KitStatus.DONE;

	// } else {
	// print("Kit Inspection failed.");
	// mk.kit.updateParts(k.badKitConfig);
	// mk.ks = KitStatus.FAILED;
	// }
	animation.release();
	stateChanged();
    }

    /*
     * Scheduler (non-Javadoc)
     * 
     * @see agent.Agent#pickAndExecuteAnAction()
     */
    @Override
    public boolean pickAndExecuteAnAction() {
	synchronized (nests) {
	    for (int i = 0; i < nests.size(); i += 2) {
		if (nests.size() > i + 1) { // Quick check to make sure there
					    // is a nest paired with this
					    // one
		    if (nests.get(i).state == NestStatus.READY && nests.get(i + 1).state == NestStatus.READY) {
			print("Taking photos of nests");
			takePictureOfNest(nests.get(i), nests.get(i + 1));
			return true;
		    }
		}
	    }
	}

	synchronized (nests) {
	    for (MyNest n : nests) {
		if (n.state == NestStatus.PHOTOGRAPHED) {
		    tellPartsRobot(n);
		    return true;
		}
	    }
	}

	if (mk != null) {
	    if (mk.ks == KitStatus.NOT_READY) {
		takePictureOfKit(mk);
		return true;
	    } else if (mk.ks == KitStatus.DONE) {
		tellKitRobot(mk);
		return true;
	    } else if (mk.ks == KitStatus.FAILED) {
		tellKitRobotFailure(mk);
		return true;
	    }

	}

	return false;
    }

    /*
     * Actions
     */
    private void tellKitRobot(MyKit kit) {
	mk = null;
	kitRobot.msgKitPassedInspection();
	stateChanged();
    }

    private void tellKitRobotFailure(MyKit kit) {
	mk = null;
	kitRobot.msgKitFailedInspection();
	stateChanged();
    }

    private void takePictureOfKit(MyKit kit) {
	mk.ks = KitStatus.PICTURE_BEING_TAKEN;
	if (guiCamera != null) {
	    guiCamera.takeKitPhoto(kit.kit.kitGraphics);
	    print("Blocking");
	    try {
		animation.acquire();
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    print("Got permit");
	}
	stateChanged();
    }

    private void tellPartsRobot(MyNest n) {
	List<Part> goodParts = new ArrayList<Part>();

	// Parts missing - Non Norm
	if (n.numFilledSnapshot < n.nest.full) {
	    /**
	     * 1) Nothing, just wait a little longer.This may be the first time parts are coming down the lane and your
	     * estimation may be wrong. (1) Just wait a little longer and see if parts have arrived.
	     ** 
	     * 2) Lane is jammed. (2) hi-speed (turn up the amplitude of the lanes).
	     ** 
	     * 6) Your feeder algorithm for keeping parts in two lanes did not changeover fast enough. (6) adjust the
	     * parameter of your feeder algorithm for changing parts over from one lane to another.
	     */
	    print("Missing Parts in Nest : non-normative");
	}
 else {
	    for (MyPart part : n.nest.currentParts) {
		if (part.part.isGood) {
		    goodParts.add(part.part);
		}
	    }
	    print("Good parts count: " + goodParts.size());

	    // No Good Parts found - non norm
	    if (goodParts.size() == 0) {
		print("No Good Parts Found : non-normative");

	    } else {
		partRobot.msgHereAreGoodParts(n.nest, goodParts);
	    }
	}

	n.state = NestStatus.NOT_READY;
	stateChanged();

    }

    private void takePictureOfNest(MyNest n, MyNest n2) {
	synchronized (nests) {
	    n.state = NestStatus.PHOTOGRAPHING;
	    n2.state = NestStatus.PHOTOGRAPHING;
	    if (guiCamera != null) {
		guiCamera.takeNestPhoto(n.nest.nestGraphics, n2.nest.nestGraphics);
		// print("Blocking");
		try {
		    animation.acquire();
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		// print("Got permit");

	    }
	    stateChanged();
	}
    }

    public void setNest(NestAgent nest) {
	nests.add(new MyNest(nest));
    }

    public void setPartsRobot(PartsRobotAgent parts) {
	this.partRobot = parts;

    }

    public void setNests(ArrayList<NestAgent> nests) {
	for (NestAgent nest : nests) {
	    this.nests.add(new MyNest(nest));
	}
    }

    @Override
    public String getName() {
	return name;
    }

    public List<MyNest> getNests() {
	return nests;
    }

    public void setNests(List<MyNest> nests) {
	this.nests = nests;
    }

    public CameraGraphics getGuiCamera() {
	return guiCamera;
    }

    @Override
    public void setGraphicalRepresentation(DeviceGraphics guiCamera) {
	this.guiCamera = (CameraGraphics) guiCamera;
    }

    public KitRobotAgent getKitRobot() {
	return kitRobot;
    }

    public void setKitRobot(KitRobotAgent kitRobot) {
	this.kitRobot = kitRobot;
    }

    public PartsRobotAgent getPartRobot() {
	return partRobot;
    }

    public MyKit getKit() {
	return mk;
    }

}
