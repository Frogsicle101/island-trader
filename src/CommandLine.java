import java.util.ArrayList;
import java.util.InputMismatchException;
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
	
	// ----- Input/Output Helper Functions -----
	
	/**
	 * Repeatedly prints `message` until the input is an integer between minRange and maxRange
	 * @param message The message which gets displayed
	 * @param minRange The smallest valid number inclusive
	 * @param maxRange The biggest valid number inclusive
	 * @return The user's number
	 */
	private static int nextInt(String message, int minRange, int maxRange) {
		boolean validInput = false;
		int userNum = -1;
		do {
			try {
				System.out.print(message);
				userNum = keyboard.nextInt();
				if (userNum < minRange || userNum > maxRange)
					System.out.println(String.format("Invalid Range - Must be between %d and %d", minRange, maxRange));
				else
					validInput = true;
			} catch (InputMismatchException _e) {
				System.out.println("Input must be an integer");
				keyboard.next();
			}
		} while (!validInput);
		
		return userNum;
	}
	
	// -----
	
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
	
	
	public static int getValidShipChoice(Ship[] possibleShips) {
		int choice = 0;
		
		System.out.println("To be a captain, you need a ship! The local shipyard has several available:");
		
		for (int i=0; i<possibleShips.length; i++) { 	// Prints all ships in the format [num] shipType
			System.out.println("[%d] %s".formatted(i+1, possibleShips[i].getShipType()));
			
		}
		
		System.out.println("To select a ship, input the number next to your choice:");
		choice = nextInt(">", 1, possibleShips.length);
		return choice - 1;
		
	}
	
	// ----- Island States -----

	private static void s_setUp() {
		// Get name
		String name = getValidName();
		environment.setName(name);
		System.out.println();
		
		// Get duration
		int duration = getValidDuration();
		environment.setGameLength(duration);
		System.out.println();
		
		// Get ship type
		Ship[] possibleShips = environment.getPossibleShips();
		int shipChoice = getValidShipChoice(possibleShips);
		Ship chosenShip = possibleShips[shipChoice];
		environment.setShip(chosenShip);
		
		environment.startGame();
	}
	
	
	private static void s_onIsland() {
		printDashes();
		System.out.println("Current location: " + environment.getCurrentIsland().getName());
		System.out.println(environment.getCurrentIsland().getDescription());
		printDashes();
		System.out.println("[1] Set sail");
		System.out.println("[2] Visit the store");
		System.out.println("[0] Quit game");
		System.out.println("Enter your choice");
		
		int choice = nextInt("> ", 0, 2);
		
		switch (choice) {
			case 1:
				s_setSail();
				break;
			case 2: 
				s_visitStore();
				break;
			case 0:
				stillPlaying = false;
				break;
			default:
				System.out.println("Invalid Input");
		}
				
	}
	

	private static void s_visitStore() {
		Store store = environment.getCurrentIsland().getStore();
		
		printDashes();
		
		System.out.println("Welcome to " + store.getShopName() + "");
		System.out.println(String.format("You have: %d gold, and %d spare cargo capacity.",
									environment.getMoney(), environment.getShip().getSpareCapacity()));
		System.out.println("Do you wish to buy or sell?\n"
						+ "[1] Buy\n"
						+ "[2] Sell\n"
						+ "[0] Back");
		int choice = nextInt("> ", 0, 2);
		switch (choice) {
		case 0:	// 
			break;
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
			int capacity = environment.getShip().getSpareCapacity();
			int playerGold = environment.getMoney();
			// 1. List the items
			System.out.println("Here's what we've got for sale:");
			int i = 0;
			for (TradeGood item: TradeGood.ALL_GOODS) {
				String name = item.getName();
				int price = store.getPrice(item);
				System.out.println(String.format("[%d] %s | %d gold", ++i, name, price));
			}
			// -- Exit condition : No spare cargo space --
			if (capacity == 0) {
				System.out.println("Sorry, you have no spare cargo capacity");
				stillHere = false;
				break;
			}
			// 2. Pick the item to buy
			System.out.println("What item would you like to buy? (Enter '0' to go back)");
			int buyIdx = nextInt("> ", 0, i) - 1;
			// -- Exit Condition : Doesn't wanna buy --
			if (buyIdx == -1) {
				stillHere = false;
				break;
			}
			// 3. Buy it if you can afford it
			TradeGood item = TradeGood.ALL_GOODS[buyIdx];
			int price = store.getPrice(item);
			if (price > playerGold) {
				System.out.println("Sorry, you can not afford that");
			} else {
				environment.removeMoney(price);
				item = item.makeBought(price, store.getShopName());
				environment.getShip().storeItem(item);
			}
		}
	}
	
	
	private static void s_wantToSell(Store store) {
		boolean stillHere = true;
		Ship ship = environment.getShip();
		while (stillHere) {
			// -- Exit condition : No cargo to sell --
			if (ship.getCargo().isEmpty()) {
				System.out.println("You have no cargo to sell.");
				stillHere = false;
				break;
			}
			
			// 1. List the cargo
			System.out.println("Here's your cargo items:");
			int i = 0;
			for (Item item: ship.getCargo()) {
				String name = item.getName();
				int price = store.getPrice(item);
				int priceDiff = price - item.getPurchasedPrice();
				System.out.println(String.format("[%d] %s | %d (%s%d) gold", ++i, name, price, (priceDiff >= 0 ? "+" : ""), priceDiff));
			}
			// 2. Pick the item to sell
			System.out.println("What item would you like to sell? (Enter '0' to do back)");
			int sellIdx = nextInt("> ", 0, i) - 1;
			// -- Exit Condition : Doesn't wanna sell --
			if (sellIdx == -1) {
				stillHere = false;
				break;
			}
			Item item = ship.popItem(sellIdx);
			int price = store.getPrice(item);
			int priceDiff = price - item.getPurchasedPrice();
			environment.addMoney(price);
			System.out.println(String.format("Sold %s for %d gold (%d %s)", item.getName(), price, Math.abs(priceDiff), (priceDiff < 0 ? "loss" : "profit")));
			
		}
	}
	
	
	private static void s_setSail() {
		Island currentIsland = environment.getCurrentIsland();
		Island destination;
		float shipSpeed = environment.getShip().getSpeed();
		ArrayList<Route> validRoutes = Route.availableRoutes(environment.getAllRoutes(), currentIsland);
		int i = 0;
		for (Route route : validRoutes) {
			String name = route.getOtherIsland(currentIsland).getName();
			int days = (int) (route.getDistance() / shipSpeed);
			System.out.println("[%d] %s: %d days, %s risk".formatted(++i, name, days, "RANDOM_EVENTS_INCOMPLETE"));
		}
		System.out.println("Where would you like to sail? (Enter '0' to go back)");
		int choice = nextInt(">", 0, i);
		// -- Breaking condition --
		if (choice == 0)
			return;
		// --------
		// We have a destination, set sail!
		Route route = validRoutes.get(choice - 1);
		destination = route.getOtherIsland(currentIsland);
		environment.setDestination(destination);
		environment.setSail(route);
		
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
