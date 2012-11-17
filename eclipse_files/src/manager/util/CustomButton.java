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
	private Color backgroundColor = new Color(255, 255, 255);
	private Color borderColor = new Color(35, 35, 35);
	private Color borderHoverColor = new Color(150, 150, 150);
	private String name;
	
	Border border;
	Border padding;
	Border compoundBorder;
	
	public CustomButton(String name) {
		super(name);
		this.name = name;
		
		setForeground(new Color(255, 255, 255));
		
		setBackground(new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 30));
		setOpaque(true);
		
		border = BorderFactory.createLineBorder(borderColor, 1);
		padding = BorderFactory.createEmptyBorder(4, 20, 4, 20);
		compoundBorder = new CompoundBorder(border, padding);
		setBorder(compoundBorder);
		
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setBackground(new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 40));
		
		border = BorderFactory.createLineBorder(borderHoverColor, 1);
		compoundBorder = new CompoundBorder(border, padding);
		setBorder(compoundBorder);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setBackground(new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 30));
		
		border = BorderFactory.createLineBorder(borderColor, 1);
		compoundBorder = new CompoundBorder(border, padding);
		setBorder(compoundBorder);
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
