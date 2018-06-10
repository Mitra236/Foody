package guiChange;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import employees.Supplier;
import enumeration.EnumGender;
import enumeration.EnumVehicle;
import loadFiles.LoadF;
import net.miginfocom.swing.MigLayout;

public class SupplierChange extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblName = new JLabel("Ime");
	private JTextField txtName = new JTextField(20);
	private JLabel lblSurname = new JLabel("Prezime");
	private JTextField txtSurname = new JTextField(20);
	private JLabel lblGender = new JLabel("Pol");
	private JComboBox<EnumGender> cbGender = new JComboBox<EnumGender>(EnumGender.values());
	private JLabel lblUsername = new JLabel("Korisnicko ime");
	private JTextField txtUsername = new JTextField(20);
	private JLabel lblPassword = new JLabel("Lozinka");
	private JPasswordField pfPassword = new JPasswordField(20);
	private JLabel lblJmbg = new JLabel("JMBG");
	private JTextField txtJmbg = new JTextField(20);
	private JLabel lblSalary = new JLabel("Plata");
	private JTextField txtSalary = new JTextField(20);
	private JLabel lblLicence  = new JLabel("Registarska oznaka");
	private JTextField txtLicence = new JTextField(20);
	private JLabel lblVehicle = new JLabel("Tip vozila");
	private JComboBox<EnumVehicle> cbVehicle = new JComboBox<EnumVehicle>(EnumVehicle.values());
	private JButton btnOK = new JButton("OK");
	private JButton btncancel = new JButton("Cancel");
	private LoadF entities;
	private Supplier supplier;
	
	public SupplierChange(LoadF entities, Supplier supplier) {
		this.entities = entities;
		this.supplier = supplier;
		if(this.supplier == null) {
			setTitle("Dodavanje dostavljaca");
		}else {
			setTitle("Izmena dostavljaca");
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		initGui();
		initActions();
		pack();
	}
	
	public void initGui() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		getContentPane().setBackground(Color.WHITE);
		
		if(this.supplier != null) {
			txtName.setText(this.supplier.getName());
			txtSurname.setText(this.supplier.getSurname());
			cbGender.setSelectedItem(this.supplier.getGender());
			txtUsername.setText(this.supplier.getUsername());
			pfPassword.setText(this.supplier.getPassword());
			txtJmbg.setText(this.supplier.getJMBG());
			txtSalary.setText(String.valueOf(this.supplier.getSalary()));
			txtLicence.setText(this.supplier.getDriversLicence());
		}
		
		
		add(lblName);
		add(txtName);
		add(lblSurname);
		add(txtSurname);
		add(lblGender);
		cbGender.setModel(new DefaultComboBoxModel<>(EnumGender.values()));
		add(cbGender);
		add(lblUsername);
		add(txtUsername);
		add(lblPassword);
		add(pfPassword);
		add(lblJmbg);
		add(txtJmbg);
		add(lblSalary);
		add(txtSalary);
		add(lblLicence);
		add(txtLicence);
		add(lblVehicle);
		cbVehicle.setModel(new DefaultComboBoxModel<>(EnumVehicle.values()));
		add(cbVehicle);
		add(new JLabel());
		add(btnOK, "split 2");
		add(btncancel);
	}
	
	private boolean validation() {
		boolean ok = true;
		String message = "Greska u unosu:\n";
		if(txtName.getText().trim().equals("")) {
			ok = false;
			message += "- Ime ne sme biti prazno polje:\n";
		}else if(txtSurname.getText().trim().equals("")) {
			ok = false;
			message += "- Prezime ne sme biti prazno polje:\n";
		}else if(txtUsername.getText().trim().equals("")) {
			ok = false;
			message += "- Korisnicko ime ne sme biti prazno polje:\n";
		}else if(pfPassword.getText().trim().equals("")) {
			ok = false;
			message += "- Lozinka ne sme biti prazno polje:\n";
		}else if(txtJmbg.getText().trim().equals("")) {
			ok = false;
			message += "- JMBG ne sme biti prazno polje:\n";
		}else if(txtLicence.getText().trim().equals("")) {
			ok = false;
			message += "- Oznaka tablica ne sme biti prazno polje:\n";
		}
		try {
			Double.parseDouble(txtSalary.getText().trim());
		}catch(NumberFormatException e) {
			ok = false;
			message += "- Plata mora biti broj";
		}
		
		if(ok == false) {
			JOptionPane.showMessageDialog(null, message, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}
	
	private void initActions() {
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validation() == true) {
					String name = txtName.getText().trim();
					String surname = txtSurname.getText().trim();
					EnumGender gender = (EnumGender) cbGender.getSelectedItem();
					String username = txtUsername.getText().trim();
					String password = pfPassword.getText().trim();
					String JMBG = txtJmbg.getText().trim();
					String salaryString = txtSalary.getText().trim();
					double salary = Double.parseDouble(salaryString);
					String licence = txtLicence.getText().trim();
					EnumVehicle vehicle = (EnumVehicle)cbVehicle.getSelectedItem();
					
					if(supplier == null) { //dodavanje
						supplier = new Supplier(name, surname, gender, username, password, JMBG, salary, licence, vehicle);
						entities.getSupplier().add(supplier);
					}else { //izmena
						supplier.setName(name);
						supplier.setSurname(surname);
						supplier.setGender(gender);
						supplier.setUsername(username);
						supplier.setPassword(password);
						supplier.setJMBG(JMBG);
						supplier.setSalary(salary);;
						supplier.setVehicleType(vehicle);
					}
					entities.writeSuppliers();
					SupplierChange.this.dispose();
					SupplierChange.this.setVisible(false);
				}
				
			}
		});
		
		btncancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SupplierChange.this.setVisible(false);
				SupplierChange.this.dispose();
				
			}
		});
	}

}
