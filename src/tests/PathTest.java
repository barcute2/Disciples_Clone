/**
 * 
 */
package tests;

import static org.junit.Assert.*;


import org.junit.*;
import model.*;
/**
 * @author zeno
 *
 */
public class PathTest {
	Map map;
	private final int GREEN = 0, BLUE = 1, RED = 2, GRAY = 3, BROWN = 4, BLACK = 5;
	@Before
	public void init(){
		map = new Map();
	}
	@Test
	public void testColors() {
		map.setUpPath(6, 4);
		assertEquals(BLUE, map.getSquares()[6][4].getColor());
		map.setUpPath(4,  7);
		assertEquals(GREEN, map.getSquares()[6][4].getColor());
		//assertEquals(BLUE, map.getSquares()[4][7].getColor());
	}
	
	@Test
	public void testEnemyAt(){
		assertEquals(false, map.areThereEnemiesAt(4, 7));
	}

}
