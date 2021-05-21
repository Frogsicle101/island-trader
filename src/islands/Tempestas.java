package islands;
import java.util.HashMap;

import items.TradeTypes;

public class Tempestas extends Island {

	public Tempestas() {
		this.name = "Tempestas";
		this.description = "Surrounded by storms, not many traders brave these waters.\n"
							+"The rain is good for growing crops though";
		// ----- Generate the store prices -----
		HashMap<TradeTypes, Float> prices = Store.getDefaultPrices();
		// Food is very cheap
		prices.put(TradeTypes.FOOD, 0.2f);
		
		// Everything else is slightly more expensive than normal
		prices.put(TradeTypes.TOOL, 1.2f);
		prices.put(TradeTypes.ARTIFACT, 1.2f);
		prices.put(TradeTypes.LUXURY, 1.4f);
		
		
		Store store = new Store(this.name, prices);
		this.store = store;
		
	}

}
