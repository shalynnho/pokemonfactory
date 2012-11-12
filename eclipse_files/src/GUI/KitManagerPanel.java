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
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class KitManagerPanel extends JPanel {
	private JTextField textField;
	private JComboBox[] cbPart;

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
		kitPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblName = new JLabel("Name:");
		kitPanel.add(lblName, "2, 2");
		
		textField = new JTextField();
		kitPanel.add(textField, "2, 4, fill, default");
		textField.setColumns(10);
		
		JLabel lblNumberOfParts = new JLabel("Number of Parts:");
		kitPanel.add(lblNumberOfParts, "2, 6");
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(4, 4, 8, 1));
		kitPanel.add(spinner, "2, 8, left, center");
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblSelectParts = new JLabel("Select Parts");
		panel.add(lblSelectParts, "2, 2");
		
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

	}
}
