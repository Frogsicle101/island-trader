import java.util.Scanner;

/**
 * This class is run when launching the game as a command line application.
 * It provides the link between the player interacting with the computer and the 
 * actual game code in GameEnviroment.
 * @author fma107
 *
 */
public class CommandLine {
	
	
	public static void displayBanner() {
		System.out.println("---------------------");
		System.out.println("ISLAND TRADER");
		System.out.println("---------------------");
		System.out.println();
	}
	
	
	public static String getValidName() {
		Scanner keyboard = new Scanner(System.in);
		
		String name;
		
		boolean valid_name = false;
		do {
			System.out.println("Please enter your name:");
			name = keyboard.nextLine();
			valid_name = true;		// Assume the input is valid
			
			
			if (name.length() < 3 || name.length() > 15) {
				System.out.println("Name should be between 3 and 15 characters.");
				valid_name = false;
			}
			
			if (!name.matches("[a-zA-Z ]*")) {
				System.out.println("Name should not contain numbers or special characters.");
				valid_name = false;
			}
			
			
		} while (!valid_name);
		
		
		return name;
	}
	
	
	public static int getValidDuration() {
		Scanner keyboard = new Scanner(System.in);
		
		int duration = -1;
		
		boolean valid_duration = false;
		do {
			System.out.println("How long would you like the game to last [20-50 days]:");
			
			String input = keyboard.nextLine();
			
					
			if (!input.matches("[0-9]*")) {
				System.out.println("Input was not a valid number");
				continue;
			}
			
			duration = Integer.parseInt(input);
			if (duration < 20 || duration > 50) {
				System.out.println("The duration should be between 20 and 50 days.");
			} else {
				valid_duration = true;
			}
			
			
			
			
		} while (!valid_duration);
		
		keyboard.close();
		
		return duration;
		
	}

	
	
	/**
	 * The main method
	 * 
	 */
	public static void main(String[] args) {
		
		GameEnvironment environment = new GameEnvironment();
		
		displayBanner();
		
		String name = getValidName();
		System.out.println();
		int duration = getValidDuration();
		System.out.println();
		
		Ship[] possibleShips = environment.getPossibleShips();
		
		System.out.println("To be a captain, you need a ship! The local shipyard has several available:");
		
		for (int i=0; i<possibleShips.length; i++) {
			System.out.println("[%d] %s".formatted(i, possibleShips[i].getShipType()));
		}
		
		
		
		
	}

}
