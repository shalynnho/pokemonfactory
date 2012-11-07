package agent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import agent.data.Bin;
import agent.data.PartType;
import agent.data.Bin.BinStatus;
import agent.interfaces.Gantry;

import DeviceGraphics.DeviceGraphics;

/**
 * Gantry delivers parts to the feeder
 * @author Arjun Bhargava
 */
public class GantryAgent extends Agent implements Gantry {

	public List<Bin> binList = new ArrayList<Bin>();
	public List<PartType> requestedParts = new ArrayList<PartType>();

	// WAITING FOR GANTRYGRAPHICS
	// private GantryGraphics gantryGraphic;
	private FeederAgent feeder;

	private final String name;
	
	public Semaphore animation = new Semaphore(0, true);

	public GantryAgent(String name) {
		super();
		this.name = name;
		print("I'm working");
	}

	@Override
	public void msgHereIsBinConfig(Bin bin) {
		binList.add(bin);
		stateChanged();
	}

	@Override
	public void msgINeedParts(PartType type) {
		//print("PartType added to gantry");
		requestedParts.add(type);
		stateChanged();
	}

	@Override
	public void msgreceiveBinDone(Bin bin) {
		bin.binState = BinStatus.OVER_FEEDER;
		animation.release();
		stateChanged();
	}

	@Override
	public void msgdropBinDone(Bin bin) {
		bin.binState = BinStatus.EMPTY;
		animation.release();
		stateChanged();
	}

	@Override
	public void msgremoveBinDone(Bin bin) {
		binList.remove(bin);
		animation.release();
		stateChanged();
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		for (PartType requested : requestedParts) {
			//print("in requested");
			for (Bin bin : binList) {
				//print("in binlist");
				if (bin.part.type == requested
						&& bin.binState == BinStatus.FULL) {
					print("Moving to feeder");
					moveToFeeder(bin);
					return true;
				}
			}
		}
		//print("I'm not having problems either");
		for (PartType requested : requestedParts) {
			for (Bin bin : binList) {
				if (bin.part.type == requested
						&& bin.binState == BinStatus.OVER_FEEDER) {
					fillFeeder(bin);
					return true;
				}
			}
		}
		//print("I'm not having problems too");
		for (PartType requested : requestedParts) {
			for (Bin bin : binList) {
				if (bin.part.type == requested
						&& bin.binState == BinStatus.EMPTY) {
					discardBin(bin);
					return true;
				}
			}
		}
		print("I'm returning false");
		return false;
	}

	@Override
	public void moveToFeeder(Bin bin) {
		print("Moving bin to over feeder");
		bin.binState = BinStatus.MOVING;
		
		// GUIGantry.receiveBin(bin);
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stateChanged();
	}

	@Override
	public void fillFeeder(Bin bin) {
		print("Placing bin in feeder and filling feeder");
		feeder.msgHereAreParts(bin.part.type,bin);
		bin.binState = BinStatus.FILLING_FEEDER;
		
		// GUIGantry.dropBin(bin, bin.feeder);
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stateChanged();
	}

	@Override
	public void discardBin(Bin bin) {
		print("Discarding bin");
		bin.binState = BinStatus.DISCARDING;
		
		// GUIGangry.removeBin(bin);
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stateChanged();
	}

	@Override
	public String getName() {
		return name;
	}

	public void setFeeder(FeederAgent feeder) {
		this.feeder = feeder;
	}
	
	public void setGraphicalRepresentation(DeviceGraphics dg) {
		
	}

}
