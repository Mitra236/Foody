package orderModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import articles.Article;
import articles.Dish;
import articles.Drink;
import buyers.Buyer;
import employees.Supplier;
import enumeration.EnumStatus;
import loadFiles.LoadF;
import net.miginfocom.swing.MigLayout;
import order.Order;
import points.Points;
import restaurants.Restaurants;
import user.Users;

public class OrderBuyer extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTable articlesTable;
	private JLabel lblDish = new JLabel("Izabrano jelo");
	private JTextField txtDish = new JTextField(20);
	private JLabel lblDrink = new JLabel("Izabrano pice");
	private JTextField txtDrink = new JTextField(20);
	private JLabel lblRest = new JLabel("Restoran");
	private JTextField txtRest = new JTextField(20);
	private JLabel lblDate = new JLabel("Datum");
	private JTextField txtDate = new JTextField(20);
	private JLabel lblBuyer = new JLabel("Kupac");
	private JTextField txtBuyer = new JTextField(20);
	private JLabel lblSupplier = new JLabel("Dostavljac");
	private JTextField txtSupplier = new JTextField(20);
	private JLabel lblStatus = new JLabel("Status");
	private JComboBox<EnumStatus> cbStatus = new JComboBox<EnumStatus>(EnumStatus.values());
	private JLabel lblPoints = new JLabel("Bodovi");
	private JComboBox<String> cbPoints = new JComboBox<String>();
	private JLabel lblPrice = new JLabel("Cena");
	private JTextField txtPrice = new JTextField(20);
	private JButton btnDish = new JButton("Poruci Jelo");
	private JButton btnRemove = new JButton("Ukloni jelo");
	private JButton btnDrink = new JButton("Poruci Pice");
	private JButton btnRem = new JButton("Ukloni pice");
	private JButton btnCheckPoints = new JButton("Proveri poene");
	private JButton btnOK = new JButton("Sacuvaj");
	private JButton btnCancel = new JButton("Otkazi");
	private static ArrayList<Article> orderedArticle = new ArrayList<Article>();
	private static ArrayList<Article> availableArticle = new ArrayList<Article>();
	
	private LoadF entities;
	private Order order;
	private Restaurants rest;
	private Users user;
	public static double startPrice = 200;
	
	public OrderBuyer(LoadF entities, Order order, Restaurants rest, Users user) throws ParseException {
		this.entities = entities;
		this.order = order;
		this.rest = rest;
		this.user = user;
		orderedArticle.removeAll(orderedArticle);
		setTitle("Artikli");
		setSize(1000, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		initGui();
		initActions();
		showDate();
		enableFields();
		orderConfirmed();
	}

	

	private void initGui() {
		
		ArrayList<Dish> dishRest = new ArrayList<Dish>();
		ArrayList<Drink> drinkRest = new ArrayList<Drink>();
		
		for(Dish d: entities.getDish()) {
			if(d.getRestaurantName().getRestName().equals(rest.getRestName())){
				dishRest.add(d);
				availableArticle.add(d);
			}
		}
		
		for(Drink dr: entities.getDrink()) {
			if(dr.getRestaurantName().getRestName().equals(rest.getRestName())){
				drinkRest.add(dr);
				availableArticle.add(dr);
			}
		}
		
		
		
		int countArticles = dishRest.size() + drinkRest.size();
		String[] header = new String[] {"Tip", "Restoran", "Naziv artikla", "Cena", "Opis", "Kolicina"};
		Object[][] content = new Object[countArticles][header.length];
		
		for(int i = 0; i < dishRest.size(); i++) {
			Dish dish = dishRest.get(i);
			if(dish.getRestaurantName().getRestName().equals(rest.getRestName())){
				content[i][0] = dish.getArticle();
				content[i][1] = dish.getRestaurantName().getRestName();
				content[i][2] = dish.getArticleName();
				content[i][3] = dish.getPrice();
				content[i][4] = dish.getDescription();
				content[i][5] = dish.getAmount();
				
			}
		}
		
		for(int i = 0; i < drinkRest.size(); i++) {
			Drink drink = drinkRest.get(i);
			if(drink.getRestaurantName().getRestName().equals(rest.getRestName())) {
				int row = drinkRest.size() + i;
				content[row][0] = drink.getArticle();
				content[row][1] = drink.getRestaurantName().getRestName();
				content[row][2] = drink.getArticleName();
				content[row][3] = drink.getPrice();
				content[row][4] = drink.getDescription();
				content[row][5] = drink.getAmount();
			}
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
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setBackground(Color.decode("#e6ffcc"));
		
		MigLayout layout = new MigLayout("wrap 2");
		panel.setLayout(layout);
		
		
		panel.add(lblDish);
		panel.add(txtDish);
		panel.add(new JLabel());
		panel.add(btnDish, "split 2");
		panel.add(btnRemove);
		panel.add(lblDrink);
		panel.add(txtDrink);
		panel.add(new JLabel());
		panel.add(btnDrink, "split 2");
		panel.add(btnRem);
		panel.add(lblRest);
		panel.add(txtRest);
		panel.add(lblDate);
		panel.add(txtDate);
		panel.add(lblBuyer);
		panel.add(txtBuyer);
		panel.add(lblSupplier);
		panel.add(txtSupplier);
		panel.add(lblStatus);
		panel.add(cbStatus);
		panel.add(lblPoints);
		panel.add(cbPoints, "split 2");
		panel.add(btnCheckPoints);
		panel.add(lblPrice);
		panel.add(txtPrice);
		panel.add(new JLabel());
		panel.add(btnOK, "split 2");
		panel.add(btnCancel);
		
	}
	private void initActions() {
		txtSupplier.setText("prazno");
		txtBuyer.setText(this.user.getUsername());
		
		btnCheckPoints.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Points p: entities.getPoints()) {
					int sum = 0;
					if(p.getBuyer().getUsername().equals(txtBuyer.getText().trim())) {
						sum += p.getPoints();
						for(int i = 0; i <= sum; i++) {
							cbPoints.addItem(Integer.toString(i));
						}
					}
				}
			}
		});
		
		
		
		articlesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						
			DefaultTableModel model = (DefaultTableModel)articlesTable.getModel();
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = articlesTable.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String type = (String)articlesTable.getValueAt(selectedRow, 0).toString();
					if(type.equals("Jelo") && !e.getValueIsAdjusting()) {
		        	  txtDish.setText(model.getValueAt(articlesTable.getSelectedRow(), 2).toString());
		          }  
					}
			}
		});
		
		btnDish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validation() == true) {
					String dishString = txtDish.getText().trim();
					Dish dish = (Dish) entities.gArticle(dishString);
					orderedArticle.add(dish);
				}
			}
		});
		
		btnRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String dishString = txtDish.getText().trim();
				Dish dish = (Dish) entities.gArticle(dishString);
				orderedArticle.remove(dish);
			}
		});
		
		articlesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			DefaultTableModel model = (DefaultTableModel)articlesTable.getModel();
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = articlesTable.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String type = (String)articlesTable.getValueAt(selectedRow, 0).toString();
					if(type.equals("Pice") && !e.getValueIsAdjusting()) {
		        	  txtDrink.setText(model.getValueAt(articlesTable.getSelectedRow(), 2).toString());
		          }  
					}
			}
		});
		
		btnDrink.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validation() == true) {
					String drinkString = txtDrink.getText().trim();
					Drink drink = (Drink) entities.gName(drinkString);
					orderedArticle.add(drink);
				}
			}
		});
		
		btnRem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String drinkString = txtDrink.getText().trim();
				Drink drink = (Drink) entities.gName(drinkString);
				orderedArticle.remove(drink);
				
			}
		});
		
		

		articlesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			DefaultTableModel model = (DefaultTableModel)articlesTable.getModel();
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
		          if(!e.getValueIsAdjusting()) {
		        	  txtRest.setText(model.getValueAt(articlesTable.getSelectedRow(), 1).toString());
		          }  	   
			}
		});
		
		cbStatus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(entities.sName(txtSupplier.getText().toString()) == null) {
					cbStatus.setSelectedItem(EnumStatus.porucena);
				}
			}
		});
		
		cbPoints.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				getSum(Integer.parseInt((String) cbPoints.getSelectedItem()));
				
				
			}
		});
		
	}
	
	private double getSum(int points) {
		double startPrice = 0;
		double discount = 0;
		for(Article a: orderedArticle) {
			startPrice += a.getPrice() + 200;
			
		}
		double discountPercent = points * 3;
		discount =  startPrice * (discountPercent / 100);
		double finalPrice = (int) (startPrice - discount);
		txtPrice.setText(Double.toString(finalPrice));
		return startPrice;
	}
	
	
	
	private void showDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		GregorianCalendar date = new GregorianCalendar();
		txtDate.setText(format.format(date.getTime()));
		
	}
	
			
			
	private void enableFields() {
		txtDish.setEditable(false);
		txtDrink.setEditable(false);
		txtRest.setEditable(false);
		txtDate.setEditable(false);
		txtBuyer.setEditable(false);
		txtSupplier.setEditable(false);
		cbStatus.setEnabled(false);
		txtPrice.setEditable(false);
	}
	
	private boolean validation() {
		boolean ok = true;
		String message = "Greska u unosu:\n";
		if(txtDish.getText().trim().equals("")) {
			ok = false;
			message += "- Jelo ne sme biti prazno polje:\n";
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, message, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}
	
	private void orderConfirmed() throws ParseException {
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validation() == true) {
					ImageIcon icon = new ImageIcon("src/projectImages/spongebob.gif");
					JOptionPane.showMessageDialog(null, "Uspesna porudzbina", "Uspesna porudzbina", JOptionPane.INFORMATION_MESSAGE, icon);
					String reststring = txtRest.getText().trim();
					Restaurants resto = entities.rName(reststring);
					String dateString = txtDate.getText().trim();
					SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
					Date date = null;
					try {
						date = (Date)format.parse(dateString);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					String supplierString = txtSupplier.getText().trim();
					Supplier supplier = entities.sName(supplierString);
					EnumStatus status = (EnumStatus)cbStatus.getSelectedItem();
					double price = Double.parseDouble(txtPrice.getText().trim());
					if(order == null) {
						Order o = new Order(orderedArticle, rest, date, (Buyer) user, supplier, status, price);
						entities.getOrder().add(o);
					}else if(order != null) {
						order.setOrderedDish(orderedArticle);
						order.setRest(resto);
						order.setDate(date);
						order.setBuyer((Buyer) user);
						order.setSupplier(supplier);
						order.setStatus(status);
						order.setPrice(price);
						
					}
					entities.writeOrders();
					
					for(int p = 0; p < entities.getPoints().size(); p++) {
						int pointsLeft = 0;
						int points = Integer.valueOf((String)cbPoints.getSelectedItem());
						if(entities.getPoints().get(p).getBuyer().getUsername().equals(user.getUsername())) {
							pointsLeft = entities.getPoints().get(p).getPoints() - points;
							entities.getPoints().get(p).setPoints(pointsLeft);;
							break;
					
						}
					}
					availableArticle.removeAll(availableArticle);
					entities.writePoints();
					OrderBuyer.this.dispose();
					OrderBuyer.this.setVisible(false);
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OrderBuyer.this.dispose();
				OrderBuyer.this.setVisible(false);
				
			}
		});
	
	}	

}
