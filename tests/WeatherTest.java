import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import events.Weather;

class WeatherTest {
	
	Weather storm;
	
	@BeforeEach
	void setUp() throws Exception {
		storm = new Weather(1f);
	}
	
	/**
	 * Tests that the damage is between acceptable levels
	 */
	@Test
	void damageDealtIsCorrect() {
		int N_TESTS = 100;
		float MIN = Weather.MIN_DAMAGE;
		float MAX = Weather.MAX_DAMAGE;
		for (int i = 0; i < N_TESTS; i++) {
			float damage = new Weather(1f).getDamage();
			// Make sure it's between the minimum and maximum
			assertTrue(damage >= MIN && damage <= MAX,
					"%f isn't between [%f-%f] inclusive".formatted(damage, MIN, MAX));
		}
	}

}
