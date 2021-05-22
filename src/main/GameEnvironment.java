package main;
import java.util.ArrayList;
import java.util.Random;

import events.RandomEvent;
import exceptions.InvalidState;
import islands.*;
import ships.*;
import items.Item;

/**
 * The main controller of the game.
 * All interactions with the game should be through this class.
 * @author Andrew Hall and fma107
 */
public class GameEnvironment {
	// Settings - Variables the user changes
	private String name;
	private Ship ship;
	private int gameLength;
	
	// Tracking progress
	private GameState state;
	private int money;
	private int totalProfit;
	private ArrayList<Item> allSold = new ArrayList<Item>();
	private int gameTime;
	private Island[] allIslands;
	private Route[] allRoutes;
	private Island currentIsland;
	// Sailing Specifics
	private Island destination;
	private Route route;
	private int arrivalTime;
	
	
	public GameEnvironment() {
		resetGame();
		generateIslands();
	}
	
	
	/**
	 * Generates the islands, and all the routes between the islands
	 */
	private void generateIslands() {
		Island pratum = new Pratum();
		Island fordina = new Fodina();
		Island tempetas = new Tempestas();
		Island terminus = new Terminus();
		Island officina = new Officina();
		allIslands = new Island[] {pratum, fordina, officina, tempetas, terminus};
		
		//TODO: Maybe add multiple ways of getting between the same islands?
		allRoutes = new Route[] {
				new Route(pratum, 	fordina,  2,  new String[]{"none",  "low",   "low"}),
				new Route(pratum, 	officina, 3,  new String[]{"low",   "none",  "low"}),
				new Route(pratum, 	tempetas, 3,  new String[]{"none",  "high",  "medium"}),
				new Route(pratum, 	terminus, 7,  new String[]{"high",  "low",   "medium"}),
				new Route(fordina, 	officina, 5,  new String[]{"none",  "low",   "low"}),
				new Route(fordina, 	tempetas, 5,  new String[]{"none",  "high",  "medium"}),
				new Route(fordina, 	terminus, 5,  new String[]{"high",  "low",   "medium"}),
				new Route(officina, tempetas, 2,  new String[]{"none",  "high",  "medium"}),
				new Route(officina, terminus, 9,  new String[]{"high",  "low",   "medium"}),
				new Route(tempetas, terminus, 10, new String[]{"high",  "high",  "high"}),
		};
	}
	
