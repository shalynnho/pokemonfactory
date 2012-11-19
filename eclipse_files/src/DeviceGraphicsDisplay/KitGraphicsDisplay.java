package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.awt.Image;

import javax.swing.JComponent;

import Networking.Request;
import Utils.Constants;
import Utils.Location;
import factory.PartType;

public class KitGraphicsDisplay extends DeviceGraphicsDisplay {
	private static final int MAX_PARTS = 8;

	private Location kitLocation;

	// RotationPart
	public int degreeCountDown;
	private int degreeStep;

	private double rotationAxisX;
	private double rotationAxisY;

	private int position;

	private boolean rotating;

	private ArrayList<PartGraphicsDisplay> parts = new ArrayList<PartGraphicsDisplay>();

	private AffineTransform trans = new AffineTransform();
	
	Image kitImage;
	
	public Image getKitImage() {
		return kitImage;
	}

	public void setKitImage(Image kitImage) {
		this.kitImage = kitImage;
	}

	public KitGraphicsDisplay() {

		kitLocation = Constants.KIT_LOC;
		position = 0;
		degreeCountDown = 0;
		degreeStep = Constants.KIT_ROBOT_DEGREE_STEP;
		rotationAxisX = Constants.KIT_ROBOT_KIT_ROTATION_AXIS_LOC.getXDouble();
		rotationAxisY = Constants.KIT_ROBOT_KIT_ROTATION_AXIS_LOC.getYDouble();
		kitImage = Constants.KIT_IMAGE;
	}
	
	public void setTranslation(int offset){
		trans.translate(Constants.KIT_ROBOT_KIT_LOC.getXDouble() + offset,
				Constants.KIT_ROBOT_KIT_LOC.getYDouble());
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setLocation(Location newLocation) {
		kitLocation = newLocation;
	}

	public Location getLocation() {
		return kitLocation;
	}
	
	public void draw(JComponent c, Graphics2D g) {
		drawWithOffset(c, g, 0);
	}

	public void drawWithOffset(JComponent c, Graphics2D g, int offset) {
		g.drawImage(kitImage, kitLocation.getX() + offset,
				kitLocation.getY(), c);

		//TODO fix so that it draws the actual parts
		for (PartGraphicsDisplay part : parts) {
			g.drawImage(part.getPartType().getImage(), part.getLocation().getX() + offset, part
					.getLocation().getY(), c);
		}

	}

	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.KIT_UPDATE_PARTS_LIST_COMMAND)) {
			PartType type = (PartType) req.getData();
			receivePart(new PartGraphicsDisplay(type));
		}
	}

	// Drawing using AffineTransform part

	public void receivePart(PartGraphicsDisplay pgd) {
		parts.add(pgd);

		// set location of the part
		if ((parts.size() % 2) == 1) {
			pgd.setLocation(new Location(kitLocation.getX() + 5, kitLocation
					.getY() + (20 * (parts.size() - 1) / 2)));

		} else {
			pgd.setLocation(new Location(kitLocation.getX() + 34, kitLocation
					.getY() + (20 * (parts.size() - 2) / 2)));
		}

		if (parts.size() == MAX_PARTS) {
			parts.clear();
		}

	}

	public void setDegreeCountDown(int degreeCountDown) {
		this.degreeCountDown = degreeCountDown;

		if (this.degreeCountDown < 0) {
			this.degreeStep = -Constants.KIT_ROBOT_DEGREE_STEP;
		} else {
			this.degreeStep = Constants.KIT_ROBOT_DEGREE_STEP;
		}
	}

	public void drawRotate(JComponent c, Graphics2D g) {
		rotate();
		checkDegrees();
		g.drawImage(kitImage, trans, null);
	}

	public void rotate() {
		if (rotating) {
			trans.rotate(Math.toRadians(degreeStep), rotationAxisX,
					rotationAxisY);
			degreeCountDown -= degreeStep;

		}
	}

	public void checkDegrees() {
		if (rotating) {
			if (degreeCountDown == 0) {
				rotating = false;
			}
		}
	}

	public void startRotating() {
		rotating = true;
	}

}
