package game.venturead.core.world.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import game.venturead.core.world.Direction;
import game.venturead.core.world.Location;

import org.junit.Before;
import org.junit.Test;

public class LocationTest {
	
	private Location house;
	private Location houseClone;
	private Location attic;
	private Location garden;
	
	@Before
	public void setUp() throws Exception {
		house  = Location.newLocation("The House", "You see a quaint house, with tons of knickknacks.");
		houseClone = Location.newLocation("The House", "This is a clone of the house, but it is equal to it.");
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
		
		assertEquals("Houses with equal names should produce the same hash", house.hashCode(), houseClone.hashCode());
		
		Random r = new Random(System.currentTimeMillis()+System.nanoTime()); 
		RandomStringUtils.random(7, true, false);
		String[] randomNames = new String[10000];
		
		System.out.println("Test");
		
		for(int i=0; i<10000; i++) {
			randomNames[i] = RandomStringUtils.random(7, true, false);
		}

		
		Location location1;
		Location location2;
		int maxTimesLocationHashClashed = 0;
		
		for(int i=0; i<10000; i++) {
			String nameToCheck = randomNames[i];
			location1 = Location.newLocation(nameToCheck, "This is a mock description.");
			int numTimesLocationHashClashed = 0; 
				//I expect this to be one because it will always clash with itself.
			
			for(String otherNameToCheck : randomNames) {
				location2 = Location.newLocation(otherNameToCheck, "This is another mock description.");
				if(location1.hashCode() == location2.hashCode()) {
					numTimesLocationHashClashed++;
					if(numTimesLocationHashClashed > 1)
					{
						System.out.print("Location "+location1.getName());
						System.out.println(" clashed with "+location2.getName()+" more than once.");
					}
				}
			}
			
			if(numTimesLocationHashClashed > maxTimesLocationHashClashed) {
				maxTimesLocationHashClashed = numTimesLocationHashClashed;
			}
		}
		
		assertEquals(1, maxTimesLocationHashClashed);
	}

	@Test
	public void testRegisterCharacter() {
		fail("Not yet implemented");
	}

}
