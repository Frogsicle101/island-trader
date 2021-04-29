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
	public static final float DAILY_WAGE = 1.0f;
	
	// Each ship has these unique stats
	private String shipType;	// The ship's name e.g. "War Ship"...
	private float speed;		// The time a route takes is divided by speed e.g. 10 days / 2 speed = 5 days
	private int crew;			// Number of crew on the ship
	private float repairCost;	// Every 1.0 damage taken costs this much to fix
	private int startingMoney;// Better ships start you off with less money
	private int cargoCapacity;// How many item weight can be carried
	
	private float damage;		// Random events (rocks, pirates...) can damage a ship
								// Note that a ship can't be destroyed if too badly damaged
	private ArrayList<Item> cargo;	// Here's your cargo


	/**
	 * Constructor, allowing the ship subclasses to specify what's unique about them
	 * @param speed        	The time a route takes is divided by speed e.g. 10 days / 2 speed = 5 days 
	 * @param shipType     	The ship's name e.g. "War Ship"...                                         
	 * @param crew         	Number of crew on the ship                                                 
	 * @param cargoCapacity	How many item weight can be carried                                        
	 * @param repairCost   	Every 1.0 damage taken costs this much to fix                              
	 * @param startingMoney	Better ships start you off with less money                                 
	 */
	public Ship(float speed, String shipType, int crew, int cargoCapacity, float repairCost, int startingMoney) {
		this.speed = speed;
		this.shipType = shipType;
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
		return speed;
	}
	public String getShipType() {
		return shipType;
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
	 * Searches the cargo hold by name, deletes it from cargo, and returns it
	 * @param itemName	The text name of the item
	 * @return The item in question
	 * @throws ItemNotFoundException	If the given itemName doesn't match anything in cargo
	 */
	public Item popItem(String itemName) throws ItemNotFoundException {
		Item wantedItem;
		for (int i = 0; i < cargo.size(); i++) {
			if (cargo.get(i).getName().equals(itemName)) {
				wantedItem = cargo.get(i);
				cargo.remove(i);
				return wantedItem;
			}
		}
		
		throw new ItemNotFoundException("Item "+itemName+" not found in cargo");
	}
	
}
