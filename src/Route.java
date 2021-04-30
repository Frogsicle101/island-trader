
/**
 * This class links two islands together. 
 * 
 * Routes have a distance, and an array of random events.
 * The distance affects how long it will take a ship to get to a destination.
 * When a route is traveled, there is a chance for various random events to
 * happen. These are represented by the array of random events.
 *
 * @author fma107
 */
public class Route {
	
	private Island[] islands;			// 2-len of the islands it connects
	private int distance;				// Higher distance routes take longer to finish
	private RandomEvent[] randomEvents;	// Random events have a chance of triggering on every day spent sailing
	
	/**
	 * @param distance How long the route is
	 * @param randomEvents The list of random events
	 */
	public Route(Island island1, Island island2, int distance, RandomEvent[] randomEvents) {
		this.islands = new Island[]{island1, island2};
		this.distance = distance;
		this.randomEvents = randomEvents;
	}
	
	/**
	 * Every route connects 2 islands, this gets those islands
	 * @return 2-length array of the islands this route connects
	 */
	public Island[] getIslands() {
		return islands;
	}
	
	/**
	 * Since routes are two-way connection, checks if this connects the given island
	 * @param island Island we're checking
	 * @return true if this route connects to that island 
	 */
	public boolean connectsIsland(Island island) {
		return islands[0] == island || islands[1] == island;
	}
	
	
	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * @return the randomEvents
	 */
	public RandomEvent[] getRandomEvents() {
		return randomEvents;
	}
	
	
	
	
	
}
