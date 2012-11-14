package manager.panel;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.JSplitPane;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTable;
import java.awt.event.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Insets;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

/*
* Authorship: Aaron Harris
*/

public class KitManagerPanel extends JPanel{
	private JComboBox[] cbPart;
	private JTable tblSched;
	private JTextField tfName;

	/**
	 * Create the panel.
	 */
	public KitManagerPanel() {
		setLayout(new GridLayout(1, 1));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		JPanel managerPanel = new JPanel();
		tabbedPane.addTab("Manage Kits", managerPanel);
		managerPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlKitChooser = new JPanel();
		managerPanel.add(pnlKitChooser, BorderLayout.NORTH);
		
		JComboBox cbKits = new JComboBox();
		pnlKitChooser.add(cbKits);
		
		JButton btnAddKit = new JButton("New Kit Arrangement");
		pnlKitChooser.add(btnAddKit);
		
		JPanel pnlButtons = new JPanel();
		managerPanel.add(pnlButtons, BorderLayout.SOUTH);
		pnlButtons.setLayout(new CardLayout(0, 0));
		
		JPanel pnlView = new JPanel();
		pnlButtons.add(pnlView, "View Buttons");
		
		JButton btnEditKit = new JButton("Edit Kit Arrangement");
		pnlView.add(btnEditKit);
		
		JButton btnDeleteKit = new JButton("Delete Kit Arrangement");
		pnlView.add(btnDeleteKit);
		
		JPanel pnlEdit = new JPanel();
		pnlButtons.add(pnlEdit, "Edit Buttons");
		
		JButton btnSaveChg = new JButton("Save Changes");
		pnlEdit.add(btnSaveChg);
		
		JButton btnCnclChg = new JButton("Cancel Changes");
		pnlEdit.add(btnCnclChg);
		
		JPanel pnlAdd = new JPanel();
		pnlButtons.add(pnlAdd, "Add Buttons");
		
		JButton btnCreateKit = new JButton("Create Kit Arrangement");
		pnlAdd.add(btnCreateKit);
		
		JButton btnClrFields = new JButton("Clear Fields");
		pnlAdd.add(btnClrFields);
		
		JPanel pnlDisplay = new JPanel();
		pnlDisplay.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		managerPanel.add(pnlDisplay, BorderLayout.CENTER);
		pnlDisplay.setLayout(new BorderLayout());
		
		JPanel pnlName = new JPanel();
		pnlDisplay.add(pnlName, BorderLayout.NORTH);
		
		JLabel lblKitName = new JLabel("Kit Name:");
		pnlName.add(lblKitName);
		
		tfName = new JTextField();
		pnlName.add(tfName);
		tfName.setColumns(10);
		
		JPanel pnlParts = new JPanel();
		pnlDisplay.add(pnlParts, BorderLayout.CENTER);
		GridBagLayout gbl_pnlParts = new GridBagLayout();
//              The below code aligns the GridBagLayout in the upper left corner of the panel
//		gbl_pnlParts.columnWidths = new int[]{0, 0, 0, 0, 0};
//		gbl_pnlParts.rowHeights = new int[]{0, 0};
//		gbl_pnlParts.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
//		gbl_pnlParts.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlParts.setLayout(gbl_pnlParts);
		
		JLabel lblPart = new JLabel("Part 1:");
		GridBagConstraints gbc_lblPart = new GridBagConstraints();
		gbc_lblPart.anchor = GridBagConstraints.EAST;
		gbc_lblPart.insets = new Insets(0, 0, 5, 5);
		gbc_lblPart.gridx = 0;
		gbc_lblPart.gridy = 0;
		pnlParts.add(lblPart, gbc_lblPart);
	
		JLabel lblPart_1 = new JLabel("Part 5:");
		GridBagConstraints gbc_lblPart_1 = new GridBagConstraints();
		gbc_lblPart_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPart_1.anchor = GridBagConstraints.EAST;
		gbc_lblPart_1.gridx = 2;
		gbc_lblPart_1.gridy = 0;
		pnlParts.add(lblPart_1, gbc_lblPart_1);
		
		JLabel lblPart_2 = new JLabel("Part 2:");
		GridBagConstraints gbc_lblPart_2 = new GridBagConstraints();
		gbc_lblPart_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblPart_2.gridx = 0;
		gbc_lblPart_2.gridy = 1;
		pnlParts.add(lblPart_2, gbc_lblPart_2);
		
		JLabel lblPart_5 = new JLabel("Part 6:");
		GridBagConstraints gbc_lblPart_5 = new GridBagConstraints();
		gbc_lblPart_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblPart_5.gridx = 2;
		gbc_lblPart_5.gridy = 1;
		pnlParts.add(lblPart_5, gbc_lblPart_5);
		
		JLabel lblPart_3 = new JLabel("Part 3:");
		GridBagConstraints gbc_lblPart_3 = new GridBagConstraints();
		gbc_lblPart_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblPart_3.gridx = 0;
		gbc_lblPart_3.gridy = 2;
		pnlParts.add(lblPart_3, gbc_lblPart_3);
		
		JLabel lblPart_6 = new JLabel("Part 7:");
		GridBagConstraints gbc_lblPart_6 = new GridBagConstraints();
		gbc_lblPart_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblPart_6.gridx = 2;
		gbc_lblPart_6.gridy = 2;
		pnlParts.add(lblPart_6, gbc_lblPart_6);
		
		JLabel lblPart_4 = new JLabel("Part 4:");
		GridBagConstraints gbc_lblPart_4 = new GridBagConstraints();
		gbc_lblPart_4.insets = new Insets(0, 0, 0, 5);
		gbc_lblPart_4.gridx = 0;
		gbc_lblPart_4.gridy = 3;
		pnlParts.add(lblPart_4, gbc_lblPart_4);
		
		JLabel lblPart_7 = new JLabel("Part 8:");
		GridBagConstraints gbc_lblPart_7 = new GridBagConstraints();
		gbc_lblPart_7.insets = new Insets(0, 0, 0, 5);
		gbc_lblPart_7.gridx = 2;
		gbc_lblPart_7.gridy = 3;
		pnlParts.add(lblPart_7, gbc_lblPart_7);

		// Array of comboBoxes is used to iteratively construct the comboboxes
		// This is used to make sure all comboBoxes are made the same way.
		cbPart = new JComboBox[8];
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		for (int i = 0; i < 4; i++) {
			cbPart[i] = new JComboBox();
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = i;
			pnlParts.add(cbPart[i], gbc_comboBox);
			
			cbPart[i+1] = new JComboBox();
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.gridx = 3;
			gbc_comboBox.gridy = i;
			pnlParts.add(cbPart[i+1], gbc_comboBox);
		}

		// iteratively construct jLabels
		for (int i = 0; i < 4; i++ ) {

		}
		
		JPanel schedPanel = new JPanel();
		tabbedPane.addTab("View Schedule", null, schedPanel, null);
		schedPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlRefresh = new JPanel();
		schedPanel.add(pnlRefresh, BorderLayout.NORTH);
		
		JButton btnRefresh = new JButton("Refresh");
		pnlRefresh.add(btnRefresh);
		
		tblSched = new JTable();
		tblSched.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		schedPanel.add(tblSched, BorderLayout.CENTER);

	}
}
