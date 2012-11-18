package manager.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import manager.FactoryProductionManager;
import manager.panel.KitsListPanel.KitSelectHandler;
import manager.util.ClickablePanel;
import manager.util.CustomButton;
import manager.util.ListPanel;
import manager.util.OverlayInternalFrame;
import factory.KitConfig;
import factory.Order;

/**
*
* @author Shalynn Ho, Harry Trieu, Matt Zecchini, Peter Zhang
*/

public class FactoryProductionManagerPanel extends OverlayInternalFrame implements ActionListener, MouseListener {
	// Width of the JPanel
	private static final int PANEL_WIDTH = 300;
	
	// A reference to the FactoryProductionManager client
	private FactoryProductionManager fpmClient;
	
	// Stores current list of orders
	private ArrayList<Order> queue = new ArrayList<Order>();
	// Stores the selected kitConfig for a new order
	private KitConfig selectedKit;
	// Stores the selected Order
	private Order selectedOrder;
	
	/** JComponents **/
	// Displays current schedule of orders
	private SpinnerNumberModel spinnerModel;
	private JSpinner quantitySpinner;
	private JButton orderButton;
	private KitsListPanel kitsPanel;
	private JScrollPane kitsScrollPane;
	private OrdersListPanel ordersPanel;
	private JScrollPane ordersScrollPane;	
	
	private int height;
	
	/**
	 * Constructor
	 * @param f a reference to the FactoryProductionManager client.
	 * @param height of the JFrame
	 */
	public FactoryProductionManagerPanel(FactoryProductionManager f, int height) {
		super();
		fpmClient = f;
		setPreferredSize(new Dimension(PANEL_WIDTH, height));
		setMinimumSize(new Dimension(PANEL_WIDTH, height));
		setMaximumSize(new Dimension(PANEL_WIDTH, height));
		
		this.height = height;
		addMouseListener(this);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	
		// Setup KitsListPanel
		kitsPanel = new KitsListPanel(new KitSelectHandler() {
			@Override
			public void onKitSelect(KitConfig kc) {
				selectedKit = kc;
			}
		});
		
		kitsPanel.setVisible(true);
		kitsPanel.setBackground(new Color(0, 0, 0, 30));
		addMouseListeners(kitsPanel);
		
		kitsScrollPane = new JScrollPane(kitsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		kitsScrollPane.setOpaque(false);
		kitsScrollPane.getViewport().setOpaque(false);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 2;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		
		add(kitsScrollPane, c);
		
		// Setup JSpinner
		spinnerModel = new SpinnerNumberModel(0, 0, 1000, 1);
	    quantitySpinner = new JSpinner(spinnerModel);
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		c.weighty = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(quantitySpinner, c);
		// Add listener to nested components - for disappearing panel
		for (int i = 0; i < quantitySpinner.getComponentCount(); i++) {
			quantitySpinner.getComponents()[i].addMouseListener(this);
		}
		((JSpinner.DefaultEditor)quantitySpinner.getEditor()).getTextField().addMouseListener(this);
		
		// Setup order button
		orderButton = new CustomButton("Order Kits >");
		orderButton.addActionListener(this);
		c.gridx = 0;
		c.gridy = 3;
		add(orderButton, c);
		
		// Setup OrdersListPanel
		OrdersListPanel.OrderSelectHandler selectHandler = new OrdersListPanel.OrderSelectHandler() {
			@Override
			public void onOrderSelect(Order o) {
				selectedOrder = o;
			}
		};
		ordersPanel = new OrdersListPanel(selectHandler);
		ordersPanel.setVisible(true);
		ordersPanel.setBackground(new Color(0, 0, 0, 30));
		
		ordersScrollPane = new JScrollPane(ordersPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ordersScrollPane.setOpaque(false);
		ordersScrollPane.getViewport().setOpaque(false);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.PAGE_END;

		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		add(ordersScrollPane, c);
		
		// Add mouseListener to second-level components
		for (int i = 0; i < getComponentCount(); i++) {
			getComponents()[i].addMouseListener(this);
		}
		
		
	}

	/**
	 * Handle action events.
	 * @param ae action event
	 */
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == orderButton) {	
			
			KitConfig kitToMake;
			
			if(selectedKit != null) {
				kitToMake = selectedKit;
				
				//Set variable quantityToMake equal to number user enters in spinnerModel
				int quantityToMake = spinnerModel.getNumber().intValue();
				
				//Creates new Order and passes it the kit the User selects and the quantity to make
				Order newOrder = new Order(kitToMake, quantityToMake);
				
				//sends message to FCS with order info
				fpmClient.createOrder(newOrder);
			}
		}
	}
		
	
	/**
	 * This function is called by FactoryProductionManager whenever KitConfigs are updated.
	 * @param kc ArrayList of current KitConfigs
	 */
	public void updateKitConfigs(ArrayList<KitConfig> kc) {
		kitsPanel.updateList(kc);
		addMouseListeners(kitsPanel);
	}
	
	/**
	 * This function is called by FactoryProductionManager whenever orders are updated.
	 * @param o ArrayList of orders
	 */
	public void updateOrders(ArrayList<Order> o) {
		queue = o;
		System.out.println("Queue size: " + queue.size());
		ordersPanel.updateList(o);
		addMouseListeners(ordersPanel);
	}
	
	/**
	 * Adds this FPMPanel as the mouseListener for the components of the ListPanel
	 * @param panel - KitsListPanel or OrdersListPanel
	 */
	private void addMouseListeners(ListPanel<?> panel) {
		for(ClickablePanel p : panel.getPanels().values()) {
			for(int i = 0; i < p.getMouseListeners().length; i++) {
				if (!p.getMouseListeners()[i].equals(this)) {
					p.addMouseListener(this);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		for (int i = 0; i < getComponentCount(); i++) {
			getComponent(i).setVisible(true);
		}
		setPanelSize(PANEL_WIDTH, height);
		// revalidate();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		for (int i = 0; i < getComponentCount(); i++) {
			getComponent(i).setVisible(false);
		}
		setPanelSize(PANEL_WIDTH/4, height);
		// revalidate();
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

}
