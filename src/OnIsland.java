import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class OnIsland extends JFrame {

	private JPanel contentPane;
	private GameEnvironment environment;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		
		JLabel lblNewLabel = new JLabel("Day: " + environment.getGameTime());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		headerPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Gold: " + environment.getMoney());
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		headerPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Spare Cargo Capacity: " + environment.getShip().getSpareCapacity());
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 0;
		headerPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JTextPane textPane = new JTextPane();
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPane.insets = new Insets(0, 0, 5, 0);
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 1;
		contentPane.add(textPane, gbc_textPane);
		String message = "Current location: " + environment.getCurrentIsland().getName() + "\n" + 
				environment.getCurrentIsland().getDescription();
		textPane.setText(message);
		
		textPane.setBackground(SystemColor.menu);
		textPane.setEditable(false);
		
		
		JButton setSailBtn = new JButton("Set sail");
		GridBagConstraints gbc_setSailBtn = new GridBagConstraints();
		gbc_setSailBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_setSailBtn.insets = new Insets(10, 0, 5, 0);
		gbc_setSailBtn.gridx = 0;
		gbc_setSailBtn.gridy = 3;
		contentPane.add(setSailBtn, gbc_setSailBtn);
		
		JButton vistStoreBtn = new JButton("Visit the store");
		vistStoreBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaleScreen frame = new SaleScreen(environment);
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
				ViewShipCharacteristics frame = new ViewShipCharacteristics(environment.getShip());
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
				ViewLedger frame = new ViewLedger(soldArray);
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
		GridBagConstraints gbc_quitBtn = new GridBagConstraints();
		gbc_quitBtn.gridx = 0;
		gbc_quitBtn.gridy = 7;
		contentPane.add(quitBtn, gbc_quitBtn);
	}

}
