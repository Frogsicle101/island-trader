import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

public class SaleScreen extends JFrame {

	private JPanel contentPane;
	private JTable table;
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
					SaleScreen frame = new SaleScreen(env);
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
	public SaleScreen(GameEnvironment environment) {
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
		
		
		
		
		String[][] data = new String[TradeGood.ALL_GOODS.length][3];
		for (int i = 0; i < data.length; i++) {
			TradeGood item = TradeGood.ALL_GOODS[i];
			data[i][0] = item.getName();
			data[i][1] = String.valueOf(store.getPrice(item));
			float mult = store.getItemPrices().get(item.getType());
			data[i][2] = (mult == 1 ? "-" : "x%.2f".formatted(mult));
		}
		String[] columnNames = {"Name", "Price", "Multiplier"};
		
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
		
		JLabel lbl_Day = new JLabel("Day: "+environment.getGameTime());
		GridBagConstraints gbc_lbl_Day = new GridBagConstraints();
		gbc_lbl_Day.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_Day.gridx = 0;
		gbc_lbl_Day.gridy = 0;
		headerPane.add(lbl_Day, gbc_lbl_Day);
		
		JLabel lblNewLabel_1 = new JLabel("Gold: "+environment.getMoney());
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		headerPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Spare Cargo Capacity: "+environment.getShip().getSpareCapacity());
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 0;
		headerPane.add(lblNewLabel_2, gbc_lblNewLabel_2);
		table = new JTable(data, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnBuy = new JButton("Buy");
		panel.add(btnBuy);
		
		JPanel voidPanel = new JPanel();
		panel.add(voidPanel);
		
		JButton backBtn = new JButton("Back");
		panel.add(backBtn);
		
		
		
				
		
		
		
		
	}

}
