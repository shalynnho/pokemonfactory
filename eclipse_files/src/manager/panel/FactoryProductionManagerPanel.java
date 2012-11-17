package manager.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import manager.FactoryProductionManager;
import manager.panel.KitsListPanel.KitSelectHandler;
import manager.util.ClickablePanel;
import manager.util.OverlayInternalFrame;
import Utils.Constants;
import factory.KitConfig;
import factory.Order;

/**
*
* @author Shalynn Ho, Harry Trieu and Matt Zecchini
*/

public class FactoryProductionManagerPanel extends OverlayInternalFrame implements ActionListener, MouseListener {
	// Width of the JPanel
	private static final int PANEL_WIDTH = 300;
	
	// A reference to the FactoryProductionManager client
	private FactoryProductionManager fpmClient;
	
	// Stores kits currently available for order
	private ArrayList<KitConfig> kitConfigs = new ArrayList<KitConfig>();
	// Stores current list of orders
	private ArrayList<Order> queue = new ArrayList<Order>();
	// Stores the selected kitConfig for a new order
	private KitConfig selectedKit;
	
	/** JComponents **/
	// Displays kits currently available for order
	private JComboBox kitComboBox;
	private DefaultComboBoxModel defaultComboBox;
	// Displays current schedule of orders
	private JTextArea orderScheduleTextArea;
	private SpinnerNumberModel spinnerModel;
	private JSpinner quantitySpinner;
	private JButton orderButton;
	private JScrollPane orderScrollPane;
	
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
		
		// stuff for disappearing panel
		this.height = height;
		addMouseListener(this);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	
		/*
		kitComboBox = new JComboBox();
		
		for (int i = 0; i < kitConfigs.size(); i++) {
			kitComboBox.addItem(kitConfigs.get(i).getName());
		}
		
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		add(kitComboBox, c);
		kitComboBox.addMouseListener(this);
		for (int i = 0; i < kitComboBox.getComponentCount(); i++) {
			Component comp = kitComboBox.getComponent(i);
			comp.addMouseListener(this);
		}
		((JTextField)kitComboBox.getEditor().getEditorComponent()).addMouseListener(this);
		*/
		
		KitsListPanel kitsPanel = new KitsListPanel(new KitSelectHandler() {
			@Override
			public void onKitSelect(KitConfig kc) {
				selectedKit = kc;
			}
			
		});
		kitsPanel.setVisible(true);
		kitsPanel.setBackground(new Color(0, 0, 0, 30));
		for(ClickablePanel panel : kitsPanel.getPanels().values()) {
			panel.addMouseListener(this);
		}
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 2;
		
		add(kitsPanel, c);
		
		spinnerModel = new SpinnerNumberModel(0, 0, 1000, 1);
	    quantitySpinner = new JSpinner(spinnerModel);
		c.gridx = 0;
		c.gridy = 2;
		// Top padding
		c.insets = new Insets(50,0,0,0);
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		add(quantitySpinner, c);
		quantitySpinner.addMouseListener(this);
		for (int i = 0; i < quantitySpinner.getComponentCount(); i++) {
			quantitySpinner.getComponents()[i].addMouseListener(this);
		}
		((JSpinner.DefaultEditor)quantitySpinner.getEditor()).getTextField().addMouseListener(this);
		
		orderButton = new JButton("ORDER KITS");
		orderButton.addActionListener(this);
		c.gridx = 0;
		c.gridy = 3;
		add(orderButton, c);
		orderButton.addMouseListener(this);
				
		orderScheduleTextArea = new JTextArea();
		orderScheduleTextArea.setColumns(20);
		orderScheduleTextArea.setRows(20);
		orderScheduleTextArea.setLineWrap(true);
		orderScheduleTextArea.setEditable(false);
		orderScheduleTextArea.setWrapStyleWord(true);
		orderScheduleTextArea.addMouseListener(this);
		
		orderScrollPane = new JScrollPane(orderScheduleTextArea);
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.PAGE_END;

		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		add(orderScrollPane, c);
		orderScrollPane.addMouseListener(this);
		for (int i = 0; i < orderScrollPane.getComponentCount(); i++) {
			orderScrollPane.getComponents()[i].addMouseListener(this);
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
		kitConfigs = kc;
		
		// Clear the JComboBox
		defaultComboBox.removeAllElements();
		
		// Populate the JComboBox with the new kitConfigs
		for (int i = 0; i < kitConfigs.size(); i++) {
			kitComboBox.addItem(kitConfigs.get(i).getName());
		}
	}
	
	/**
	 * This function is called by FactoryProductionManager whenever orders are updated.
	 * @param o ArrayList of orders
	 */
	public void updateOrders(ArrayList<Order> o) {
		queue = o;
		
		// Clear the contents of the JTextArea
		orderScheduleTextArea.setText("");
		
		System.out.println("Queue size: " + queue.size());
		
		// Populate the JTextArea with current orders
		for (int i = 0; i < queue.size(); i++) {
			// System.out.println("--------- Orders in the Queue ----------");
			// System.out.println("Kit name: " + queue.get(i).getConfig().getName() + " // Quantity: " + queue.get(i).getNumKits());
			
			orderScheduleTextArea.append("Kit Type: " + queue.get(i).getConfig().getName() + "  |  Quantity: " + queue.get(i).getNumKits() + "\n");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) {
		for (int i = 0; i < getComponentCount(); i++) {
			getComponent(i).setVisible(true);
		}
		setPreferredSize(new Dimension(PANEL_WIDTH, height));
		setMinimumSize(new Dimension(PANEL_WIDTH, height));
		setMaximumSize(new Dimension(PANEL_WIDTH, height));
		revalidate();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		for (int i = 0; i < getComponentCount(); i++) {
			getComponent(i).setVisible(false);
		}
		setPreferredSize(new Dimension(PANEL_WIDTH/4, height));
		setMinimumSize(new Dimension(PANEL_WIDTH/4, height));
		setMaximumSize(new Dimension(PANEL_WIDTH/4, height));
		revalidate();
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
