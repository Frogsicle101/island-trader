import com.sun.jdi.InvalidStackFrameException;

/**
 * The main controller of the game.
 * All interactions with the game should be through this class.
 * @author Andrew Hall
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
	// private object[] allSales TODO: Figure this out eventually
	private int gameTime;
	private Island[] islands;
	private Island currentIsland;
	private Island destination;
	
	public int getGameLength() {
		return gameLength;
	}
	
	/**
	 * Required State: SETUP
	 * @throws InvalidState
	 */
	public void setGameLength(int gameLength) throws InvalidState {
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
	public void setName(String name) throws InvalidState {
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
	public void setShip(Ship ship) throws InvalidState {
		if (state != GameState.SETUP)
			throw new InvalidState();
		this.ship = ship;
	}
	
	// ------------
	
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
		islands = null;
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
		// TODO - GET THE USER ONTO AN ISLAND
		return true;
	}

}
