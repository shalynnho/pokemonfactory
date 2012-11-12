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
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.File;

public class PartsManagerPanel extends JPanel {
	private JPanel pnlButtons;
	private JPanel pnlView;
	private JPanel pnlEdit;
	private JPanel pnlAdd;
	private JTextField tfImgPath;
	private JTextField tfSndPath;
	private JTextField tfName;
	private JComboBox cbPart;
	private String[] backupFields; // used for temporarily storing old field data in case a user wants to revert
	private final JFileChooser fc;
	private JButton btnBrowseImg;
	private JButton btnBrowseSnd;
	
	/**
	 * Create the panel.
	 */
	public PartsManagerPanel() {
		setLayout(new GridLayout(1, 1));
		fc = new JFileChooser();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setSelectedIndex(0);
		add(tabbedPane);
		JPanel viewPanel = new JPanel();
		tabbedPane.addTab("Part Manager", viewPanel);
		viewPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlPartChooser = new JPanel();
		viewPanel.add(pnlPartChooser, BorderLayout.NORTH);
		
		cbPart = new JComboBox();
		cbPart.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				viewPart((String) ie.getItem());
			}
		});
		pnlPartChooser.add(cbPart);
		
		JButton btnNewPart = new JButton("New Part");
		btnNewPart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAddPanel();
				// change combo box to "Select Part..."
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
			public void actionPerformed(ActionEvent ae) {
				// play sound file
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
		
		btnBrowseImg = new JButton("Browse...");
		btnBrowseImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browse("image");
			}
		});
		pnlForm.add(btnBrowseImg, "6, 4");
		
		JLabel lblSoundPath = new JLabel("Sound Path:");
		pnlForm.add(lblSoundPath, "2, 6, right, default");
		
		tfSndPath = new JTextField();
		pnlForm.add(tfSndPath, "4, 6, fill, default");
		tfSndPath.setColumns(10);
		
		btnBrowseSnd = new JButton("Browse...");
		btnBrowseSnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browse("sound");
			}
		});
		pnlForm.add(btnBrowseSnd, "6, 6");
		
		pnlButtons = new JPanel();
		viewPanel.add(pnlButtons, BorderLayout.SOUTH);
		pnlButtons.setLayout(new CardLayout(0, 0));
		
		pnlView = new JPanel();
		pnlButtons.add(pnlView, "View Part Type");
		
		JButton btnEditPartType = new JButton("Edit Part Type");
		btnEditPartType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editPart((String) cbPart.getSelectedItem());
			}
		});
		pnlView.add(btnEditPartType);
		
		JButton btnDeletePartType = new JButton("Delete Part Type");
		btnDeletePartType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletePart((String) cbPart.getSelectedItem());
			}
		});
		pnlView.add(btnDeletePartType);
		
		pnlEdit = new JPanel();
		pnlButtons.add(pnlEdit, "Edit Part Type");
		
		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePartEdit((String) cbPart.getSelectedItem());
			}
		});
		pnlEdit.add(btnSaveChanges);
		
		JButton btnCnclChanges = new JButton("Cancel Changes");
		btnCnclChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelEdit();
				viewPart((String) cbPart.getSelectedItem());
			}
		});
		pnlEdit.add(btnCnclChanges);
		
		pnlAdd = new JPanel();
		pnlButtons.add(pnlAdd, "Add Part Type");
		
		JButton btnCreatePartType = new JButton("Create Part Type");
		btnCreatePartType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createPart();
			}
		});
		pnlAdd.add(btnCreatePartType);
		
		JButton btnClearFields = new JButton("Clear Fields");
		btnClearFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
		pnlAdd.add(btnClearFields);
	}
	
	protected void showAddPanel() {
		CardLayout cl = (CardLayout)(pnlButtons.getLayout());
        cl.show(pnlButtons, "Add Part Type");
        clearFields();
        enableFields();
	}
	
	protected void viewPart(String part) {
		CardLayout cl = (CardLayout)(pnlButtons.getLayout());
        cl.show(pnlButtons, "View Part Type");
        disableFields();
        // Show the part type items in form elements
	}
	
	protected void editPart(String part) {
		CardLayout cl = (CardLayout)(pnlButtons.getLayout());
        cl.show(pnlButtons, "Edit Part Type");
        // "back-up" the original values in case a user decides to cancel changes
        backupFields[0] = tfName.getText();
        backupFields[1] = tfImgPath.getText();
        backupFields[2] = tfSndPath.getText();
        enableFields();        
	}
	
	protected void cancelEdit() {
		if (backupFields[0] != null) tfName.setText(backupFields[0]);
		if (backupFields[1] != null) tfImgPath.setText(backupFields[1]);
		if (backupFields[2] != null) tfSndPath.setText(backupFields[2]);
	}
	
	protected void createPart() {
		String partName = tfName.getText();
		// creates the part
		viewPart(partName);		
	}
	
	protected void deletePart(String part) {
        int choice = JOptionPane.showConfirmDialog(null,
        		"Are you sure you want to delete this part type?\nNote: the action cannot be undone.",
                "Delete Part",
                JOptionPane.YES_NO_OPTION);
        if (choice == 0) ; // delete part
        // remove the part option from cbPart
        // view the next item in the list, or no item if there are no items in the list
	}
	
	protected void browse(String type) {
		if (type.equals("image")) {
			int choice = fc.showOpenDialog(btnBrowseImg);
	        if (choice == JFileChooser.APPROVE_OPTION) {
	        	File file = fc.getSelectedFile();
	        	tfImgPath.setText(file.getCanonicalPath());
	        }
		} else if (type.equals("sound")) { 
			int choice = fc.showOpenDialog(btnBrowseSnd);
			if (choice == JFileChooser.APPROVE_OPTION) {
	        	File file = fc.getSelectedFile();
	        	tfSndPath.setText(file.getCanonicalPath());
	        }
		}
	}
	
	protected void clearFields() {
		tfName.setText("");
		tfImgPath.setText("");
		tfSndPath.setText("");
	}
	
	protected void toggleFields() {
		if (tfName.isEnabled()) tfName.setEnabled(false);
		else tfName.setEnabled(true);
		if (tfImgPath.isEnabled()) tfImgPath.setEnabled(false);
		else tfImgPath.setEnabled(true);
		if (tfSndPath.isEnabled()) tfSndPath.setEnabled(false);
		else tfSndPath.setEnabled(true);
	}
	protected void enableFields() {
		if (tfName.isEnabled()) toggleFields();
	}
	protected void disableFields() {
		if (!tfName.isEnabled()) toggleFields();
	}
}
