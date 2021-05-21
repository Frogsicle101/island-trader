package items;
/**
 * Sails are upgrade items, used to decrease the time to get between Islands.
 * A trade-off: The more Sails you have, the faster you go,
 * but they take up cargo space.
 * 
 * @author fma107
 *
 */
public class Sail extends Upgrade {
	
	// The speed bonus given from a single Sail
	public static final int SPEED_BUFF = 1;
	
	/**
	 * Creates a new Sail object
	 */
	public Sail() {
		super("Sail",		// name
				40			// baseValue
		);
	}

	private Sail(String name, int baseValue, int purchasedPrice, String purchasedFrom) {
		super(name, baseValue, purchasedPrice, purchasedFrom);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Sail copy() {
		return new Sail(name, baseValue, purchasedPrice, purchasedFrom);
	}

}