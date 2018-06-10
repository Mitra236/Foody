package guiChange;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import articles.Article;
import articles.Dish;
import articles.Drink;
import enumeration.EnumArticle;
import loadFiles.LoadF;
import net.miginfocom.swing.MigLayout;
import restaurants.Restaurants;

public class ArticleChange extends JFrame {
	
private static final long serialVersionUID = 1L;
	
	private JLabel lblType = new JLabel("Tip");
	private JComboBox<EnumArticle> cbArticle = new JComboBox<EnumArticle>();
	private JLabel lblRestaurant = new JLabel("Restoran");
	private JTextField txtRestaurant = new JTextField(20);
	private JLabel lblName = new JLabel("Naziv artikla");
	private JTextField txtName = new JTextField(20);
	private JLabel lblPrice = new JLabel("Cena");
	private JTextField txtPrice = new JTextField(20);
	private JLabel lblDesc = new JLabel("Opis");
	private JTextField txtDesc = new JTextField(20);
	private JLabel lblAmount = new JLabel("Kolicina");
	private JTextField txtAmount = new JTextField(20);
	private JButton btnOK = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");
	private LoadF entities;
	private Article article;
	
	public ArticleChange(LoadF entities, Article article) {
		this.entities = entities;
		this.article = article;
		if(this.article == null){
			setTitle("Dodavanje artikla");
		}else {
			setTitle("Izmena artikla");
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		setResizable(false);
		initGui();
		initActions();
		pack();
	}

	private void initGui() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		
		if(this.article != null) {
			cbArticle.setSelectedItem(this.article.getArticle());
			txtRestaurant.setText(this.article.getRestaurantName().getRestName());
			txtName.setText(this.article.getArticleName());
			txtPrice.setText(String.valueOf(this.article.getPrice()));
			txtDesc.setText(this.article.getDescription());
		}
		
		for(Dish d: entities.getDish()) {
			if(article instanceof Dish) {
				txtAmount.setText(String.valueOf(d.getAmount()));
			}
		}
		
		for(Drink dr: entities.getDrink()) {
			if(article instanceof Drink) {
				txtAmount.setText(String.valueOf(dr.getAmount()));
			}
		}
		
		add(lblType);
		cbArticle.setModel(new DefaultComboBoxModel<>(EnumArticle.values()));
		add(cbArticle);
		add(lblRestaurant);
		add(txtRestaurant);
		add(lblName);
		add(txtName);
		add(lblPrice);
		add(txtPrice);
		add(lblDesc);
		add(txtDesc);
		add(lblAmount);
		add(txtAmount);
		add(new JLabel());
		add(btnOK, "split 2");
		add(btnCancel);
		
	}
	
	private boolean validation() {
		boolean ok = true;
		String message = "Greska u unosu:\n";
		if(txtRestaurant.getText().trim().equals("")) {
			ok = false;
			message += "- Restoran ne sme biti prazno polje:\n";
		}else if(txtName.getText().trim().equals("")) {
			ok = false;
			message += "- Ime artikla ne sme biti prazno polje:\n";
		}else if(txtDesc.getText().trim().equals("")) {
			ok = false;
			message += "- Opis ne sme biti prazno polje:\n";
		}
		try {
			Double.parseDouble(txtPrice.getText().trim());
		}catch(NumberFormatException e) {
			ok = false;
			message += "- Cena mora biti broj";
		}
		
		try {
			Double.parseDouble(txtAmount.getText().trim());
		}catch(NumberFormatException e) {
			ok = false;
			message += "- Kolicina mora biti broj";
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, message, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		
		return ok;
	}
	
	private void initActions() {
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validation() == true) {
					EnumArticle articles = (EnumArticle)cbArticle.getSelectedItem();
					String restaurantString = txtRestaurant.getText().trim();
					Restaurants restaurant = entities.rName(restaurantString);
					String name = txtName.getText().trim();
					String priceString = txtPrice.getText().trim();
					double price = Double.parseDouble(priceString);
					String desc = txtDesc.getText().trim();
					String amountString = txtAmount.getText().trim();
					double amount = Double.parseDouble(amountString);
					if(articles == EnumArticle.Jelo) {
						if(article == null) {
							Dish d = new Dish(articles, restaurant, name, price, desc, amount);
							entities.getDish().add(d);
						}else {
							Dish d = (Dish)article;
							d.setArticle(articles);
							d.setRestaurantName(restaurant);
							d.setArticleName(name);
							d.setPrice(price);
							d.setDescription(desc);
							d.setAmount(amount);
						}
					}else {
						if(article == null) {
							Drink dr = new Drink(articles, restaurant, name, price, desc, amount);
							entities.getDrink().add(dr);
						}else {
							Drink dr = (Drink)article;
							dr.setArticle(articles);
							dr.setRestaurantName(restaurant);
							dr.setArticleName(name);
							dr.setPrice(price);
							dr.setDescription(desc);
							dr.setAmount(amount);
						}
					}
					
					entities.writeArticles();
					ArticleChange.this.dispose();
					ArticleChange.this.setVisible(false);
				}
				
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArticleChange.this.setVisible(false);
				ArticleChange.this.dispose();
			}	
		});
	}


}