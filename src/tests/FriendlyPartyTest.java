/**
 * 
 */
package tests;

import static org.junit.Assert.*;



import org.junit.*;
import model.FriendlyParty;
import model.Knight;
/**
 * @author zeno
 *
 */
public class FriendlyPartyTest {

	private FriendlyParty party;
	
	@Before
	public void initializeParty(){
		party = new FriendlyParty();
		party.setLocation(1,1);
	}
	@Test
	public void testConstructor() {
		assertEquals(100, party.getUnits()[0].getMaxHp());
	}
	@Test
	public void testAddCharacter(){
		Knight knight = new Knight();
		assertEquals(1, knight.getPosition());
		party.addCharacter(knight);
		assertEquals(50, party.getUnits()[2].getMaxHp());
	}
	@Test
	public void testChangeCharacterPosition(){
		party.changeCharacterPosition(party.getUnits()[0], 3);
		assertEquals(null, party.getUnits()[0]);
		assertEquals(100, party.getUnits()[3].getMaxHp());
		party.changeCharacterPosition(party.getUnits()[3], 0);
		assertEquals(100, party.getUnits()[0].getMaxHp());
		party.changeCharacterPosition(party.getUnits()[0], 1);
		assertEquals(100, party.getUnits()[1].getMaxHp());
		assertEquals(50, party.getUnits()[0].getMaxHp());
	}
	@Test
	public void testHealUnit(){
		assertEquals(100, party.getUnits()[0].getHp());
		party.getUnits()[0].setHp(20);
		party.healCharacter(0, 10);
		assertEquals(30, party.getUnits()[0].getHp());
	}
	@Test
	public void testReviveUnit(){
		party.getUnits()[0].setHp(0);
		party.reviveCharacter(0);
		assertEquals(1, party.getUnits()[0].getHp());
	}
	@Test
	public void testGameOver(){
		party.getUnits()[0].setHp(0);
		party.getUnits()[1].setHp(0);
		assertEquals(1, party.gameOver());
		party.reviveCharacter(0);
		assertEquals(0, party.gameOver());
	}
}
