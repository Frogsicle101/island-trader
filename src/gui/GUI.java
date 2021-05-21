package gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import main.GameEnvironment;

/**
 * The main class used to run the GUI of the game.
 * @author fm107
 *
 */
public class GUI {
	
	static GameEnvironment environment;
	
	/**
	 * The main method
	 * @param args
	 */
	public static void main(String[] args) {
		environment = new GameEnvironment();
		environment.resetGame();
				
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					openNextFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

	}
	
	public static void openNextFrame() {
		JFrame frame = new JFrame(); //TODO: Fix this by throwing an exception or something if not in any of the states
		switch (environment.getState()) {
			case SETUP:
				frame = new GameSetup(environment);
				break;
			case ON_ISLAND:
				frame = new OnIsland(environment);
				break;
			case EMBARKING:
				frame = new Embark(environment);
				break;
			case SAILING:
				frame = new Sailing(environment);
				break;
			case FIGHTING:
				break;
			case GAME_OVER:
				frame = new GameOverScreen(environment);
				break;
			default:
				break;
			}
		frame.setVisible(true);
	}

}
