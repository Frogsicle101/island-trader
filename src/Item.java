/**
 * The superclass of all item types.
 * @author Andrew
 *
 */
public abstract class Item {
	public static final Item[] ALL_ITEMS = {};
	
	private String name;
	private int baseValue;
	private int purchasedPrice;
	private String purchasedFrom;
	
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
}
