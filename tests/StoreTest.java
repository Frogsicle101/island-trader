import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoreTest {
	
	int baseValue = 10;
	Store store;	// With expensive food, cheap tools, and base everything else
	TradeGood food, tool, luxury;
	Upgrade cannon;
	

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
		luxury = new TradeGood("Luxury", baseValue, TradeTypes.LUXURY);
		cannon = new Cannon();
	}

	/**
	 * Store prices should be correctly displayed/reduced/untouched
	 */
	@Test
	void correctPricesTest() {
		// Should be double the price
		int foodPrice = store.getPrice(food);
		assertEquals(foodPrice, 20);
		// Should be half the price
		int toolPrice = store.getPrice(tool);
		assertEquals(toolPrice, 5);
		// Should be normal price
		int luxPrice = store.getPrice(luxury);
		assertEquals(luxPrice, 10);	
	}
	
	/**
	 * A purchased item should have all its values filled out
	 */
	@Test
	void correctlyBoughtItem() {
		TradeGood boughtFood = store.buyItem(food);
		// Base price shouldn't have changed
		assertEquals(boughtFood.getBaseValue(), 10);
		// The name shouldn't have changed
		assertEquals(boughtFood.getName(), "Food");
		// Should now contain where it was bought
		assertEquals(boughtFood.getPurchasedFrom(), "Test store");
		// Should now contain how much it was bought for
		assertEquals(boughtFood.getPurchasedPrice(), 20);		
	}

	/**
	 * Upgrades should be correctly removed from the store's pool
	 */
	@Test
	void canRemoveUpgrade() {
		// How many cannons exist originally
		int numCannonsOrig = 0;
		for (Upgrade upgrade : store.getUpgrades()) {
			if (upgrade instanceof Cannon) {
				numCannonsOrig++;
			}
		}
		// The store SHOULD contain a cannon at least
		assertNotEquals(numCannonsOrig, 0);
		// Remove a cannon and see if it got removed
		int numCannonsNow = 0;
		store.removeUpgrade(cannon);
		for (Upgrade upgrade : store.getUpgrades()) {
			if (upgrade instanceof Cannon) {
				numCannonsNow++;
			}
		}
		// There should be one less cannon
		assertEquals(numCannonsNow, numCannonsOrig - 1);
		
	}

}
