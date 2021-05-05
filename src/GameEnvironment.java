import java.util.HashMap;

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


	// Tracking progress
	private GameState state;
	private int money;
	private int totalProfit;
	// private object[] allSales TODO: Figure this out eventually
	private int gameTime;
	private Island[] allIslands;
	private Route[] allRoutes;
	private Island currentIsland;
	private Island destination;
	
	
	public GameEnvironment() {
		resetGame();
		generateIslands();
	}
	
	private void generateIslands() {
		// TODO: Plan out what the islands are gonna be
		///////////// allIslands = new Island[] {new Pratum(), /*Add more as necessary*/};
		// TODO: Generate the routes
	}
	
	public Ship[] getPossibleShips() {
		Ship[] ships = {new WarShip()};
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
		gameTime = 0;
		currentIsland = null;
		destination = null;
	}
	
	
	/**
	 * !!! INCOMPLETE !!!
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
		
		HashMap<TradeTypes, Float> prices = new HashMap<TradeTypes, Float>();
		prices.put(TradeTypes.FOOD, 1f);
		prices.put(TradeTypes.TOOL, 1f);
		prices.put(TradeTypes.ARTIFACT, 1f);
		prices.put(TradeTypes.LUXURY, 1f);
		prices.put(TradeTypes.MATERIAL, 1f);
		
		
		Store tempStore = new Store("Default", prices);
		
		currentIsland = new Island(tempStore, "Default", "description"); 
		return true;
	}

}
