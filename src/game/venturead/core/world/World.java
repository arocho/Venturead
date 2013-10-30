package game.venturead.core.world;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class World 
{
	private HashSet<Location> worldLocations;
	//locations must be unique, hashset provides O(1) basic operations

	private HashSet<Character> worldCharacters;
	//characters must be unique!

	public World() {
		worldLocations  = new HashSet<Location>();
		worldCharacters = new HashSet<Character>(); 
	}

	/**
	 * @return the worldLocations
	 */
	public HashSet<Location> getWorldLocations() {
		return worldLocations;
	}

	/**
	 * @return the worldCharacters
	 */
	public HashSet<Character> getWorldCharacters() {
		return worldCharacters;
	}

	/**
	 * Registers a new <tt>Location</tt> in this <tt>World</tt>.
	 * The <tt>Location</tt> must be uniquely named, otherwise, this method
	 * throws an exception.
	 * @param name the name of the new <tt>Location</tt>
	 * @param description the description of the new <tt>Location</tt>
	 * @return the freshly created <tt>Location</tt>
	 * @throws <tt>IllegalArgumentException</tt> if the <tt>Location</tt> name has already been registered
	 */
	public Location registerNewLocation(String name, String description) {
		Location newLocation = Location.newLocation(name, description);
		if(worldLocations.contains(newLocation)) {
			throw new IllegalArgumentException("There has already been a location registered with name "+name);
		}

		worldLocations.add(newLocation);
		return newLocation;
	}

	/**
	 * Creates a Route from one <tt>Location</tt> to another <tt>Location</tt>.
	 * The parameter <i>throughThisDirection</i> refers to the <tt>Direction</tt>
	 * of the <tt>Location</tt> <i>to</i> relative to <tt>Location</tt> <i>from</i>.
	 * This method creates a <tt>Route</tt> both ways (from <i>from</i> to <i>to</i>,
	 * and from <i>to</i> to <i>from</i>).
	 * 
	 * @param from a <tt>Location</tt>
	 * @param throughThisDirection the <tt>Direction</tt> of <i>to</i>, relative to <i>from</i>
	 * @param to another <tt>Location</tt>
	 */
	public void createPath(Location from, Direction throughThisDirection, Location to) {
		from.connectToLocation(to, throughThisDirection);
		to.connectToLocation(from, Direction.findOppositeDirection(throughThisDirection));
	}
	
	public LinkedList<Location> findPathToTargetFromLocation(Location from, Location target) {
		// finds a sequence of Locations that connect the parameter to the target
		// null, if none exists.  Considers the Paths between Locations, as well
		// as whether or not the Paths are locked.
		// uses Djikstra to compute the Route
		
		PriorityQueue<LinkedList<Location>> openLocations = new PriorityQueue<LinkedList<Location>>();
		PriorityQueue<LinkedList<Location>> closedLocations = new PriorityQueue<LinkedList<Location>>();
		
		
		
		
		return null;
	}
	
	

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for(Location location : worldLocations) {
			sb.append(location.toString());
		}

		return sb.toString();
	}

}
