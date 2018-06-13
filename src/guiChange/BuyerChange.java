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

import buyers.Buyer;
import enumeration.EnumGender;
import loadFiles.LoadF;
import net.miginfocom.swing.MigLayout;

public class BuyerChange extends JFrame {
	
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
	private JLabel lblAddress = new JLabel("Adresa");
	private JTextField txtAddress = new JTextField(20);
	private JLabel lblNumber = new JLabel("Broj telefona");
	private JTextField txtNumber = new JTextField(20);
	private JButton btnOK = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");
	private LoadF entities;
	private Buyer buyer;
	
	public BuyerChange(LoadF entites, Buyer buyer) {
		this.entities = entites;
		this.buyer = buyer;
		if(this.buyer == null) {
			setTitle("Dodavanje korisnika");
		}else {
			setTitle("Izmena korisnika");
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectData/hamburger.png"));
		initGui();
		initActions();
		pack();
	}
	

	public void initGui() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		getContentPane().setBackground(Color.WHITE);
		
		if(this.buyer != null) {
			fillupFields();
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
		add(lblAddress);
		add(txtAddress);
		add(lblNumber);
		add(txtNumber);
		add(new JLabel());
		add(btnOK, "split 2");
		add(btnCancel);
		
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
					String address = txtAddress.getText().trim();
					String number = txtNumber.getText().trim();
					
					if(buyer == null) { //dodavanje
						buyer = new Buyer(name, surname, gender, username, password, address, number);
						entities.getBuyer().add(buyer);
					}else { //izmena
						buyer.setName(name);
						buyer.setSurname(surname);
						buyer.setGender(gender);
						buyer.setUsername(username);
						buyer.setPassword(password);
						buyer.setAddress(address);
						buyer.setPhoneNum(number);
					}
					entities.writeBuyers();
					BuyerChange.this.dispose();
					BuyerChange.this.setVisible(false);
					
				}
				
			}
		});
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BuyerChange.this.setVisible(false);
				BuyerChange.this.dispose();
			}	
		});
	}
	
	private void fillupFields() {
		txtName.setText(this.buyer.getName());
		txtSurname.setText(this.buyer.getSurname());
		cbGender.setSelectedItem(this.buyer.getGender());
		txtUsername.setText(this.buyer.getUsername());
		pfPassword.setText(this.buyer.getPassword());
		txtAddress.setText(this.buyer.getAddress());
		txtNumber.setText(this.buyer.getPhoneNum());
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
		}else if(txtAddress.getText().trim().equals("")) {
			ok = false;
			message += "- Adresa ne sme biti prazno polje:\n";
		}else if(txtNumber.getText().trim().equals("")) {
			ok = false;
			message += "- Broj ne sme biti prazno polje:\n";
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, message, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		
		return ok;
	}

}
