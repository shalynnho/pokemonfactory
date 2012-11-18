package manager.panel;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.Border;

import manager.util.ClickablePanel;
import manager.util.ClickablePanelClickHandler;
import manager.util.ListPanel;
import manager.util.WhiteLabel;
import Utils.Constants;
import factory.KitConfig;
import factory.PartType;


public class KitsListPanel extends ListPanel<KitConfig> {
	private String header;
	private WhiteLabel headerLabel;
	
	private KitSelectHandler handler;
	
	public KitsListPanel(String header, KitSelectHandler h) {
		super();
		handler = h;
		itemList = (ArrayList<KitConfig>) Constants.DEFAULT_KITCONFIGS.clone();
		this.header = header;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setAlignmentX(LEFT_ALIGNMENT);
		setBorder(generalPadding);
		
		parseList();
	}
	
	public void parseList() {
		panels.clear();
		removeAll();
		repaint();
		
		headerLabel = new WhiteLabel(header);
		headerLabel.setBorder(Constants.LIGHT_BOTTOM_PADDING);
		headerLabel.setFont(headerLabel.getFont().deriveFont(Font.BOLD));
		add(headerLabel);
		
		for(KitConfig kc : itemList) {
			ClickablePanel panel = new ClickablePanel(new KitClickHandler(kc));
			panel.setPanelSize(itemWidth, itemHeight);
			panel.setBorder(itemPadding);
			panel.setAlignmentX(0);
			
			add(panel);
			
			WhiteLabel nameLabel = new WhiteLabel("Kit: " + kc.getName());
			nameLabel.setLabelSize(itemWidth, itemHeight);
			panel.add(nameLabel);
			
			// add padding
			add(Box.createVerticalStrut(itemMargin));
			panels.put(kc, panel);
		}
		
		validate();
	}
	
	public interface KitConfigPanelHandler {
		public void editPart(PartType pt);
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
