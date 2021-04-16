/**
 * A class for islands that the player will visit on their journey.
 * 
 * The main flow of the game is based on the player moving between islands. The 
 * connections between the islands are represented by the array of Routes. 
 * @author fma107
 *
 */
public class Island {
	
	private Route[] routes;
	private Store store;
	private String description;
	
	
	/**
	 * @param routes An array of Routes that the player could take from this island
	 * @param store A store object. 
	 * @param description A string describing the island
	 */
	public Island(Route[] routes, Store store, String description) {
		this.routes = routes;
		this.store = store;
		this.description = description;
	}


	/**
	 * @return the routes
	 */
	public Route[] getRoutes() {
		return routes;
	}


	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	
	
	
}
