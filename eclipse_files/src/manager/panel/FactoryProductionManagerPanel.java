package manager.panel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import manager.FactoryProductionManager;
import manager.util.OverlayPanel;
import Utils.Constants;
import factory.KitConfig;
import factory.Order;

/**
*
* @author Shalynn Ho, Harry Trieu and Matt Zecchini
*/

public class FactoryProductionManagerPanel extends OverlayPanel implements ActionListener {
	private static final int PANEL_WIDTH = 300;

	private KitConfig selectedKit;
	
	// displays kits available for order
	private JComboBox kitComboBox;
	private SpinnerNumberModel spinModel;
	// a reference to the FactoryProductionManager client
	private FactoryProductionManager fpm;
	
	// stores kits available for order
	private ArrayList<KitConfig> kitConfigs = new ArrayList<KitConfig>();
	// stores current list of orders
	private ArrayList<Order> queue = new ArrayList<Order>();
	
	JButton orderButton;
	JTextArea kitSchedule;
	JScrollPane scrollPane;
	JSpinner numSpinner;
	
	//receives a Client because Harry and Matt are trying to figure out how
	//we can create the order and send it to the FactoryProductionManager, which will then
	//send it to the server (rather than having this GUI class send it directly to server)
	public FactoryProductionManagerPanel(FactoryProductionManager f, int height) {
		super();
		fpm = f;
		setPreferredSize(new Dimension(PANEL_WIDTH, height));
		setMinimumSize(new Dimension(PANEL_WIDTH, height));
		setMaximumSize(new Dimension(PANEL_WIDTH, height));
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	
		kitComboBox = new JComboBox();
		
		// TODO: REMOVE - FOR TESTING ONLY
		kitConfigs = (ArrayList<KitConfig>) Constants.DEFAULT_KITCONFIGS.clone();
		
		for (int i = 0; i < kitConfigs.size(); i++) {
			kitComboBox.addItem(kitConfigs.get(i).getName());
		}
		
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		add(kitComboBox, c);
		
		spinModel = new SpinnerNumberModel(0, 0, 1000, 1);
	    numSpinner = new JSpinner(spinModel);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(50,0,0,0); //Top Padding
		c.anchor = GridBagConstraints.CENTER;
		add(numSpinner, c);
		
		orderButton = new JButton("ORDER KITS");
		orderButton.addActionListener(this);
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.PAGE_END;
		add(orderButton, c);
				
		kitSchedule = new JTextArea();
		kitSchedule.setColumns(20);
		kitSchedule.setRows(20);
		kitSchedule.setLineWrap(true);
		kitSchedule.setEditable(false);
		kitSchedule.setWrapStyleWord(true);
		
		scrollPane = new JScrollPane(kitSchedule);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.PAGE_END;
		add(scrollPane, c);
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if (ae.getSource() == orderButton) {
			//For loop iterates through ArrayList of KitConfigs to find a match between the selected
			//ComboBoxItem (presented as a String) and the actual KitConfig with that String. 
			//Then it sets variable selectedKit equal to that kit, which allows an order 
			//to be created and sent to server
			
			// does not work
			for (int i = 0; i < kitConfigs.size(); i++) {
				if (kitConfigs.get(i).getName().equals(kitComboBox.getSelectedItem())) {
					selectedKit = kitConfigs.get(i);
				}
			}
			
			//Set variable quantityToMake equal to number user enters in SpinModel
			int quantityToMake = spinModel.getNumber().intValue();
			
			//Creates new Order and passes it the kit the User selects and the quantity to make
			Order newOrder = new Order(selectedKit, quantityToMake);
			
			//sends message to FCS with order info
			fpm.createOrder(newOrder);
			
			//testing purposes only -- feel free to comment out or delete print statement below
			//System.out.println("Kit: " + newOrder.kitConfig + " Qty: " + quantityToMake);
		}
	}
	
	/**
	 * This function is called by FactoryProductionManager whenever KitConfigs are updated.
	 * @param kc ArrayList of current KitConfigs
	 */
	public void updateKitConfigs(ArrayList<KitConfig> kc) {
		kitConfigs = kc;
	}
	
	/**
	 * This function is called by FactoryProductionManager whenever orders are updated.
	 * @param o ArrayList of orders
	 */
	public void updateOrders(ArrayList<Order> o) {
		queue = o;
	}
}
