package ships;

/**
 * The barge is a slower ship, but makes up for it with its cargo capacity
 * @author fma107
 *
 */
public class Barge extends Ship {
	public Barge() {
		super(
				0.6f,		// speed
				"Barge", 	// shipType
				"The Barge is a slow ship with a large cargo hold",	// Description
				45,			// crew
				14,			// cargoCapacity
				3f,			// repairCost
				65			// startingMoney
		);

	}

}
