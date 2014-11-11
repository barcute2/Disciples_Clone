package model;

/**
 * This is the one settlement the 
 * player will control by default
 * @author zeno
 *
 */
public class Capital extends Settlement{
	/**
	 * The constructor for the capital must
	 * declare the enemy party present there as null,
	 * since the player controls the capital by default
	 */
	public Capital(){
		super(50, 4, 4, null);
	}
}
