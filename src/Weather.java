import java.util.Random;
public class Weather extends RandomEvent {
	
	
	private int damage;
	private final double DAMAGE_SCALE = 1.0;
	
	public Weather(float probability) {
		super(probability);
		damage = (int)(new Random().nextDouble());
	}
}
