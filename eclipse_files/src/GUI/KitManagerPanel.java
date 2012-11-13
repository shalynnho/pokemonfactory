package GUI;

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

/*
* Authorship: Aaron Harris
*/

public class KitManagerPanel extends JPanel{
	private JComboBox[] cbPart;
	private JTable tblSched;

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
		
		cbPart = new JComboBox[8];
		for (int i = 0; i < 8; i++) {
			cbPart[i] = new JComboBox();
			pnlParts.add(cbPart[i], "2, " + (4+(2*i)) + ", fill, default");
		}
		
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
		
		JPanel schedPanel = new JPanel();
		tabbedPane.addTab("View Schedule", null, schedPanel, null);
		schedPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlRefresh = new JPanel();
		schedPanel.add(pnlRefresh, BorderLayout.NORTH);
		
		JButton btnRefresh = new JButton("Refresh");
		pnlRefresh.add(btnRefresh);
		
		tblSched = new JTable();
		schedPanel.add(tblSched, BorderLayout.CENTER);

	}
}
