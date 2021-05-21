import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import islands.Island;
import islands.Route;
import islands.Store;
import items.Item;
import items.TradeGood;
import items.TradeTypes;
import main.GameEnvironment;
import main.GameState;
import ships.Ship;

class GameEnvTest {
	GameEnvironment env;
	
	/**
	 * Helper function - Puts the game into an "on island" state
	 */
	void gotoTestIsland() {
		env.setGameLength(20);
		env.setName("Test");
		env.setShip(new TestShip());
		env.startGame();
		Island testIsland = new Island(Store.getDefaultStore(), "Test", "Test");
		env.setCurrentIsland(testIsland);
	}
	
	@BeforeEach
	void setUp() {
		env = new GameEnvironment();
	}
	
	/**
	 * Check if the islands have been created
	 */
	@Test
	public void wereIslandsGenerated() {
		assertTrue(env.getAllIslands().length > 0);
	}
	
	/**
	 * Check if the routes have been created
	 */
	@Test
	public void wereRoutesGenerated() {
		assertTrue(env.getAllRoutes().length > 0);
	}
	
	/**
	 * Check if there's a route between every island
	 */
	@Test
	public void routesBetweenEveryIsland() {
		// Setup
		Island[] all_islands = env.getAllIslands();
		Route[] all_routes = env.getAllRoutes();
		HashSet<String> islandNames = new HashSet<>();
		for (Island island : all_islands) {
			islandNames.add(island.getName());
		}
		// Count how many other islands an island can reach
		for (Island island : all_islands) {
			ArrayList<Route> connRoutes = Route.availableRoutes(all_routes, island);
			// Set building is easy in python I miss python :(
			// Get all the islands connected to the current island (+ itself)...
			HashSet<String> setAllIslands = new HashSet<>();
			setAllIslands.add(island.getName());
			for (Route route : connRoutes) {
				setAllIslands.add(route.getOtherIsland(island).getName());
			}
			// ... then make sure every island's present
			assertEquals(island.getName()+" doesn't have a route to every island",
					setAllIslands, islandNames);
			
		}
	}
	
	/**
	 * Checks the name validation
	 */
	@Test
	public void doesNameValidationWork() {
		String[] validNames = {"Jimbo", "You are friend", "   ", "abc", "ABC", "a".repeat(15)};
		String[] invalidNames = {"Bo", "He's cool", "He's cool & smart & awesome!", "a".repeat(16)};
		for (String name : validNames) {
			assertTrue("'%s' should pass".formatted(name),
						env.isValidName(name));
		}
		for (String name : invalidNames) {
			assertFalse("'%s' should fail".formatted(name),
					env.isValidName(name));
		}
	}
	
	/**
	 * Checks the game length validation
	 */
	@Test
	public void doesDurationValidationWork() {
		String[] validLengths = {"20", "040", "50", "000030"};
		String[] invalidLengths = {"one", "cheese", "a", "19", "51", "25!"};
		for (String duration : validLengths) {
			assertTrue("'%s' should pass".formatted(duration),
						env.isValidDuration(duration));
		}
		for (String duration : invalidLengths) {
			assertFalse("'%s' should fail".formatted(duration),
					env.isValidDuration(duration));
		}
	}
	
	/**
	 * Checks if the player's money can be correctly added to
	 */
	@Test
	public void canAddMoney() {
		int origMoney = env.getMoney();
		env.addMoney(20);
		int newMoney = env.getMoney();
		assertEquals(newMoney, origMoney + 20);
	}
	
	/**
	 * Checks if the player's money can be correctly removed
	 */
	@Test
	public void canRemoveMoney() {
		env.addMoney(20);
		int origMoney = env.getMoney();
		env.removeMoney(20);
		int newMoney = env.getMoney();
		assertEquals(newMoney, origMoney - 20);
	}
	
	/**
	 * Checks if purchasing an item works right
	 */
	@Test
	public void canBuyItem() {
		int price = 10;
		TradeGood food = new TradeGood("Food", price, TradeTypes.FOOD);
		gotoTestIsland();
		env.buyItem(food, price);
		ArrayList<Item> cargo = env.getShip().getCargo();
		// Was it added to the ship's cargo?
		boolean containsFood = false;
		for (Item item : cargo) {
			if (item.getName().equals("Food")) {
				containsFood = true;
				food = (TradeGood) item;
				break;
			}
		}
		if (!containsFood) {
			fail("Food wasn't added to the cargo");
		}
		// Does buying correctly assign the price
		assertEquals(food.getPurchasedPrice(), price);
		// Does buying correctly assign where it's bought?
		// Reminder: We're at the "Default store"
		assertEquals(food.getPurchasedFrom(), "Default store");
	}
	
