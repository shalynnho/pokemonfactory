package Utils;

public class Location {
	private double x;
	private double y;
	
	public Location(double newX, double newY) {
		x = newX;
		y = newY;
	}
	
	/**
	 * Find the distance from one Location object to another by passing in another Location object -Aaron
	 */
	public double distance(Location l) {
		return Math.sqrt(Math.pow(l.getX()-getX(),2)+Math.pow(l.getY()-getY(),2));
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	/**
	 * Call this with no params to increment x by 1. Returns x after increment.
	 */
	public double incrementX() {
		incrementX(1);
		return x;
	}
	
	/**
	 * Specify how much to increase to x. Can be negative. Returns x after increment. 
	 */
	public double incrementX(double toAdd) {
		x += toAdd;
		return x;
	}
	
	/**
	 * Call this with no params to increment y by 1. Returns y after increment.
	 */
	public double incrementY() {
		incrementY(1);
		return y;
	}
	
	/**
	 * Specify how much to increase to y. Can be negative. Returns y after increment. 
	 */
	public double incrementY(double toAdd) {
		y += toAdd;
		return y;
	}
	
	public void setX(double newX) {
		x = newX;
	}
	
	public void setY(double newY) {
		y = newY;
	}
	
	public boolean equals(Location otherLoc) {
		return x == otherLoc.getX() && y == otherLoc.getY(); 
	}
}
