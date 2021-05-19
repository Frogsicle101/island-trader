import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 */

/**
 * @author Andrew
 *
 */
class ShopTest {
	
	int baseValue = 10;
	Store store;	// With expensive food, and cheap tools
	TradeGood food;
	TradeGood tool;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		HashMap<TradeTypes, Float> prices = Store.getDefaultPrices();
		prices.put(TradeTypes.FOOD, 2f);
		prices.put(TradeTypes.TOOL, 0.5f);
		store = new Store("Test store", prices);
		food = new TradeGood("Food", baseValue, TradeTypes.FOOD);
		tool = new TradeGood("Tool", baseValue, TradeTypes.TOOL);
	}

	@Test
	void correctPricesTest() {
		
		fail("Not yet implemented");
	}

}
