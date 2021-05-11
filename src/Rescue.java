import java.util.Random;

/**
 * A random event representing rescuing sailors.
 * When this event is drawn, a random number of shipwrecked sailors are found.
 * Sailors give the player a monetary reward when rescued.
 * 
 * @author fma107
 * 
 */
public class Rescue extends RandomEvent {
	private int numSailors;
	private final int REWARD_PER_SAILOR  = 2;
	
	public Rescue(float probability) {
		super(probability);
		numSailors = new Random().nextInt(10) + 2;	// Minimum of 2 sailors saved 
	}
	
	public int getNumSailors() {
		return numSailors;
	}
	
	public int getReward() {
		return REWARD_PER_SAILOR * numSailors;
	}
	
	
}

