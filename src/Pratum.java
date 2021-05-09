import java.util.HashMap;

public class Pratum extends Island {

	public Pratum() {
		this.name = "Pratum";
		this.description = "The starter island, a wealthy farming settlement.";
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
