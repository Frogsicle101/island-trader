package ships;

/**
 * The TallShip is a pretty average ship. Reasonable cargo, reasonable speed, etc
 * @author fma107
 *
 */
public class TallShip extends Ship {
	public TallShip() {
		super(
				1f,		// speed
				"Tall Ship", // shipType
				"The Tall Ship is a ship with balanced stats all around.",	// Description
				40,		// crew
				8,			// cargoCapacity
				3f,			// repairCost
				65			// startingMoney
		);

	}

}
