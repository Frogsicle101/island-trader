/**
 * Cannons are upgrade items, used to increase combat rolls.
 * A trade-off: The more cannons you have, the easier it is to repeal pirates,
 * but they take up cargo space.
 * 
 * @author Andrew Hall
 *
 */
public class Cannon extends Upgrade {
	
	public static final int FIGHT_BUFF = 1;
	
	/**
	 * Creates a new cannon object
	 */
	public Cannon() {
		super("Cannon",		// name
				40			// baseValue
		);
	}

	private Cannon(String name, int baseValue, int purchasedPrice, String purchasedFrom) {
		super(name, baseValue, purchasedPrice, purchasedFrom);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Cannon copy() {
		// TODO Auto-generated method stub
		return new Cannon(name, baseValue, purchasedPrice, purchasedFrom);
	}

}
