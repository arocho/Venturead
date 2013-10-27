package game.venturead.core.world;

/**
 * A <tt>Path</tt> represents a connection between two <tt>Location</tt>s,
 * that is traversable in <b>one way</b>.  Paths can be locked or unlocked. 
 * @author recardona
 */
public class Path {
	
	private boolean isLocked;
	private Location source;
	private Location destination;
	
	/**
	 * Creates a <tt>Path</tt> from the <i>source</i> <tt>Location</tt>
	 * to the <i>destination</i>
	 * @param source one <tt>Location</tt>
	 * @param destination another <tt>Location</tt>
	 * @throws <tt>IllegalArgumentException</tt> if <i>source</i> and <i>destination</i> are the same
	 */
	public Path(Location source, Location destination) {
		if(source.equals(destination)) {
			throw new IllegalArgumentException("Locations cannot be connected to themselves.");
		}
		
		this.source = source;
		this.destination = destination;
		this.isLocked = false;
	}
	
	/**
	 * @return the isLocked
	 */
	public boolean isLocked() {
		return isLocked;
	}
	
	/**
	 * Toggles this <tt>Path</tt>'s lock
	 */
	public void toggleLock() {
		this.isLocked = !this.isLocked;
	}

	/**
	 * @return the locationOne
	 */
	protected Location getSource() {
		return source;
	}

	/**
	 * @return the locationTwo
	 */
	protected Location getDestination() {
		return destination;
	}
	
	@Override
	public String toString() {
		return "path to "+this.getDestination().getName();
	}
	
		
}
