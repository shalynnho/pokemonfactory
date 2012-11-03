package Utils;

public class Location {
	private int x;
	private int y;
	
	public Location(int newX, int newY) {
		x = newX;
		y = newY;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	/**
	 * Call this with no params to increment x by 1. Returns x after increment.
	 */
	public int incrementX() {
		incrementX(1);
		return x;
	}
	
	/**
	 * Specify how much to increase to x. Can be negative. Returns x after increment. 
	 */
	public int incrementX(int toAdd) {
		incrementX(toAdd);
		return x;
	}
	
	/**
	 * Call this with no params to increment y by 1. Returns y after increment.
	 */
	public int incrementY() {
		incrementY(1);
		return y;
	}
	
	/**
	 * Specify how much to increase to y. Can be negative. Returns y after increment. 
	 */
	public int incrementY(int toAdd) {
		incrementY(toAdd);
		return y;
	}
	
	public void setX(int newX) {
		x = newX;
	}
	
	public void setY(int newY) {
		y = newY;
	}
}
