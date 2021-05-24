package ships;
import exceptions.CargoFullException;
import items.Sail;

/**
 * The Clipper is the fastest ship, good for moving quickly between islands. It doesn't have much space though.
 * @author fma107
 *
 */
public class Clipper extends Ship {
	public Clipper() {
		super(
				1f,		// speed
				"Clipper", // shipType
				"The Clipper is lightning fast, but can't hold much cargo", // Description
				25,		// crew
				7,			// cargoCapacity
				3f,			// repairCost
				65			// startingMoney
		);
			try {
				storeItem(new Sail());
			} catch (CargoFullException e) {
				e.printStackTrace();
				System.err.println("Clipper construction error: Clipper can't store that many sails");
				System.exit(1);
			}

	}

}
