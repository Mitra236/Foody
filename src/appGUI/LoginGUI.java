package appGUI;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import buyers.Buyer;
import employees.Administrator;
import employees.Supplier;
import loadFiles.LoadF;
import net.miginfocom.swing.MigLayout;
import user.Users;

public class LoginGUI extends JFrame{

	private static final long serialVersionUID = 1L;
	
	
	private ImageIcon img = new ImageIcon("src/projectImages/diet.png");
	private JLabel lblImage = new JLabel(img) ;
	private JLabel lblUsername = new JLabel("Korisnicko ime");
	private JTextField txtUsername = new JTextField(20);
	private JLabel lblPassword = new JLabel("Lozinka");
	private JPasswordField pfPassword = new JPasswordField(20);
	private JButton btnOK = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");
	
	private LoadF entities;

	
	public LoginGUI(LoadF entities) {
		this.entities = entities;
		setTitle("Sistem za porucivanje hrane");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		initGui();
		pack();
	}
	
	
	private void initGui() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		getContentPane().setBackground(Color.WHITE);
		
		add(new JLabel());
		add(lblImage);
		add(lblUsername);
		add(txtUsername);
		add(lblPassword);
		add(pfPassword);
		add(new JLabel());
		add(btnOK, "split 2");
		add(btnCancel);

		
		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String us = txtUsername.getText().trim();
				String pas = new String(pfPassword.getPassword());
				
				// ** PROVERA KORISNIKA **//
				if(us.equals("") || pas.equals("")) {
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke.");
				}else {
					Users u = entities.login(us, pas);
					if(u instanceof Buyer) {
						LoginGUI.this.setVisible(false);
						LoginGUI.this.dispose();
						BuyerWindow bw = null;
						try {
							bw = new BuyerWindow(entities, u);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						bw.setVisible(true);
					}else if(u instanceof Administrator) {
						LoginGUI.this.setVisible(false);
						LoginGUI.this.dispose();
						AdministratorWindow bw = new AdministratorWindow(entities, u);
						bw.setVisible(true);
					}else if (u instanceof Supplier) {
						LoginGUI.this.setVisible(false);
						LoginGUI.this.dispose();
						SupplierWindow sw = null;
						try {
							sw = new SupplierWindow(null, entities, u);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						sw.setVisible(true);					}
					else {
						JOptionPane.showMessageDialog(null, "Uneli ste pogresne podatke!");
					}
			}
		}
	});
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LoginGUI.this.setVisible(false);
				LoginGUI.this.dispose();
			}	
		});
	}
	
}
