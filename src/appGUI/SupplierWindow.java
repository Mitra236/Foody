package appGUI;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import employees.Supplier;
import loadFiles.LoadF;
import order.Order;
import orderModel.OrderSupplierEmpty;
import orderModel.OrderSuppliers;
import user.Users;

public class SupplierWindow extends JFrame {

private static final long serialVersionUID = 1L;
	
	private JMenuBar mainMenu;
	private JMenu orders;
	private JMenuItem ordersItem;
	private JMenuItem takeOrdersItem;
	
	private LoadF entities;
	private Users user;
	private Order order;
	
	public SupplierWindow(Order order, LoadF entities, Users user) throws ParseException {
		this.order = order;
		this.entities = entities;
		this.user = user;
		setTitle("Meni kupac-" + user.getUsername());
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		initGui();
		initActions();
		
	}

	private void initActions() {
		ordersItem.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
							OrderSuppliers ors = new OrderSuppliers(entities, user);
							ors.setVisible(true);
						
					}
				});
		
		takeOrdersItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OrderSupplierEmpty os = new OrderSupplierEmpty(entities, user, null);
				os.setVisible(true);
				
			}
		});
		
	}

	private void initGui() {
		getContentPane().setBackground(Color.decode("#e6ffcc"));
		this.mainMenu = new JMenuBar();
		this.orders = new JMenu("Poruzbina");
		this.ordersItem = new JMenuItem("Prikazi porudzbine");
		this.takeOrdersItem = new JMenuItem("Preuzmi porudzbine");
		
		this.orders.add(ordersItem);
		this.orders.add(takeOrdersItem);
		
		this.mainMenu.add(orders);
		setJMenuBar(mainMenu);

		
	}
}
