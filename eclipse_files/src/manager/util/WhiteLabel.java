package manager.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class WhiteLabel extends JLabel {
	public WhiteLabel(String text) {
		super(text);
		setForeground(Color.WHITE);
		setFont(new Font("Arial", Font.PLAIN, 14));
	}
	
	public void setLabelSize(Dimension d) {
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
	}
	
	public void setLabelSize(int width, int height) {
		setLabelSize(new Dimension(width, height));
	}
}
