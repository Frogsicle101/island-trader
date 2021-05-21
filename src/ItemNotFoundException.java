/**
 * Exception thrown when trying to find an Item that doesn't exist
 * @author Andrew Hall
 *
 */
public class ItemNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6060712775625310787L;

	public ItemNotFoundException() {}
	
	public ItemNotFoundException(String message) {
		super(message);
	}
}
