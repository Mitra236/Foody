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

import employees.Administrator;
import enumeration.EnumGender;
import loadFiles.LoadF;
import net.miginfocom.swing.MigLayout;

public class AdminChange extends JFrame {

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
	private JButton btnOK = new JButton("OK");
	private JButton btncancel = new JButton("Cancel");
	
	private LoadF entities;
	private Administrator admin;
	
	public AdminChange(LoadF entities, Administrator admin) {
		this.entities = entities;
		this.admin = admin;
		if(this.admin == null) {
			setTitle("Dodavanje Administratora");
		}else {
			setTitle("Izmena Administratora");
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		setLocationRelativeTo(null);
		initGui();
		initActions();
		pack();
	}
	
	public void initGui() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		getContentPane().setBackground(Color.WHITE);
		
		if(this.admin != null) {
			txtName.setText(this.admin.getName());
			txtSurname.setText(this.admin.getSurname());
			cbGender.setSelectedItem(this.admin.getGender());
			txtUsername.setText(this.admin.getUsername());
			pfPassword.setText(this.admin.getPassword());
			txtJmbg.setText(this.admin.getJMBG());
			txtSalary.setText(String.valueOf(this.admin.getSalary()));
		}
		
		add(lblName);
		add(txtName);
		add(lblSurname);
		add(txtSurname);
		add(lblGender);
		add(cbGender);
		add(lblUsername);
		add(txtUsername);
		add(lblPassword);
		add(pfPassword);
		add(lblJmbg);
		add(txtJmbg);
		add(lblSalary);
		add(txtSalary);
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
	
	public void initActions() {
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
					
					if(admin == null) { //dodavanje
						admin = new Administrator(name, surname, gender, username, password, JMBG, salary);
						entities.getAdmin().add(admin);
					}else { //izmena
						admin.setName(name);
						admin.setSurname(surname);
						admin.setGender(gender);
						admin.setUsername(username);
						admin.setPassword(password);
						admin.setJMBG(JMBG);
						admin.setSalary(salary);;
					}
					entities.writeAdmins();
					AdminChange.this.dispose();
					AdminChange.this.setVisible(false);
					
				}
				
			}
		});
		btncancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AdminChange.this.setVisible(false);
				AdminChange.this.dispose();
			}	
		});
	}
}
