package islands;
import java.util.HashMap;

import items.TradeTypes;

/**
 * Terminus is an isolated pirate haven.<br>
 * Food, tools, and materials are expensive. But the stolen luxury and artifacts are cheap.
 * @author Andrew
 *
 */
public class Terminus extends Island {

	public Terminus() {
		this.name = "Terminus";
		this.description = "An isolated outpost, far from the law, pirates favor this Island";
		// ----- Generate the store prices -----
		HashMap<TradeTypes, Float> prices = Store.getDefaultPrices();
		// Most things are expensive
		prices.put(TradeTypes.FOOD, 2f);
		prices.put(TradeTypes.TOOL, 1.8f);
		prices.put(TradeTypes.MATERIAL, 1.8f);
		// The things that pirates have stolen are cheap
		prices.put(TradeTypes.LUXURY, 0.4f);
		prices.put(TradeTypes.ARTIFACT, 0.5f);
		Store store = new Store(this.name, prices);
		this.store = store;
		
	}

}
