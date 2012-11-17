package manager.panel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;

import manager.util.ClickablePanel;
import manager.util.ClickablePanelClickHandler;
import manager.util.OverlayPanel;
import manager.util.WhiteLabel;
import Utils.Constants;
import factory.KitConfig;
import factory.PartType;


public class KitsListPanel extends OverlayPanel {
	ArrayList<KitConfig> kitConfigs = new ArrayList<KitConfig>();
	HashMap<KitConfig, ClickablePanel> panels = new HashMap<KitConfig, ClickablePanel>();
	
	KitSelectHandler handler;
	
	public KitsListPanel(KitSelectHandler h) {
		super();
		handler = h;
		kitConfigs = (ArrayList<KitConfig>) Constants.DEFAULT_KITCONFIGS.clone();
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setAlignmentX(LEFT_ALIGNMENT);
		setBorder(Constants.PADDING);
		
		parsePartTypes();

	}
	
	public void parsePartTypes() {
		panels.clear();
		removeAll();
		repaint();
		
		for(KitConfig kc : kitConfigs) {
			ClickablePanel panel = new ClickablePanel(new KitClickHandler(kc));
			panel.setSize(300, 50);
			panel.setBorder(Constants.MEDIUM_PADDING);
			panel.setAlignmentX(0);
			
			add(panel);
			
			WhiteLabel nameLabel = new WhiteLabel("Kit: " + kc.getName());
			nameLabel.setLabelSize(165, 30);
			panel.add(nameLabel);
			
			/*
			JButton deleteButton = new JButton("delete");
			deleteButton.addActionListener(new DeleteClickHandler(pt));
			panel.add(deleteButton);
			*/
			
			// add padding
			add(Box.createVerticalStrut(10));
			panels.put(kc, panel);
		}
		validate();
	}
	
	public void updatePartTypes(ArrayList<KitConfig> kc) {
		kitConfigs = kc;
		parsePartTypes();
		restoreColors();
	}
	
	public void restoreColors() {
		for(ClickablePanel panel : panels.values()) {
			panel.restoreColor();
		}
	}
	
	public interface KitConfigPanelHandler {
		public void editPart(PartType pt);
	}
	
	public HashMap<KitConfig, ClickablePanel> getPanels() {
		return panels;
	}
	
	public interface KitSelectHandler {
		public void onKitSelect(KitConfig kc);
	}
	
	private class KitClickHandler implements ClickablePanelClickHandler{
		KitConfig kc;
		public KitClickHandler(KitConfig kc) {
			this.kc = kc;
		}

		@Override
		public void mouseClicked() {
			restoreColors();
			handler.onKitSelect(kc);
			panels.get(kc).setColor(new Color(5, 151, 255));
		}
	}

}
