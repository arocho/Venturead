package game.venturead.core.world.test;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import game.venturead.core.world.World;

import org.junit.Before;
import org.junit.Test;

public class WorldTest {
	
	World world;

	@Before
	public void setUp() throws Exception {
		world = new World();
	}

	@Test
	public void testRegisterNewLocation() {
		world.registerNewLocation("The Attic", "a musty old attic.");
		world.registerNewLocation("The House", "a quaint house, with tons of knickknacks.");
		world.registerNewLocation("The Garden", "a garden full of gnomes.");
		
		assertEquals("There should be 3 locations!", 3, world.getWorldLocations().size());
		
		try {
			world.registerNewLocation("The Attic", "Oh no!  It's an attic clone!");
		} catch(IllegalArgumentException e) {
			assertTrue("Exception caught!",true);
		}
	}

	@Test
	public void testCreatePath() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
