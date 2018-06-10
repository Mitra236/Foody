package order;

import java.util.ArrayList;
import java.util.Date;

import articles.Article;
import buyers.Buyer;
import employees.Supplier;
import enumeration.EnumStatus;
import restaurants.Restaurants;

public class Order {

	private ArrayList<Article> orderedArticle;
	private Restaurants rest;
	private Date date;
	private Buyer buyer;
	private Supplier supplier;
	private EnumStatus status;
	private double price;

	public Order() {
		this.orderedArticle = new ArrayList<Article>();
		this.rest = new Restaurants();
		this.date = new Date();
		this.buyer = new Buyer();
		this.supplier = new Supplier();
		this.status = EnumStatus.porucena;
		this.price = 0;

	}

	public Order(ArrayList<Article> orderedArticle, Restaurants rest, Date date, Buyer buyer, Supplier supplier,
			EnumStatus status, double price) {
		super();
		this.orderedArticle = orderedArticle;
		this.rest = rest;
		this.date = date;
		this.buyer = buyer;
		this.supplier = supplier;
		this.status = status;
		this.price = price;
	}

	public ArrayList<Article> getOrderedDish() {
		return orderedArticle;
	}

	public void setOrderedDish(ArrayList<Article> orderedArticle) {
		this.orderedArticle = orderedArticle;
	}

	public Restaurants getRest() {
		return rest;
	}

	public void setRest(Restaurants rest) {
		this.rest = rest;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public EnumStatus getStatus() {
		return status;
	}

	public void setStatus(EnumStatus status) {
		this.status = status;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double cena) {
		this.price = cena;
	}

	public String forWrite() {
		StringBuffer forShow = new StringBuffer("");
		for (Article ar : this.orderedArticle) {
			if (ar != null) {
				forShow.append(ar.getArticleName() + ",");
			}
		}
		return forShow.toString();

	}

	public String writeOrd() {
		String forWrite = "";
		for (Article art : this.orderedArticle) {
			if (art != null) {
				forWrite += art.getArticleName() + ",";
			}
		}
		String supplierUsername = "prazno";
		if (this.supplier != null)
			supplierUsername = this.supplier.getUsername();
		return forWrite + "|" + this.rest.getRestName() + "|" + this.date + "|" + this.buyer.getUsername() + "|"
				+ supplierUsername + "|" + EnumStatus.toInt(this.status) + "|" + String.valueOf(this.price);
	}

	@Override
	public String toString() {
		return "Order: orderedArticle = " + forWrite() + ", rest=" + rest.getRestName() + ", date=" + date + ", buyer="
				+ buyer.getUsername() + ", supplier=" + supplier.getUsername() + ", status=" + status + ", price="
				+ price + "]";
	}

}
