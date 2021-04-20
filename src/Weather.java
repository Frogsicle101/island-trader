import java.util.Random;

/**
 * Random event representing poor weather.
 * When this event is drawn, the player's ship takes a random
 * amount of damage, which must be repaired at the next island.
 * @author Andrew Hall
 *
 */
public class Weather extends RandomEvent {
	
	
	private float damage;
	private final float DAMAGE_SCALE = 1.0f;
	
	public Weather(float probability) {
		super(probability);
		damage = new Random().nextFloat() * DAMAGE_SCALE;
	}
	
	public float getDamage() {
		return damage;
	}
}
