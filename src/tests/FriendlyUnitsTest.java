/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import model.Priest;
import model.Knight;
import model.Wizard;
/**
 * @author zeno
 *
 */
public class FriendlyUnitsTest {

	@Test
	public void testKnight() {
		Knight knight = new Knight();
		assertEquals(50, knight.getMaxHp());
	}
	@Test
	public void testWizard(){
		Wizard wizard = new Wizard();
		assertEquals(30, wizard.getMaxHp());
	}
	@Test
	public void testPriest(){
		Priest priest = new Priest();
		assertEquals(25, priest.getMaxHp());
	}

}
