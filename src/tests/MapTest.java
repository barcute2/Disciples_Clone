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
public class MapTest {
	Map map;
	@Before
	public void initialize(){
		map = new Map();
	}
	@Test
	public void testConstructor() {
		assertEquals(4, map.getFriendlyParty().getLocationX());
		assertEquals(0, map.getMoney());
		assertEquals(0, map.getDay());
		assertEquals(false, map.gameOver());
	}
	@Test
	public void testIncrementTurn(){
		map.incrementTurn();
		assertEquals(1, map.getDay());
		assertEquals(50, map.getMoney());
		
	}

}
