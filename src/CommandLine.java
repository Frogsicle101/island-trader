import java.util.Scanner;

/**
 * This class is run when launching the game as a command line application.
 * It provides the link between the player interacting with the computer and the 
 * actual game code in GameEnviroment.
 * @author fma107
 *
 */
public class CommandLine {
	
	static GameEnvironment environment;
	static Scanner keyboard;
	static boolean stillPlaying;
	
	private static void printDashes() {
		System.out.println("---------------------");
	}
	
	public static void displayBanner() {
		printDashes();
		System.out.println("ISLAND TRADER");
		printDashes();
		System.out.println();
	}
	
	
	public static String getValidName() {
		
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
	
	
	public static int getValidDuration() {
		
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
	
	
	public static int getValidShipChoice(int numShips) {
		
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
	
	// ----- Island States -----

	private static void s_setUp() {
		String name = getValidName();
		environment.setName(name);
		System.out.println();
		
		int duration = getValidDuration();
		environment.setGameLength(duration);
		System.out.println();
		
		Ship[] possibleShips = environment.getPossibleShips();
		
		System.out.println("To be a captain, you need a ship! The local shipyard has several available:");
		
		for (int i=0; i<possibleShips.length; i++) { 	// Prints all ships in the format [num] shipType
			System.out.println("[%d] %s".formatted(i, possibleShips[i].getShipType()));
			
		}
		
		Ship chosenShip = possibleShips[getValidShipChoice(possibleShips.length)];
		environment.setShip(chosenShip);
		
		environment.startGame();
	}
	
	private static void s_onIsland() {
		printDashes();
		System.out.println("Current location: " + environment.getCurrentIsland().getName());
		System.out.println(environment.getCurrentIsland().getDescription());
		printDashes();
		System.out.println("[0] Visit the store");
		System.out.println("[1] Set sail");
		System.out.println("[2] Quit game");
		System.out.print("Enter your choice: ");
		
		int choice = keyboard.nextInt();	//TODO: Add validation on this input
		
		switch (choice) {
			case 0: {
				s_visitStore();
				break;
			}
			
			case 2: {
				stillPlaying = false;
				break;
			}
			default:
				System.out.println("Invalid Input");
		}
				
	}
	

	private static void s_visitStore() {
		Store store = environment.getCurrentIsland().getStore();
		
		printDashes();
		
		System.out.println("Welcome to " + store.getShopName() + "");
		System.out.println("Do you wish to buy or sell?\n"
						+ "[1] Buy\n"
						+ "[2] Sell");
		int choice = keyboard.nextInt(); // TODO: We should probably create some helper functions for this
		switch (choice) {
		case 1:	// Buy
			s_wantToBuy(store);
			break;
		case 2: // Sell
			s_wantToSell(store);
			break;
		}
	}
	
	private static void s_wantToBuy(Store store) {
		boolean stillHere = true;
		
		while (stillHere) {
			System.out.println("Here's what we've got for sale:");
			int i = 1;
			for (TradeGood item: TradeGood.ALL_GOODS) {
				int price = store.getPrice(item);
				System.out.println("[" + (i++) + "] " + item.getName() + " | " + price);
			}
		}
	}
	
	private static void s_wantToSell(Store store) {
		
	}
	
	
	
	/**
	 * The main method
	 * 
	 */
	public static void main(String[] args) {
		
		environment = new GameEnvironment();
		keyboard = new Scanner(System.in);
		stillPlaying = true;
		
		displayBanner();

		
		// The main game loop
		
		do {
			switch (environment.getState()) {
			case SETUP:
				s_setUp();
				break;
			case ON_ISLAND:
				s_onIsland();
				break;
			case FIGHTING:
				break;
			case GAME_OVER:
				break;
			case SAILING:
				break;
			}
			
		} while (stillPlaying);
		
		
		keyboard.close();
		
		
		
		
	}

}
