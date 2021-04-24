/**
 * A tradable good.
 * 
 * Buying and selling these items are how you make money.
 * 
 * @author Andrew Hall
 */
public class TradeGood extends Item {
	
	public static final TradeGood[] ALL_GOODS = {};
	protected TradeTypes type;
	
	/**
	 * The basic constructor of an item. Likely the version used in shops.
	 * 
	 * @param name		The item's name ("Food", "Statue"...)
	 * @param baseValue	The item's base value
	 * @param type		The item's type (food, luxury...)
	 */
	public TradeGood(String name, int baseValue, TradeTypes type) {
		super(name, baseValue);
		this.type = type;
	}
	/**
	 * A more detailed constructor, used for purchased items.
	 * Gives info on how much it cost, and where it was purchased.
	 * 
	 * @param name				The item's name (Food, wool...)
	 * @param baseValue			The item's base value
	 * @param purchasedPrice	How much it was bought for
	 * @param purchasedFrom		The name of the island it was bought from
	 * @param type				The item's type (food, luxury...)
	 */
	public TradeGood(String name, int baseValue, int purchasedPrice, String purchasedFrom, TradeTypes type) {
		super(name, baseValue, purchasedPrice, purchasedFrom);
		this.type = type;
	}
	
	public TradeTypes getType() {
		return type;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TradeGood copy() {
		return new TradeGood(name, baseValue, purchasedPrice, purchasedFrom, type);
	}
}
