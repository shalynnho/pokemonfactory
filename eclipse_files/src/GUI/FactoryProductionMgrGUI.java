package GUI;

import java.awt.Dimension;

import javax.swing.JPanel;

public class FactoryProductionMgrGUI extends OverlayPanel {
	private static final int PANEL_WIDTH = 300;
	
	
	public FactoryProductionMgrGUI(int height) {
		super();
		setPreferredSize(new Dimension(PANEL_WIDTH, height));
		setMinimumSize(new Dimension(PANEL_WIDTH, height));
		setMaximumSize(new Dimension(PANEL_WIDTH, height));
		
	}
}
