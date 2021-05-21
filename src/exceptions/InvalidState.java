package exceptions;
/**
 * Thrown whenever the GameState is incorrect
 * @author Andrew
 *
 */
public class InvalidState extends RuntimeException {

	private static final long serialVersionUID = 479207411946064648L;
	
	public InvalidState() {
		super();
	}
	
	public InvalidState(String message) {
		super(message);
	}

}
