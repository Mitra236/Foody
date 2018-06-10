package appGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import orderModel.OrderAdmin;
import orderModel.OrderBuyer;
import buyers.Buyer;
import employees.Administrator;
import loadFiles.LoadF;
import net.miginfocom.swing.MigLayout;
import restaurants.Restaurants;
import user.Users;

public class SelectRestaurant extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lblChoice = new JLabel("Odaberi restoran: ");
	private JComboBox<String> cbRest = new JComboBox<String>();
	private JButton btnOK = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");
	
	private LoadF entities;
	private Users user;
	
	public SelectRestaurant(LoadF entities, Users user) {
		this.entities = entities;
		this.user = user;
		setTitle("Odabir restorana");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		initGui();
		pack();
	}

	private void initGui() {
		MigLayout layout = new MigLayout("wrap");
		setLayout(layout);
		getContentPane().setBackground(Color.decode("#e6ffcc"));
		
		for(Restaurants r : entities.getRest()) {
			cbRest.addItem(r.getRestName());
		}
		
		lblChoice.setFont(new Font("Serif", Font.PLAIN, 18));
		add(lblChoice);
		add(cbRest);
		add(btnOK, "split 2");
		add(btnCancel);
		
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Restaurants item = entities.rName((String) cbRest.getSelectedItem());
				if(user instanceof Administrator) {
					OrderAdmin oa = null;
					try {
						oa = new OrderAdmin(entities, null, item);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					oa.setVisible(true);
				}else if(user instanceof Buyer) {
					OrderBuyer ob = null;
					try {
						ob = new OrderBuyer(entities, null, item, user);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					ob.setVisible(true);
				}
				SelectRestaurant.this.dispose();
				SelectRestaurant.this.setVisible(false);
				
			}
		});
		
		
	}
	

}
