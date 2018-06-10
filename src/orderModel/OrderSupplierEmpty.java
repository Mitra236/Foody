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

import employees.Supplier;
import enumeration.EnumStatus;
import loadFiles.LoadF;
import order.Order;
import user.Users;

public class OrderSupplierEmpty extends JFrame{
	private static final long serialVersionUID = 1L;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnEdit = new JButton();
	private JTable orderTable;
	
	private LoadF entities;
	private Users user;
	private Order order;
	public static ArrayList<Order> emptyOrder = new ArrayList<Order>();
	
	public OrderSupplierEmpty(LoadF entities, Users user, Order order) {
		this.entities = entities;
		this.user = user;
		this.order = order;
		emptyOrder.removeAll(emptyOrder);
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
		ImageIcon editIcon = new ImageIcon("src/projectImages/edit.png");
		btnEdit.setIcon(editIcon);
		mainToolbar.add(btnEdit);
		
		add(mainToolbar, BorderLayout.NORTH);
		for(Order o: entities.getOrder()) {
			if(o.getStatus().equals(EnumStatus.porucena)) {
				emptyOrder.add(o);
			}
		}
		
		
		int countOrders = emptyOrder.size();
		String[] header = new String[] {"Poruceni artikli", "Restoran", "Datum", "Kupac", "Dostavljac", "Status", "Cena"};
		Object[][] content = new Object[countOrders][header.length];
		
		for(int i = 0; i < emptyOrder.size(); i++) {
			Order order = emptyOrder.get(i);
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
		orderTable.setSelectionBackground(Color.decode("#ffcc66"));
		orderTable.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(orderTable);
		add(scrollPane, BorderLayout.CENTER);
		
		
		orderTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = orderTable.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					EnumStatus status = (EnumStatus)orderTable.getValueAt(selectedRow, 5);
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
		
		
		
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = orderTable.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					Order o = emptyOrder.get(selectedRow);
					Supplier supplier = entities.sName(user.getUsername());
					if(o != null) {
						int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da dodelite porudzbinu?",
																	"Dodela", JOptionPane.YES_NO_OPTION);
						if(choice == JOptionPane.YES_OPTION) {
							Order o1 = new Order(o.getOrderedDish(), o.getRest(), o.getDate(), o.getBuyer(),
									supplier, EnumStatus.dostava, o.getPrice());
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