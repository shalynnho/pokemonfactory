package manager.panel;

import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;

import manager.util.ClickablePanel;
import manager.util.ClickablePanelClickHandler;
import manager.util.ListPanel;
import manager.util.WhiteLabel;
import factory.Order;


public class OrdersListPanel extends ListPanel<Order> {
	OrderSelectHandler handler;
	
	public OrdersListPanel(OrderSelectHandler orderSelectHandler) {
		super();
		handler = orderSelectHandler;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setAlignmentX(LEFT_ALIGNMENT);
		setBorder(generalPadding);
		
		parseList();
	}
	
	public void parseList() {
		panels.clear();
		removeAll();
		repaint();
		
		for(Order o : itemList) {
			ClickablePanel panel = new ClickablePanel(new OrderClickHandler(o));
			panel.setPanelSize(itemWidth, itemHeight);
			panel.setBorder(itemPadding);
			panel.setAlignmentX(0);
			
			WhiteLabel nameLabel = new WhiteLabel(o.getConfig().getName() + ": ");
			WhiteLabel numLabel = new WhiteLabel("" + o.getNumKits());
			nameLabel.setLabelSize((int)(itemWidth * 0.7), itemHeight);
			numLabel.setLabelSize((int)(itemWidth * 0.3), itemHeight);
			panel.add(nameLabel);
			panel.add(numLabel);
			
			add(panel);
			
			// add padding
			add(Box.createVerticalStrut(itemMargin));
			panels.put(o, panel);
		}
		validate();
	}
	
	public interface OrderSelectHandler {
		public void onOrderSelect(Order o);
	}
	
	private class OrderClickHandler implements ClickablePanelClickHandler{
		Order o;
		public OrderClickHandler(Order o) {
			this.o = o;
		}

		@Override
		public void mouseClicked() {
			restoreColors();
			handler.onOrderSelect(o);
			panels.get(o).setColor(new Color(5, 151, 255));
		}
	}

}
