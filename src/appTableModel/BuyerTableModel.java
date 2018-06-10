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

import guiChange.BuyerChange;
import buyers.Buyer;
import loadFiles.LoadF;

public class BuyerTableModel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnAdd = new JButton();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	private JTable tableBuyers;
	
	private LoadF entities;
	
	public BuyerTableModel(LoadF entities) {
		this.entities = entities;
		setTitle("Prikaz prodavaca");
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
		
		String [] header = new String[] {"Ime", "Prezime", "Pol", "Korisnicko ime", "Lozinka", "Adresa", "Broj telefona"};
		int count = entities.getBuyer().size();
		Object [][] content = new Object[count][header.length];
		
		for(int i = 0; i < entities.getBuyer().size(); i++) {
			Buyer buyer = entities.getBuyer().get(i);
			content [i][0] = buyer.getName();
			content [i][1] = buyer.getSurname();
			content [i][2] = buyer.getGender();
			content [i][3] = buyer.getUsername();
			content [i][4] = buyer.getPassword();
			content [i][5] = buyer.getAddress();
			content [i][6] = buyer.getPhoneNum();
			
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tableBuyers = new JTable(model);
		tableBuyers.setRowSelectionAllowed(true);
		tableBuyers.setColumnSelectionAllowed(false);
		tableBuyers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableBuyers.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tableBuyers);
		add(scrollPane, BorderLayout.CENTER);
		
	}
	
	private void initActions() {
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BuyerChange bc = new BuyerChange(entities, null);
				bc.setVisible(true);
			}

		});
		
		
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableBuyers.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "Morate izbarati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String username = tableBuyers.getValueAt(row, 3).toString();
					Buyer b = entities.bName(username);
					if(b != null) {
						BuyerChange bch = new BuyerChange(entities, b);
						bch.setVisible(true);
					}
				}
				
			}
		});
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int selectedRow = tableBuyers.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String name = (String)tableBuyers.getValueAt(selectedRow, 3);
					Buyer b = entities.bName(name);
					if(b != null) {
						int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete kupca",
																	"Brisanje", JOptionPane.YES_NO_OPTION);
						if(choice == JOptionPane.YES_OPTION) {
							entities.getBuyer().remove(b);
							DefaultTableModel model = (DefaultTableModel)tableBuyers.getModel();
							model.removeRow(selectedRow);
							entities.writeBuyers();
							
						}
					}
				}
			}
			
		});
		
	}
	
	
}
