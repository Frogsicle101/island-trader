import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.CargoFullException;
import exceptions.ItemNotFoundException;
import items.Cannon;
import items.Item;
import items.Sail;
import items.TradeGood;
import items.TradeTypes;
import ships.Ship;

class ShipTest {
	Ship ship;
	// Items we'll be adding
	Item food;
	Item tool;
	Item cannon;
	Item sail;
	// Ship's initial capacity
	int initCapacity;
	/**
	 * Helper function: Adds 2 food, 1 tool, 2 cannons, 1 sail<br>
	 * Total: 6 items
	 * @param ship The ship we're adding to
	 */
	void addCargo(Ship ship) {
		ship.storeItem(food);
		ship.storeItem(cannon);
		ship.storeItem(food);
		ship.storeItem(cannon);
		ship.storeItem(sail);
		ship.storeItem(tool);
	}
	
	@BeforeEach
	void setUp() {
		ship = new TestShip();
		food = new TradeGood("Food", 10, TradeTypes.FOOD);
		tool = new TradeGood("Tool", 10, TradeTypes.TOOL);
		cannon = new Cannon();
		sail = new Sail();
		initCapacity = ship.getCargoCapacity();
	}
	
	/**
	 * Make sure that damage is dealt correctly
	 */
	@Test
	void handlesDamageCorrectly() {
		int repairCost = (int)ship.getRepairCost();
		float dealtDamage = 1f;
		// Firstly, make sure damage is dealt in the first place
		ship.damageShip(dealtDamage);
		assertEquals(ship.getDamage(), dealtDamage);
		// Does damage stack?
		ship.damageShip(dealtDamage);
		assertEquals(ship.getDamage(), 2*dealtDamage);
		// Secondly, make sure it won't accept negative damage
		boolean canHandleNegatives = false;
		try {
			ship.damageShip(-0.1f);
		} catch (IllegalArgumentException e) {
			canHandleNegatives = true;
		}
		if (!canHandleNegatives) {
			fail("Ships shouldn't be able to take negative damage");
		}
		// Thirdly, make sure it can repair damage
		int cost = ship.repairShip();
		assertEquals(ship.getDamage(), 0);
		// ... and the cost is right (damage was dealt twice
		assertEquals(cost, (int)(2*repairCost));
	}
	
	/**
	 * Adding sails should correctly increase the speed
	 */
	@Test
	void correctSpeedBoost() {
		float BASE_SPEED = 1f;
		// Originally, a ship should have no sails
		assertEquals(ship.getSpeed(), BASE_SPEED);
		// Add a bunch of cargo, including one sail
		addCargo(ship);
		assertEquals(ship.getSpeed(), BASE_SPEED + Sail.SPEED_BUFF);
		// Add another sail
		ship.storeItem(sail);
		assertEquals(ship.getSpeed(), BASE_SPEED + (2*Sail.SPEED_BUFF));		
	}
	
	/**
	 * Adding cannons should correctly add a boost
	 */
	@Test
	void correctDamageBoost() {
		// Should have no bonus initially
		assertEquals(ship.getCombatBonus(), 0);
		// Add cargo, including 2 cannons
		addCargo(ship);
		assertEquals(ship.getCombatBonus(), 2*(Cannon.FIGHT_BUFF));
	}
	
	/**
	 * Ship should correctly report how much space is left in the hold
	 */
	@Test
	void hasCorrectSpareCapacity() {
		// TestShip should hold nothing
		assertEquals(ship.getSpareCapacity(), initCapacity);
		// Add 6 items
		addCargo(ship);
		assertEquals(ship.getSpareCapacity(), initCapacity - 6);
		// Remove an item, that makes 5
		ship.popItem(0);
		assertEquals(ship.getSpareCapacity(), initCapacity - 5);
	}
	
	/**
	 * Adding an item should put it in the hold
	 */
	@Test
	void canAddItemToCargo() {
		// TestShip should hold nothing
		assertEquals(ship.getSpareCapacity(), initCapacity);
		// Add items and see if it correctly decrements
		ship.storeItem(food);
		assertEquals(ship.getSpareCapacity(), initCapacity - 1);
		ship.storeItem(cannon);
		assertEquals(ship.getSpareCapacity(), initCapacity - 2);
		ship.storeItem(sail);
		ship.storeItem(tool);
		ship.storeItem(tool);
		assertEquals(ship.getSpareCapacity(), initCapacity - 5);
		// addCargo() adds 6 items, and right now the ship can only hold 5
		// The ship should complain
		assertThrows(CargoFullException.class, () -> addCargo(ship));
	}
	
	/**
	 * Removing cargo should work
	 */
	void canRemoveItemsFromCargo() {
		// Reminder: Adds 6 items
		addCargo(ship);
		assertEquals(ship.getSpareCapacity(), initCapacity - 6);
		// Pop the first item
		// First item should be food
		Item cargoFood = ship.popItem(0);
		assertEquals(food.getName(), cargoFood.getName());
		assertEquals(ship.getSpareCapacity(), initCapacity - 5);
		// Remove an item by name
		Item cargoSail = ship.popItem("Sail");
		assertEquals(sail.getName(), cargoSail.getName());
		assertEquals(ship.getSpareCapacity(), initCapacity - 4);
		// Removing items that don't exist should cause exceptions to be thrown
		assertThrows(IndexOutOfBoundsException.class, () -> ship.popItem(4));
		// addCargo only adds 1 sail, and we already removed it
		assertThrows(ItemNotFoundException.class, () -> ship.popItem("Sail"));
	}
	
	/**
	 * Checks if the ship can state if it has something in cargo
	 */
	@Test
	void canCheckIfCargoExists() {
		addCargo(ship);
		// Should contain a sail, food...
		assertTrue(ship.hasInCargo(sail));
		assertTrue(ship.hasInCargo(food));
		// Shouldn't contain a keyboard
		assertFalse(ship.hasInCargo(new TradeGood("Keyboard", 0, TradeTypes.FOOD)));
		// Remove the sail
		ship.popItem("Sail");
		// Sail shouldn't be there anymore
		assertFalse(ship.hasInCargo(sail));
	}
	
	/**
	 * Checks if the ship can dump all its cargo
	 */
	void canRemoveAllCargo() {
		addCargo(ship);
		assertEquals(ship.getSpareCapacity(), initCapacity-6);
		ship.removeAllCargo();
		assertEquals(ship.getSpareCapacity(), initCapacity);
	}
	
	/**
	 * Checks if the ship can be repaired
	 */
}
