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

import guiChange.SupplierChange;
import employees.Supplier;
import loadFiles.LoadF;

public class SupplierTableModel extends JFrame{

	private static final long serialVersionUID = 1L;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnAdd = new JButton();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	private JTable tableSuppliers;
	
	private LoadF entities;
	
	
	public SupplierTableModel(LoadF entities) {
		this.entities = entities;
		setTitle("Prikaz dostavljaca");
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		setResizable(false);
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
		
		String [] header = new String[] {"Ime", "Prezime", "Pol", "Korisnicko ime", "Lozinka", "Adresa", 
											"Broj telefona", "Registarska oznaka", "Tip vozila"};
		int count = entities.getSupplier().size();
		Object [][] content = new Object[count][header.length];
		
		for(int i = 0; i < entities.getSupplier().size(); i++) {
			Supplier supplier = entities.getSupplier().get(i);
			content [i][0] = supplier.getName();
			content [i][1] = supplier.getSurname();
			content [i][2] = supplier.getGender();
			content [i][3] = supplier.getUsername();
			content [i][4] = supplier.getPassword();
			content [i][5] = supplier.getJMBG();
			content [i][6] = supplier.getSalary();
			content [i][7] = supplier.getDriversLicence();
			content [i][8] = supplier.getVehicleType();
			
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tableSuppliers = new JTable(model);
		tableSuppliers.setRowSelectionAllowed(true);
		tableSuppliers.setColumnSelectionAllowed(false);
		tableSuppliers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableSuppliers.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tableSuppliers);
		add(scrollPane, BorderLayout.CENTER);
	

	}
	
	private void initActions() {
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SupplierChange spc = new SupplierChange(entities, null);
				spc.setVisible(true);
				
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableSuppliers.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "Morate izbrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String username = tableSuppliers.getValueAt(row, 3).toString();
					Supplier s = entities.sName(username);
					if(s != null) {
						SupplierChange sch = new SupplierChange(entities, s);
						sch.setVisible(true);
					}
				}
			}		
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableSuppliers.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String name = (String)tableSuppliers.getValueAt(selectedRow, 3);
					Supplier b = entities.sName(name);
					if(b != null) {
						int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete kupca",
																	"Brisanje", JOptionPane.YES_NO_OPTION);
						if(choice == JOptionPane.YES_OPTION) {
							entities.getSupplier().remove(b);
							DefaultTableModel model = (DefaultTableModel)tableSuppliers.getModel();
							model.removeRow(selectedRow);
							entities.writeSuppliers();
						}
					}
				}
						
			}
		});
				
	}
}

