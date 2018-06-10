package main;

import java.text.ParseException;


import appGUI.LoginGUI;
import loadFiles.LoadF;

public class FoodOrderMain {

	public static void main(String[] args) throws ParseException  {
		LoadF entities = new LoadF();
		entities.addUsers();
		entities.loadRestaurants();
		entities.addArticle();
		entities.loadOrders();
		entities.loadPoints();
		
		LoginGUI log = new LoginGUI(entities);
		log.setVisible(true);

	}

}
