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
import java.awt.CardLayout;

public class PartsManagerPanel extends JPanel {
	private JButton btnClearFields;
	private JPanel pnlButtons;
	private JPanel pnlView;
	private JPanel pnlEdit;
	private JPanel pnlAdd;
	private JPanel pnlDelete;
	private JTextField tfImgPath;
	private JTextField tfSndPath;
	private JTextField tfName;

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
		btnNewPart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAddPanel();
			}
		});
		pnlPartChooser.add(btnNewPart);
		
		JSplitPane splitPane = new JSplitPane();
		viewPanel.add(splitPane, BorderLayout.CENTER);
		
		JPanel pnlPreview = new JPanel();
		splitPane.setLeftComponent(pnlPreview);
		pnlPreview.setLayout(new BoxLayout(pnlPreview, BoxLayout.Y_AXIS));
		
		JLabel lblImageIcon = new JLabel("Image Icon");
		lblImageIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlPreview.add(lblImageIcon);
		
		JLabel lblimageicon = new JLabel("[ImageIcon]");
		lblimageicon.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlPreview.add(lblimageicon);
		
		JPanel pnlSound = new JPanel();
		pnlPreview.add(pnlSound);
		pnlSound.setLayout(new BoxLayout(pnlSound, BoxLayout.X_AXIS));
		
		JLabel lblSoundPreview = new JLabel("Sound preview:");
		pnlSound.add(lblSoundPreview);
		lblSoundPreview.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		pnlSound.add(btnPlay);
		
		JPanel pnlForm = new JPanel();
		splitPane.setRightComponent(pnlForm);
		pnlForm.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblName = new JLabel("Name:");
		pnlForm.add(lblName, "2, 2, right, default");
		
		tfName = new JTextField();
		pnlForm.add(tfName, "4, 2, fill, default");
		tfName.setColumns(10);
		
		JLabel lblImagePath = new JLabel("Image Path:");
		pnlForm.add(lblImagePath, "2, 4, right, default");
		
		tfImgPath = new JTextField();
		pnlForm.add(tfImgPath, "4, 4, fill, default");
		tfImgPath.setColumns(10);
		
		JButton btnBrowseImg = new JButton("Browse...");
		pnlForm.add(btnBrowseImg, "6, 4");
		
		JLabel lblSoundPath = new JLabel("Sound Path:");
		pnlForm.add(lblSoundPath, "2, 6, right, default");
		
		tfSndPath = new JTextField();
		pnlForm.add(tfSndPath, "4, 6, fill, default");
		tfSndPath.setColumns(10);
		
		JButton btnBrowseSnd = new JButton("Browse...");
		pnlForm.add(btnBrowseSnd, "6, 6");
		
		pnlButtons = new JPanel();
		viewPanel.add(pnlButtons, BorderLayout.SOUTH);
		pnlButtons.setLayout(new CardLayout(0, 0));
		
		pnlView = new JPanel();
		pnlButtons.add(pnlView, "View Part Type");
		
		JButton btnEditPartType = new JButton("Edit Part Type");
		pnlView.add(btnEditPartType);
		
		pnlEdit = new JPanel();
		pnlButtons.add(pnlEdit, "Edit Part Type");
		
		JButton btnSaveChanges = new JButton("Save Changes");
		pnlEdit.add(btnSaveChanges);
		
		JButton btnCnclChanges = new JButton("Cancel Changes");
		pnlEdit.add(btnCnclChanges);
		
		pnlAdd = new JPanel();
		pnlButtons.add(pnlAdd, "Add Part Type");
		
		JButton btnCreatePartType = new JButton("Create Part Type");
		pnlAdd.add(btnCreatePartType);
		
		JButton btnClearFields_1 = new JButton("Clear Fields");
		btnClearFields_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
		pnlAdd.add(btnClearFields_1);
		
		pnlDelete = new JPanel();
		pnlButtons.add(pnlDelete, "Delete Part Type");
		
		JButton btnDeletePartType = new JButton("Delete Part Type");
		pnlDelete.add(btnDeletePartType);
	}
	
	protected void showAddPanel() {
		CardLayout cl = (CardLayout)(pnlButtons.getLayout());
        cl.show(pnlButtons, "Add Part Type");
        clearFields();
        enableFields();
	}
	
	protected void clearFields() {
		tfName.setText("");
		tfImgPath.setText("");
		tfSndPath.setText("");
	}
	
	protected void toggleFields() {
		tfName.isEnabled() ? tfName.setEnabled(false) : tfName.setEnabled(true);
		tfImgPath.isEnabled() ? tfImgPath.setEnabled(false) : tfImgPath.setEnabled(true);
		tfSndPath.isEnabled() ? tfSndPath.setEnabled(false) : tfSndPath.setEnabled(true);
	}
	protected void enableFields() {
		tfName.isEnabled() ? : toggleFields();
	}
	protected void desableFIelds() {
		tfName.isEnabled() ? toggleFields();
	}
}
