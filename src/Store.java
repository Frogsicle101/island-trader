import java.util.ArrayList;
import java.util.HashMap;

/**
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
	private ArrayList<Upgrade> upgrades;		// Every store has a finite upgrade pool
	
	private final static Store DEFAULT_STORE = new Store("Default store", new HashMap<TradeTypes, Float>());
	private final static Upgrade[] POSSIBLE_UPGRADES = new Upgrade[] {new Cannon(), new Sail()};
	

	/**
	 * Creates a new island that values each trade type accordingly
	 * @param itemPrices A mapping of TradeType: ValueMultiplier
	 * e.g. a $50 item with a 0.8 multiplier is worth $40 at this store
	 * @param shopName The name of this shop, usually bound to any bought items
	 */
	public Store(String shopName, HashMap<TradeTypes, Float> itemPrices) {
		this.itemPrices = itemPrices;
		this.shopName = shopName;
		this.upgrades = new ArrayList<Upgrade>();
		this.upgrades.add(new Cannon());
		this.upgrades.add(new Sail());
	}

	public HashMap<TradeTypes, Float> getItemPrices() {
		return itemPrices;
	}
	
	public String getShopName() {
		return shopName;
	}
	
	/**
	 * @return the possibleUpgrades
	 */
	public static Upgrade[] getPossibleUpgrades() {
		return POSSIBLE_UPGRADES;
	}

	
	public ArrayList<Upgrade> getUpgrades() {
		return upgrades;
	}
	
	/**
	 * Gets the default store, an islandless store with no item preferences
	 * @return The default store
	 */
	public static Store getDefaultStore() {
		return DEFAULT_STORE;
	}
	

	/**
	 * Creates a pricing map of every type, initialized to 1 (base value)
	 * @return The default pricing
	 */
	public static HashMap<TradeTypes, Float> getDefaultPrices() {
		HashMap<TradeTypes, Float> prices = new HashMap<>();
		for (TradeTypes type : TradeTypes.values()) {
			prices.put(type, 1f);
		}
		return prices;
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
	 * @param item The item whose price we ant
	 * @return The good's price at this store
	 */
	public int getPrice(Item item) {
		int price;
		
		if (item instanceof TradeGood) {
			Float valueMult = itemPrices.get(((TradeGood)item).getType());
			if (valueMult == null)
				valueMult = 1f;
			price = (int) (item.getBaseValue() * valueMult);
		} else {
			price = item.getBaseValue();
		}
		return price;
	}
	
	/**
	 * Takes an item (usually an "unbought" item from a store), and fills out all the
	 * fields about where/how much it was bought for
	 * @param item The trade god we want
	 * @return The requested item, with the purchasedPrice/From fields filled
	 */
	public TradeGood buyItem(TradeGood item) {
		int price = getPrice(item);
		return item.copy().makeBought(price, shopName);
	}
	
	
	/**
	 * Adds the given upgrade into the store's upgrade pool
	 * @param upgrade The upgrade getting added
	 */
	public void addUpgrade(Upgrade upgrade) {
		upgrades.add(upgrade);
	}
	
	/**
	 * Puts the given upgrade back into the store's upgrade pool, returning the upgrade's value
	 * @param upgrade The upgrade that's gotten rid of
	 * @return The upgrade's value
	 */
	public int sellUpgrade(Upgrade upgrade) {
		addUpgrade(upgrade);
		return getPrice(upgrade);
	}
	
	public void removeUpgrade(Upgrade toBeBought) {
		for (int i = 0; i < upgrades.size(); i++) {
			if (upgrades.get(i).getName().equals(toBeBought.getName())) {
				upgrades.remove(i);
				break;
			}
		}
	}
}

