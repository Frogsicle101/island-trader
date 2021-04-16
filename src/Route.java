/**
 * This class links together various island classes. 
 * 
 * Routes have a distance, and an array of random events.
 * The distance affects how long it will take a ship to get to a destination.
 * When a route is traveled, there is a chance for various random events to
 * happen. These are represented by the array of random events.
 *
 * @author fma107
 */
public class Route {
	
	private int distance;
	private RandomEvent[] randomEvents;
	
	/**
	 * @param distance How long the route is
	 * @param randomEvents The list of random events
	 */
	public Route(int distance, RandomEvent[] randomEvents) {
		this.distance = distance;
		this.randomEvents = randomEvents;
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
