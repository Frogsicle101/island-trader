/**
 * The war ship is a combat ready ship, good at dealing with pirates.<br>
 * It has above-average speed, and comes pre-equipped with cannons, however
 * its cargo hold is small, it's expensive to repair, and 
 * 
 * @author Andrew Hall
 *
 */
public class WarShip extends Ship {
	private final int N_CANNONS = 3;	// Number of cannons the ship has by default
	public WarShip() {
		super(
				1.2f,		// speed
				"War Ship", // shipType
				100,		// crew
				8,			// cargoCapacity
				5f,			// repairCost
				50			// startingMoney
		);
		Store anonStore = Store.getDefaultStore();
		for (int i=0; i<N_CANNONS; i++)
			try {
				storeItem(anonStore.buyItem("Cannon"));
			} catch (CargoFullException | ItemNotFoundException e) {
				e.printStackTrace();
				System.out.println("Fatal error in WarShip class constructor");
				System.exit(1);
			}

	}

}
