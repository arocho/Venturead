package game.ventureread.core.world.test;

import static org.junit.Assert.fail;
import game.venturead.core.world.Direction;
import game.venturead.core.world.Location;

import org.junit.Before;
import org.junit.Test;

public class LocationTest {
	
	private Location house;
	private Location attic;
	private Location garden;
	
	@Before
	public void setUp() throws Exception {
		house  = Location.newLocation("The House", "You see a quaint house, with tons of knickknacks.");
		attic  = Location.newLocation("The Attic", "You see a grue!  Run!");
		garden = Location.newLocation("The Garden", "You see a beautiful colorful garden.");
		
		house.connectToLocation(attic, Direction.UP);
		house.connectToLocation(garden, Direction.EAST);
		garden.connectToLocation(house, Direction.WEST);
		attic.connectToLocation(house, Direction.DOWN); 
	}

	@Test
	public void testToString() {
		System.out.println(house);
	}
	
	@Test
	public void testHashCode() {
		
		fail("Not yet implemented");
	}

	@Test
	public void testNewLocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testConnectToLocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testRegisterCharacter() {
		fail("Not yet implemented");
	}

}
