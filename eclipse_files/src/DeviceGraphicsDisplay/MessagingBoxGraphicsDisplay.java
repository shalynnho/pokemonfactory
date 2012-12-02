package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;

public class MessagingBoxGraphicsDisplay extends DeviceGraphicsDisplay {

	private Image image = Constants.MESSAGE_BOX_IMAGE.getScaledInstance(480, 80, Image.SCALE_DEFAULT);
	private Image arrowImage = Constants.MESSAGE_BOX_ARROW_IMAGE.getScaledInstance(12, 8, Image.SCALE_DEFAULT);

	private String msgToDisplay = "";
	private int charsDisplayed = 0;

	private int flashCounter = 0;

	public MessagingBoxGraphicsDisplay(Client c) {
		client = c;
		location = new Location(20, 580);
	}

	@Override
	public void draw(JComponent c, Graphics2D g) {
		g.setFont(Client.pokeFont);
		g.drawImage(image,
				location.getX() + client.getOffset(), location.getY(), c);

		if (charsDisplayed < Math.min(62, msgToDisplay.length())) {
			drawMessage(msgToDisplay.substring(0, charsDisplayed), c, g);
			charsDisplayed++;
		} else {
			drawMessage(msgToDisplay, c, g);
			if (flashCounter % 30 < 15) {
				g.drawImage(arrowImage, location.getX() + 265, location.getY() + 60, c);
			}
		}

		flashCounter++;
	}

	@Override
	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.MSGBOX_DISPLAY_MSG)) {
			msgToDisplay = (String) req.getData();
			charsDisplayed = 0;
		}
	}

	public void drawMessage(String s, JComponent c, Graphics2D g) {
		s = s.trim();
		if (s.length() > 31) {
			g.drawString(s.substring(0, 31), location.getX() + 23, location.getY() + 35);
			s = s.substring(31, Math.min(s.length(), 62));
			s = s.trim();
			g.drawString(s, location.getX() + 23, location.getY() + 57);
		} else {
			g.drawString(s, location.getX() + 23, location.getY() + 35);
		}
	}

}
