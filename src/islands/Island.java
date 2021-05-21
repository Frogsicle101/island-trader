package islands;

/**
 * A class for islands that the player will visit on their journey.
 * 
 * The main flow of the game is based on the player moving between islands. The 
 * connections between the islands are represented by the array of Routes. 
 * @author fma107
 *
 */
public class Island {
	
	protected Store store;
	protected String name;
	protected String description;
	
	
	public Island() {}
	
	/**
	 * @param store A store object. 
	 * @param name The island's name
	 * @param description A string describing the island
	 */
	public Island(Store store, String name, String description) {
		this.store = store;
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}
	
	/**
	 * @return Island's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
}
