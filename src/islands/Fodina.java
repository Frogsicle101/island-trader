package islands;
import java.util.HashMap;

import items.TradeTypes;

/**
 * Fodina is a mining island<br>
 * Has expensive food and tools, but cheap luxury and materials
 * @author Andrew
 *
 */

public class Fodina extends Island {

	public Fodina() {
		this.name = "Fodina";
		this.description = "The main town on Fodina is almost entirely underground.\n"
				+ "Its expansive natural cave network gives the miners here easy access to its mineral riches.";
		// ----- Generate the store prices -----
		HashMap<TradeTypes, Float> prices = Store.getDefaultPrices();
		// Tools and Food are in high demand for the workers
		prices.put(TradeTypes.TOOL, 1.5f);
		prices.put(TradeTypes.FOOD, 1.4f);
		// Materials and Gems (Luxury goods) are cheap here
		prices.put(TradeTypes.LUXURY, 0.8f);
		prices.put(TradeTypes.MATERIAL, 0.6f);
		Store store = new Store(this.name, prices);
		this.store = store;
		
	}

}
