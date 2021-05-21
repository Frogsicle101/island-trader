package events;

/**
 * An abstract base class for random events that players can encounter
 * when moving between islands.
 * @author fma107
 *
 */
public abstract class RandomEvent {
	private float probability;
	
	/**
	 * Converts the string representation of a risk into a probability
	 * Used in getEvents
	 * @param risk A string in {low, medium, high, none}
	 * @return
	 */
	private static float getProbability(String risk) {
		switch (risk) {
			case "none":
				return 0f;
			case "low":
				return 0.2f;
			case "medium":
				return 0.4f;
			case "high":
				return 0.6f;
			default:
				throw new IllegalArgumentException("`type` must be either low, medium, high, or none");
		}
	}
	
	
	/**
	 * Gets an array of random events from the given preset.<br>
	 * {low, medium, high, none}
	 * @param risks A String[] corresponding to the risks of Pirates, Weather, and Rescue (in that order)
	 * @return An array of random events corresponding to the given risk
	 */
	public static RandomEvent[] getEvents(String[] risks) {
		RandomEvent[] events = {
				new PirateAttack(getProbability(risks[0])),
				new Weather(getProbability(risks[1])),
				new Rescue(getProbability(risks[2]))
		};
		
		
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
