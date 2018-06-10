package restaurants;

import enumeration.EnumRestaurant;


public class Restaurants {

	private String restName;
	private String restAddress;
	private EnumRestaurant category;
	
	public Restaurants() {
		this.restName = "";
		this.restAddress = "";
		this.category = EnumRestaurant.picerija;
	}

	public Restaurants(String restaurantName, String restAddress, EnumRestaurant category) {
		super();
		this.restName = restaurantName;
		this.restAddress = restAddress;
		this.category = category;
	}
	
	public Restaurants(Restaurants original) {
		this.restName = original.restName;
		this.restAddress = original.restAddress;
		this.category = original.category;
	}

	
	public String getRestName() {
		return restName;
	}

	public void setRestName(String restaurantName) {
		this.restName = restaurantName;
	}

	public String getRestAddress() {
		return restAddress;
	}

	public void setRestAddress(String restAddress) {
		this.restAddress = restAddress;
	}

	public EnumRestaurant getCategory() {
		return category;
	}

	public void setCategory(EnumRestaurant category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "restaurantName=" + restName + ", restAddress=" + restAddress + ", category="
				+ category;
	}

	
}
