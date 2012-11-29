package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;
import factory.PartType;

public class KitGraphicsDisplay extends DeviceGraphicsDisplay  {

	private static final int MAX_PARTS = 8;

	private Location kitLocation;
	

	private int position;



	private ArrayList<PartGraphicsDisplay> parts = new ArrayList<PartGraphicsDisplay>();
	
	ImageIcon kitImage;
	int velocity ;
	private Client kitClient;
	
	public ImageIcon getKitImage() {
		return kitImage;
	}

	public void setKitImage(Image kitImage) {
		this.kitImage.setImage( kitImage);
	}

	public KitGraphicsDisplay(Client kitClient){
		kitLocation = Constants.KIT_LOC;
		position = 0;
		kitImage = new ImageIcon( Constants.KIT_IMAGE );
		this.kitClient = kitClient;
		velocity = 0;
	}
	
	public KitGraphicsDisplay() {
		kitLocation = Constants.KIT_LOC;
		position = 0;
		velocity =0;
		kitImage = new ImageIcon( Constants.KIT_IMAGE );
	
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
		
	}
	
	public void drawKit(JComponent c, Graphics2D g) {
		drawWithOffset(c, g, 0);
		setLocation(new Location(kitLocation.getX()+velocity, kitLocation.getY()));
		if(kitLocation.getX() == -40)
		{
			kitClient.sendData(new Request(
						Constants.CONVEYOR_RECEIVE_KIT_COMMAND
								+ Constants.DONE_SUFFIX,
						Constants.CONVEYOR_TARGET, null));
		}
	}

	public void drawWithOffset(JComponent c, Graphics2D g, int offset) {
		g.drawImage(kitImage.getImage(), kitLocation.getX() + offset,
				kitLocation.getY(), c);

		//TODO fix so that it draws the actual parts
		for (PartGraphicsDisplay part : parts) {
			g.drawImage(part.getPartType().getImage(), kitLocation.getX() + offset, kitLocation.getY(), c);
		}

	}

	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.KIT_UPDATE_PARTS_LIST_COMMAND)) {
			PartType type = (PartType) req.getData();
			receivePart(new PartGraphicsDisplay(type));
		} else if(req.getCommand().equals(Constants.CONVEYOR_RECEIVE_KIT_COMMAND)){
			moveAway();
		}
	}


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
	
	public void moveAway(){
		velocity = -5;
	}

}
