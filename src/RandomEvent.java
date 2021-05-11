import java.util.HashMap;
import java.util.Map;

/**
 * An abstract base class for random events that players can encounter
 * when moving between islands.
 * @author fma107
 *
 */
public abstract class RandomEvent {
	private float probability;
	
	/**
	 * Gets an array of random events from the given preset.
	 * @param type What 
	 * @return
	 */
	public static RandomEvent[] getEvents(String type) {
		RandomEvent[] events;
		if (type.equals("none")) {
			events = new RandomEvent[] {};
		} else if (type.equals("low")) {
			events = new RandomEvent[] {new Rescue(0.12f), new Weather(0.1f)};
		} else if (type.equals("medium")) {
			events = new RandomEvent[] {new Rescue(0.15f), new Weather(0.2f)};
		} else if (type.equals("high")) {
			events = new RandomEvent[] {new Rescue(0.2f), new Weather(0.2f)};
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
