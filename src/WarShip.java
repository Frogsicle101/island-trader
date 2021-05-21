/**
 * The war ship is a combat ready ship, good at dealing with pirates.<br>
 * It has below-average speed, and comes pre-equipped with cannons, however
 * its cargo hold is small, it's expensive to repair, and 
 * 
 * @author Andrew Hall
 *
 */
public class WarShip extends Ship {
	private final static int N_CANNONS = 3;	// Number of cannons the ship has by default
	public WarShip() {
		super(
				0.8f,		// speed
				"War Ship", // shipType
				"This Navy relic is expensive to maintain, slow, and comes with a tiny cargo hold. But its "+N_CANNONS+" cannons will keep pirates at bay.",
				100,		// crew
				5,			// cargoCapacity
				5f,			// repairCost
				10			// startingMoney
		);
		for (int i=0; i<N_CANNONS; i++)
			try {
				storeItem(new Cannon());
			} catch (CargoFullException e) {
				e.printStackTrace();
				System.out.println("Error: WarShip constructor's adding more cannons than the ship can hold");
				System.exit(1);
			}

	}

}
