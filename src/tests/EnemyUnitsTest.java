/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import model.Gargoyle;
import model.SkeletonArcher;
import model.SkeletonWarrior;
/**
 * @author zeno
 *
 */
public class EnemyUnitsTest {

	@Test
	public void testGargoyle() {
		Gargoyle garg = new Gargoyle();
		assertEquals(200, garg.getMaxHp());
	}
	@Test 
	public void testSkeletonArcher(){
		SkeletonArcher skel = new SkeletonArcher();
		assertEquals(40, skel.getMaxHp());
	}
	@Test 
	public void testSkeletonWarrior(){
		SkeletonWarrior skel = new SkeletonWarrior();
		assertEquals(100, skel.getMaxHp());
	}

}
