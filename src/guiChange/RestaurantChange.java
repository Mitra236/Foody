package guiChange;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import enumeration.EnumRestaurant;
import loadFiles.LoadF;
import net.miginfocom.swing.MigLayout;
import restaurants.Restaurants;

public class RestaurantChange extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblName = new JLabel("Ime");
	private JTextField txtName = new JTextField(20);
	private JLabel lblAddress = new JLabel("Adresa");
	private JTextField txtAddress = new JTextField(20);
	private JLabel lblType = new JLabel("Kategorija");
	private JComboBox<EnumRestaurant> cbType = new JComboBox<EnumRestaurant>();
	private JButton btnOK = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");
	private LoadF entities;
	private Restaurants restaurant;
	
	public RestaurantChange(LoadF entities, Restaurants restaurant) {
		this.entities = entities;
		this.restaurant = restaurant;
		if(this.restaurant == null) {
			setTitle("Dodavanje restorana");
		}else {
			setTitle("Izmena restorana");
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		setResizable(false);
		initGui();
		initActions();
		pack();
	}

	private void initGui() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		
		if(this.restaurant != null) {
			txtName.setText(this.restaurant.getRestName());
			txtAddress.setText(this.restaurant.getRestAddress());
			cbType.setSelectedItem(this.restaurant.getCategory());
		}
		
		add(lblName);
		add(txtName);
		add(lblAddress);
		add(txtAddress);
		add(lblType);
		cbType.setModel(new DefaultComboBoxModel<>(EnumRestaurant.values()));
		add(cbType);
		add(new JLabel());
		add(btnOK, "split 2");
		add(btnCancel);
		
	}
	
	private boolean validation() {
		boolean ok = true;
		String message = "Greska u unosu:\n";
		if(txtName.getText().trim().equals("")) {
			ok = false;
			message += "- Ime ne sme biti prazno polje";
		}else if(txtAddress.getText().trim().equals("")) {
			ok = false;
			message += "- Adresa ne sme biti prazno polje";
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
					String address = txtAddress.getText().trim();
					EnumRestaurant category = (EnumRestaurant)cbType.getSelectedItem();
					if(restaurant == null) {
						restaurant = new Restaurants(name, address, category);
						entities.getRest().add(restaurant);
					}else {
						restaurant.setRestName(name);
						restaurant.setRestAddress(address);
						restaurant.setCategory(category);
					}
					entities.writeRestaurants();
					RestaurantChange.this.dispose();
					RestaurantChange.this.setVisible(false);
				}
				
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RestaurantChange.this.setVisible(false);
				RestaurantChange.this.dispose();
				
			}
		});
	}

}
