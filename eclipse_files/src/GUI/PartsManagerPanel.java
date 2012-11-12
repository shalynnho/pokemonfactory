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
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import java.awt.event.ActionEvent;

public class PartsManagerPanel extends JPanel implements ActionListener {
	private JButton btnClearFields;
	private JTextField tfName;
	private JTextField tfImgPath;
	private JTextField tfSndPath;

	/**
	 * Create the panel.
	 */
	public PartsManagerPanel() {
		setLayout(new GridLayout(1, 1));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setSelectedIndex(0);
		add(tabbedPane);
		JPanel viewPanel = new JPanel();
		tabbedPane.addTab("Part Manager", viewPanel);
		viewPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlPartChooser = new JPanel();
		viewPanel.add(pnlPartChooser, BorderLayout.NORTH);
		
		JComboBox cbPart = new JComboBox();
		pnlPartChooser.add(cbPart);
		
		JButton btnNewPart = new JButton("New Part");
		pnlPartChooser.add(btnNewPart);
		
		JSplitPane splitPane = new JSplitPane();
		viewPanel.add(splitPane, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		splitPane.setLeftComponent(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		JLabel lblImageIcon = new JLabel("Image Icon");
		lblImageIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(lblImageIcon);
		
		JLabel lblimageicon = new JLabel("[ImageIcon]");
		lblimageicon.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(lblimageicon);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		JLabel lblSoundPreview = new JLabel("Sound preview:");
		panel_4.add(lblSoundPreview);
		lblSoundPreview.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel_4.add(btnPlay);
		
		JPanel pnlForm = new JPanel();
		splitPane.setRightComponent(pnlForm);
		pnlForm.setLayout(new FormLayout(new ColumnSpec[] {
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblName_1 = new JLabel("Name:");
		pnlForm.add(lblName_1, "2, 2, right, default");
		
		tfName = new JTextField();
		pnlForm.add(tfName, "4, 2, fill, default");
		tfName.setColumns(10);
		
		JLabel lblImagePath = new JLabel("Image Path:");
		pnlForm.add(lblImagePath, "2, 4, right, default");
		
		tfImgPath = new JTextField();
		pnlForm.add(tfImgPath, "4, 4, fill, default");
		tfImgPath.setColumns(10);
		
		JLabel lblSoundPath = new JLabel("Sound path:");
		pnlForm.add(lblSoundPath, "2, 6, right, default");
		
		tfSndPath = new JTextField();
		pnlForm.add(tfSndPath, "4, 6, fill, default");
		tfSndPath.setColumns(10);
		
		JButton button = new JButton("Create Part");
		pnlForm.add(button, "2, 10");
		
		JButton button_1 = new JButton("Clear Fields");
		button_1.addActionListener(this);
		pnlForm.add(button_1, "2, 12");
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
