package guiChange;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import articles.Article;
import articles.Dish;
import articles.Drink;
import buyers.Buyer;
import employees.Supplier;
import enumeration.EnumStatus;
import loadFiles.LoadF;
import net.miginfocom.swing.MigLayout;
import order.Order;
import restaurants.Restaurants;
import user.Users;

public class BuyerOrderChange extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblnewArticle = new JLabel("Izaberite novi artikal");
	private JComboBox<String> cbArticle = new JComboBox<String>();
	private JButton btnAdd = new JButton("Dodaj");
	private JLabel lblArticle = new JLabel("Poruceni artikal");
	private JTextField txtArticle = new JTextField(20);
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
	private JLabel lblPrice = new JLabel("Cena");
	private JTextField txtPrice = new JTextField(20);
	private JButton btnOK = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");
	private static ArrayList<Article> orderedArticle = new ArrayList<Article>();
	
	private LoadF entities;
	private Order order;
	private Users user;
	
	public BuyerOrderChange(LoadF entities, Order order, Users user) {
		this.entities = entities;
		this.order = order;
		this.user = user;
		//orderedArticle.removeAll(orderedArticle);
		setTitle("Izmena porudzbine");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		setResizable(false);
		initGui();
		initActions();
		enableFields();
		pack();
	}

	private void initGui() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		String suString = "prazno";
		if(this.order.getSupplier() != null) suString = this.order.getSupplier().getUsername();
		
		if(this.order != null) {
			txtArticle.setText(this.order.forWrite());
			txtRest.setText(this.order.getRest().getRestName());
			txtDate.setText(this.order.getDate().toString());
			txtBuyer.setText(this.order.getBuyer().getUsername());
			txtSupplier.setText(suString);
			cbStatus.setSelectedItem(this.order.getStatus());
			txtPrice.setText(String.valueOf(this.order.getPrice()));

		}
		
		for(Order o: entities.getOrder()) {
			if(o.getOrderedDish() == order.getOrderedDish()) {
				orderedArticle.addAll(o.getOrderedDish());
				orderedArticle.removeAll(orderedArticle);
			}
		}
		
		String restString = txtRest.getText().trim();
		Restaurants rest = entities.rName(restString);
		
		for(Dish s: entities.getDish()) {
			if(s.getRestaurantName().getRestName().equals(rest.getRestName())) {
				cbArticle.addItem(s.getArticleName());
			}
		}
		for(Drink dr: entities.getDrink()) {
			if(dr.getRestaurantName().getRestName().equals(rest.getRestName())) {
				cbArticle.addItem(dr.getArticleName());
			}
		}
		
		
		
		add(lblnewArticle);
		add(cbArticle, "split 2");
		add(btnAdd);
		add(lblArticle);
		add(txtArticle);
		add(lblRest);
		add(txtRest);
		add(lblDate);
		add(txtDate);
		add(lblBuyer);
		add(txtBuyer);
		add(lblSupplier);
		add(txtSupplier);
		add(lblStatus);
		add(cbStatus);
		add(lblPrice);
		add(txtPrice);
		add(new JLabel());
		add(btnOK, "split 2");
		add(btnCancel);
		
	}
	
	private double getSum() {
		double startPrice = 0;
		for(Article a: orderedArticle) {
			startPrice += a.getPrice() + 200;
			
		}
		txtPrice.setText(String.valueOf(startPrice));
		return startPrice;
	}
	
	private void enableFields() {
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
		if(txtArticle.getText().trim().equals("")) {
			ok = false;
			message += "- Artikal ne sme biti prazno polje:\n";
		}else if(txtRest.getText().trim().equals("")) {
			ok = false;
			message += "- Restoran ne sme biti prazno polje:\n";
		}else if(txtDate.getText().trim().equals("")) {
			ok = false;
			message += "- Datum ne sme biti prazno polje:\n";
		}
		try {
			Double.parseDouble(txtPrice.getText().trim());
		}catch(NumberFormatException e) {
			ok = false;
			message += "- Cena mora biti broj";
		}
		
		if(ok == false) {
			JOptionPane.showMessageDialog(null, message, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		
		return ok;
	}
	
	private void initActions() {
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Article ar = entities.gArticle(cbArticle.getSelectedItem().toString());
				orderedArticle.add(ar);
				getSum();
				
			}
		});
		
		
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validation() == true) {
					String restString = txtRest.getText().trim();
					Restaurants rest = entities.rName(restString);
					String dateString = txtDate.getText().trim();
					SimpleDateFormat format =  new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
					Date date = null;
					try {
						date = (Date)format.parse(dateString);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					Buyer buyer = entities.bName(txtBuyer.getText().trim());
					Supplier supplier = entities.sName(txtSupplier.getText().trim());
					EnumStatus status = (EnumStatus)cbStatus.getSelectedItem();
					String priceString = txtPrice.getText().trim();
					double price = Double.parseDouble(priceString);
					if(order != null) {
						order.setOrderedDish(orderedArticle);
						order.setRest(rest);
						order.setDate(date);
						order.setBuyer(buyer);
						order.setSupplier(supplier);
						order.setStatus(status);
						order.setPrice(price);
						
					}
					entities.writeOrders();
					
					BuyerOrderChange.this.dispose();
					BuyerOrderChange.this.setVisible(false);
					
				}
				
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BuyerOrderChange.this.dispose();
				BuyerOrderChange.this.setVisible(false);
				
			}
		});
	}

}
