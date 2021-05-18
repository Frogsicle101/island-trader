import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewShipCharacteristics extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewShipCharacteristics frame = new ViewShipCharacteristics(new TallShip());
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		int num_crew = ship.getCrew();
		
		String text = "Name: %s".formatted(ship.getShipType()) + "\n" +
					ship.getDescription() + "\n" + 
					"Crew: %d (%d gold per day)".formatted(num_crew, (int)(Ship.DAILY_WAGE * num_crew)) + "\n" +
					"Remaining cargo capacity: %d".formatted(ship.getSpareCapacity()) + "\n" +
					"Upgrades:\n";
					
		for (Item item : ship.getCargo()) {
			if (item instanceof Upgrade) {
				text = text + item.getName();
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