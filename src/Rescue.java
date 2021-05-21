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
	public static final int REWARD_PER_SAILOR  = 2;
	public static final int MIN_NUM_SAILORS = 2;
	public static final int MAX_NUM_SAILORS = 15;
	
	public Rescue(float probability) {
		super(probability);
		int nSavedWithoutMin = MAX_NUM_SAILORS - MIN_NUM_SAILORS + 1;			// +1 because nextInt is exclusive
		numSailors = new Random().nextInt(nSavedWithoutMin) + MIN_NUM_SAILORS;	// Minimum of 2 sailors saved 
	}
	
	public int getNumSailors() {
		return numSailors;
	}
	
	public int getReward() {
		return REWARD_PER_SAILOR * numSailors;
	}
	
	
}

