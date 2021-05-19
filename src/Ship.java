import java.util.ArrayList;

/**
 * The superclass of all ship types.
 * 
 * A ship is a vessel for holding items, while simultaneously acting as "The Player".
 * Each ship type has a number of unique statistics that can change how the game plays
 * 
 * @author Andrew Hall
 * 
 */
public abstract class Ship {
	public static final float DAILY_WAGE = 0.1f;
	
	// Each ship has these unique stats
	private String shipType;	// The ship's name e.g. "War Ship"...
	private String description; // An overview of the ship
	private float speed;		// The time a route takes is divided by speed e.g. 10 days / 2 speed = 5 days
	private int crew;			// Number of crew on the ship
	private float repairCost;	// Every 1.0 damage taken costs this much to fix
	private int startingMoney;	// Better ships start you off with less money
	private int cargoCapacity;	// How many item weight can be carried
	
	private float damage;		// Random events (rocks, pirates...) can damage a ship
								// Note that a ship can't be destroyed if too badly damaged
	private ArrayList<Item> cargo;	// Here's your cargo


	/**
	 * Constructor, allowing the ship subclasses to specify what's unique about them
	 * @param speed        	The time a route takes is divided by speed e.g. 10 days / 2 speed = 5 days 
	 * @param shipType     	The ship's name e.g. "War Ship...
	 * @param description 	An overview of the ship
	 * @param crew         	Number of crew on the ship                                                 
	 * @param cargoCapacity	How many item weight can be carried                                        
	 * @param repairCost   	Every 1.0 damage taken costs this much to fix                              
	 * @param startingMoney	Better ships start you off with less money                                 
	 */
	public Ship(float speed, String shipType, String description, int crew, int cargoCapacity, float repairCost, int startingMoney) {
		this.speed = speed;
		this.shipType = shipType;
		this.description = description;
		this.crew = crew;
		this.cargoCapacity = cargoCapacity;
		this.repairCost = repairCost;
		this.startingMoney = startingMoney;
		
		cargo = new ArrayList<Item>(cargoCapacity);
	}
	
	/**
	 * Ships are damaged through random events (pirates, rocks...) and damage must be repaired
	 * @param damage 	The amount of damage to deal
	 * @throws IllegalArgumentException if the given damage is negative
	 */
	public void damageShip(float damage) throws IllegalArgumentException {
		if (damage < 0f)
			throw new IllegalArgumentException("A ship can't take negative damage ("+damage+")");
		this.damage += damage;
	}

	public float getDamage() {
		return damage;
	}
	public float getSpeed() {
		int buff = 0;
		for (Item item: cargo) {
			if (item instanceof Sail)
				buff += Sail.SPEED_BUFF;
		}
		
		return speed + buff;
	}
	public String getShipType() {
		return shipType;
	}
	public String getDescription() {
		return description;
	}
	public int getCrew() {
		return crew;
	}
	public int getCargoCapacity() {
		return cargoCapacity;
	}
	public float getRepairCost() {
		return repairCost;
	}
	public int getStartingMoney() {
		return startingMoney;
	}
	public ArrayList<Item> getCargo() {
		return cargo;
	}
	
	/**
	 * Calculates the amount of spare cargo capacity
	 * @return Amount of free cargo space
	 */
	public int getSpareCapacity() {
		return cargoCapacity - cargo.size();
	}
	
	/**
	 * Calculates the bonus received in combat, based on any upgrades in cargo
	 * @return The bonus given to rolls in combat
	 */
	public int getCombatBonus() {
		int bonus = 0;
		for (Item item: cargo) {
			if (item instanceof Cannon)
				bonus += Cannon.FIGHT_BUFF;
		}
		
		return bonus;
	}
	
	/**
	 * Adds an item to your ship's cargo. Note that it can be used for either trade goods, or upgrades.
	 * @param item	Item to add
	 * @throws CargoFullException If adding an item would exceed the cargo capacity
	 */
	public void storeItem(Item item) throws CargoFullException {
		if (getSpareCapacity() <= 0)
			throw new CargoFullException("Item "+item.toString()+" can't fit in the cargo hold");
		cargo.add(item);
	}
	
	/**
	 * TODO: Fix this docstring
	 * Searches the cargo hold by name, deletes it from cargo, and returns it
	 * @param itemName	The text name of the item
	 * @return The item in question
	 * @throws IndexOutOfBoundsException	If the given itemName doesn't match anything in cargo
	 */
	public Item popItem(int cargoIdx) throws IndexOutOfBoundsException {
		Item wantedItem = cargo.get(cargoIdx);
		cargo.remove(cargoIdx);
		return wantedItem;
	}
	
	public Item popItem(String name) throws ItemNotFoundException {
		for (int i = 0; i < cargo.size(); i++) {
			if (cargo.get(i).getName().equals(name)) {
				return popItem(i);
			}
		}
		throw new ItemNotFoundException(name + " is not in cargo");
		
	}
	
	public boolean hasInCargo(Item searchingFor) {
		for (Item item : cargo) {
			if (item.getName().equals(searchingFor.getName()))
				return true;
		}
		return false;
	}
	
	/**
	 * Removes all the cargo from the ship, and returning it.
	 * @return The ship's (former) cargo.
	 */
	public ArrayList<Item> removeAllCargo() {
		@SuppressWarnings("unchecked")
		ArrayList<Item> stolenCargo = (ArrayList<Item>) cargo.clone();
		cargo.clear();
		return stolenCargo;
		
	}
	
	/**
	 * Removes all damage to the ship, calculating what the repairs cost
	 * @return The cost of repairing the ship
	 */
	public int repairShip() {
		int cost = (int) Math.ceil(getDamage() * getRepairCost());	// Round up so repairs don't cost 0
		damage = 0f;
		return cost;
	}
	
}
