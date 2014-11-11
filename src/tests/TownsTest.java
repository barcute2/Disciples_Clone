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
public class TownsTest {
	private Settlement settlement;
	private Town town;
	private Capital capital;
	private EnemyParty party;	
	private Unit[] units;
	private int[] positions = new int[2];
	@Before
	public void initialize(){
		units = new Unit[2];
		units[0] = new Gargoyle();
		units[1] = new SkeletonArcher();
		positions[0] = 1;
		positions[1] = 2;
		party = new EnemyParty(units, positions);
		settlement = new Settlement(100, 4, 5, null);
		town = new Town(3, 2, party);
		capital = new Capital();
	}
	@Test
	public void testSettlement() {
		assertEquals(100, settlement.getRevenue());
		assertEquals(4, settlement.getLocationX());
		assertEquals(5, settlement.getLocationY());
		assertEquals(null, settlement.getParty());
	}
	@Test
	public void testTown(){
		assertEquals(25, town.getRevenue());
		assertEquals(200, town.getParty().getUnits()[1].getMaxHp());
	}
	@Test
	public void testCapital(){
		assertEquals(50, capital.getRevenue());
	}

}
