import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

/**
 * The main controller of the game.
 * All interactions with the game should be through this class.
 * @author Andrew Hall & fma107
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
				new Route(pratum, fordina, 2, new String[]{"none", "low", "low"}),
				new Route(pratum, officina, 3, new String[]{"low", "none", "low"}),
				new Route(pratum, tempetas, 3, new String[]{"none", "high", "medium"}),
				new Route(pratum, terminus, 7, new String[]{"high", "low", "medium"}),
				new Route(fordina, officina, 5, new String[]{"none", "low", "low"}),
				new Route(fordina, tempetas, 5, new String[]{"none", "high", "medium"}),
				new Route(fordina, terminus, 5, new String[]{"high", "low", "medium"}),
				new Route(officina, tempetas, 2, new String[]{"none", "high", "medium"}),
				new Route(officina, terminus, 9, new String[]{"high", "low", "medium"}),
				new Route(tempetas, terminus, 10, new String[]{"high", "high", "high"}),
		};
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
	
	public Ship[] getPossibleShips() {
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
	 * @param item
	 * @param price
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
	 * Resets the game's variables & state back to the set-up phase
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
	 * Transitions the game into a sailing state
	 * @param route The route we're sailing along
	 */
	public void setSail(Route route, int wages) {
		removeMoney(wages);
		route.getOtherIsland(currentIsland);
		setDestination(route.getOtherIsland(currentIsland));
		this.route = route;
		int sailLength = (int)(route.getDistance() / this.getShip().getSpeed());
		this.arrivalTime = getGameTime() + sailLength;
		
		this.state = GameState.SAILING;
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

}
