package game.venturead.core.world.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import game.venturead.core.world.Direction;
import game.venturead.core.world.Location;
import game.venturead.core.world.World;

import org.junit.Before;
import org.junit.Test;

public class WorldTest {
	
	World world;
	Location attic;
	Location house;
	Location garden;

	@Before
	public void setUp() throws Exception {
		world = new World();
		attic=world.registerNewLocation("The Attic", "a musty old attic.");
		house=world.registerNewLocation("The House", "a quaint house, with tons of knickknacks.");
		garden=world.registerNewLocation("The Garden", "a garden full of gnomes.");
	}

	@Test
	public void testRegisterNewLocation() {
		
		assertEquals("There should be 3 locations!", 3, world.getWorldLocations().size());
		
		try {
			world.registerNewLocation("The Attic", "Oh no!  It's an attic clone!");
		} catch(IllegalArgumentException e) {
			assertTrue("Exception caught!",true);
		}
	}

	@Test
	public void testCreatePath() {
		world.createPath(attic, Direction.DOWN, house);
		world.createPath(garden, Direction.EAST, house);
	}

	@Test
	public void testToString() {
		System.out.println(world.toString());
	}

}
