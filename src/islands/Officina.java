package islands;
import java.util.HashMap;

import items.TradeTypes;

public class Officina extends Island {

	public Officina() {
		this.name = "Officina";
		this.description = "Officina is where raw materials are manafactured into products";
		// ----- Generate the store prices -----
		HashMap<TradeTypes, Float> prices = Store.getDefaultPrices();
		// They make tools and artifacts
		prices.put(TradeTypes.TOOL, 0.6f);
		prices.put(TradeTypes.ARTIFACT, 0.9f);
		// They need raw materials
		prices.put(TradeTypes.MATERIAL, 1.5f);
		Store store = new Store(this.name, prices);
		this.store = store;
		
	}

}