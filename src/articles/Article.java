package articles;



import enumeration.EnumArticle;
import restaurants.Restaurants;

public abstract class Article {
	
	protected EnumArticle article;
	protected Restaurants restaurantName;
	protected String articleName;
	protected double price;
	protected String description;

	
	public Article() {
		this.article = EnumArticle.Jelo;
		this.restaurantName = new Restaurants();
		this.articleName = "";
		this.price = 0;
		this.description = "";
	}




	public Article(EnumArticle article, Restaurants restaurantName, String articleName, double price,
			String description) {
		super();
		this.article = article;
		this.restaurantName = restaurantName;
		this.articleName = articleName;
		this.price = price;
		this.description = description;
	}


	public Article(Article original) {
		this.article = original.article;
		this.restaurantName = original.restaurantName;
		this.articleName = original.articleName;
		this.price = original.price;
		this.description = original.description;
	}
	

	public EnumArticle getArticle() {
		return article;
	}


	public void setArticle(EnumArticle article) {
		this.article = article;
	}


	public Restaurants getRestaurantName() {
		return restaurantName;
	}


	public void setRestaurantName(Restaurants restaurantName) {
		this.restaurantName = restaurantName;
	}



	public String getArticleName() {
		return articleName;
	}


	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public String toString() {
		return "Article: "
				+ "article=" + article + ", restaurantName=" + restaurantName.getRestName()  + ", articleName=" + articleName
				+ ", price=" + price + ", description=" + description + "";
	}


}
	