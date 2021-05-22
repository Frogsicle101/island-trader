package gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import islands.Island;
import islands.Route;
import islands.Store;
import items.Item;
import ships.Ship;
import main.GameEnvironment;

import javax.swing.JButton;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Embark extends JFrame {

	private static final long serialVersionUID = 3925205876529262794L;

	private JPanel contentPane;
	private boolean canAffordRoutes = false;;
	private boolean enoughTimeForRoutes = false;;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameEnvironment env = new GameEnvironment();
					env.setGameLength(20);
					env.setName("test");
					env.setShip(GameEnvironment.getPossibleShips()[0]);
					env.startGame();
					Embark frame = new Embark(env);
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
	public Embark(GameEnvironment environment) {
		Ship ship = environment.getShip();
		Island currentIsland = environment.getCurrentIsland();
		float shipSpeed = environment.getShip().getSpeed();
		
		setTitle("Where would you like to go?");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 7, 0, 0));
		
		
		//Headings
		JLabel destLbl = new JLabel("Destination:");
		destLbl.setFont(new Font("Tahoma", Font.ITALIC, 11));
		panel.add(destLbl);
		
		JLabel timeLbl = new JLabel("Time:");
		timeLbl.setFont(new Font("Tahoma", Font.ITALIC, 11));
		panel.add(timeLbl);
		
		JLabel costLbl = new JLabel("Cost:");
		timeLbl.setFont(new Font("Tahoma", Font.ITALIC, 11));
		panel.add(costLbl);
		
		JLabel pRiskLbl = new JLabel("Pirate Risk");
		pRiskLbl.setFont(new Font("Tahoma", Font.ITALIC, 11));
		panel.add(pRiskLbl);
		
		JLabel wRiskLbl = new JLabel("Weather Risk");
		wRiskLbl.setFont(new Font("Tahoma", Font.ITALIC, 11));
		panel.add(wRiskLbl);
		
		JLabel rRiskLbl = new JLabel("Rescue Risk");
		rRiskLbl.setFont(new Font("Tahoma", Font.ITALIC, 11));
		panel.add(rRiskLbl);
		
		
		//Fills in the extra item at the end of the row for grid layout
		JPanel voidPanel = new JPanel();
		panel.add(voidPanel);
		
		// Fill in the routes
		ArrayList<Route> validRoutes = Route.availableRoutes(environment.getAllRoutes(), currentIsland);
		for (Route route : validRoutes) {
			// Name
			String name = route.getOtherIsland(currentIsland).getName();
			JLabel loopDestLbl = new JLabel(name);
			panel.add(loopDestLbl);
			
			// Time
			int days = (int) Math.ceil(route.getDistance() / shipSpeed);
			JLabel loopTimeLbl = new JLabel(days + " days");
			panel.add(loopTimeLbl);
			
			// Costs
			int wages = (int) (Ship.DAILY_WAGE * ship.getCrew()) * (int)(route.getDistance() / shipSpeed);
			JLabel loopCostLbl = new JLabel(wages+" gold");
			panel.add(loopCostLbl);
			
			// Risks
			String[] risks = route.getRisk();
			for (int j = 0; j < risks.length; j++) {
				JLabel riskLbl = new JLabel(risks[j]);
				panel.add(riskLbl);
			}
			
			// The button
			JButton loopGoBtn = new JButton("Go!");
			// Is there something that makes this voyage impossible?
			// Would taking this route take longer than you have left (in game duration)?
			if (environment.getGameTime() + days > environment.getGameLength()) {
				loopGoBtn.setEnabled(false);
				loopGoBtn.setToolTipText("The game will end while on this voyage");
			} else {
				enoughTimeForRoutes = true;
			}
			// Can you afford to pay your crew during the voyage?
			if (environment.getMoney() < wages) {
				loopGoBtn.setEnabled(false);
				loopGoBtn.setToolTipText("Unfortunately, you can't pay your crew enough to get you there.");
			} else {
				canAffordRoutes = true;
			}
			
			loopGoBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String message = "You pay your crew %d in wages and set off".formatted(wages);
					JOptionPane.showMessageDialog(contentPane, message, "Setting sail...", JOptionPane.INFORMATION_MESSAGE);
					environment.setSail(route, wages);
					
					GUI.openNextFrame();
					dispose();
				}
			});
			panel.add(loopGoBtn);
		}
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			/**
			 * If the player can't progress, it's game over.
			 * Otherwise simply back out
			 */
			public void actionPerformed(ActionEvent e) {
				// Check if we're stuck on the island
				String message = "";
				// The game time will run out while sailing
				if (!enoughTimeForRoutes) {
					message = "Every route will end after your retirement date.\n"
							+ "You sell all your remaining cargo and settle down on "+environment.getCurrentIsland().getName();
					// Sell all the player's cargo
					Store store = environment.getCurrentIsland().getStore();
					for (Item cargo : environment.getShip().getCargo()) {
						int price = store.getPrice(cargo);
						cargo.setSalePrice(price); //So the sale price is visible in the ledger
						environment.addMoney(price);
					}
					// then it's game over
					JOptionPane.showMessageDialog(contentPane, message, "Retirement", JOptionPane.INFORMATION_MESSAGE);
					environment.gameOver();
					
				}
				// We can't afford to pay our men on any voyage
				else if (!canAffordRoutes) {
					if (environment.getShip().getCargo().size() > 0) {
						message = "You can't afford to pay your men on any voyage. Sell some cargo.";
						JOptionPane.showMessageDialog(contentPane, message, "Not Enough Gold", JOptionPane.WARNING_MESSAGE);
						environment.disembark();
					} else { // Cargo hold's empty - GAME OVER
						message = "You can't afford any route, and you have nothing to sell.\nGame Over";
						JOptionPane.showMessageDialog(contentPane, message, "Game Over", JOptionPane.ERROR_MESSAGE);
						environment.gameOver();
					}
				} else {
					environment.disembark();
				}
				// Fall down here if you can actually continue
				GUI.openNextFrame();
				dispose();
			}
		});
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
	}

}
