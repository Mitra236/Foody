package articles;



import enumeration.EnumArticle;
import restaurants.Restaurants;

public class Dish extends Article {
	
	private double amount;
	
	public Dish() {
		this.amount = 0;
	}
	

	public Dish(EnumArticle article, Restaurants restaurantName, String articleName, double price, String description,
			double amount) {
		super(article, restaurantName, articleName, price, description);
		this.amount = amount;
	}


	public Dish(Dish original) {
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
		return "Dish [article=" + article + ",restaurantName=" + this.restaurantName.getRestName() +
				", articleName=" + articleName + ", price=" + price + ", description=" + description + " amount= " + amount + "]";
	}
	

}
