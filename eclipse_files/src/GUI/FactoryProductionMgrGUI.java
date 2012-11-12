package GUI;

import java.awt.Dimension;

import javax.swing.JPanel;

public class FactoryProductionMgrGUI extends JPanel {
	private static final int PANEL_WIDTH = 300;
	
	
	public FactoryProductionMgrGUI(int height) {
		OverlayPanel op = new OverlayPanel();
		op.setPreferredSize(new Dimension(PANEL_WIDTH, height));
		op.setMinimumSize(new Dimension(PANEL_WIDTH, height));
		op.setMaximumSize(new Dimension(PANEL_WIDTH, height));
		
		op.add(this);
	}
}
