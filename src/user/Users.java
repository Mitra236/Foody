package user;

import enumeration.EnumGender;

public abstract class Users {
	
	protected String name;
	protected String surname;
	protected EnumGender gender;
	protected String username;
	protected String password;
	
	public Users() {
		this.name = "";
		this.surname = "";
		this.gender = EnumGender.zenski;
		this.username = "";
		this.password = "";
	}

	public Users(String name, String surname, EnumGender gender, String username, String password) {
		super();
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.username = username;
		this.password = password;
	}
	
	public Users(Users original) {
		this.name = original.name;
		this.surname = original.surname;
		this.gender = original.gender;
		this.username = original.username;
		this.password = original.password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public EnumGender getGender() {
		return gender;
	}

	public void setGender(EnumGender gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "name=" + name + ", surname=" + surname + ", gender=" + gender + ", username=" + username
				+ ", password=" + password;
	}
	
}
