/**
 * The Clipper is the fastest ship, good for moving quickly between islands. It doesn't have much space though.
 * @author fma107
 *
 */
public class Clipper extends Ship {
	public Clipper() {
		super(
				2f,		// speed
				"Clipper", // shipType
				"The Clipper is lightning fast, but can't hold much cargo", // Description
				25,		// crew
				6,			// cargoCapacity
				3f,			// repairCost
				65			// startingMoney
		);

	}

}
