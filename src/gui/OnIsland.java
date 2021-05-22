package gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import items.Item;
import main.GameEnvironment;

import java.awt.GridBagLayout;
import javax.swing.JTextPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class OnIsland extends JFrame {

	private static final long serialVersionUID = -2570954685247136260L;
	
	private JPanel contentPane;
	private GameEnvironment environment;
	private JLabel lblDay, lblGold, lblCapacity;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//TODO: Make this into a unit test?
					GameEnvironment env = new GameEnvironment();
					env.setGameLength(20);
					env.setName("test");
					env.setShip(GameEnvironment.getPossibleShips()[0]);
					env.startGame();
					GUI.environment = env;
					OnIsland frame = new OnIsland(env);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OnIsland(GameEnvironment environment) {
		this.environment = environment;
		JFrame self = this;
		
		if (environment.getShip().getDamage() > 0f) {
			float damage = environment.getShip().getDamage();
			int cost = environment.repairDamage();
			JOptionPane.showMessageDialog(contentPane,
										"During the voyage, your ship took %.2f damage\nYou pay %d gold to repair your ship".formatted(damage, cost),
										"Paying off damages",
										JOptionPane.INFORMATION_MESSAGE
			);
		}
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle(this.environment.getCurrentIsland().getName());
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel headerPanel = new JPanel();
		GridBagConstraints gbc_headerPanel = new GridBagConstraints();
		gbc_headerPanel.insets = new Insets(0, 0, 5, 0);
		gbc_headerPanel.fill = GridBagConstraints.BOTH;
		gbc_headerPanel.gridx = 0;
		gbc_headerPanel.gridy = 0;
		contentPane.add(headerPanel, gbc_headerPanel);
		GridBagLayout gbl_headerPanel = new GridBagLayout();
		gbl_headerPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_headerPanel.rowHeights = new int[]{0, 0};
		gbl_headerPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_headerPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		headerPanel.setLayout(gbl_headerPanel);
		
		lblDay = new JLabel("<<DAY>>");
		GridBagConstraints gbc_lblDay = new GridBagConstraints();
		gbc_lblDay.insets = new Insets(0, 0, 0, 5);
		gbc_lblDay.gridx = 0;
		gbc_lblDay.gridy = 0;
		headerPanel.add(lblDay, gbc_lblDay);
		
		lblGold = new JLabel("<<GOLD>>");
		GridBagConstraints gbc_lblGold = new GridBagConstraints();
		gbc_lblGold.insets = new Insets(0, 0, 0, 5);
		gbc_lblGold.gridx = 1;
		gbc_lblGold.gridy = 0;
		headerPanel.add(lblGold, gbc_lblGold);
		
		lblCapacity = new JLabel("<<SPARE CAPACITY>>");
		GridBagConstraints gbc_lblCapacity = new GridBagConstraints();
		gbc_lblCapacity.gridx = 2;
		gbc_lblCapacity.gridy = 0;
		headerPanel.add(lblCapacity, gbc_lblCapacity);
		
		JTextPane describeIslandPane = new JTextPane();
		GridBagConstraints gbc_describeIslandPane = new GridBagConstraints();
		gbc_describeIslandPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_describeIslandPane.insets = new Insets(0, 0, 5, 0);
		gbc_describeIslandPane.gridx = 0;
		gbc_describeIslandPane.gridy = 1;
		contentPane.add(describeIslandPane, gbc_describeIslandPane);
		String message = "Current location: " + environment.getCurrentIsland().getName() + "\n" + 
				environment.getCurrentIsland().getDescription();
		describeIslandPane.setText(message);
		
		describeIslandPane.setBackground(SystemColor.menu);
		describeIslandPane.setEditable(false);
		
		
		JButton setSailBtn = new JButton("Set sail");
		setSailBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				environment.embark();
				GUI.openNextFrame();
				dispose();
			}
		});
		GridBagConstraints gbc_setSailBtn = new GridBagConstraints();
		gbc_setSailBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_setSailBtn.insets = new Insets(10, 0, 5, 0);
		gbc_setSailBtn.gridx = 0;
		gbc_setSailBtn.gridy = 3;
		contentPane.add(setSailBtn, gbc_setSailBtn);
		
		JButton vistStoreBtn = new JButton("Visit the store");
		vistStoreBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaleScreen frame = new SaleScreen(environment, self);
				frame.setVisible(true);
			}
		});
		GridBagConstraints gbc_vistStoreBtn = new GridBagConstraints();
		gbc_vistStoreBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_vistStoreBtn.insets = new Insets(0, 0, 5, 0);
		gbc_vistStoreBtn.gridx = 0;
		gbc_vistStoreBtn.gridy = 4;
		contentPane.add(vistStoreBtn, gbc_vistStoreBtn);
		JButton viewShipBtn = new JButton("View ship characteristics");
		viewShipBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewShipCharacteristics frame = new ViewShipCharacteristics(environment.getShip(), self);
				frame.setVisible(true);
			}
		});
		GridBagConstraints gbc_viewShipBtn = new GridBagConstraints();
		gbc_viewShipBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_viewShipBtn.insets = new Insets(0, 0, 5, 0);
		gbc_viewShipBtn.gridx = 0;
		gbc_viewShipBtn.gridy = 5;
		contentPane.add(viewShipBtn, gbc_viewShipBtn);
		
		JButton viewLedgerBtn = new JButton("View the ledger");
		viewLedgerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Type casting juggle
				ArrayList<Item> soldItems = environment.getAllSold();
				Item[] soldArray = new Item[soldItems.size()];
				soldArray = soldItems.toArray(soldArray);
				ViewLedger frame = new ViewLedger(soldArray, self);
				frame.setVisible(true);
			}
		});
		GridBagConstraints gbc_viewLedgerBtn = new GridBagConstraints();
		gbc_viewLedgerBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_viewLedgerBtn.insets = new Insets(0, 0, 5, 0);
		gbc_viewLedgerBtn.gridx = 0;
		gbc_viewLedgerBtn.gridy = 6;
		contentPane.add(viewLedgerBtn, gbc_viewLedgerBtn);
		
		JButton quitBtn = new JButton("Quit");
		quitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Farewell", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
		GridBagConstraints gbc_quitBtn = new GridBagConstraints();
		gbc_quitBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_quitBtn.gridx = 0;
		gbc_quitBtn.gridy = 7;
		contentPane.add(quitBtn, gbc_quitBtn);
		
		updateStats();
	}
	
	/**
	 * Updates the day, gold, and ship capacity.<br>
	 * Used by child windows to change the display as needed
	 */
	public void updateStats() {
		lblDay.setText("Day: %d of %d".formatted(environment.getGameTime(), environment.getGameLength()));
		lblGold.setText("Gold: "+environment.getMoney());
		lblCapacity.setText("Spare Cargo Capacity: " + environment.getShip().getSpareCapacity());
	}

}
