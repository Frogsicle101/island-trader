package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import main.GameEnvironment;
import ships.WarShip;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import items.Item;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class GameOverScreen extends JFrame {

	private static final long serialVersionUID = -4251156707658833557L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameEnvironment env = new GameEnvironment();
					env.setName("Test");
					env.setGameLength(20);
					env.setShip(new WarShip());
					env.startGame();
					env.gameOver();
					GameOverScreen window = new GameOverScreen(env);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameOverScreen(GameEnvironment env) {
		JFrame self = this;
		setBounds(100, 100, 500, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// GAME OVER
		JLabel lblGameOver = new JLabel("Game Over");
		lblGameOver.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblGameOver, BorderLayout.NORTH);
		
		// How well you did
		JTextPane statsPane = new JTextPane();
		statsPane.setContentType("text/html");
		statsPane.setEditable(false);
		statsPane.setText("<html><body style=\"font: 'Tahoma'\">" + generateStatsMessage(env) + "</body></html>");
		getContentPane().add(statsPane, BorderLayout.CENTER);
		
		// Click click click click
		JPanel panelButtons = new JPanel();
		getContentPane().add(panelButtons, BorderLayout.SOUTH);
		panelButtons.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnRestart = new JButton("New Game");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				env.resetGame();
				GUI.openNextFrame();
				dispose();
			}
		});
		panelButtons.add(btnRestart);
		
		JButton btnViewLedger = new JButton("View Ledger");
		btnViewLedger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Type casting juggle
				ArrayList<Item> soldItems = env.getAllSold();
				Item[] soldArray = new Item[soldItems.size()];
				soldArray = soldItems.toArray(soldArray);
				ViewLedger frame = new ViewLedger(soldArray, self);
				frame.setVisible(true);
			}
		});
		panelButtons.add(btnViewLedger);
		
		JPanel panelVoidSpace = new JPanel();
		panelButtons.add(panelVoidSpace);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panelButtons.add(btnQuit);		
	}

	
	/**
	 * Generates a message containing all the stats of the game
	 */
	private String generateStatsMessage(GameEnvironment env) {
		return "<b>Name:</b> <i>%s</i><br />".formatted(escape(env.getName()))
			+ "<b>Ship:</b> <i>%s</i>".formatted(env.getShip().getShipType())
			+ "<p>Made it to day <b>%d</b> of <b>%d</b></p>".formatted(env.getGameTime(), env.getGameLength())
			+ "<p><b>Total Profit: </b>%d gold</p>".formatted(env.getTotalProfit());
	}
	
	private String escape(String input) {
		return input.replace("<", "&lt;");
	}

}
