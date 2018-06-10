package appTableModel;

import java.awt.BorderLayout;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import guiChange.AdminChange;
import employees.Administrator;
import loadFiles.LoadF;

public class AdminTableModel extends JFrame{

	private static final long serialVersionUID = 1L;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnAdd = new JButton();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	private JTable tableAdmins;
	
	private LoadF entities;
	
	public AdminTableModel(LoadF entities) {
		this.entities = entities;
		setTitle("Prikaz administratora");
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		initGui();
		initActions();
	}

	private void initGui() {
		
		ImageIcon addIcon = new ImageIcon("src/projectImages/add.png");
		btnAdd.setIcon(addIcon);
		mainToolbar.add(btnAdd);
		
		ImageIcon editIcon = new ImageIcon("src/projectImages/edit.png");
		btnEdit.setIcon(editIcon);
		mainToolbar.add(btnEdit);
		
		ImageIcon deleteIcon = new ImageIcon("src/projectImages/delete.png");
		btnDelete.setIcon(deleteIcon);
		mainToolbar.add(btnDelete);
		
		add(mainToolbar, BorderLayout.NORTH);
		
		String [] header = new String[] {"Ime", "Prezime", "Pol", "Korisnicko ime", "Lozinka", "JMBG", "Plata"};
		int count = entities.getAdmin().size();
		Object [][] content = new Object[count][header.length];
		
		for(int i = 0; i < entities.getAdmin().size(); i++) {
			Administrator admin = entities.getAdmin().get(i);
			content [i][0] = admin.getName();
			content [i][1] = admin.getSurname();
			content [i][2] = admin.getGender();
			content [i][3] = admin.getUsername();
			content [i][4] = admin.getPassword();
			content [i][5] = admin.getJMBG();
			content [i][6] = admin.getSalary();
			
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tableAdmins = new JTable(model);
		tableAdmins.setRowSelectionAllowed(true);
		tableAdmins.setColumnSelectionAllowed(false);
		tableAdmins.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAdmins.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tableAdmins);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	private void initActions() {
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminChange ach = new AdminChange(entities, null);
				ach.setVisible(true);
				
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableAdmins.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "Morate izbrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String username = tableAdmins.getValueAt(row, 3).toString();
					Administrator a = entities.aName(username);
					if(a != null) {
						AdminChange ach = new AdminChange(entities, a);
						ach.setVisible(true);
					}
				}
				
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableAdmins.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String name = (String)tableAdmins.getValueAt(selectedRow, 3);
					Administrator b = entities.aName(name);
					if(b != null) {
						int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete kupca",
																	"Brisanje", JOptionPane.YES_NO_OPTION);
						if(choice == JOptionPane.YES_OPTION) {
							entities.getAdmin().remove(b);
							DefaultTableModel model = (DefaultTableModel)tableAdmins.getModel();
							model.removeRow(selectedRow);
							entities.writeAdmins();
						}
					}
				}
				
			}
		});
	}
}