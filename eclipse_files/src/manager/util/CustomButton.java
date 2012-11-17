package manager.util;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

public class CustomButton extends JButton implements MouseListener {
	private Color color = new Color(0, 0, 0);
	private String name;
	
	public CustomButton(String name) {
		super(name);
		this.name = name;
		
		setForeground(new Color(95, 170, 220));
		setBackground(new Color(0, 0, 0));
		setOpaque(false);
		
		Border loweredEtched = BorderFactory.createLineBorder(new Color(35, 35, 35), 5, false);
		Border padding = BorderFactory.createEmptyBorder(7, 20, 7, 20);
		Border compound = new CompoundBorder(loweredEtched, padding);
		setBorder(compound);
		
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setBackground(new Color(0, 0, 0, 50));
		setOpaque(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setOpaque(false);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
