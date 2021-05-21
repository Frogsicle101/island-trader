import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import events.Rescue;

class RescueTest {

	Rescue res;

	@BeforeEach
	void setUp() throws Exception {
		res = new Rescue(1f);
	}
	
	/**
	 * There should be a minimum of 2 sailors rescued, and a maximum of 12
	 */
	@Test
	void numSailorsAreCorrect() {
		int N_TESTS = 100;
		int MIN = Rescue.MIN_NUM_SAILORS;
		int MAX = Rescue.MAX_NUM_SAILORS;
		for (int i = 0; i < 100; i++) {
			int numSailors = new Rescue(1f).getNumSailors();
			// Make sure it's between the minimum and maximum
			assertTrue(numSailors >= MIN && numSailors <= MAX,
					"%d isn't between [%d-%d] inclusive".formatted(numSailors, MIN, MAX));
		}
	}
	
	@Test
	void rewardAmountIsCorrect() {
		int numSailors = res.getNumSailors();
		int expectedReward = res.REWARD_PER_SAILOR * numSailors;
		assertEquals(res.getReward(), expectedReward);
	}

}
