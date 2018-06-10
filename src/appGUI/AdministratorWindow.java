package appGUI;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import appTableModel.AdminTableModel;
import appTableModel.ArticleTableModel;
import appTableModel.BuyerTableModel;
import appTableModel.OrderTableModel;
import appTableModel.RestaurantTableModel;
import appTableModel.SupplierTableModel;
import loadFiles.LoadF;
import user.Users;

public class AdministratorWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private JMenuBar mainMenu;
	private JMenu entitiesMenu;
	private JMenuItem buyerItem;
	private JMenuItem adminItem;
	private JMenuItem supplierItem;
	private JMenu orderMenu;
	private JMenuItem restaurantItem;
	private JMenuItem articlesItem;
	private JMenuItem orderItem;
	private JMenuItem order;
	
	private Users user;
	private LoadF entities;
	
	public AdministratorWindow(LoadF entities, Users user) {
		this.entities = entities;
		this.user = user;
		setTitle("Administrator meni - " + user.getUsername());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		initGui();
		initActions();
	}


	private void initGui() {
		getContentPane().setBackground(Color.decode("#e6ffcc"));
		
		this.mainMenu = new JMenuBar();
		this.entitiesMenu = new JMenu("Entiteti");
		this.buyerItem = new JMenuItem("Prikaz kupaca");
		this.adminItem = new JMenuItem("Prikaz administratora");
		this.supplierItem = new JMenuItem("Prikazi dostavljaca");
		this.orderMenu = new JMenu("Porucivanje");
		this.restaurantItem = new JMenuItem("Restorani");
		this.articlesItem = new JMenuItem("Artikli");
		this.orderItem = new JMenuItem("Porudzbina");
		this.order = new JMenuItem("Poruci");
		
		this.entitiesMenu.add(buyerItem);
		this.entitiesMenu.add(adminItem);
		this.entitiesMenu.add(supplierItem);
		this.orderMenu.add(restaurantItem);
		this.orderMenu.add(articlesItem);
		this.orderMenu.add(orderItem);
		this.orderMenu.add(order);
		
		this.mainMenu.add(entitiesMenu);
		this.mainMenu.add(orderMenu);
		
		setJMenuBar(mainMenu);

		
	}
	
	private void initActions() {
			buyerItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					BuyerTableModel bt = new BuyerTableModel(entities);
					bt.setVisible(true);
					
				}
			});
			
			adminItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					AdminTableModel at = new AdminTableModel(entities);
					at.setVisible(true);
					
				}
			});
			
			supplierItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					SupplierTableModel st = new SupplierTableModel(entities);
					st.setVisible(true);
					
				}
			});
			
			restaurantItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					RestaurantTableModel rt = new RestaurantTableModel(entities);
					rt.setVisible(true);
					
				}
			});
			
			articlesItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ArticleTableModel art = new ArticleTableModel(entities);
					art.setVisible(true);
					
				}
			});
			
			orderItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					OrderTableModel ord = new OrderTableModel(entities, user);
					ord.setVisible(true);
					
				}
			});
			
			order.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					SelectRestaurant sl = new SelectRestaurant(entities, user);
					sl.setVisible(true);
					
				}
			});
			
	}
	
}
