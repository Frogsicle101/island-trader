/**
 * Superclass for all items which give the ship an advantage, e.g. cannons
 * @author Andrew Hall
 */
public abstract class Upgrade extends Item {
	
	/**
	 * The basic constructor of an item. Likely the version used in shops.
	 * 
	 * @param name		The item's name (Food, wool...)
	 * @param baseValue	The item's base value
	 */
	public Upgrade(String name, int baseValue) {
		super(name, baseValue);
	}
	/**
	 * A more detailed constructor, used for purchased items.
	 * Gives info on how much it cost, and where it was purchased.<br>
	 * Probably used by copiers
	 * 
	 * @param name				The item's name (Food, wool...)
	 * @param baseValue			The item's base value
	 * @param purchasedPrice	How much it was bought for
	 * @param purchasedFrom		The name of the island it was bought from
	 */
	public Upgrade(String name, int baseValue, int purchasedPrice, String purchasedFrom) {
		super(name, baseValue, purchasedPrice, purchasedFrom);
	}

}
