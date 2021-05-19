import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

public class UpgradeSale extends JFrame {

	private JPanel contentPane;
	private JLabel goldLbl;
	private JLabel capacityLbl;
	private JButton[] buyButtons = new JButton[2];
	private JButton[] sellButtons = new JButton[2];
	
	
	private GameEnvironment environment;
	private Store store;
	

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameEnvironment env = new GameEnvironment();
					env.setName("Jim");
					env.setGameLength(25);
					env.setShip(new WarShip());
					env.startGame();
					UpgradeSale frame = new UpgradeSale(env);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	private void updateDisplay() {
		int money = environment.getMoney();
		goldLbl.setText("Gold: " + money);
		int spareCapacity = environment.getShip().getSpareCapacity();
		capacityLbl.setText("Spare Cargo Capacity: " + spareCapacity);
		
		
		for (int i = 0; i < buyButtons.length; i++) {
			Upgrade upgrade = Store.getPossibleUpgrades()[i];
			if (spareCapacity > 0 && store.getPrice(upgrade) <= money)
				buyButtons[i].setEnabled(true);
			else
				buyButtons[i].setEnabled(false);
			
			if (environment.getShip().hasInCargo(upgrade))
				sellButtons[i].setEnabled(true);
			else
				sellButtons[i].setEnabled(false);
			
		}
	}
	
	
	/**
	 * Create the frame.
	 */
	public UpgradeSale(GameEnvironment environment) {
		this.environment = environment;
		this.store = environment.getCurrentIsland().getStore();
		setTitle("Welcome to %s's store!".formatted(store.getShopName()));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JPanel upgradesPane = new JPanel();
		scrollPane.setViewportView(upgradesPane);
		upgradesPane.setLayout(new GridLayout(0, 4, 0, 0));
		
		
		Store store = environment.getCurrentIsland().getStore();
		for (int i = 0; i < Store.getPossibleUpgrades().length; i++) {
			Upgrade upgrade = Store.getPossibleUpgrades()[i];
			JLabel nameLbl = new JLabel(upgrade.getName());
			upgradesPane.add(nameLbl);
			
			int price = store.getPrice(upgrade);
			
			JLabel priceLbl = new JLabel(String.valueOf(price));
			upgradesPane.add(priceLbl);
			
			JButton buyBtn = new JButton("Buy");
			buyBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					environment.buyItem(upgrade, price);
					store.removeUpgrade(upgrade);
					updateDisplay();
				}
			});
			
			upgradesPane.add(buyBtn);
			buyButtons[i] = buyBtn;
			
			JButton sellBtn = new JButton("Sell");
			sellBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					upgrade.setSalePrice(price); //So the sale price is visible in the ledger
					environment.addMoney(price);
					try {
						Upgrade sold = (Upgrade)environment.getShip().popItem(upgrade.getName());
						store.addUpgrade(sold);
					}
					catch (ItemNotFoundException exception) {
						JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
					updateDisplay();
				}
			});
			upgradesPane.add(sellBtn);
			sellButtons[i] = sellBtn;
		}
		
		
		
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
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
