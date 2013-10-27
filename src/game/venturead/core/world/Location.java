package game.venturead.core.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * A Location represents a place that you can inhabit.  
 * It is connected to other Locations by way of Paths, and hosts a list of
 * Characters that are here.  
 * @author recardona
 */
public class Location 
{
	private String name;
	private String description;
	private HashMap<Direction,Path> connectingPaths;
	private ArrayList<Character> charactersThatAreHere;
	
	private Location(String name, String description, HashMap<Direction,Path> paths, ArrayList<Character> characters)
	{
		this.name = name;
		this.description = description;
		this.connectingPaths = paths;
		this.charactersThatAreHere = characters;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return the connectingPaths
	 */
	public HashMap<Direction,Path> getConnectingPaths() {
		return this.connectingPaths;
	}

	/**
	 * @return the charactersThatAreHere
	 */
	public ArrayList<Character> getCharactersThatAreHere() {
		return this.charactersThatAreHere;
	}
	
	/**
	 * Returns a new <tt>Location</tt> with no connections, and the given name.
	 * Checks to see if a <tt>Location</tt> with the given <i>name</i> has
	 * already been registered, and throws an exception if that is the case.
	 * @param name the name of this new <tt>Location</tt>
	 * @param description the description of what is seen at this <tt>Location</tt>
	 * @return the newly created <tt>Location</tt>
	 */
	public static Location newLocation(String name, String description) {
		return new Location(name, description, new HashMap<Direction,Path>(), new ArrayList<Character>());
	}
	
	/**
	 * Connects this <tt>Location</tt> to the other, by way of a 
	 * <tt>OneWayPath</tt> or <tt>TwoWayPath</tt> at the given <tt>Direction</tt>.
	 * @param other the other <tt>Location</tt> to connect to
	 * @param isOneWay whether or not the path is one way
	 * @param direction the <tt>Direction</tt> of the <tt>Path</tt> relative to the <tt>Location</tt>
	 * @see <tt>OneWayPath.java</tt>
	 * @see <tt>TwoWayPath.java</tt>
	 */
	public void connectToLocation(Location other, Direction direction) {
		Path newPath = new Path(this, other);

		if(connectingPaths.containsKey(direction)) {
			throw new IllegalArgumentException("A Path at given Direction " +direction.name()+" has already been registered.");
		}
		
		else {
			connectingPaths.put(direction, newPath);
		}
	}
	
	/**
	 * Registers the parameter <tt>Character</tt> to this <tt>Location</tt>
	 * only if the <tt>Character</tt> isn't already registered.
	 * @param c the <tt>Character</tt> to register.
	 */
	public void registerCharacter(Character c) {
		if(!(this.getCharactersThatAreHere().contains(c))) {
			this.charactersThatAreHere.add(c);
		}
	}
	
	/**
	 * Unregisters the parameter <tt>Character</tt> from this <tt>Location</tt>,
	 * if present
	 * @param c the <tt>Character</tt> to unregister.
	 */
	public void unregisterCharacter(Character c) {
		this.charactersThatAreHere.remove(c);
	}
	
	@Override
	public boolean equals(Object obj) {
		// Two Locations are equal if their names are equal.
		if(obj == null) {return false;}
		else if(!(obj instanceof Location)) {return false;}
		else {
			Location other = (Location) obj;
			if(this.hashCode() != other.hashCode()) {return false;}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
        hash = hash * 17 * 13 * 31+ this.name.hashCode();
		return hash;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();

		sb.append(this.name+"\n\n");
		sb.append(this.description+"\n\n");
		
		if(!this.connectingPaths.isEmpty()) {
			for(Entry<Direction,Path> connectingPath : this.connectingPaths.entrySet()) {
				sb.append("There is a "+connectingPath.getValue()+ ", ");
				sb.append(connectingPath.getKey().name()+" from here.\n");
			}
		}
			
		if(!this.charactersThatAreHere.isEmpty()) {
			sb.append("\n You can see ");
			for(Character c : this.charactersThatAreHere) {
				sb.append(c.toString()+", ");
			}
			sb.append("and no one else.\n");
		}
		
		else {
			sb.append("\nThere is no one here.\n");
		}
		
		return sb.toString();
	}
	
}
