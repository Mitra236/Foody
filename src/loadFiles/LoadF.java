package loadFiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import articles.Article;
import articles.Dish;
import articles.Drink;
import buyers.Buyer;
import employees.Administrator;
import employees.Supplier;
import enumeration.EnumArticle;
import enumeration.EnumGender;
import enumeration.EnumRestaurant;
import enumeration.EnumStatus;
import enumeration.EnumVehicle;
import order.Order;
import points.Points;
import restaurants.Restaurants;
import user.Users;

public class LoadF {

	private ArrayList<Users> users = new ArrayList<Users>();
	private ArrayList<Buyer> buyer = new ArrayList<Buyer>();
	private ArrayList<Administrator> admin = new ArrayList<Administrator>();
	private ArrayList<Supplier> supplier = new ArrayList<Supplier>();

	private ArrayList<Restaurants> rest = new ArrayList<Restaurants>();
	private ArrayList<Dish> dish = new ArrayList<Dish>();
	private ArrayList<Drink> drink = new ArrayList<Drink>();
	private ArrayList<Order> order = new ArrayList<Order>();
	private ArrayList<Article> article = new ArrayList<Article>();
	private ArrayList<Points> points = new ArrayList<Points>();
	

	public LoadF() {
		this.users = new ArrayList<Users>();
		this.buyer = new ArrayList<Buyer>();
		this.admin = new ArrayList<Administrator>();
		this.supplier = new ArrayList<Supplier>();

		this.rest = new ArrayList<Restaurants>();
		this.dish = new ArrayList<Dish>();
		this.drink = new ArrayList<Drink>();
		this.order = new ArrayList<Order>();
		this.article = new ArrayList<Article>();
	}

	public ArrayList<Users> getUsers() {
		return users;
	}

	public Users uName(String uname) {
		for (Users u : users) {
			if (u.getUsername().equals(uname)) {
				return u;
			}
		}
		return null;
	}

	public Users login(String uname, String password) {
		for (Users u : users) {
			if (u.getUsername().equals(uname) && u.getPassword().equals(password)) {
				return u;
			}
		}
		return null;
	}

	public ArrayList<Buyer> getBuyer() {
		return buyer;
	}

	public Buyer bName(String uname) {
		for (Buyer b : buyer) {
			if (b.getUsername().equals(uname)) {
				return b;
			}
		}
		return null;
	}

	public void deleteBuyer(Buyer index) {
		this.buyer.remove(index);
	}

	public ArrayList<Administrator> getAdmin() {
		return admin;
	}

	public Administrator aName(String uname) {
		for (Administrator a : admin) {
			if (a.getUsername().equals(uname))
				return a;
		}
		return null;
	}

	public void deleteAdmin(Administrator adm) {
		this.admin.remove(adm);
	}

	public ArrayList<Supplier> getSupplier() {
		return supplier;
	}

	public Supplier sName(String uname) {
		for (Supplier s : supplier) {
			if (s.getUsername().equals(uname)) {
				return s;
			}
		}
		return null;
	}

	public void deleteSupplier(Supplier supl) {
		this.supplier.remove(supl);
	}

	public ArrayList<Restaurants> getRest() {
		return rest;
	}

	public Restaurants rName(String rname) {
		for (Restaurants restaurant : rest) {
			if (restaurant.getRestName().equals(rname)) {
				return restaurant;
			}
		}
		return null;
	}

	public ArrayList<Dish> getDish() {
		return dish;
	}

	public ArrayList<Drink> getDrink() {
		return drink;
	}

	public Article gArticle(String aName) {
		for (Dish d : dish) {
			if (d.getArticleName().equals(aName)) {
				return d;
			}
		}

		for (Drink dr : drink) {
			if (dr.getArticleName().equals(aName)) {
				return dr;
			}
		}

		return null;
	}

	public ArrayList<Order> getOrder() {
		return order;
	}

	public Order gOrder(String uname) {
		for (Order o : order) {
			if (o.getBuyer().getUsername().equals(uname)) {
				return o;
			}
		}
		return null;
	}

	public Article gName(String name) {
		for (Article a : article) {
			if (a.getArticleName().equals(name)) {
				return a;
			}
		}
		return null;
	}

	public ArrayList<Article> getArticle() {
		return article;
	}
	

	public ArrayList<Points> getPoints() {
		return points;
	}

