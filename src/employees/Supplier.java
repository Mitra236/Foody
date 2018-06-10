package employees;


import enumeration.EnumGender;
import enumeration.EnumVehicle;
import user.Users;

public class Supplier extends Users {
	
	private String JMBG;
	private double salary;
	private String driversLicence;
	private EnumVehicle vehicleType;
	
	public Supplier() {
		super();
		this.JMBG = "";
		this.salary = 0;
		this.driversLicence = "";
		this.vehicleType = EnumVehicle.bicikl;
	}

	public Supplier(String name, String surname, EnumGender gender, String username, String password, String jMBG,
			double salary, String driversLicence, EnumVehicle vehicleType) {
		super(name, surname, gender, username, password);
		JMBG = jMBG;
		this.salary = salary;
		this.driversLicence = driversLicence;
		this.vehicleType = vehicleType;
	}
	
	public Supplier(Supplier original) {
		super(original);
		this.JMBG = original.JMBG;
		this.salary = original.salary;
		this.driversLicence = original.driversLicence;
		this.vehicleType = original.vehicleType;
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

	public String getDriversLicence() {
		return driversLicence;
	}

	public void setDriversLicence(String driversLicence) {
		this.driversLicence = driversLicence;
	}

	public EnumVehicle getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(EnumVehicle vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Override
	public String toString() {
		return "name=" + name + ", surname=" + surname + ", gender=" + gender
				+ ", username=" + username + ", password=" + password + ", JMBG=" + JMBG + ", salary=" + salary + ", driversLicence=" + driversLicence + ", vehicleType="
						+ vehicleType;
	}

}
