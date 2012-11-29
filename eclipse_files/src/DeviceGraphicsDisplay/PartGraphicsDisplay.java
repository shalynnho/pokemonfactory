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
    Image pokeballImage;

    public PartGraphicsDisplay(PartType pt) {
	partType = pt;
	partImage = partType.getImage();
	pokeballImage = partType.getPokeballImage();
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
	    drawTransition(c, g, offset);
	    if (trans.animate == false) {
		drawPokeball(c, g, offset);
	    }
	}
    }
    
    public void drawTransition(JComponent c, Graphics2D g, int offset) {
	trans.drawTrans(offset, partLocation, c, g);
    }
    
    public void drawPokeball(JComponent c, Graphics2D g, int offset) {
	trans.drawPokeball(offset, partLocation, c, g, pokeballImage);
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
