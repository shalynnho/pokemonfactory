package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class FactoryProductionMgrGUI extends OverlayPanel implements ActionListener {
	private static final int PANEL_WIDTH = 300;
	
	
	public FactoryProductionMgrGUI(int height) {
		super();
		setPreferredSize(new Dimension(PANEL_WIDTH, height));
		setMinimumSize(new Dimension(PANEL_WIDTH, height));
		setMaximumSize(new Dimension(PANEL_WIDTH, height));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// TODO: get array of possible kits from Kit Assembly Manager
		JComboBox<String> kitComboBox = new JComboBox<String>();
		// TODO: change this, 
		kitComboBox.addItem("DEFAULT KIT");
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		add(kitComboBox, c);
		
		SpinnerNumberModel spinModel = new SpinnerNumberModel(0, 0, 1000, 1);
		JSpinner numSpinner = new JSpinner(spinModel);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10,0,0,0); //Top Padding
		c.anchor = GridBagConstraints.CENTER;
		add(numSpinner, c);
		
		JButton orderButton = new JButton("ORDER KITS");
		orderButton.addActionListener(this);
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.PAGE_END;
		add(orderButton, c);
				
		JTextArea kitSchedule = new JTextArea();
		kitSchedule.setColumns(20);
		kitSchedule.setRows(20);
		kitSchedule.setLineWrap(true);
		kitSchedule.setEditable(false);
		kitSchedule.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(kitSchedule);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.PAGE_END;
		add(scrollPane, c);
				
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO: send message to FCS with order info
		
	}
}
