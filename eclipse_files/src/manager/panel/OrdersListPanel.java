package manager.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import manager.util.ClickablePanel;
import manager.util.ClickablePanelClickHandler;
import manager.util.OverlayPanel;
import manager.util.WhiteLabel;
import Utils.Constants;
import factory.Order;
import factory.PartType;


public class OrdersListPanel extends OverlayPanel {
	OrdersListPanelHandler handler;
	ArrayList<Order> orders = new ArrayList<Order>();
	HashMap<PartType, ClickablePanel> panels = new HashMap<PartType, ClickablePanel>();
	
	
	public OrdersListPanel(OrdersListPanelHandler h) {
		super();
		handler = h;
		
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
			ClickablePanel panel = new ClickablePanel(new EditClickHandler(o));
			panel.setSize(350, 50);
			panel.setBorder(Constants.MEDIUM_PADDING);
			panel.setAlignmentX(0);
			
			add(panel);
			
			JLabel imageLabel = new JLabel(new ImageIcon(o.getImage()));
			imageLabel.setMinimumSize(new Dimension(50, 30));
			imageLabel.setPreferredSize(new Dimension(50, 30));
			imageLabel.setMaximumSize(new Dimension(50, 30));
			panel.add(imageLabel);
			
			WhiteLabel nameLabel = new WhiteLabel("Part: " + o.getName());
			nameLabel.setLabelSize(165, 30);
			panel.add(nameLabel);
			
			JButton deleteButton = new JButton("delete");
			deleteButton.addActionListener(new DeleteClickHandler(o));
			panel.add(deleteButton);
			
			// add padding
			add(Box.createVerticalStrut(10));
			panels.put(o, panel);
		}
		validate();
	}
	
	public void updatePartTypes(ArrayList<PartType> pt) {
		partTypes = pt;
		parseOrders();
		restoreColors();
	}
	
	public void restoreColors() {
		for(ClickablePanel panel : panels.values()) {
			panel.restoreColor();
		}
	}
	
	public interface OrdersListPanelHandler {
		public void editPart(PartType pt);
		public void deletePart(PartType pt);
	}
	
	private class EditClickHandler implements ClickablePanelClickHandler{
		PartType pt;
		public EditClickHandler(PartType pt) {
			this.pt = pt;
		}

		@Override
		public void mouseClicked() {
			restoreColors();
			handler.editPart(pt);
			panels.get(pt).setColor(new Color(5, 151, 255));
		}
	}
	
	private class DeleteClickHandler implements ActionListener{
		PartType pt;
		public DeleteClickHandler(PartType pt) {
			this.pt = pt;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			restoreColors();
			handler.deletePart(pt);
			panels.get(pt).setColor(new Color(150, 6, 6));
		}
	}
}
