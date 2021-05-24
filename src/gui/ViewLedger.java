package gui;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import items.Item;
import items.TradeGood;

import javax.swing.JTable;


/**
 * Popup window - Displays every item bought by the player.<br>
 * Displays every item's purchase price, purchased at, and sale price (if applicable)

 * @author fma107
 */
public class ViewLedger extends JDialog {

	private static final long serialVersionUID = 7835393291078337369L;

	private JPanel contentPane;
	private JTable table;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewLedger frame = new ViewLedger(TradeGood.ALL_GOODS, new JFrame("Test"));
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
	public ViewLedger(Item[] allSold, JFrame parent) {
		super(parent, "", Dialog.ModalityType.DOCUMENT_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Ledger");
		setBounds(150, 150, 450, 300);
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
