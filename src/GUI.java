import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GameSetup setupFrame = new GameSetup(environment);
		setupFrame.setVisible(true);
		

	}

}
