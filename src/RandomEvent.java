/**
 * @author fma107
 * An abstract base class for random events that players can encounter
 * when moving between islands.
 *
 */
public abstract class RandomEvent {
	private float probability;

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
