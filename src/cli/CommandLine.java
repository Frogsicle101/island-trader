package cli;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import events.*;
import exceptions.InvalidState;
import islands.Island;
import islands.Route;
import islands.Store;
import items.Item;
import items.TradeGood;
import items.Upgrade;
import main.GameEnvironment;
import ships.Ship;

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
	
	// ----- Setup Input Functions -----
	/**
	 * Gets a valid (string) name from stdin: <br>
	 * - Must be between 3-15 characters long (inclusive)<br>
	 * - Must contain only valid characters (alphabet & spaces)
	 * @return The valid name
	 */
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
	
	/**
	 * Gets a valid game length from stdin:<br>
	 * - Between 20 and 50 (inclusive)
	 * @return The valid game length
	 */
	public static int getValidDuration() {
		
		int duration;
		
		System.out.println("How long would you like the game to last [20-50 days]:");
		duration = nextInt(">", 20, 50);
	
		return duration;
	}
	
	/**
	 * Lists & describes the available ships to the player, returning their choice.<br>
	 * All input/output is though stdin/stdout
	 * @param possibleShips The ships available to choose from
	 * @return 
	 */
	public static Ship getValidShipChoice(Ship[] possibleShips) {
		int choiceIdx = 0;
		boolean hasChosen = false;
		Ship chosenShip;
		System.out.println("To be a captain, you need a ship! The local shipyard has several available:");
		do {
			for (int i=0; i<possibleShips.length; i++) { 	// Prints all ships in the format [num] shipType
				System.out.println("[%d] %s".formatted(i+1, possibleShips[i].getShipType()));
			}
			
			System.out.println("To select a ship, input the number next to your choice:");
			choiceIdx = nextInt(">", 1, possibleShips.length) - 1;
			chosenShip = possibleShips[choiceIdx];
			// Describe the ship
			System.out.println("Name: "+chosenShip.getShipType());
			System.out.println(chosenShip.getDescription());
			System.out.println("Do you wish to use this ship?\n"
							+ "[1] Yes\n"
							+ "[0] No");
			choiceIdx = nextInt(">", 0, 1);
			if (choiceIdx == 1) {
				hasChosen = true;
			}
		} while (!hasChosen);
		return chosenShip;
		
	}
	/**
	 * Main State Function - Gets the player's name, ship, and game length, then starts the game
	 */
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
		Ship[] possibleShips = GameEnvironment.getPossibleShips();
		Ship chosenShip = getValidShipChoice(possibleShips);
		environment.setShip(chosenShip);
		
		environment.startGame();
	}
	
	// ----- Island States -----
	
	/**
	 * Main State Function - Handles the choices a player can make on an island
	 */
	private static void s_onIsland() {
		
		if (environment.getShip().getDamage() > 0) {
			int cost = environment.repairDamage();
			System.out.println("You pay " + cost + " gold to repair your ship.");
		}
		
		
		printDashes();
		System.out.println("Day " + environment.getGameTime());
		System.out.println("Current location: " + environment.getCurrentIsland().getName());
		System.out.println(environment.getCurrentIsland().getDescription());
		
		System.out.println(String.format("You have: %d gold, and %d spare cargo capacity.",
				environment.getMoney(), environment.getShip().getSpareCapacity()));
		
		printDashes();
		System.out.println("[1] Set sail");
		System.out.println("[2] Visit the store");
		System.out.println("[3] View Ship Characteristics");
		System.out.println("[4] View the ledger");
		System.out.println("[0] Quit game");
		System.out.println("Enter your choice");
		
		int choice = nextInt("> ", 0, 4);
		
		switch (choice) {
			case 1:
				s_embark();
				break;
			case 2: 
				s_visitStore();
				break;
			case 3:
				s_viewCharacteristics();
				break;
			case 4:
				s_viewLedger();
				break;
			case 0:
				stillPlaying = false;
				break;
			default:
				System.out.println("Invalid Input");
		}
				
	}
	
	/**
	 * Displays the ship's characteristics (name, crew, upgrades, etc...)
	 */
	private static void s_viewCharacteristics() {
		
		Ship ship = environment.getShip();
		
		printDashes();
		System.out.println("SHIP CHARACTERISTICS");
		
		System.out.println("Name: %s".formatted(ship.getShipType()));
		System.out.println(ship.getDescription());
		int num_crew = ship.getCrew();
		System.out.println("Crew: %d (%d gold per day)".formatted(num_crew, (int)(Ship.DAILY_WAGE * num_crew)));
		System.out.println("Remaining cargo capacity: %d".formatted(ship.getSpareCapacity()));
		System.out.println("Upgrades:");
		for (Item item : ship.getCargo()) {
			if (item instanceof Upgrade) {
				System.out.println(item.getName());
			}
		}
	}
	
	/**
	 * Prints info on every item the user has bought
	 */
	private static void s_viewLedger() {
		printDashes();
		System.out.println("LEDGER");
		System.out.println("Name\tOrigin\tCost\tSale Value");
		for (Item item: environment.getAllSold()) {
			String salePrice = (item.getSalePrice() == 0) ? "-" : String.valueOf(item.getSalePrice());
			
			System.out.println(item.getName() + "\t" + 
							item.getPurchasedFrom() + "\t" +
							item.getPurchasedPrice() + "\t" +
							salePrice);
		}
	}
	
	/**
	 * State function - Shows the player's options at a store
	 */
	private static void s_visitStore() {
		Store store = environment.getCurrentIsland().getStore();
		
		printDashes();
		boolean stayInStore = true;
		System.out.println("Welcome to " + store.getShopName() + "");
		System.out.println(String.format("You have: %d gold, and %d spare cargo capacity.",
									environment.getMoney(), environment.getShip().getSpareCapacity()));
		while (stayInStore) {
			System.out.println("Do you wish to buy or sell?\n"
							+ "[1] Buy\n"
							+ "[2] Buy upgrades\n"
							+ "[3] Sell\n"
							+ "[0] Back");
			int choice = nextInt("> ", 0, 3);
			switch (choice) {
			case 0:	// Back 
				stayInStore = false;
				break;
			case 1:	// Buy
				s_wantToBuy(store);
				break;
			case 2:
				s_wantToBuyUpgrades(store);
				break;
			case 3: // Sell
				s_wantToSell(store);
				break;
			}
		}
	}
	
	/**
	 * State function - Lets the player purchase trade items at the store
	 * @param store The current store
	 */
	private static void s_wantToBuy(Store store) {
		boolean stillHere = true;
		
		while (stillHere) {
			int capacity = environment.getShip().getSpareCapacity();
			int playerGold = environment.getMoney();
			System.out.println("You have: %d gold, and %d spare cargo capacity.".formatted(playerGold, capacity));
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
			System.out.println("[0] Back");
			System.out.println("What item would you like to buy?");
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
				environment.buyItem(item, price);
			}
		}
	}
	
	/**
	 * State function - Lets the player buy upgrade items at the store
	 * @param store The current store
	 */
	private static void s_wantToBuyUpgrades(Store store) {
		boolean stillHere = true;
		
		while (stillHere) {
			int capacity = environment.getShip().getSpareCapacity();
			int playerGold = environment.getMoney();
			System.out.println("You have: %d gold, and %d spare cargo capacity.".formatted(playerGold, capacity));
			// 1. List the items
			System.out.println("Here's what we've got for sale:");
			int i = 0;
			for (Upgrade item: store.getUpgrades()) {
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
			System.out.println("[0] Back");
			System.out.println("What item would you like to buy?");
			int buyIdx = nextInt("> ", 0, i) - 1;
			// -- Exit Condition : Doesn't wanna buy --
			if (buyIdx == -1) {
				stillHere = false;
				break;
			}
			// 3. Buy it if you can afford it
			Item item = store.getUpgrades().get(buyIdx);
			int price = store.getPrice(item);
			if (price > playerGold) {
				System.out.println("Sorry, you can not afford that");
			} else {
				environment.buyItem(item, price);
			}
		}
	}
	
	/**
	 * State function - Lets the player sell their items
	 * @param store The current store
	 */
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
			System.out.println("[0] Back");
			System.out.println("What item would you like to sell?");
			int sellIdx = nextInt("> ", 0, i) - 1;
			// -- Exit Condition : Doesn't wanna sell --
			if (sellIdx == -1) {
				stillHere = false;
				break;
			}
			Item item = ship.popItem(sellIdx);
			int price = store.getPrice(item);
			item.setSalePrice(price); //So the sale price is visible in the ledger
			int priceDiff = price - item.getPurchasedPrice();
			environment.addMoney(price);
			System.out.println(String.format("Sold %s for %d gold (%d %s)", item.getName(), price, Math.abs(priceDiff), (priceDiff < 0 ? "loss" : "profit")));
			
		}
	}
	
	/**
	 * State function - Lets the player choose what island to sail to next
	 */
	private static void s_embark() {
		// -- Breaking condition --
		// Debt Check: Can't sail if the player's in debt
		int money = environment.getMoney();
		if (money < 0) {
			System.out.println("!!! HALT - You're not allowed to leave while %d gold in debt".formatted(-money));
			System.out.println("!!! Sell your cargo at the store to get yourself back in the black");
			return;
		}
		// List all available routes
		printDashes();
		Island currentIsland = environment.getCurrentIsland();
		float shipSpeed = environment.getShip().getSpeed();
		ArrayList<Route> validRoutes = Route.availableRoutes(environment.getAllRoutes(), currentIsland);
		int i = 0;
		for (Route route : validRoutes) {
			String name = route.getOtherIsland(currentIsland).getName();
			int days = (int) (route.getDistance() / shipSpeed);
			String[] risks = route.getRisk();
			System.out.println("[%d] %s: %d days \t(Pirates: %s | Weather: %s | Rescue: %s)"
					.formatted(++i, name, days, risks[0], risks[1], risks[2]));
		}
		System.out.println("[0] Back");
		System.out.println("Where would you like to sail?");
		int choice = nextInt(">", 0, i);
		// -- Breaking condition --
		if (choice == 0)
			return;
		// --------
		// We have a destination, set sail!
		Route route = validRoutes.get(choice - 1);
		
		
		Ship ship = environment.getShip();
		int wages = (int) (Ship.DAILY_WAGE * ship.getCrew() * (route.getDistance() / shipSpeed));
		if (environment.getMoney() > wages) {
			System.out.println("You pay your crew %d in wages and set off".formatted(wages));
			environment.setSail(route, wages);
		}
		else {
			System.out.println("Unfortunately, you can't pay your crew enough to get you there.");
		}
		
	}
	
	// ----- Sailing State -----
	/**
	 * Storms are random events that occur while sailing.<br>
	 * They deal damage to your ship, which must be healed at the nearest island
	 * @param storm The random event
	 */
	private static void ev_storm(Weather storm) {
		float damage = storm.getDamage();
		System.out.println("!!! RANDOM EVENT !!!");
		System.out.println("!!!     STORM    !!!");
		System.out.println("Took %f damage".formatted(damage));
		environment.getShip().damageShip(damage);
	}
	
	/**
	 * Rescues are random events that occur while sailing.<br>
	 * You rescue x amount of sailors, who give a cash reward for saving them
	 * @param rescue The random event
	 */
	private static void ev_rescue(Rescue rescue) {
		System.out.println("!!! RANDOM EVENT !!!");
		System.out.println("!!!    RESCUE    !!!");
		System.out.println("Rescued %d sailors.".formatted(rescue.getNumSailors()));
		System.out.println("They thank you with %d gold.".formatted(rescue.getReward()));
		environment.addMoney(rescue.getReward());
	}
	
	/**
	 * Pirate attacks are random events that occur while sailing.<br>
	 * The player must roll a D6
	 * @param pirate The random event
	 */
	private static void ev_pirate(PirateAttack pirate) {
		Ship ship = environment.getShip();
		System.out.println("!!! RANDOM EVENT !!!");
		System.out.println("!!!    PIRATES   !!!");
		int bonus = ship.getCombatBonus();
		System.out.println("Upgrades: Combat bonus +"+bonus);
		// If you wanted to create more options, this is where you'd do it
		// -- ROLL TO SAVE YOUR LIFE --
		System.out.println("[1] Roll dice (1-6)");
		nextInt(">", 1, 1);
		int roll = new Random().nextInt(6) + 1; // Between 1-6 inclusive
		System.out.println("Rolled a %d (+%d)".formatted(roll, bonus));
		PirateOutcome outcome = pirate.outcome(roll + bonus);
		// Determine the outcome
		System.out.println(outcome.toString());
		switch (outcome) {
		case WIN:
			System.out.println("Success! The pirate ship sinks without you taking a scratch");
			break;
		case DAMAGED:
			float damage = pirate.getDamageDealt();
			System.out.println("The pirates have been repelled, however "+damage+" damage has been taken");
			ship.damageShip(damage);
			break;
		case LOSS:
			System.out.println("The pirates board your ship and take all your cargo!");
			// Catalog all the stolen items
			ArrayList<Item> lostCargo = ship.getCargo();
			printDashes();
			System.out.println("Cargo lost:");
			int valueLost = 0;
			for (Item item : lostCargo) {
				valueLost += item.getPurchasedPrice();
				System.out.println("- %s | %d gold".formatted(item.getName(), item.getPurchasedPrice()));
			}
			System.out.println("Lost "+valueLost+" gold worth of items");
			// Did you have enough cargo to satisfy the pirates?
			boolean satisfied = environment.piratesTakeCargo();
			if (satisfied) {
				System.out.println("They sail away with all your cargo");
			} else {
				System.out.println("The pirates aren't satisfied with the measly contents of your cargo hold!");
				System.out.println("Out of anger, they make you and your entire crew walk the plank.");
			}
			break;
		}
	}
	
	/**
	 * State function - Represent a single day of the player sailing.<br>
	 * During a day at sea, random events can occur.
	 */
	private static void s_sailing() {
		RandomEvent event = environment.passDay();
		
		// Handle random events
		if (event == null)
			return;
		printDashes();
		if (event instanceof Weather) {
			ev_storm((Weather) event);
		} else if (event instanceof Rescue) {
			ev_rescue((Rescue) event);
		} else if (event instanceof PirateAttack) {
			ev_pirate((PirateAttack) event);
		} else {
			System.out.println("Error: Can't handle RandomEvent of type " + event.getClass().getTypeName());
		}
		
		printDashes();
	}
	
	private static void s_gameOver() {
		printDashes();
		System.out.println("Game End");
		System.out.println("Made it to: Day %d of %d".formatted(environment.getGameTime(), environment.getGameLength()));
		System.out.println("Total profit: %d gold".formatted(environment.getTotalProfit()));
		printDashes();
		System.out.println("Restart game?\n"
						+ "[1] Restart\n"
						+ "[0] Quit");
		int choice = nextInt(">", 0, 1);
		if (choice == 1)
			environment.resetGame();
		else
			System.exit(0);
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
			case SAILING:
				s_sailing();
				break;
			case FIGHTING:
				break;
			case GAME_OVER:
				s_gameOver();
				break;
			default:
				throw new InvalidState("Invalid state: "+environment.getState().toString());
			}
			
		} while (stillPlaying);
		
		
		keyboard.close();
		
		
		
		
	}

}
