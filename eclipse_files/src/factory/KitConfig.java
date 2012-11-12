package factory;

import java.util.*;

public class KitConfig {
	
	private HashMap<PartType, Integer> config;
	
	public KitConfig() {
		config = new HashMap<PartType, Integer>();
	}
	
	/**
	 * Adds a new part type (and number) to this kit configuration.
	 * @param pt - the part type
	 * @param n - number of that part type required for the config
	 */
	public void addItem(PartType pt, int n) {
			config.put(pt, n);
	}
	
	/**
	 * Removes the part type from the kit configuration
	 * @param pt - part type
	 */
	public void removeItem(PartType pt) {
		if (config.containsKey(pt)) {
			config.remove(pt);
		}
	}
	

}
