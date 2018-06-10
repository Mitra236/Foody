package articles;



import enumeration.EnumArticle;
import restaurants.Restaurants;

public class Drink extends Article {

	private double amount;
	
	public Drink() {
		this.amount = 0;
	}

	
	
	public Drink(EnumArticle article, Restaurants restaurantName, String articleName, double price, String description,
			double amount) {
		super(article, restaurantName, articleName, price, description);
		this.amount = amount;
	}



	public Drink(Drink original) {
		this.amount = original.amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Drink [article=" + article + ",restaurantName=" + this.restaurantName.getRestName() 
				+ ", articleName=" + articleName + ", price=" + price + ", description=" + description + "amount=" + amount + "]";
	}
	
	

}
