import java.util.Random;
public class Weather extends RandomEvent {
	
	
	private float damage;
	private final float DAMAGE_SCALE = 1.0f;
	
	public Weather(float probability) {
		super(probability);
		damage = new Random().nextFloat() * DAMAGE_SCALE;
	}
}
