package agent.data;

import java.util.ArrayList;

import DeviceGraphics.KitGraphics;

public class Kit {

	public KitGraphics kitGraphics;

	public String kitID;

	public ArrayList<PartType> partsExpected = new ArrayList<PartType>();

	public ArrayList<Part> parts = new ArrayList<Part>();

	public Kit() {

	}

	public Kit(ArrayList<PartType> expected) {
		partsExpected = expected;
	}

	public Kit(String kitID) {
		this.kitID = kitID;
	}

	public boolean needPart(Part part) {
		int count = 0;
		for (PartType type : partsExpected) {
			if (type == part.type) {
				count++;
			}
		}
		for (Part tempPart : parts) {
			if (tempPart.type == part.type) {
				count--;
			}
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

}
