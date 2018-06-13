package appTableModel;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import appGUI.SelectRestaurant;
import guiChange.OrderChange;
import loadFiles.LoadF;
import order.Order;
import user.Users;


public class OrderTableModel extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnAdd = new JButton();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	private JTable orderTable;
	
	private LoadF entities;
	private Users user;
	
	public OrderTableModel(LoadF entities, Users user) {
		this.entities = entities;
		this.user = user;
		setTitle("Porudzbine");
		setSize(1300, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		initGui();
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
		
		int countOrders = entities.getOrder().size();
		String[] header = new String[] {"Poruceni artikli", "Restoran", "Datum", "Kupac", "Dostavljac", "Status", "Cena"};
		Object[][] content = new Object[countOrders][header.length];
		
		for(int i = 0; i < entities.getOrder().size(); i++) {
			Order order = entities.getOrder().get(i);
				content[i][0] = order.forWrite();
				content[i][1] = order.getRest().getRestName();
				content[i][2] = order.getDate();
				content[i][3] = order.getBuyer().getUsername();
				String supplier = "Dostavljac nije dodjeljen";
				if (order.getSupplier() != null) supplier = order.getSupplier().getUsername();
				content[i][4] = supplier;
				content[i][5] = order.getStatus();
				content[i][6] = order.getPrice();
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		orderTable = new JTable(model);
		orderTable.setRowSelectionAllowed(true);
		orderTable.setColumnSelectionAllowed(false);
		orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		orderTable.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(orderTable);
		add(scrollPane, BorderLayout.CENTER);
		
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectRestaurant sl = new SelectRestaurant(entities, user);
				sl.setVisible(true);
				OrderTableModel.this.dispose();
				OrderTableModel.this.setVisible(false);
				
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = orderTable.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "Morate izbrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
			//		String userName = orderTable.getValueAt(row, 3).toString();
					Order a = entities.getOrder().get(row);
					if(a != null) {
						OrderChange och = new OrderChange(entities, a);					
						och.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani artikal", "Greska", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = orderTable.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String name = (String)orderTable.getValueAt(selectedRow, 3);
					Order b = entities.gOrder(name);
					if(b != null) {
						int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete kupca",
																	"Brisanje", JOptionPane.YES_NO_OPTION);
						if(choice == JOptionPane.YES_OPTION) {
							entities.getOrder().remove(b);
							DefaultTableModel model = (DefaultTableModel)orderTable.getModel();
							model.removeRow(selectedRow);
							entities.writeOrders();
						}
					}
				}
				
			}
		});
		
	}
	

}
