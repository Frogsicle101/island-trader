import java.util.ArrayList;
import java.util.HashMap;

/**
 * INCOMPLETE CLASS!!! - 
 * A store is a place to buy and sell items.
 * 
 * Every island has a store, and every store has different
 * prices that it'll buy and sell an item for.
 * 
 * @author Andrew Hall
 */
public class Store {
	private String shopName;
	private HashMap<TradeTypes, Float> itemPrices;
	private ArrayList<Upgrade> upgrades;		// TODO: Work out upgrades
	
	private final static Store DEFAULT_STORE = new Store("Default store", new HashMap<TradeTypes, Float>());
	
	/**
	 * Creates a new island that values each trade type accordingly
	 * @param itemPrices A mapping of TradeType: ValueMultiplier
	 * e.g. a $50 item with a 0.8 multiplier is worth $40 at this store
	 * @param shopName The name of this shop, usually bound to any bought items
	 */
	public Store(String shopName, HashMap<TradeTypes, Float> itemPrices) {
		this.itemPrices = itemPrices;
		this.shopName = shopName;
		this.upgrades = new ArrayList<>();
	}

	public HashMap<TradeTypes, Float> getItemPrices() {
		return itemPrices;
	}
	
	public String getShopName() {
		return shopName;
	}
	
	/**
	 * Gets the default store, an islandless store with no item preferences
	 * @return The default store
	 */
	public static Store getDefaultStore() {
		return DEFAULT_STORE;
	}
	
	/**
	 * Gets the item with that name as defined in TradeGood.ALL_GOODS
	 * @param name The item's name to search for
	 * @return The requested item
	 * @throws ItemNotFoundException The given name doesn't match any items
	 */
	private TradeGood getItem(String name) throws ItemNotFoundException {
		for (TradeGood item: TradeGood.ALL_GOODS) {
			if (item.getName().equals(name)) {
				return item;
			}
		}
		throw new ItemNotFoundException();
	}
	
	/**
	 * Gives an item's price at this store
	 * @param name The item's name to search for
	 * @return The item's price
	 * @throws ItemNotFoundException The given name doesn't match any items
	 */
	public int getPrice(String name) throws ItemNotFoundException {
		TradeGood item = getItem(name);
		Float valueMult = itemPrices.get(item.getType());
		if (valueMult == null)
			valueMult = 1f;
		int price = (int) (item.getBaseValue() * valueMult);
		return price;
	}
	
	/**
	 * 
	 * @param name The item's name to search for
	 * @return The requested item, with the purchasedPrice/From fields filled
	 * @throws ItemNotFoundException The given name doesn't match any items
	 */
	public TradeGood buyItem(String name) throws ItemNotFoundException {
		TradeGood item = getItem(name);
		int price = getPrice(name);
		return item.copy().makeBought(price, shopName);
	}	
}
