import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

/**
 * The superclass of all ship types.
 * 
 * @author Andrew Hall
 * 
 */
public abstract class Ship {
	public static final float DAILY_WAGE = 1.0f;
	
	// Each ship have these unique stats
	protected float speed;		// The time a route takes is divided by speed e.g. 10 days / 2 speed = 5 days
	protected String shipType;	// The ship's name e.g. "War Ship"...
	protected int crew;			// Number of crew on the ship
	protected int cargoCapacity;// How many item weight can be carried
	protected float repairCost;	// Every 1.0 damage taken costs this much to fix
	protected int startingMoney;// Better ships start you off with less money
	
	private float damage;		// Random events (rocks, pirates...) can damage a ship
								// Note that a ship can't be destroyed if too badly damaged
	private ArrayList<Item> cargo;	// Here's your cargo
	
	
	public float getDamage() {
		return damage;
	}
	/**
	 * 
	 * @param damage 	The amount of damage to deal
	 * @throws IllegalArgumentException if the given damage is negative
	 */
	public void damageShip(float damage) throws IllegalArgumentException {
		if (damage < 0f)
			throw new IllegalArgumentException("A ship can't take negative damage ("+damage+")");
		this.damage += damage;
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
	 * TODO: Implement once cannons etc. are implemented
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
	 * Searches the cargo hold by name and deletes any 
	 * @param itemName
	 * @return
	 * @throws ItemNotFoundException If the 
	 */
	public Item popItem(String itemName) throws ItemNotFoundException {
		for (Item item: cargo) {
			if (item.getName().equals(itemName))
				return item;
		}
		
		throw new ItemNotFoundException("Item "+itemName+" not found in cargo");
	}
	
}
