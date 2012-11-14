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
//      The below code aligns the GridBagLayout in the upper left corner of the panel
//		gbl_pnlParts.columnWidths = new int[]{0, 0, 0, 0, 0};
//		gbl_pnlParts.rowHeights = new int[]{0, 0};
//		gbl_pnlParts.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
//		gbl_pnlParts.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlParts.setLayout(gbl_pnlParts);

		// This loop adds the 8 Part labels (Part 1, Part 2, ... Part 8) to the panel iteratevly
		for (int i = 0; i < 4; i++) {
			GridBagConstraints gbc_lblPart = new GridBagConstraints();
			JLabel lblPart = new JLabel("Part " + (i+1) + ":");
			if (i =- 0) gbc_labelPart.anchor = GridBagConstraints.EAST;
			gbc_lblPart.insets = new Insets(0, 0, 5, 5);
			gbc_lblPart.gridx = 0;
			if (i == 3) gbc_labelPart.insets = new Insets(0,0,0,5);
			gbc_lblPart.gridy = i;
			pnlParts.add(lblPart, gbc_lblPart);

			JLabel lblPart = new JLabel("Part " + (i+5) + ":");
			gbc_lblPart.gridx = 2;
			gbc_lblPart.gridy = i;
			pnlParts.add(lblPart, gbc_lblPart);
		}

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
