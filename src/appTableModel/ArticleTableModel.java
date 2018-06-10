package appTableModel;

import java.awt.BorderLayout;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;


import guiChange.ArticleChange;
import articles.Article;
import articles.Dish;
import articles.Drink;
import loadFiles.LoadF;

public class ArticleTableModel extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnAdd = new JButton();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	private JTable articlesTable;
	
	private LoadF entities;
	
	public ArticleTableModel(LoadF entities) {
		this.entities = entities;
		setTitle("Artikli");
		setSize(1000, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/projectImages/hamburger.png"));
		initGui();
		initActions();
	}

	private void initGui() {
		ImageIcon addIcon = new ImageIcon("src/projectImages/add.png");
		btnAdd.setIcon(addIcon);
		mainToolbar.add(btnAdd);
		
		ImageIcon editIcon = new ImageIcon("src/projectImages/edit.png");
		btnEdit.setIcon(editIcon);
		mainToolbar.add(btnEdit);
		
		ImageIcon deleteIcon = new ImageIcon("src/projectImages/delete.png");
		btnDelete.setIcon(deleteIcon);
		mainToolbar.add(btnDelete);
		
		add(mainToolbar, BorderLayout.NORTH);
		
		int countArticles = entities.getDish().size() + entities.getDrink().size();
		String[] header = new String[] {"Tip", "Restoran", "Naziv artikla", "Cena", "Opis", "Kolicina"};
		Object[][] content = new Object[countArticles][header.length];
		
		for(int i = 0; i < entities.getDish().size(); i++) {
			Dish dish = entities.getDish().get(i);
			content[i][0] = dish.getArticle();
			content[i][1] = dish.getRestaurantName().getRestName();
			content[i][2] = dish.getArticleName();
			content[i][3] = dish.getPrice();
			content[i][4] = dish.getDescription();
			content[i][5] = dish.getAmount();
		}
		
		for(int i = 0; i < entities.getDrink().size(); i++) {
			Drink drink = entities.getDrink().get(i);
			int row = entities.getDish().size() + i;
			content[row][0] = drink.getArticle();
			content[row][1] = drink.getRestaurantName().getRestName();
			content[row][2] = drink.getArticleName();
			content[row][3] = drink.getPrice();
			content[row][4] = drink.getDescription();
			content[row][5] = drink.getAmount();
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		articlesTable = new JTable(model);
		articlesTable.setRowSelectionAllowed(true);
		articlesTable.setColumnSelectionAllowed(false);
		articlesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		articlesTable.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(articlesTable);
		add(scrollPane, BorderLayout.CENTER);
		
		
	}
	
	private void initActions() {
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArticleChange arch = new ArticleChange(entities, null);
				arch.setVisible(true);
				
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = articlesTable.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, "Morate izbrati red u tabeli", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String articleName = articlesTable.getValueAt(row, 2).toString();
					Article a = entities.gArticle(articleName);
					if(a != null) {
						ArticleChange ach = new ArticleChange(entities, a);
						ach.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani artikal", "Greska", JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = articlesTable.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String name = articlesTable.getValueAt(red, 2).toString();
					Article artikal = entities.gArticle(name);
					if(artikal != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete artikal?", artikal.getArticleName() + " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							DefaultTableModel model = (DefaultTableModel) articlesTable.getModel();
							if(artikal instanceof Dish) {
								entities.getDish().remove(artikal);
							}else {
								entities.getDrink().remove(artikal);
							}
							model.removeRow(red);
							entities.writeArticles();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani artikal!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
	}
	

}
