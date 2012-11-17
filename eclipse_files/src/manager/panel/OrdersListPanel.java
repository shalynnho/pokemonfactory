package manager.panel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;

import manager.util.ClickablePanel;
import manager.util.ClickablePanelClickHandler;
import manager.util.ListPanel;
import manager.util.OverlayPanel;
import manager.util.WhiteLabel;
import Utils.Constants;
import factory.FactoryData;
import factory.Order;


public class OrdersListPanel extends OverlayPanel implements ListPanel {
	ArrayList<Order> orders = new ArrayList<Order>();
	HashMap<FactoryData, ClickablePanel> panels = new HashMap<FactoryData, ClickablePanel>();
	
	OrderSelectHandler handler;
	
	public OrdersListPanel(OrderSelectHandler orderSelectHandler) {
		super();
		handler = orderSelectHandler;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setAlignmentX(LEFT_ALIGNMENT);
		setBorder(Constants.PADDING);
		
		parseOrders();

	}
	
	public void parseOrders() {
		panels.clear();
		removeAll();
		repaint();
		
		for(Order o : orders) {
			ClickablePanel panel = new ClickablePanel(new OrderClickHandler(o));
			panel.setSize(300, 50);
			panel.setBorder(Constants.MEDIUM_PADDING);
			panel.setAlignmentX(0);
			
			WhiteLabel nameLabel = new WhiteLabel(o.getConfig().getName() + ": ");
			WhiteLabel numLabel = new WhiteLabel("" + o.getNumKits());
			nameLabel.setLabelSize(165, 30);
			numLabel.setLabelSize(50, 30);
			panel.add(nameLabel);
			panel.add(numLabel);
			
			add(panel);
			
			/*
			JButton deleteButton = new JButton("delete");
			deleteButton.addActionListener(new DeleteClickHandler(pt));
			panel.add(deleteButton);
			*/
			
			// add padding
			add(Box.createVerticalStrut(10));
			panels.put(o, panel);
		}
		validate();
	}
	
	public void updateOrders(ArrayList<Order> o) {
		orders = o;
		parseOrders();
		restoreColors();
	}
	
	public void restoreColors() {
		for(ClickablePanel panel : panels.values()) {
			panel.restoreColor();
		}
	}
	
	public interface OrderPanelHandler {
		public void editOrder(Order o);
	}
	
	public HashMap<FactoryData, ClickablePanel> getPanels() {
		return panels;
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
