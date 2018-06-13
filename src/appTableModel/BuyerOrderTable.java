package appTableModel;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import enumeration.EnumStatus;
import guiChange.BuyerOrderChange;
import loadFiles.LoadF;
import order.Order;
import orderModel.OrderBuyer;
import user.Users;

public class BuyerOrderTable extends JFrame {

	private static final long serialVersionUID = 1L;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	private JTable articlesTable;
	
	private LoadF entities;
	private Users user;
	public static ArrayList<Order> orderBuyer = new ArrayList<Order>();
	
	public BuyerOrderTable(LoadF entities, Users user) {
		this.entities = entities;
		this.user = user;
		orderBuyer.removeAll(orderBuyer);
		setTitle("Porudzbine");
		setSize(1300, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		initGui();
	}

	private void initGui() {
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);
		
		ImageIcon editIcon = new ImageIcon("src/projectImages/edit.png");
		btnEdit.setIcon(editIcon);
		mainToolbar.add(btnEdit);
		
		ImageIcon deleteIcon = new ImageIcon("src/projectImages/delete.png");
		btnDelete.setIcon(deleteIcon);
		mainToolbar.add(btnDelete);
		
		add(mainToolbar, BorderLayout.NORTH);
		
		for(Order ord: entities.getOrder()) {
			if(ord.getBuyer().getUsername().equals(user.getUsername())) {
				orderBuyer.add(ord);
			}
		}
		
		int countOrders = orderBuyer.size();
		String[] header = new String[] {"Poruceni artikli", "Restoran", "Datum", "Kupac", "Dostavljac", "Status", "Cena"};
		Object[][] content = new Object[countOrders][header.length];
		
		for(int i = 0; i < orderBuyer.size(); i++) {
			Order order = orderBuyer.get(i);
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
		articlesTable = new JTable(model);
		articlesTable.setRowSelectionAllowed(true);
		articlesTable.setColumnSelectionAllowed(false);
		articlesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		articlesTable.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(articlesTable);
		add(scrollPane, BorderLayout.CENTER);
		
		articlesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = articlesTable.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					EnumStatus status = (EnumStatus)articlesTable.getValueAt(selectedRow, 5);
					if(!e.getValueIsAdjusting() && status == EnumStatus.otkazana) {
						btnDelete.setEnabled(false);
					}else if(!e.getValueIsAdjusting() && status == EnumStatus.dostavljena) {
						btnDelete.setEnabled(false);
					}else if(!e.getValueIsAdjusting() && status == EnumStatus.odbijena) {
						btnDelete.setEnabled(false);
					}else if(!e.getValueIsAdjusting() && status == EnumStatus.porucena) {
						btnDelete.setEnabled(true);
					}
				}
			}
		});
		
		articlesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = articlesTable.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					EnumStatus status = (EnumStatus)articlesTable.getValueAt(selectedRow, 5);
					if(!e.getValueIsAdjusting() && status == EnumStatus.otkazana) {
						btnEdit.setEnabled(false);
					}else if(!e.getValueIsAdjusting() && status == EnumStatus.dostavljena) {
						btnEdit.setEnabled(false);
					}else if(!e.getValueIsAdjusting() && status == EnumStatus.odbijena) {
						btnEdit.setEnabled(false);
					}else if(!e.getValueIsAdjusting() && status == EnumStatus.porucena) {
						btnEdit.setEnabled(true);
					}
				}
			}
		});
		
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = articlesTable.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					Order o = orderBuyer.get(selectedRow);
					 if(o != null ) {
						int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da otkazete porudzbinu?",
																	"Otkazivanje", JOptionPane.YES_NO_OPTION);
						if(choice == JOptionPane.YES_OPTION) {
							Order o1 = new Order(o.getOrderedDish(), o.getRest(), o.getDate(), o.getBuyer(),
									o.getSupplier(), EnumStatus.otkazana, o.getPrice());
							entities.getOrder().remove(o);
							entities.getOrder().add(o1);
							entities.writeOrders();
							}
					
								
						}
					}
				}
				
			
		});
		
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = articlesTable.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "Morate izbrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					Order a = orderBuyer.get(row);
					if(a != null) {
						BuyerOrderChange bch = new BuyerOrderChange(entities, a, user);
						bch.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani artikal", "Greska", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
	}
	
	

}
