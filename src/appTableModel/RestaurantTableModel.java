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

import guiChange.RestaurantChange;
import loadFiles.LoadF;
import restaurants.Restaurants;

public class RestaurantTableModel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnAdd = new JButton();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	private JTable tableRestaurants;
	
	private LoadF entities;
	
	public RestaurantTableModel(LoadF entities) {
		this.entities = entities;
		setTitle("Prikaz restorana");
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectData/hamburger.png"));
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
		
		String[] header = new String[] {"Naziv", "Adresa", "Kategorija"};
		int count = entities.getRest().size();
		Object[][] content = new Object[count][header.length];
		
		for(int i = 0; i < entities.getRest().size(); i++) {
			Restaurants rest = entities.getRest().get(i);
			content [i][0] = rest.getRestName();
			content [i][1] = rest.getRestAddress();
			content [i][2] = rest.getCategory();
			
			
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tableRestaurants = new JTable(model);
		tableRestaurants.setRowSelectionAllowed(true);
		tableRestaurants.setColumnSelectionAllowed(false);
		tableRestaurants.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableRestaurants.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tableRestaurants);
		add(scrollPane, BorderLayout.CENTER);
		
	}
	
	private void initActions() {
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RestaurantChange rsc = new RestaurantChange(entities, null);
				rsc.setVisible(true);
				
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableRestaurants.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "Morate izbrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String name = tableRestaurants.getValueAt(row, 0).toString();
					Restaurants r = entities.rName(name);
					if(r != null) {
						RestaurantChange rch = new RestaurantChange(entities, r);
						rch.setVisible(true);
					}
				}
				
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableRestaurants.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String name = (String)tableRestaurants.getValueAt(selectedRow, 0);
					Restaurants b = entities.rName(name);
					if(b != null) {
						int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete kupca",
																	"Brisanje", JOptionPane.YES_NO_OPTION);
						if(choice == JOptionPane.YES_OPTION) {
							entities.getRest().remove(b);
							DefaultTableModel model = (DefaultTableModel)tableRestaurants.getModel();
							model.removeRow(selectedRow);
							entities.writeRestaurants();
							
						}
					}
				}
				
			}
		});
	}

	
}
