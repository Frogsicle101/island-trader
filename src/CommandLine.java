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
	
	
	public static String getValidName(Scanner keyboard) {
		
		String name;
		
		boolean validName = false;
		do {
			System.out.println("Please enter your name:");
			name = keyboard.nextLine();
			validName = true;		// Assume the input is valid
			
			
			if (name.length() < 3 || name.length() > 15) {
				System.out.println("Name should be between 3 and 15 characters.");
				validName = false;
			}
			
			if (!name.matches("[a-zA-Z ]*")) {
				System.out.println("Name should not contain numbers or special characters.");
				validName = false;
			}
			
			
		} while (!validName);
		
		
		return name;
	}
	
	
	public static int getValidDuration(Scanner keyboard) {
		
		int duration = -1;
		
		boolean validDuration = false;
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
				validDuration = true;
			}
			
			
			
			
		} while (!validDuration);
		
		return duration;
		
	}
	
	
	public static int getValidShipChoice(Scanner keyboard, int numShips) {
		
		int choice = 0;
		
		boolean validChoice = false;
		do {
			System.out.println("To select a ship, input the number next to your choice:");
			
			String input = keyboard.nextLine();
			
					
			if (!input.matches("[0-9]*")) {
				System.out.println("Input was not a valid number");
				continue;
			}
			
			choice = Integer.parseInt(input);
			if (choice < 0 || choice > numShips - 1) {
				System.out.println("Your choice must be between 0 and " + (numShips - 1));
			} else {
				validChoice = true;
			}
			
			
			
			
		} while (!validChoice);
		
		return choice;
		
	}
	

	
	
	/**
	 * The main method
	 * 
	 */
	public static void main(String[] args) {
		
		GameEnvironment environment = new GameEnvironment();
		
		displayBanner();
		
		
		Scanner keyboard = new Scanner(System.in);
		String name = getValidName(keyboard);
		System.out.println();
		int duration = getValidDuration(keyboard);
		System.out.println();
		
		Ship[] possibleShips = environment.getPossibleShips();
		
		System.out.println("To be a captain, you need a ship! The local shipyard has several available:");
		
		for (int i=0; i<possibleShips.length; i++) { 	// Prints all ships in the format [num] shipType
			System.out.println("[%d] %s".formatted(i, possibleShips[i].getShipType()));
			
		}
		
		Ship chosenShip = possibleShips[getValidShipChoice(keyboard, possibleShips.length)];
		
		
		
		
		
		keyboard.close();
		
		
		
		
	}

}
