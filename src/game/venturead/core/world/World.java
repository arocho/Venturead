package game.venturead.core.world;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
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

	/**
	 * Finds a <tt>Path</tt> that connects the parameter <tt>Location</tt><i>from</i> 
	 * to the <i>target</i><tt>Location</tt>, or null if none exists.  Considers the
	 * available <tt>Route</tt>s between <tt>Location</tt>s, as well as whether or not
	 * the <tt>Route</tt>s are locked.
	 * @param start the starting <tt>Location</tt> for search
	 * @param goal the target <tt>Location</tt> for search
	 * @return a sequence of <tt>Location</tt>s
	 */
	public Path<Location> findPath(Location start, Location goal) {
		// uses Djikstra to compute the path

		PriorityQueue<Path<Location>> closedPaths = new PriorityQueue<Path<Location>>();
		//paths that I have already explored, and don't want to explore again

		PriorityQueue<Path<Location>> openPaths   = new PriorityQueue<Path<Location>>(10);
		//paths that I'm still exploring; sorted in natural order

		openPaths.add(new Path<Location>(start, 0));

		Path<Location> solutionPath = null;
		Path<Location> tempPath;

		while(!openPaths.isEmpty())
		{
			tempPath = openPaths.poll();

			//we have found the path!
			if(tempPath.getLastItem().equals(goal)) {
				solutionPath = tempPath;
				break; //assign, and break
			}

			closedPaths.add(tempPath);
			//add it to the closed paths so we don't check it again

			HashMap<Direction,Route> expandedRoutes = tempPath.getLastItem().getUnlockedRoutes();
			//find all the routes that connect to other Locations

			for(Entry<Direction,Route> expandedRoute : expandedRoutes.entrySet()) {
				Route routeInfo = expandedRoute.getValue();
				Location expandedLocation = routeInfo.getDestination();
				//the destination of the route is the next step to explore 

				Path<Location> expandedPath = new Path<Location>(tempPath.append(expandedLocation), tempPath.pathCost++);
				//the expanded path is the old path, expanded by 1, and with its cost also incremented by 1.
				//admittedly, the increase cost of 1 is arbitrary, but since we're exploring by 1, it makes sense


				Path<Location> repeatedPath = null;

				repeatedPath = findPathWithEndLocation(expandedPath.getLastItem(), closedPaths);
				if(repeatedPath != null) {

					//this really shouldn't happen...but if it does, it means we
					//expanded into a path whose last Location was explored by a
					//(closed) previous path.  

					//let's check if the expanded path is cheaper.
					if(expandedPath.pathCost < repeatedPath.pathCost) {
						closedPaths.remove(repeatedPath);
						//if it's cheaper, then we need to consider it again!
					}						
				}

				repeatedPath = findPathWithEndLocation(expandedPath.getLastItem(), openPaths);
				if(repeatedPath != null) {

					//this can legitimately happen for arbitrary graphs:
					//we could find a path whose last Location matches another
					//(yet to be explored) path.

					//let's check if the expanded path is cheaper.
					if(expandedPath.pathCost < repeatedPath.pathCost) {
						openPaths.remove(repeatedPath);
						//if it's cheaper, then we need to consider the cheaper one only!
					}
				}

				//this sorts the nodes from smallest to largest!
				openPaths.add(expandedPath);
			}
		}
		return solutionPath;
	}

	/**
	 * Searches for and returns the first <tt>Path</tt> found within <i>collectionOfPaths</i>, 
	 * whose last <tt>Location</tt> matches <i>endLocationToMatch</i>.  If no such <tt>Path</tt>
	 * is found, this method returns <tt>null</tt>. 
	 * @param endLocationToMatch the <tt>Location</tt> to find within <i>collectionOfPaths</i>
	 * @param collectionOfPaths the <tt>Collection</tt> to search
	 * @return the first <tt>Path</tt> whose last element is <i>endLocationToMatch</i> 
	 */
	private Path<Location> findPathWithEndLocation(Location endLocationToMatch, Collection<Path<Location>> collectionOfPaths) {
		for(Path<Location> path : collectionOfPaths) {
			if(path.getLastItem().equals(endLocationToMatch)) {
				return path;
			}
		}

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

	public class Path<T> implements Iterable<T>, Comparable<T>
	{
		private LinkedList<T> pathList;
		private int pathCost;

		public Path() {
			this.pathList = new LinkedList<T>();
			this.pathCost = 0;
		}

		public Path(T init, int pathCost) {
			this.pathList = new LinkedList<T>();
			this.pathList.add(init);
			this.pathCost = 0;
		}

		public Path(LinkedList<T> existingPathList, int pathCost) {
			this.pathList = existingPathList;
			this.pathCost = pathCost;
		}

		public Path(Path<T> existingPath, int pathCost) {
			this.pathList = existingPath.pathList;
			this.pathCost = pathCost;
		}

		/**
		 * Appends the item to this <tt>Path</tt>, and returns the list with this item.
		 * @param item the item to add.
		 * @return the <tt>Path</tt> after appending
		 */
		public Path<T> append(T item) {
			this.pathList.add(item);
			return this;
		}

		/**
		 * @return the last item of this <tt>Path</tt>
		 */
		public T getLastItem() {
			return this.pathList.get(this.pathList.size()-1);
		}

		@Override
		public boolean equals(Object obj) {
			//two Paths are equal if both their path costs, and
			//their contents are equal

			if(obj == null)
				return false;

			else if(!(obj instanceof Path<?>))
				return false;

			else {
				Path<?> other = (Path<?>) obj;
				if(this.pathCost != other.pathCost)
					return false;

				if(!this.pathList.equals(other.pathList))
					return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			int hash = 11 * this.pathList.peekLast().hashCode();
			return hash;
		}

		@Override
		public int compareTo(Object arg0) {

			if(arg0 == null)
				throw new NullPointerException(arg0 + " cannot be null");

			else if(!(arg0 instanceof Path<?>))
				throw new ClassCastException(arg0.getClass().getName() +" is not of type "+this.getClass().getName());

			Path<?> other = (Path<?>) arg0;

			if(this.pathCost < other.pathCost)
				return -1;

			else if(this.pathCost > other.pathCost)
				return 1;

			else
				return 0;
		}

		@Override
		public Iterator<T> iterator() {
			return new PathIterator<T>(this);
		}

		/**
		 * The PathIterator that makes <tt>World.Path</tt> iterable.
		 * @param <S> the PathIterator type.
		 * @see <tt>World.Path<T></tt> at <tt>World.java</tt> 
		 */
		private class PathIterator<S> implements Iterator<S> {
			private int iteratorIndex;
			private int iterablePathSize;
			private Path<S> iterablePath;

			public PathIterator(Path<S> p) {
				iteratorIndex = 0;
				iterablePathSize = p.pathList.size();
			}

			@Override
			public boolean hasNext() {
				if(iterablePathSize > 0 || iteratorIndex < iterablePathSize-1)
					return true;

				return false;
			}

			@Override
			public S next() {
				S pathItem = iterablePath.pathList.get(iteratorIndex);
				iteratorIndex++;
				return pathItem;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("The PathIterator does not support this method.");

			}

		}



	}

}
