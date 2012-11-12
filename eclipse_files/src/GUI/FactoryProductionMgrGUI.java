package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FactoryProductionMgrGUI extends OverlayPanel implements ActionListener {
	private static final int PANEL_WIDTH = 300;
	
	
	public FactoryProductionMgrGUI(int height) {
		super();
		setPreferredSize(new Dimension(PANEL_WIDTH, height));
		setMinimumSize(new Dimension(PANEL_WIDTH, height));
		setMaximumSize(new Dimension(PANEL_WIDTH, height));
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// TODO: get array of possible kits from Kit Assembly Manager
		JComboBox kitComboBox = new JComboBox();
		
		kitComboBox.addItem(arg0)
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
