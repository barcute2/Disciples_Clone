/**
 * 
 */
package model;

/**
 * The towns which must be controlled by the player in order to 
 * win the game
 * @author zeno
 *
 */
public class Town extends Settlement{
	/**
	 * The default constructor must specify that
	 * a regular town generates half the income of the capital
	 * @param location
	 * @param p
	 */
	public Town(int x, int y, EnemyParty p){
		super(25, x, y, p);
	}
}
