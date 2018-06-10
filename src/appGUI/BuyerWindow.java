package appGUI;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import appTableModel.BuyerOrderTable;
import loadFiles.LoadF;
import user.Users;

public class BuyerWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JMenuBar mainMenu;
	private JMenu articles;
	private JMenuItem articlesItem;
	private JMenuItem ordersItem;
	
	private LoadF entities;
	private Users user;
	
	public BuyerWindow(LoadF entities, Users user) throws ParseException {
		this.entities = entities;
		this.user = user;
		setTitle("Meni kupac-" + user.getUsername());
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		setLocationRelativeTo(null);
		initGui();
		initActions();
		
	}

	private void initActions() {
		articlesItem.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectRestaurant sl = new SelectRestaurant(entities, user);
				sl.setVisible(true);
				
			}
		});
		ordersItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BuyerOrderTable bt = new BuyerOrderTable(entities, user);
				bt.setVisible(true);
				
			}
		});
	}

	private void initGui() {
		getContentPane().setBackground(Color.decode("#e6ffcc"));
		this.mainMenu = new JMenuBar();
		this.articles = new JMenu("Porudzbina");
		this.articlesItem = new JMenuItem("Poruci");
		this.ordersItem = new JMenuItem("Prikazi porudzbine");
		
		this.articles.add(articlesItem);
		this.articles.add(ordersItem);
		
		this.mainMenu.add(articles);
		setJMenuBar(mainMenu);

		
	}
	

}
