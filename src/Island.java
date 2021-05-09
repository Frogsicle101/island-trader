import java.util.ArrayList;
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
	
	/**
	 * Filter function.<br>
	 * Takes a list of routes & an island, returns given routes that contain the island.
	 * @param allRoutes List of all routes you want to check. Recommend just checking every route
	 * @param currIsland Island that's getting checked for
	 * @return An array of every route that connects to/from the given island
	 */
	public static ArrayList<Route> availableRoutes(Route[] allRoutes, Island currIsland) {
		ArrayList<Route> validRoutes = new ArrayList<>();
		for (Route route : allRoutes) {
			if (route.connectsIsland(currIsland)) {
				validRoutes.add(route);
			}
		}
		
		return validRoutes;
		
	}
	
	
}
