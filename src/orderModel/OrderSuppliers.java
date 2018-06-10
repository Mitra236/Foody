package orderModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import loadFiles.LoadF;
import order.Order;
import user.Users;

public class OrderSuppliers extends JFrame{
	private static final long serialVersionUID = 1L;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	private JTable articlesTable;
	
	private LoadF entities;
	private Users user;
	public static ArrayList<Order> orderSupplier = new ArrayList<Order>();
	
	public OrderSuppliers(LoadF entities, Users user) {
		this.entities = entities;
		this.user = user;
		orderSupplier.removeAll(orderSupplier);
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
		
		
		for(Order o: entities.getOrder()) {
				if(o.getSupplier() != null && o.getSupplier() == user) {
					orderSupplier.add(o);
				}
		}
		
		int countOrders = orderSupplier.size();
		String[] header = new String[] {"Poruceni artikli", "Restoran", "Datum", "Kupac", "Dostavljac", "Status", "Cena"};
		Object[][] content = new Object[countOrders][header.length];
		
		for(int i = 0; i < orderSupplier.size(); i++) {
			Order order = orderSupplier.get(i);
			
				content[i][0] = order.forWrite();
				content[i][1] = order.getRest().getRestName();
				content[i][2] = order.getDate();
				content[i][3] = order.getBuyer().getUsername();
				String supplier = "Dostavljac nije dodjeljen";
				if (order.getSupplier() != null) supplier = user.getUsername();
				content[i][4] = supplier;
				content[i][5] = order.getStatus();
				content[i][6] = order.getPrice();
			
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		articlesTable = new JTable(model);
		articlesTable.setRowSelectionAllowed(true);
		articlesTable.setColumnSelectionAllowed(false);
		articlesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		articlesTable.setSelectionBackground(Color.decode("#ffcc66"));
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
					}else if(!e.getValueIsAdjusting() && status == EnumStatus.dostava) {
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
					}else if(!e.getValueIsAdjusting() && status == EnumStatus.dostava) {
						btnEdit.setEnabled(true);
					}
				}
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int selectedRow = articlesTable.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					Order o = orderSupplier.get(selectedRow);
					if(o != null) {
						int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da dostavite porudzbinu?",
																	"Dodela porudzbine", JOptionPane.YES_NO_OPTION);
						if(choice == JOptionPane.YES_OPTION) {
							Order o1 = new Order(o.getOrderedDish(), o.getRest(), o.getDate(), o.getBuyer(),
									o.getSupplier(), EnumStatus.dostavljena, o.getPrice());
								entities.getOrder().remove(o);
								entities.getOrder().add(o1);
							
								entities.writeOrders();
								
							
								for(int p = 0; p < entities.getPoints().size(); p++) {
									if(entities.getPoints().get(p).getBuyer().getUsername().equals(o.getBuyer().getUsername())){
										if(o.getPrice() > 500 && entities.getPoints().get(p).getPoints() < 10) {
											entities.getPoints().get(p).setPoints(entities.getPoints().get(p).getPoints() + 1);
											break;
										}
							
									}
									
								}	
								entities.writePoints();
						}
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
					EnumStatus status = (EnumStatus)articlesTable.getValueAt(selectedRow, 5);
					Order o = orderSupplier.get(selectedRow);
					if(status == EnumStatus.otkazana) {
						btnDelete.setEnabled(false);
					}else if(o != null) {
						int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da odbijete porudzbinu",
																	"Otkazivanje porudzbine", JOptionPane.YES_NO_OPTION);
						if(choice == JOptionPane.YES_OPTION) {
								Order o1 = new Order(o.getOrderedDish(), o.getRest(), o.getDate(), o.getBuyer(),
									o.getSupplier(), EnumStatus.odbijena, o.getPrice());
									entities.getOrder().remove(o);
									entities.getOrder().add(o1);
									
									entities.writeOrders();
						}
					}
				}
			}
		});
			
			
		
	}
}