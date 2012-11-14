package factory;

import java.io.Serializable;
import java.util.HashMap;

import Utils.StringUtil;

public class KitConfig implements Serializable {

	private HashMap<PartType, Integer> config;
	private final String id;
	private String name;

	public KitConfig(String name) {
		config = new HashMap<PartType, Integer>();
		this.name = name;
		this.id = StringUtil.md5(name);
	}
	
	/**
	 * Pass in multiple PartTypes as the last params in constructor to setup a KitConfig without setConfig();
	 * @param partTypes - can pass in multiple
	 */
	public KitConfig(String name, PartType... partTypes) {
		this(name);
		for(PartType pt : partTypes) {
			if(config.get(pt) != null) { 
				config.put(pt, config.get(pt) + 1);
			} else {
				config.put(pt, 1);
			}
		}

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
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public HashMap<PartType, Integer> getConfig() {
		return config;
	}

	public void setConfig(HashMap<PartType, Integer> config) {
		this.config = config;
	}
	
	public String getID() {
		return id;
	}
	
	public boolean equals(KitConfig k) {
		return this.id.equals(k.getID());
	}

}
