package gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import items.Item;
import items.Upgrade;
import ships.Ship;
import ships.WarShip;

import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewShipCharacteristics extends JDialog {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewShipCharacteristics frame = new ViewShipCharacteristics(new WarShip());
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
	public ViewShipCharacteristics(Ship ship) {
		setTitle("Ship Characteristics");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		int num_crew = ship.getCrew();
		
		String text = "Name: %s\n".formatted(ship.getShipType())
					+ ship.getDescription() + "\n"
					+ "Crew: %d (%d gold per day)\n".formatted(num_crew, (int)(Ship.DAILY_WAGE * num_crew))
					+ "Remaining cargo capacity: %d\n".formatted(ship.getSpareCapacity())
					+ "Upgrades:";
					
		for (Item item : ship.getCargo()) {
			if (item instanceof Upgrade) {
				text = text + "\n" + item.getName();
			}
		}
				
				
		JTextPane textPane = new JTextPane();
		textPane.setText(text);
		textPane.setEditable(false);
		contentPane.add(textPane, BorderLayout.CENTER);
		
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(backBtn, BorderLayout.SOUTH);
	}

}
