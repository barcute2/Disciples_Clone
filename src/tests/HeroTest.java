/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import model.Hero;
/**
 * @author zeno
 *
 */
public class HeroTest {
	private Hero hero;
	
	
	@Test
	public void testHero() {
		hero = new Hero();
		assertEquals(5, hero.getPath());
		hero.setPath();
		assertEquals(5, hero.getPath());
		hero.updateXp(200);
		hero.setPath();
		assertEquals(6, hero.getPath());
	}

}
