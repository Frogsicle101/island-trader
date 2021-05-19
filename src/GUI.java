import java.awt.EventQueue;

import javax.swing.UIManager;

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
					GameSetup setupFrame = new GameSetup(environment);
					setupFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		

	}

}
