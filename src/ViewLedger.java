import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;

public class ViewLedger extends JFrame {

	private JPanel contentPane;
	private JTable table;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewLedger frame = new ViewLedger(TradeGood.ALL_GOODS);
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
	public ViewLedger(Item[] allSold) {
		setTitle("Ledger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(backBtn, BorderLayout.SOUTH);
		
		
		
		
		String[][] data = new String[allSold.length][4];
		for (int i = 0; i < data.length; i++) {
			Item item = allSold[i];
			data[i][0] = item.getName();
			data[i][1] = item.getPurchasedFrom();
			data[i][2] = String.valueOf(item.getPurchasedPrice());
			String salePrice = (item.getSalePrice() == 0) ? "-" : String.valueOf(item.getSalePrice());
			data[i][3] = salePrice;
		}
		String[] columnNames = {"Name", "Origin", "Cost", "Sale Value"};
		table = new JTable(data, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		
		
				
		
		
		
		
	}

}
