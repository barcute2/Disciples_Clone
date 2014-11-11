/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import model.Unit;
/**
 * @author zeno
 *
 */
public class UnitTest {
	private Unit unit;
	
	@Before
	public void initializeUnit(){
		unit = new Unit(50, 5, 3, 10, 0);
	}
	@Test
	public void testUnit() {
		assertEquals(50, unit.getHp());
		assertEquals(5, unit.getDamage());
		assertEquals(3, unit.getArmor());
		assertEquals(10, unit.getXpGiven());
		assertEquals(0, unit.getPosition());
	}
	
	@Test
	public void testHP(){
		unit.updateHp(5);
		assertEquals(55, unit.getHp());
		unit.setHp(0);
		unit.updateHp(5);
		assertEquals(0, unit.getHp());
	}
	
	@Test
	public void testXp(){
		assertEquals(1, unit.getLevel());
		unit.updateXp(200);
		assertEquals(200, unit.getXp());
		assertEquals(2, unit.getLevel());
	}

}