	// ********************** UCITAVANJE KUPACA ************************** //
	public void readBuyers() {

		try {
			File buyerFile = new File("src/projectData/buyers.txt");
			BufferedReader reader = new BufferedReader(new FileReader(buyerFile));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] splitLine = line.split("\\|");
				String name = splitLine[0];
				String surname = splitLine[1];
				int genderInt = Integer.parseInt(splitLine[2]);
				EnumGender gender = EnumGender.valueOf(genderInt);
				String username = splitLine[3];
				String password = splitLine[4];
				String address = splitLine[5];
				String phoneNum = splitLine[6];
				Buyer b = new Buyer(name, surname, gender, username, password, address, phoneNum);
				buyer.add(b);

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ************************** UCITAVANJE ADMINISTRATORA ******************************* //
	public void readAdmins() {
		try {
			File adminFile = new File("src/projectData/admins.txt");
			BufferedReader readAdmin = new BufferedReader(new FileReader(adminFile));
			String line1;
			while ((line1 = readAdmin.readLine()) != null) {
				String[] splitAdmin = line1.split("\\|");
				String name = splitAdmin[0];
				String surname = splitAdmin[1];
				int genderInt = Integer.parseInt(splitAdmin[2]);
				EnumGender gender = EnumGender.valueOf(genderInt);
				String username = splitAdmin[3];
				String password = splitAdmin[4];
				String JMBG = splitAdmin[5];
				double salary = Double.parseDouble(splitAdmin[6]);
				Administrator a = new Administrator(name, surname, gender, username, password, JMBG, salary);
				admin.add(a);
			}
			readAdmin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ******************************** UCITAVANJE DOSTAVLJACA **************************** //
	public void readSuppliers() {
		try {
			File supplierFile = new File("src/projectData/suppliers.txt");
			BufferedReader readSupplier = new BufferedReader(new FileReader(supplierFile));
			String line2;
			while ((line2 = readSupplier.readLine()) != null) {
				String[] splitSupplier = line2.split("\\|");
				String name = splitSupplier[0];
				String surname = splitSupplier[1];
				int genderInt = Integer.parseInt(splitSupplier[2]);
				EnumGender gender = EnumGender.valueOf(genderInt);
				String username = splitSupplier[3];
				String password = splitSupplier[4];
				String JMBG = splitSupplier[5];
				double salary = Double.parseDouble(splitSupplier[6]);
				String driversLicence = splitSupplier[7];
				int vehicleTypeInt = Integer.parseInt(splitSupplier[8]);
				EnumVehicle vehicleType = EnumVehicle.valueOf(vehicleTypeInt);
				Supplier s = new Supplier(name, surname, gender, username, password, JMBG, salary, driversLicence,
						vehicleType);
				supplier.add(s);

			}
			readSupplier.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// UBACIVANJE ENTITETA U ZAJEDNICKU LISTU
	public void addUsers() {
		readBuyers();
		readAdmins();
		readSuppliers();
		for (int i = 0; i < buyer.size(); i++) {
			users.addAll(buyer);
		}
		for (int i = 0; i < admin.size(); i++) {
			users.addAll(admin);
		}
		for (int i = 0; i < supplier.size(); i++) {
			users.addAll(supplier);
		}
	}

	// **************************** UCITAVANJE RESTORANA ***************************** //
	public void loadRestaurants() {

		try {
			File restFile = new File("src/projectData/restaurants.txt");
			BufferedReader reader = new BufferedReader(new FileReader(restFile));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] splitRest = line.split("\\|");
				String restaurantName = splitRest[0];
				String restAddress = splitRest[1];
				int intCategory = Integer.parseInt(splitRest[2]);
				EnumRestaurant category = EnumRestaurant.valueOf(intCategory);
				Restaurants r = new Restaurants(restaurantName, restAddress, category);
				rest.add(r);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// *********************** UCITAVANJE ARTIKALA ******************************* //
	public void loadArticle() {
		try {
			File articleFile = new File("src/projectData/articles.txt");
			BufferedReader reader = new BufferedReader(new FileReader(articleFile));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] splitArticle = line.split("\\|");
				int articleInt = Integer.parseInt(splitArticle[0]);
				EnumArticle article = EnumArticle.valueOf(articleInt);
				Restaurants restaurant = this.rName(splitArticle[1]);
				String name = splitArticle[2];
				double price = Double.parseDouble(splitArticle[3]);
				String description = splitArticle[4];
				double amount = Double.parseDouble(splitArticle[5]);
				if (articleInt == 1) {
					Dish d = new Dish(article, restaurant, name, price, description, amount);
					dish.add(d);
				} else {
					Drink dr = new Drink(article, restaurant, name, price, description, amount);
					drink.add(dr);
				}

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// *********************************** UCITAVANJE PORUDZBINA ******************************* //
	public void loadOrders() throws ParseException {
		try {
			File fileOrder = new File("src/projectData/orders.txt");
			BufferedReader reader = new BufferedReader(new FileReader(fileOrder));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] splitOrders = line.split("\\|");
				String[] a = splitOrders[0].split(",");
				Article article;
				ArrayList<Article> arti = new ArrayList<Article>();
				for(String art: a) {
					article = this.gName(art);
					arti.add(article);
				}
				Restaurants rest = this.rName(splitOrders[1]);
				SimpleDateFormat format =  new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
				Date date = format.parse(splitOrders[2]);
				Buyer buyer = this.bName(splitOrders[3]);
				Supplier supplier = this.sName(splitOrders[4]);
				int statusInt = Integer.parseInt(splitOrders[5]);
				EnumStatus status = EnumStatus.valueOf(statusInt);
				double cena = Double.parseDouble(splitOrders[6]);
				Order o = new Order(arti, rest, date, buyer, supplier, status, cena);
				order.add(o);

			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	// UCITAVANJE ARTIKALA U ZAJEDNICKU LISTU
	public void addArticle() {
		loadArticle();
		for (int i = 0; i < dish.size(); i++) {
			article.addAll(dish);
		}
		for (int i = 0; i < drink.size(); i++) {
			article.addAll(drink);
		}

	}
	
	// ************************************ UCITAVANJE BODOVA ************************************************** //
	public void loadPoints() {
		try {
			File filePoints = new File("src/projectData/pointsy.txt");
			BufferedReader reader = new BufferedReader(new FileReader(filePoints));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] splitPonts = line.split("\\|");
				Buyer buyer = this.bName(splitPonts[0]);
				int pointses = Integer.parseInt(splitPonts[1]);
				Points p = new Points(buyer, pointses);
				points.add(p);
				
			}
			reader.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	

	// ********************************** AZURIRANJE KUPACA ****************************** //
	public void writeBuyers() {
		try {
			File file = new File("src/projectData/buyers.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Kreiran novi fajl.");
			} else {
				System.out.println("Fajl vec postoji");
			}
			String content = "";
			for (Buyer b : buyer) {
				content += b.getName() + "|" + b.getSurname() + "|" + EnumGender.toInt(b.getGender()) + "|"
						+ b.getUsername() + "|" + b.getPassword() + "|" + b.getAddress() + "|" + b.getPhoneNum() + "\n";
			}
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// *************************************** AZURIRANJE ADMINISTRATORA  ********************************* //
	public void writeAdmins() {
		try {
			File file = new File("src/projectData/admins.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Kreiran novi fajl.");
			} else {
				System.out.println("Fajl vec postoji.");
			}
			String content = "";
			for (Administrator a : admin) {
				content += a.getName() + "|" + a.getSurname() + "|" + EnumGender.toInt(a.getGender()) + "|"
						+ a.getUsername() + "|" + a.getPassword() + "|" + a.getJMBG() + "|" + a.getSalary() + "\n";
			}
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// **************************************** AZURIRANJE DOSTAVLJACA ********************************** //
	public void writeSuppliers() {
		try {
			File file = new File("src/projectData/suppliers.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Kreiran novi fajl.");
			} else {
				System.out.println("Fajl vec postoji.");
			}
			String content = "";
			for (Supplier s : supplier) {
				content += s.getName() + "|" + s.getSurname() + "|" + EnumGender.toInt(s.getGender()) + "|"
						+ s.getUsername() + "|" + s.getPassword() + "|" + s.getJMBG() + "|" + s.getSalary() + "|"
						+ s.getDriversLicence() + "|" + s.getVehicleType() + "\n";
			}
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// *********************************** AZURIRANJE RESTORANA  ************************************************* //
	public void writeRestaurants() {
		try {
			File file = new File("src/projectData/restaurants.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Kreiran novi fajl.");
			} else {
				System.out.println("Fajl vec postoji.");
			}
			String content = "";
			for (Restaurants r : rest) {
				content += r.getRestName() + "|" + r.getRestAddress() + "|" + EnumRestaurant.toInt(r.getCategory())
						+ "\n";
			}
			writer.write(content);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ****************************************** AZURIRANJE ARTIKALA ************************************************ //
	public void writeArticles() {
		try {
			File file = new File("src/projectData/articles.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Kreiran novi fajl.");
			} else {
				System.out.println("Fajl vec postoji.");
			}
			String content = "";
			for (Dish d : dish) {
				content += EnumArticle.toInt(d.getArticle()) + "|" + d.getRestaurantName().getRestName() + "|"
						+ d.getArticleName() + "|" + d.getPrice() + "|" + d.getDescription() + "|" + d.getAmount()
						+ "\n";
			}

			for (Drink dr : drink) {
				content += EnumArticle.toInt(dr.getArticle()) + "|" + dr.getRestaurantName().getRestName() + "|"
						+ dr.getArticleName() + "|" + dr.getPrice() + "|" + dr.getDescription() + "|" + dr.getAmount()
						+ "\n";
			}

			writer.write(content);
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ************************************** UPISIVANJE PORUDZBINA *************************************** //
	public void writeOrders() {
		try {
			File file = new File("src/projectData/orders.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Kreiran novi fajl.");
			} else {
				System.out.println("Fajl vec postoji.");
			}String toWrite = "";
			for (Order o : order) {
				toWrite += o.writeOrd() + "\n";
			}
			writer.write(toWrite);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	// **************************************** UPISIVANJE POENA ****************************************************** //
	public void writePoints() {
		try {
			File file = new File("src/projectData/pointsy.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Kreiran novi fajl.");
			} else {
				System.out.println("Fajl vec postoji.");
			}String content = "";
			for(Points p: points) {
				content += p.getBuyer().getUsername() + "|" + p.getPoints() + "\n";
			}
			writer.write(content);
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
