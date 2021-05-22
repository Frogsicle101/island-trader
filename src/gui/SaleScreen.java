package gui;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import exceptions.ItemNotFoundException;
import islands.Store;
import items.TradeGood;
import ships.WarShip;
import main.GameEnvironment;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

/**
 * 
 * @author fma107
 */
public class SaleScreen extends JDialog {

	private static final long serialVersionUID = -3815222942497164787L;

	private JPanel contentPane;
	private JLabel goldLbl;
	private JLabel capacityLbl;
	private JButton[] buyButtons = new JButton[TradeGood.ALL_GOODS.length];
	private JButton[] sellButtons = new JButton[TradeGood.ALL_GOODS.length];
	
	
	private GameEnvironment environment;
	private Store store;
	private Container parent;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameEnvironment env = new GameEnvironment();
					env.setName("Jim");
					env.setGameLength(25);
					env.setShip(new WarShip());
					env.startGame();
					SaleScreen frame = new SaleScreen(env, new OnIsland(env));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void updateDisplay() {
		int money = environment.getMoney();
		goldLbl.setText("Gold: " + money);
		int spareCapacity = environment.getShip().getSpareCapacity();
		capacityLbl.setText("Spare Cargo Capacity: " + spareCapacity);
		
		
		for (int i = 0; i < buyButtons.length; i++) {
			TradeGood item = TradeGood.ALL_GOODS[i];
			if (spareCapacity > 0 && store.getPrice(item) <= money)
				buyButtons[i].setEnabled(true);
			else
				buyButtons[i].setEnabled(false);
			
			if (environment.getShip().hasInCargo(item))
				sellButtons[i].setEnabled(true);
			else
				sellButtons[i].setEnabled(false);
		}
		if (parent instanceof OnIsland) {
			((OnIsland) parent).updateStats();
		}
	}
	
	
	/**
	 * Create the frame.
	 */
	public SaleScreen(GameEnvironment environment, Container parent) {
		super((Frame)parent);
		this.parent = parent;
		this.environment = environment;
		this.store = environment.getCurrentIsland().getStore();
		SaleScreen self = this;
		setTitle("Welcome to %s's store!".formatted(store.getShopName()));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
		setBounds(100, 100, 497, 326);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{424, 0};
		gbl_contentPane.rowHeights = new int[]{23, 228, 23, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
	
		
		JPanel headerPane = new JPanel();
		GridBagConstraints gbc_headerPane = new GridBagConstraints();
		gbc_headerPane.insets = new Insets(0, 0, 5, 0);
		gbc_headerPane.fill = GridBagConstraints.BOTH;
		gbc_headerPane.gridx = 0;
		gbc_headerPane.gridy = 0;
		contentPane.add(headerPane, gbc_headerPane);
		GridBagLayout gbl_headerPane = new GridBagLayout();
		gbl_headerPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_headerPane.rowHeights = new int[]{0, 0};
		gbl_headerPane.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_headerPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		headerPane.setLayout(gbl_headerPane);
		
		JLabel dayLbl = new JLabel("Day: "+ environment.getGameTime());
		GridBagConstraints gbc_dayLbl = new GridBagConstraints();
		gbc_dayLbl.insets = new Insets(0, 0, 0, 5);
		gbc_dayLbl.gridx = 0;
		gbc_dayLbl.gridy = 0;
		headerPane.add(dayLbl, gbc_dayLbl);
		
		goldLbl = new JLabel();
		GridBagConstraints gbc_goldLbl = new GridBagConstraints();
		gbc_goldLbl.insets = new Insets(0, 0, 0, 5);
		gbc_goldLbl.gridx = 1;
		gbc_goldLbl.gridy = 0;
		headerPane.add(goldLbl, gbc_goldLbl);
		
		capacityLbl = new JLabel();
		GridBagConstraints gbc_capacityLbl = new GridBagConstraints();
		gbc_capacityLbl.gridx = 2;
		gbc_capacityLbl.gridy = 0;
		headerPane.add(capacityLbl, gbc_capacityLbl);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JPanel pricesPane = new JPanel();
		scrollPane.setViewportView(pricesPane);
		pricesPane.setLayout(new GridLayout(0, 4, 0, 0));
		
		
		
		for (int i = 0; i < TradeGood.ALL_GOODS.length; i++) {
			TradeGood item = TradeGood.ALL_GOODS[i];
			JLabel nameLbl = new JLabel(item.getName());
			pricesPane.add(nameLbl);
			
			int price = store.getPrice(item);
			
			JLabel priceLbl = new JLabel(String.valueOf(price));
			pricesPane.add(priceLbl);
			
			JButton buyBtn = new JButton("Buy");
			buyBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					environment.buyItem(item, price);
					updateDisplay();
				}
			});
			
			pricesPane.add(buyBtn);
			buyButtons[i] = buyBtn;
			
			JButton sellBtn = new JButton("Sell");
			sellBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					item.setSalePrice(price); //So the sale price is visible in the ledger
					environment.addMoney(price);
					try {
						environment.getShip().popItem(item.getName());
					}
					catch (ItemNotFoundException exception) {
						JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
					updateDisplay();
				}
			});
			pricesPane.add(sellBtn);
			sellButtons[i] = sellBtn;
		}
		
		
		
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnBuy = new JButton("Upgrades");
		btnBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpgradeSale frame = new UpgradeSale(environment, self);
				frame.setVisible(true);
			}
		});
		panel.add(btnBuy);
		
		JPanel voidPanel = new JPanel();
		panel.add(voidPanel);
		
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(backBtn);
		
		
		

		updateDisplay();
				
		
		
		
		
	}

}
