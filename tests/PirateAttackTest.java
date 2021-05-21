import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import events.PirateAttack;
import events.PirateOutcome;

class PirateAttackTest {
	
	PirateAttack atk;
	
	@BeforeEach
	void setUp() throws Exception {
		atk = new PirateAttack(1f);
	}
	
	/**
	 * Tests that the given dice rolls have the desired outcome
	 */
	@Test
	void correctRollOutcomes() {
		// 1. Low rolls should fail
		assertEquals(atk.outcome(0), PirateOutcome.LOSS);
		assertEquals(atk.outcome(2), PirateOutcome.LOSS);
		// 2. Mid rolls should pass with damage
		assertEquals(atk.outcome(3), PirateOutcome.DAMAGED);
		assertEquals(atk.outcome(4), PirateOutcome.DAMAGED);
		// 3. High rolls should win
		assertEquals(atk.outcome(5), PirateOutcome.WIN);
		assertEquals(atk.outcome(6), PirateOutcome.WIN);
		assertEquals(atk.outcome(7), PirateOutcome.WIN);
		assertEquals(atk.outcome(345), PirateOutcome.WIN);
	}

}
