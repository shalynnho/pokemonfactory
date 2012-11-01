package DeviceGraphics;

import Utils.Location;

public class BinGraphics extends DeviceGraphics  {
	
	PartGraphics part; // Type of part found in bin
	int numParts; // Number of parts in bin
	Location binLocation;
	
	// Constructor
	public BinGraphics (PartGraphics p, int n) {
		part = p;
		numParts = n;
	}
	
	
	/**
	 * Used in order to receive parts from a feeder's purge
	 * 
	 * @param part - Part type
	 * @param partNum - Number of parts
	 */
	public void receiveParts(PartGraphics part, int np) {
		this.part = part;
		numParts = np;
	}
	
	
	public PartGraphics getPart() {
		return part;
	}
	
	
	public int getQuantity() {
		return numParts;
	}
	
	/**
	 * Empties out the bin during purge
	 */
	public void setEmpty() {
		numParts = 0;
		part = null;
	}
	
	
	public void setLocation(Location newLocation) {
		binLocation = newLocation;
	}
	
	
	public Location getLocation() {
		return binLocation;
	}
}
