package manager.util;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class WhiteLabel extends JLabel {
	public WhiteLabel(String text) {
		super(text);
		setForeground(Color.WHITE);
		setFont(new Font("Arial", Font.PLAIN, 14));
	}
}
