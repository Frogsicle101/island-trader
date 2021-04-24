/**
 * The superclass of all item types.
 * 
 * An item is an object stored in a ship's cargo, or a shop's inventory.
 * It can be either an upgrade (cannons...), or a tradable item
 * @author Andrew
 *
 */
public abstract class Item {
	public static final Item[] ALL_ITEMS = {};
	
	protected String name;
	protected int baseValue;
	protected int purchasedPrice;
	protected String purchasedFrom;
	
	/**
	 * The basic constructor of an item. Likely the version used in shops.
	 * 
	 * @param name		The item's name (Food, wool...)
	 * @param baseValue	The item's base value
	 */
	public Item(String name, int baseValue) {
		this.name = name;
		this.baseValue = baseValue;
	}
	/**
	 * A more detailed constructor, used for purchased items.
	 * Gives info on how much it cost, and where it was purchased.
	 * 
	 * @param name				The item's name (Food, wool...)
	 * @param baseValue			The item's base value
	 * @param purchasedPrice	How much it was bought for
	 * @param purchasedFrom		The name of the island it was bought from
	 */
	public Item(String name, int baseValue, int purchasedPrice, String purchasedFrom) {
		this.name = name;
		this.baseValue = baseValue;
		this.purchasedPrice = purchasedPrice;
		this.purchasedFrom = purchasedFrom;
	}
	
	public String getName() {
		return name;
	}

	public int getBaseValue() {
		return baseValue;
	}

	public int getPurchasedPrice() {
		return purchasedPrice;
	}

	public String getPurchasedFrom() {
		return purchasedFrom;
	}
	
	/**
	 * Creates a new copy of this item.
	 * Useful when moving items from the list of all items, to cargo etc.
	 * @param <T> Required because subclasses are annoying
	 * @return A new copy of this class
	 */
	public abstract <T extends Item> T copy();
	
	/**
	 * Fills out an existing item's `purchased...` fields
	 * @param <T> Required because subclasses are annoying
	 * @param purchasedPrice	How much it was bought for
	 * @param purchasedFrom		The name of the island it was bought from
	 * @return this (For chaining)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Item> T makeBought(int purchasedPrice, String purchasedFrom) {
		this.purchasedPrice = purchasedPrice;
		this.purchasedFrom = purchasedFrom;
		return (T)this;
	}
}
