import java.util.Random;

/**
 * Random event representing a pirate attack.
 * When drawn, the player rolls a D6 (+ any bonuses) to determine the outcome.<br>
 * 1-2: Pirates win, lose your cargo
 * 3-4: You win, but sustain damage
 * 5-6+: You win without a scratch
 * @author Andrew
 *
 */
public class PirateAttack extends RandomEvent {
	
	private float damageDealt;	// The amount of damage dealt if the outcome is DAMAGED
	private final float DAMAGE_SCALE = 2f;
	public PirateAttack(float probability) {
		super(probability);
		damageDealt = new Random().nextFloat() * DAMAGE_SCALE;
	}
	
	public float getDamageDealt() {
		return damageDealt;
	}
	
	/**
	 * Gives the outcome of a pirate attack dice roll
	 * @param rollValue The result of the dice roll (+ bonuses)
	 * @return The outcome of an attack - loss, damaged, or win
	 */
	public PirateOutcome outcome(int rollValue) {
		PirateOutcome result;
		if (rollValue <= 2) {
			result = PirateOutcome.LOSS;
		} else if (rollValue <= 4) {
			result = PirateOutcome.DAMAGED;
		} else {
			result = PirateOutcome.WIN;
		}
		return result;
	}

}
