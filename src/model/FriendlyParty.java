package model;

/**
 * This is the class for the party of characters
 * controlled by the player
 * @author zeno
 *
 */
public class FriendlyParty {
	private Unit[] characters = new Unit[6];
	private int locationX, locationY;
	
	/**
	 * The constructor for this class sets up the array of units
	 */
	public FriendlyParty(){
		characters[0] = new Hero();
		characters[1] = new Knight();
		characters[2] = null;
		characters[3] = null;
		characters[4] = null;
		characters[5] = null;
	}
	
	public Unit[] getUnits(){
		return characters;
	}
	public int getLocationX(){
		return locationX;
	}
	public int getLocationY(){
		return locationY;
	}
	public void setLocation(int x, int y){
		locationX = x;
		locationY = y;
	}
	
	/**
	 * This function will add a new character to the party
	 * @param unit the unit which will get added to the party
	 */
	public void addCharacter(Unit unit){
		int pos = unit.getPosition();
		if(characters[pos] == null){
			characters[pos] = unit;
		}
		else{
			for(int i = 0; i < 6; i++)
				if(characters[i] == null){
					characters[i] = unit;
					unit.setPosition(i);
					return;
				}
		}
	}
	
	/**
	 * This function will move a character within a party from one position to another.
	 * In case the desired position is taken, the two characters will switch positions
	 * @param unit The unit which will get moved
	 * @param pos The desired position for that unit
	 */
	public void changeCharacterPosition(Unit unit, int pos){		
		if(unit == null)
			return;
		int currentPosition = unit.getPosition();
		if(characters[pos] == null){
			characters[pos] = unit;
			characters[currentPosition] = null;
			unit.setPosition(pos);
		}
		else{
			Unit temp = characters[pos];
			characters[pos] = unit;
			unit.setPosition(pos);			
			characters[currentPosition] = temp;
			characters[currentPosition].setPosition(currentPosition);
		}		
	}
	
	/**
	 * In each city, the player will have the ability to heal
	 * a unit
	 * @param pos the position of the unit within the party
	 * @param hp the amount of hit points it will get healed
	 * @return
	 */
	public int healCharacter(int pos, int hp){
		if(characters[pos].getHp() < characters[pos].getMaxHp()){
			characters[pos].updateHp(hp);
			return 1;
		}
		else{
			return 0;
		}
	}
	
	/**
	 * If one of the team members has fallen, the player
	 * has the option to revive him
	 * @param pos the position of the fallen character
	 * @return
	 */
	public int reviveCharacter(int pos){
		if(characters[pos].getHp() == 0){
			characters[pos].setHp(1);
			return 1;
		}
		else{
			return 0;
		}
	}
	
	/**
	 * After each battle, the game
	 * which check if all the members of the party are dead, in which 
	 * case the game ends.
	 * @return 1 if it's over, 0 if at least one member of the party
	 *		   is still alive
	 */
	public int gameOver(){
		for(int i = 0; i < 6; i++)
			if((characters[i] != null) && (characters[i].getHp() != 0))
				return 0;
		return 1;
	}
	
	/**
	 * This function will return the hero in the party
	 * @return the hero
	 */
	public Hero getHero(){
		for(int i = 0; i < 6; i++){
			if((characters[i] != null) && (characters[i] instanceof Hero))
				return (Hero)characters[i];
		}
		return null;
	}
	
	/**
	 * Returns the unit from a certain index
	 * @param pos the position within the party
	 * @return the unit at that position
	 */
	public Unit unitAt(int pos){
		return characters[pos];
	}
	
	/**
	 * This function will remove one of the characters from the party
	 * @param pos the position within the party
	 */
	public void dismiss(int pos){
		characters[pos] = null;
	}
	
	/**
	 * Function which will determine when all the members of the
	 * friendly party are dead
	 * @return true if they are all dead and false otherwise
	 */
	public boolean allDead(){
		for(int i = 0; i < 6; i++)
			if((characters[i]!=null) && (characters[i].getHp() > 0))
				return false;
		return true;
	}
}
