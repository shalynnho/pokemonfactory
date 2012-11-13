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

/*
* Authorship: Aaron Harris
*/

public class KitManagerPanel extends JPanel{
	private JComboBox[] cbPart;
	private JTable tblSched;
	private JTextField textField;

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
		
		JSplitPane splitPane = new JSplitPane();
		managerPanel.add(splitPane, BorderLayout.CENTER);
		
		JPanel kitPanel = new JPanel();
		splitPane.setLeftComponent(kitPanel);
		kitPanel.setLayout(new BoxLayout(kitPanel, BoxLayout.Y_AXIS));
		
		JLabel lblName = new JLabel("Name:");
		lblName.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		kitPanel.add(lblName);
		
		textField = new JTextField();
		kitPanel.add(textField);
		textField.setColumns(10);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{1, 0};
		gbl_panel.rowHeights = new int[]{1, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblSelectParts = new JLabel("Select Parts");
		GridBagConstraints gbc_lblSelectParts = new GridBagConstraints();
		gbc_lblSelectParts.fill = GridBagConstraints.BOTH;
		gbc_lblSelectParts.gridx = 0;
		gbc_lblSelectParts.gridy = 0;
		panel.add(lblSelectParts, gbc_lblSelectParts);
		
		JComboBox cbTest = new JComboBox();
		GridBagConstraints gbc_cbTest = new GridBagConstraints();
		gbc_cbTest.fill = GridBagConstraints.BOTH;
		gbc_cbTest.gridx = 0;
		gbc_cbTest.gridy = 0;
		panel.add(cbTest, gbc_cbTest);
		
		cbPart = new JComboBox[8];
		for (int i = 0; i < 8; i++) {
			cbPart[i] = new JComboBox();
			panel.add(cbPart[i], "2, " + (4+(2*i)) + ", fill, default");
		}
		
		JPanel pnlButtons = new JPanel();
		managerPanel.add(pnlButtons, BorderLayout.SOUTH);
		pnlButtons.setLayout(new CardLayout(0, 0));
		
		JPanel pnlView = new JPanel();
		pnlButtons.add(pnlView, "name_23894555808367");
		
		JButton btnEditKit = new JButton("Edit Kit Arrangement");
		pnlView.add(btnEditKit);
		
		JButton btnDeleteKit = new JButton("Delete Kit Arrangement");
		pnlView.add(btnDeleteKit);
		
		JPanel pnlEdit = new JPanel();
		pnlButtons.add(pnlEdit, "name_23910758672140");
		
		JButton btnSaveChg = new JButton("Save Changes");
		pnlEdit.add(btnSaveChg);
		
		JButton btnCnclChg = new JButton("Cancel Changes");
		pnlEdit.add(btnCnclChg);
		
		JPanel pnlAdd = new JPanel();
		pnlButtons.add(pnlAdd, "name_23920408713375");
		
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
