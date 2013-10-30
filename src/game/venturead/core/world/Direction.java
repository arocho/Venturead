package game.venturead.core.world;

/**
 * Represents a direction for orientation in the world.
 * @author recardona
 */
public enum Direction {
	NORTH, SOUTH, EAST, WEST, 
	NORTHWEST, NORTHEAST,
	SOUTHWEST, SOUTHEAST,
	UP, DOWN, LEFT, RIGHT;
	
	public static Direction findOppositeDirection(Direction direction) {

		switch(direction)
		{
			case NORTH:
				return Direction.SOUTH;
				
			case SOUTH:
				return Direction.NORTH;
				
			case EAST:
				return Direction.WEST;
				
			case WEST:
				return Direction.EAST;
			
			case UP:
				return Direction.DOWN;
				
			case DOWN:
				return Direction.UP;
				
			case LEFT:
				return Direction.RIGHT;
				
			case RIGHT:
				return Direction.LEFT;
				
			case NORTHEAST:
				return Direction.SOUTHWEST;
				
			case NORTHWEST:
				return Direction.SOUTHEAST;
				
			case SOUTHEAST:
				return Direction.NORTHWEST;
				
			case SOUTHWEST:
				return Direction.NORTHEAST;
					
			default:
				throw new IllegalArgumentException("Direction " +direction.name()+" not recognized.");
		}
	}
}
