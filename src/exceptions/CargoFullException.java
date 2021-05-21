package exceptions;
/**
 * Exception thrown when trying to add an item to a ship's cargo, if the cargo hold's full
 * @author Andrew Hall
 *
 */
public class CargoFullException extends RuntimeException {
	private static final long serialVersionUID = -1284102267997105190L;

	public CargoFullException() {
	}
	
	public CargoFullException(String message) {
		super(message);
	}
}
