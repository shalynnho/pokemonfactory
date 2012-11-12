package GUI;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Component;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PartsManagerPanel extends JPanel implements ActionListener {
	private JTextField tfName;
	private JTextField tfImgPath;
	private JTextField tfSndPath;
	private JButton btnCreatePart;
	private JButton btnClearFields;

	/**
	 * Create the panel.
	 */
	public PartsManagerPanel() {
		setLayout(new GridLayout(1, 1));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setSelectedIndex(0);
		add(tabbedPane);
		JPanel viewPanel = new JPanel();
		tabbedPane.addTab("View/Edit", viewPanel);
		JPanel addPanel = new JPanel();
		tabbedPane.addTab("Add", addPanel);
		addPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		addPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 2));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblPartIcon = new JLabel("Part Icon");
		lblPartIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblPartIcon);
		
		JLabel lblimageiconGoesHere = new JLabel("[ImageIcon goes here]");
		lblimageiconGoesHere.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblimageiconGoesHere);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblName = new JLabel("Name:");
		panel_2.add(lblName, "2, 2, right, default");
		
		tfName = new JTextField();
		panel_2.add(tfName, "4, 2, fill, default");
		tfName.setColumns(10);
		
		JLabel lblImgPath = new JLabel("Image path:");
		panel_2.add(lblImgPath, "2, 4, right, default");
		
		tfImgPath = new JTextField();
		panel_2.add(tfImgPath, "4, 4, fill, default");
		tfImgPath.setColumns(10);
		
		JLabel lblSndPath = new JLabel("Sound path:");
		panel_2.add(lblSndPath, "2, 6, right, default");
		
		tfSndPath = new JTextField();
		panel_2.add(tfSndPath, "4, 6, fill, default");
		tfSndPath.setColumns(10);
		
		btnCreatePart = new JButton("Create Part");
		panel_2.add(btnCreatePart, "2, 10");
		
		JButton btnClearFields = new JButton("Clear Fields");
		panel_2.add(btnClearFields, "4, 10");
		JPanel deletePanel = new JPanel();
		tabbedPane.addTab("Delete", deletePanel);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnClearFields) {
			clearFields();
		}
	}
	
	private void clearFields() {
		tfName.setText("");
		tfImgPath.setText("");
		tfSndPath.setText("");
	}
}
