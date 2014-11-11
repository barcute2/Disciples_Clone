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
public class EnemyPartyTest {
	private EnemyParty party;	
	private Unit[] units;
	private int[] positions = new int[2];
	@Before
	public void initializeParty(){
		units = new Unit[2];
		units[0] = new Gargoyle();
		units[1] = new SkeletonArcher();
		positions[0] = 1;
		positions[1] = 2;
		party = new EnemyParty(units, positions);
	}
	@Test
	public void testConstructor() {
		assertEquals(200, party.getUnits()[1].getMaxHp());
		assertEquals(40, party.getUnits()[2].getMaxHp());
	}
	@Test
	public void testDailyUpdate(){
		assertEquals(0, party.getUnits()[1].getXp());
		party.dailyUpdate();
		assertEquals(5, party.getUnits()[1].getXp());
	}

}
