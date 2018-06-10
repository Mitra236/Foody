package employees;

import enumeration.EnumGender;
import user.Users;

public class Administrator extends Users {
	
	private String JMBG;
	private double salary;
	
	public Administrator() {
		super();
		this.JMBG = "";
		this.salary = 0;
	}

	public Administrator(String name, String surname, EnumGender gender, String username, String password, String jMBG,
			double salary) {
		super(name, surname, gender, username, password);
		JMBG = jMBG;
		this.salary = salary;
	}
	
	public Administrator(Administrator original) {
		super(original);
		this.JMBG = original.JMBG;
		this.salary = original.salary;
	}

	public String getJMBG() {
		return JMBG;
	}

	public void setJMBG(String jMBG) {
		JMBG = jMBG;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "name=" + name + ", surname=" + surname
				+ ", gender=" + gender + ", username=" + username + ", password=" + password + ", JMBG=" + JMBG + ", salary" + salary;
	}

	
}
