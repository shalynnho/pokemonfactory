package factory;

import java.awt.Image;
import java.awt.Toolkit;

import Utils.Constants;

public class PartType {
	private String name = "";
	private Image image;
	
	public PartType(String s) {
		name = s;
		setImage();
	}
	
	public void setImage() {
		image = Toolkit.getDefaultToolkit().getImage(Constants.PART_IMAGE_PATH + name + ".png");
	}
	
	public String toString() {
		return getName();
	}
	
	public String getName() {
		return name;
	}
	
	public Image getImage() {
		return image;
	}
	
}