	/**
	 * Makes sure the checks of start game works
	 */
	@Test
	public void startGameWorks() {
		// 1: Shouldn't start with none of the required fields entered
		assertFalse(env.startGame());
		assertEquals(env.getState(), GameState.SETUP);
		// 2: Shouldn't start with only some of the fields filled
		env.setName("Jimmy");
		assertFalse(env.startGame());
		env.setGameLength(25);
		assertFalse(env.startGame());
		assertEquals(env.getState(), GameState.SETUP);
		// 3: Game should start once all the fields are entered
		env.setShip(new TestShip());
		assertTrue(env.startGame());
		assertEquals(env.getState(), GameState.ON_ISLAND);
	}
	
	/**
	 * Checks that we can correctly set sail
	 */
	@Test
	public void canSetSail() {
		// Setup
		gotoTestIsland();
		int tripLength = 10;
		float shipSpeed = env.getShip().getSpeed();
		int travelTime = (int)(tripLength / shipSpeed);
		int wages = 50;
		Island orig = new Island(Store.getDefaultStore(), "orig", "where we came");
		Island dest = new Island(Store.getDefaultStore(), "dest", "where we're going");
		Route route = new Route(orig, dest, tripLength, null);
		int departureDay = env.getGameTime();
		env.addMoney(100);	// We need money for the trip
		env.setCurrentIsland(orig);
		// Set sail!
		env.setSail(route, wages);
		// We should've transitioned into a sailing state
		assertEquals(env.getState(), GameState.SAILING);
		// We should be going to the other island
		assertEquals(env.getDestination(), dest);
		// We should've been charged $50
		assertEquals(env.getMoney(), 50);
		// We should arrive in the provided number of days
		assertEquals(departureDay+travelTime, env.getArrivalTime());
		
	}
	
	/**
	 * Tests if the correct number of days pass
	 */
	@Test
	public void playerArrivesOnTime() {
		// Setup
		gotoTestIsland();
		int departureDay = env.getGameTime();
		Island orig = new Island(Store.getDefaultStore(), "orig", "where we came");
		Island dest = new Island(Store.getDefaultStore(), "dest", "where we're going");
		Route route = new Route(orig, dest, 2, new String[] {"none", "none", "none"});
		env.setCurrentIsland(orig);
		// Set sail!
		env.addMoney(20);	// Need the departure fee
		env.setSail(route, 20);
		// The route is only 2 days long, and the default ship has 1x speed
		env.passDay();
		env.passDay();
		// Have 2 days passed
		assertEquals(env.getGameTime(), departureDay + 2);
	}
	
	/**
	 * Checks if the ship arrives at the right time
	 */
	@Test
	public void sailingArrivesAtDestination() {
		// Setup
		gotoTestIsland();
		int departureDay = env.getGameTime();
		Island orig = new Island(Store.getDefaultStore(), "orig", "where we came");
		Island dest = new Island(Store.getDefaultStore(), "dest", "where we're going");
		Route route = new Route(orig, dest, 2, new String[] {"none", "none", "none"});
		env.setCurrentIsland(orig);
		// Set sail!
		env.addMoney(20);	// Need the departure fee
		env.setSail(route, 20);
		// The route is only 2 days long, and the default ship has 1x speed
		env.passDay();
		// Only a day has passed, we should still be sailing
		assertEquals(env.getState(), GameState.SAILING);
		// Two days have passed, we should have arrived by now
		env.passDay();
		assertEquals(env.getState(), GameState.ON_ISLAND);
		assertEquals(env.getCurrentIsland(), dest);
	}
	
	/**
	 * Checks that damaging and repairing a ship works correctly
	 */
	@Test
	public void isDamageDealtAndRepairedCorrectly() {
		gotoTestIsland();
		// Gotta have money to repair
		env.addMoney(50);
		env.repairDamage();	// Just to be sure it's a baseline
		Ship ship = env.getShip();
		int repairCost = (int)ship.getRepairCost();
		// Deal 1 point of damage
		ship.damageShip(1f);
		// Repair said damage
		int claimedRepairCost = env.repairDamage();
		// Was the correct amount charged?
		// (Test ship costs 1 gold per damage point)
		assertEquals(claimedRepairCost, repairCost);
		// Was the money removed from the player?
		assertEquals(env.getMoney(), 50-repairCost);
	}
	
	/**
	 * Tests that the pirates will take your cargo, and leave you alone
	 * if they take enough
	 */
	@Test
	public void canThePiratesBeSatisfied() {
		gotoTestIsland();
		env.setState(GameState.SAILING);
		Ship ship = env.getShip();
		// Pirates are satisfied if they take at least 2 pieces of cargo
		Item toStore = TradeGood.ALL_GOODS[0].copy().makeBought(10, "Test");
		ship.storeItem(toStore);
		ship.storeItem(toStore);
		// Now oh no you're being boarded!
		boolean piratesSatisfied = env.piratesTakeCargo();
		// Side test - Do they actually take all your cargo
		assertTrue("Pirates should've cleared out your cargo",
					ship.getCargo().size() == 0);
		// You had enough cargo, they should leave you alone now
		assertTrue(piratesSatisfied);
		// You should still be sailing
		assertEquals(env.getState(), GameState.SAILING);
	}
	
	public static void main(String[] args) {
		GameEnvTest self = new GameEnvTest();
		self.setUp();
		//self.something
	}
}

