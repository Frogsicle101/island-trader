
/**
 * An abstract base class for random events that players can encounter
 * when moving between islands.
 * @author fma107
 *
 */
public abstract class RandomEvent {
	private float probability;
	
	/**
	 * Gets an array of random events from the given preset.<br>
	 * {low, medium, high, none}
	 * @param risk The "risk" of a route
	 * @return An array of random events corresponding to the given risk
	 */
	public static RandomEvent[] getEvents(String risk) {
		RandomEvent[] events;
		if (risk.equals("none")) {
			events = new RandomEvent[] {};
		} else if (risk.equals("low")) {
			events = new RandomEvent[] {new Rescue(0.2f), new Weather(0.1f)};
		} else if (risk.equals("medium")) {
			events = new RandomEvent[] {new Rescue(0.15f), new Weather(0.2f)};
		} else if (risk.equals("high")) {
			events = new RandomEvent[] {new Rescue(0.3f), new Weather(0.2f)};
		} else {
			throw new IllegalArgumentException("`type` must be either low, medium, high, or none");
		}
		return events;
	}
	
	/**
	 * @param probability The chance that the random event will happen
	 */
	public RandomEvent(float probability) {
		this.probability = probability;
	}

	/**
	 * @return the probability
	 */
	public float getProbability() {
		return probability;
	}

	
}
