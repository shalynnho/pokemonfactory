package DeviceGraphicsDisplay;

import java.awt.Graphics2D;

import javax.swing.JComponent;

import Networking.Client;
import Utils.Constants;

/**
 * Graphics display side of the inspection stand.
 * @author Shalynn Ho
 *
 */
public class InspectionStandGraphicsDisplay extends StandGraphicsDisplay {
		
	public InspectionStandGraphicsDisplay(Client kam) {
		super(kam, 3);
	}
	
	@Override
	public void draw(JComponent c, Graphics2D g) {
		if (!isEmpty) {
			g.drawImage(Constants.INSPECTION_STAND_IMAGE, location.getX(), location.getY(), c);
			kit.draw(c,g);
		}	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
