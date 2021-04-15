
public class Cannon extends Upgrade {
	
	public static final int FIGHT_BUFF = 1;
	
	public Cannon(String name, int baseValue) {
		super(name, baseValue);
	}

	public Cannon(String name, int baseValue, int purchasedPrice, String purchasedFrom) {
		super(name, baseValue, purchasedPrice, purchasedFrom);
	}

}
