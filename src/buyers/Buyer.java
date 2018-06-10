package buyers;

import java.util.ArrayList;

import articles.Article;
import enumeration.EnumGender;
import user.Users;

public class Buyer extends Users {

	private String address;
	private String phoneNum;
	protected ArrayList<Article> articleOrdered = new ArrayList<Article>();

	public Buyer() {
		super();
		this.address = "";
		this.phoneNum = "";
	}

	public Buyer(String name, String surname, EnumGender gender, String username, String password, String address,
			String phoneNum) {
		super(name, surname, gender, username, password);
		this.address = address;
		this.phoneNum = phoneNum;
	}

	public Buyer(Buyer original) {
		super(original);
		this.address = original.address;
		this.phoneNum = original.phoneNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Override
	public String toString() {
		return "name=" + name + ", surname=" + surname + ", gender=" + gender + ", username=" + username + ", password="
				+ password + ", address=" + address + ", phoneNum=" + phoneNum;
	}

}
