package factory.interfaces;

import factory.data.Kit;

public interface Stand {

	/**
	 * KitRobot sends this when it has shipped a kit.
	 */
	public abstract void msgShippedKit();

	/**
	 * PartsRobot sends this when it has finished assembling a kit.
	 * @param k kit that has been assembled.
	 */
	public abstract void msgKitAssembled(Kit k);

	/**
	 * FCS sends this when a new batch of kits needs to be made
	 * @param numKits number of kits to be assembled for the current batch.
	 */
	public abstract void msgMakeKits(int numKits);

	/**
	 * KitRobot sends this when it has placed a kit on the stand.
	 * @param k kit placed on the stand.
	 */
	public abstract void msgHereIsKit(Kit k);

	public abstract boolean pickAndExecuteAnAction();

}
