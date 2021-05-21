package islands;
import java.util.HashMap;

import items.TradeTypes;

public class Pratum extends Island {

	public Pratum() {
		this.name = "Pratum";
		this.description = "A wealthy farming island, popular with retired farmers. It's also your homeland. "
						+ "The food's cheap, the water's safe, and the old folks love their trinkets.";
		// ----- Generate the store prices -----
		HashMap<TradeTypes, Float> prices = Store.getDefaultPrices();
		// Farming, thus food and resources are cheap
		prices.put(TradeTypes.FOOD, 0.6f);
		prices.put(TradeTypes.MATERIAL, 0.8f);
		// Rich people willing to pay for trinkets
		prices.put(TradeTypes.LUXURY, 1.5f);
		prices.put(TradeTypes.ARTIFACT, 1.4f);
		Store store = new Store(this.name, prices);
		this.store = store;
		
	}

}
