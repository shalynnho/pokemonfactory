package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JComponent;

import Networking.Request;
import Utils.Location;
import factory.PartType;

public class PartGraphicsDisplay extends DeviceGraphicsDisplay {
    Location partLocation;
    PartType partType;
    boolean quality = true;
    TransitionGraphicsDisplay trans;

    private Image partImage;

    public PartGraphicsDisplay(PartType pt) {
	partType = pt;
	partImage = partType.getImage();
	trans = new TransitionGraphicsDisplay(partType);
    }

    public void setLocation(Location newLocation) {
	partLocation = new Location(newLocation);
    }

    public void draw(JComponent c, Graphics2D g) {
	drawWithOffset(c, g, 0);
    }

    public void drawWithOffset(JComponent c, Graphics2D g, int offset) {
	if (!quality) {
	    // draw bad image
	} else {
	    g.drawImage(partImage, partLocation.getX() + offset, partLocation.getY(), c);
	}
    }
    
    //Neetu added this
    
    public void drawTransition(int offset, Location loc, JComponent jc, Graphics2D g) {
    	trans.drawTrans(offset, loc, jc, g);
    }
    
    //Neetu added this too
    public void drawPokeball(int offset, Location loc, JComponent jc, Graphics2D g) {
    	trans.drawPokeball(offset, loc, jc, g);
    }

    public Location getLocation() {
	return partLocation;
    }

    public PartType getPartType() {
	return partType;
    }
    
    public void setQuality(boolean quality) {
	this.quality = quality;
    }

    public void receiveData(Request req) {
    }

}
