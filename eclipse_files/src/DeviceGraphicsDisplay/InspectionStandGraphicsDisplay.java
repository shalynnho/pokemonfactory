package DeviceGraphicsDisplay;

import java.awt.Color;
import java.awt.Font;
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
		super(kam, 0);
	}
	
	@Override
	public void draw(JComponent c, Graphics2D g) {
		g.drawImage(Constants.STAND_IMAGE, location.getX() + client.getOffset(), location.getY(), c);
		if (!isEmpty) {
			kit.drawKit(c,g);
		}	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