	/**
	 * Validates the player's name.<br>
	 * A player name is valid if:<br>
	 * - It's between 3-15 (inclusive) characters long<br>
	 * - It's made up of ONLY alphabetic and space characters (no numbers, punctuation etc.)
	 * @param name The tested name
	 * @return true if the name's valid, false otherwise
	 */
	public boolean isValidName(String name) {
		if (name.length() < 3 || name.length() > 15) {
			return false;
		}
		
		else if (!name.matches("[a-zA-Z ]*")) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Validates the player's preferred game length<br>
	 * The game length is valid if:<br>
	 * - It's an integer
	 * - It's between 20-50 (inclusive)
	 * @param durationString The provided duration
	 * @return true if the duration's valid, false otherwise
	 */
	public boolean isValidDuration(String durationString) {
		try {
			int duration = Integer.parseInt(durationString);
			return (duration >= 20 && duration <= 50);
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
	
	public ArrayList<Item> getAllSold() {
		return allSold;
	}
	
	/**
	 * @return the currentIsland
	 */
	public Island getCurrentIsland() {
		return currentIsland;
	}

	/**
	 * @param currentIsland the currentIsland to set
	 */
	public void setCurrentIsland(Island currentIsland) {
		this.currentIsland = currentIsland;
	}
	
	/**
	 * Generates an array of every available ship
	 * @return Array of ships
	 */
	public static Ship[] getPossibleShips() {
		Ship[] ships = {new TallShip(),
						new WarShip(),
						new Clipper(),
						new Barge(),
						};
		return ships;
	}
	
	public int getGameLength() {
		return gameLength;
	}
	
	/**
	 * Required State: SETUP
	 * @throws InvalidState
	 */
	public void setGameLength(int gameLength) {
		if (state != GameState.SETUP)
			throw new InvalidState();
		this.gameLength = gameLength;
	}

	public String getName() {
		return name;
	}
	/**
	 * 
	 * Required State: SETUP
	 * @throws InvalidState
	 */
	public void setName(String name) {
		if (state != GameState.SETUP)
			throw new InvalidState();
		this.name = name;
	}
	
	public Ship getShip() {
		return ship;
	}
	/**
	 * Required State: SETUP
	 * @throws InvalidState
	 */
	public void setShip(Ship ship) {
		if (state != GameState.SETUP)
			throw new InvalidState();
		this.ship = ship;
		this.money = ship.getStartingMoney();
	}
	
	
	
	// ------------
	
	public int getMoney() {
		return money;
	}
	
	/**
	 * Add money to the player's purse
	 * @param added The amount of money to add
	 * @return The player's current balance
	 */
	public int addMoney(int added) {
		this.money += added;
		this.totalProfit += added;
		return this.money;
	}
	
	/**
	 * Deduct money from the balance. For buying, wages, etc.
	 * @param deducted The amount of money to remove
	 * @return The player's current balance
	 */
	public int removeMoney(int deducted) {
		this.money -= deducted;
		return this.money;
	}
	
	/**
	 * Adds an item to the player cargo hold and keeps track of it in the ledger
	 * @param item The item to add
	 * @param price How much was paid for this item
	 */
	public void buyItem(Item item, int price) {
		
		removeMoney(price);
		item = item.copy().makeBought(price, getCurrentIsland().getStore().getShopName());
		allSold.add(item);
		getShip().storeItem(item);
	}
	

	public Island getDestination() {
		return destination;
	}

	public void setDestination(Island destination) {
		this.destination = destination;
	}
	
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	public GameState getState() {
		return state;
	}

	public int getTotalProfit() {
		return totalProfit;
	}

	public int getGameTime() {
		return gameTime;
	}

	public Island[] getAllIslands() {
		return allIslands;
	}

	public Route[] getAllRoutes() {
		return allRoutes;
	}

	/**
	 * Resets the game's variables &amp; state back to the set-up phase
	 */
	public void resetGame() {
		state = GameState.SETUP;
		name = null;
		ship = null;
		gameLength = -1;
		money = 0;
		totalProfit = 0;
		gameTime = 1;
		currentIsland = null;
		destination = null;
	}
	
	
	/**
	 * Starts the game if setup has been completed,
	 * placing the player onto the starter island
	 * <br>
	 * Required State: SETUP<br>
	 * Transitions into: ON_ISLAND
	 * @return true if name, ship, and gameLength are set. false otherwise
	 */
	public boolean startGame() throws InvalidState {
		// Make sure the game can start
		if (state != GameState.SETUP)
			throw new InvalidState();
		if(name == null || ship == null || gameLength == -1)
			return false;
		
		state = GameState.ON_ISLAND;
		
		currentIsland = allIslands[0];
		return true;
	}
	
	/**
	 * Transitions into an EMBARKING state<br>
	 * Used to enter the "pick route" screen at port
	 */
	public void embark() {
		state = GameState.EMBARKING;
	}
	
	/**
	 * Transitions back into ON_ISLAND<br>
	 * Used when backing out of going somewhere
	 */
	public void disembark() {
		state = GameState.ON_ISLAND;
	}
	
	/**
	 * Transitions into a GAME_OVER state<br>
	 * Used when... well it's self-explanatory
	 */
	public void gameOver() {
		state = GameState.GAME_OVER;
	}


	/**
	 * Transitions the game into a sailing state
	 * @param route The route we're sailing along
	 */
	public void setSail(Route route, int wages) {
		removeMoney(wages);
		setDestination(route.getOtherIsland(currentIsland));
		this.route = route;
		int sailLength = (int)Math.ceil(route.getDistance() / this.getShip().getSpeed());
		this.arrivalTime = getGameTime() + sailLength;
		
		this.state = GameState.SAILING;
	}
	
	/**
	 * @return the route
	 */
	public Route getRoute() {
		return route;
	}


	/**
	 * Deducts the player's money after taking damage
	 * @return The cost of repairing the ship
	 */
	public int repairDamage() {
		int cost = getShip().repairShip();
		removeMoney(cost);
		return cost;
	}
	
	
	/**
	 * Pass a day on the open ocean, paying crew wages and checking for random events
	 * @return Any random events that occur, or null
	 */
	public RandomEvent passDay() {
		this.gameTime++;
		// Check if we've made it to shore yet
		// If so, stop sailing
		if (getGameTime() >= arrivalTime) {
			setCurrentIsland(getDestination());
			state = GameState.ON_ISLAND;
			return null;
		}
		// Check the random events
		RandomEvent triggeredEvent = null;
		for (RandomEvent event : route.getRandomEvents()) {
			float prob = new Random().nextFloat();
			float eventProb = event.getProbability();
			if (prob <= eventProb) {
				triggeredEvent = event;
				break;
			}
		}
		return triggeredEvent;
	}
	
	/**
	 * Pirates steal all your cargo.<br>
	 * If they're not satisfied with what they find, transition into a GAME OVER state.
	 * @return True if the pirates are satisfied with your cargo (game continues)<br>
	 * False if the pirates aren't satisfied (walk the plank, game over)
	 */
	public boolean piratesTakeCargo() {
		ArrayList<Item> stolenCargo = getShip().removeAllCargo();
		// Pirates are satisfied if they steal at least 2 pieces of cargo
		if (stolenCargo.size() >= 2) {
			return true;
		} else {
			state = GameState.GAME_OVER;
			return false;
		}
	}
	
	// !! DEBUGGER FUNCTIONS !!
	public void setState(GameState state) {
		this.state = state;
	}



}
