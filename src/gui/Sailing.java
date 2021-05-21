/**
 * !! KNOWN BUGS !!
 * How do you destroy the timer? I stop it but it keeps ticking even after the window's disposed of
 */

package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.GameEnvironment;
import main.GameState;
import events.*;
import items.Item;
import islands.Route;
import ships.Ship;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JProgressBar;

public class Sailing extends JFrame {

	private JPanel contentPane;
	private JLabel dayLbl;
	private JProgressBar progressBar;
	private Timer timer;
	
	private GameEnvironment environment;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameEnvironment env = new GameEnvironment();
					env.resetGame();
					env.setGameLength(20);
					env.setName("test");
					env.setShip(GameEnvironment.getPossibleShips()[0]);
					env.startGame();
					env.setSail(env.getAllRoutes()[0], 0);
					Sailing frame = new Sailing(env);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Handles the currently drawn random event
	 * @param event This day's random event
	 */
	private void processEvent(RandomEvent event) {
		if (event instanceof Weather) {
			float damage = ((Weather)event).getDamage();
			String message = "You took %.2f damage in a storm!".formatted(damage);
			environment.getShip().damageShip(damage);
			JOptionPane.showMessageDialog(contentPane, message, "Weather Event!", JOptionPane.INFORMATION_MESSAGE);
		} else if (event instanceof Rescue) {
			Rescue rescue = (Rescue)event;
			String message = "Rescued %d sailors.".formatted(rescue.getNumSailors()) + "\n" +
							"They thank you with %d gold.".formatted(rescue.getReward());
			JOptionPane.showMessageDialog(contentPane, message, "Shipwrecked Sailors!", JOptionPane.INFORMATION_MESSAGE);
			environment.addMoney(rescue.getReward());
		} else if (event instanceof PirateAttack) {
			processPirateAttack((PirateAttack) event);
		} else {
			System.out.println("Error: Can't handle RandomEvent of type " + event.getClass().getTypeName());
		}
	}
	
	private void processPirateAttack(PirateAttack pirate) {
		Ship ship = environment.getShip();
		int bonus = ship.getCombatBonus();
		String initial_message = "Pirates board your ship\n"
				+ "Roll the die to see if you can fight them off";
		JOptionPane.showOptionDialog(contentPane,
                initial_message,
                "Pirate Attack!",
                JOptionPane.OK_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[] {"Roll"},
                null);
		
		
		String message = "";
		int roll = new Random().nextInt(6) + 1; // Between 1-6 inclusive
		message += "Rolled a %d (%d + %d bonus)\n".formatted(roll+bonus, roll, bonus);
		PirateOutcome outcome = pirate.outcome(roll + bonus);
		// Determine the outcome
		switch (outcome) {
		case WIN:
			message += "Success! The pirate ship sinks without you taking a scratch";
			break;
		case DAMAGED:
			float damage = pirate.getDamageDealt();
			message += "The pirates have been repelled, however %.2f damage has been taken".formatted(damage);
			ship.damageShip(damage);
			break;
		case LOSS:
			message += "The pirates board your ship and take all your cargo!\n";
			// Catalog all the stolen items
			ArrayList<Item> lostCargo = ship.getCargo();
			message += "Cargo lost:\n";
			int valueLost = 0;
			for (Item item : lostCargo) {
				valueLost += item.getPurchasedPrice();
				message += "- %s | %d gold\n".formatted(item.getName(), item.getPurchasedPrice());
			}
			message += "Lost "+valueLost+" gold worth of items\n";
			// Did you have enough cargo to satisfy the pirates?
			boolean satisfied = environment.piratesTakeCargo();
			if (satisfied) {
				message += "They sail away with all your cargo";
			} else {
				// Game over
				message += "The pirates aren't satisfied with the measly contents of your cargo hold!\n" + 
							"Out of anger, they make you and your entire crew walk the plank.";
				timer.stop();
				GUI.openNextFrame();
				dispose();
			}
			break;
		}
		JOptionPane.showMessageDialog(contentPane, message, "Pirate Attack!", JOptionPane.INFORMATION_MESSAGE);
		
	}
	

	/**
	 * Create the frame.
	 */
	public Sailing(GameEnvironment environment) {
		this.environment = environment;
		Route route = environment.getRoute();
		int distance = route.getDistance();
		float shipSpeed = environment.getShip().getSpeed();
		int days = (int) (distance / shipSpeed);
		int startTime = environment.getGameTime();
		
				
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		dayLbl = new JLabel("Day: " + environment.getGameTime());
		GridBagConstraints gbc_dayLbl = new GridBagConstraints();
		gbc_dayLbl.anchor = GridBagConstraints.WEST;
		gbc_dayLbl.insets = new Insets(0, 0, 5, 0);
		gbc_dayLbl.gridx = 0;
		gbc_dayLbl.gridy = 0;
		contentPane.add(dayLbl, gbc_dayLbl);
		
		JLabel destinationLbl = new JLabel("Sailing to: " + environment.getDestination().getName());
		GridBagConstraints gbc_destinationLbl = new GridBagConstraints();
		gbc_destinationLbl.anchor = GridBagConstraints.WEST;
		gbc_destinationLbl.insets = new Insets(0, 0, 5, 0);
		gbc_destinationLbl.gridx = 0;
		gbc_destinationLbl.gridy = 1;
		contentPane.add(destinationLbl, gbc_destinationLbl);
		
				
		progressBar = new JProgressBar();
		progressBar.setMaximum(days);
		progressBar.setStringPainted(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.anchor = GridBagConstraints.SOUTH;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 3;
		contentPane.add(progressBar, gbc_progressBar);
		
		ActionListener timerListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (environment.getState() == GameState.ON_ISLAND) {
					timer.stop();
					GUI.openNextFrame();
					dispose();
				}
				
				RandomEvent event = environment.passDay();
				if (event != null) {
					timer.stop(); //Stop the timer while the random event runs
					processEvent(event);
					// Random events can cause a game over (pirates...)
					if (environment.getState() == GameState.GAME_OVER) {
						return;
					}
					timer.start();
				}
				progressBar.setValue(environment.getGameTime() - startTime);
				dayLbl.setText("Day: " + environment.getGameTime());
				
				
				
			}
		};
		
		
		timer = new Timer(600, timerListener);
		timer.setInitialDelay(1000);
		timer.start();
		
		
	}

}
