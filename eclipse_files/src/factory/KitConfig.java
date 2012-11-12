package factory;

import java.io.Serializable;
import java.util.HashMap;

public class KitConfig implements Serializable {
	
	private HashMap<PartType, Integer> config;
	private String name;
	
	public KitConfig(String name) {
		config = new HashMap<PartType, Integer>();
		this.name = name;
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
	
	/**
	 * 
	 * @return the name of this kit configuration
	 */
	public String getName() {
		return name;
	}

}
