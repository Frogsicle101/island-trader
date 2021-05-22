package events;
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
	public static final float MIN_DAMAGE = 0.5f;
	public static final float MAX_DAMAGE = 3.0f;
	
	public Weather(float probability) {
		super(probability);
		damage = new Random().nextFloat() * (MAX_DAMAGE - MIN_DAMAGE) + MIN_DAMAGE;
	}
	
	public float getDamage() {
		return damage;
	}
}
