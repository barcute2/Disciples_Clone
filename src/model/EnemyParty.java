package model;

/**
 * This class is a party composed of enemy units
 * @author zeno
 *
 */
public class EnemyParty {
	private Unit[] characters = new Unit[6];
	private int locationX, locationY;
	
	/**
	 * The constructor for an enemy party will be as follows,
	 * for convenience purposes - the index of the positions array
	 * will coincide with the index of the units array
	 * @param units the array of enemy units in the party
	 * @param positions the positions of each of the enemy units
	 */
	public EnemyParty(Unit[] units, int[] positions){
		if(units != null)
			for(int i = 0; i < units.length; i++){
				characters[positions[i]] = units[i];
			}
	}
	
	public void setLocation(int locX, int locY){
		locationX = locX;
		locationY = locY;
	}
	public int getLocationX(){
		return locationX;
	}
	public int getLocationY(){
		return locationY;
	}
	public Unit[] getUnits(){
		return characters;
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
					characters[i].setPosition(i);
					return;
				}
		}
	}
	
	/**
	 * To make the game more challenging, the
	 * enemy units will gain 5 xp each turn and
	 * will regenerate 5 hp each turn
	 */
	public void dailyUpdate(){
		for(int i = 0; i < 6; i++){
			if(characters[i] != null){
				characters[i].updateXp(5);
				characters[i].updateHp(5);
			}
		}
	}
	
	/**
	 * This function will determine which unit from the friendly party
	 * will get attacked
	 * @param fParty the friendly party
	 * @return the unit which will get attacked
	 */
	public Unit pickVictim(FriendlyParty fParty, int position){
		Unit tentativeVictim = weHaveKill(fParty, position);
		if(tentativeVictim != null){
			return tentativeVictim;
		}
		else{
			int leastHp = fParty.getUnits()[0].getHp();
			for(int i = 1; i < 6; i++){
				if((fParty.getUnits()[i] != null) && (fParty.getUnits()[i].getHp() > 0) && (fParty.getUnits()[i].getHp() < leastHp)){
					leastHp = fParty.getUnits()[i].getHp();					
				}
			}
			for(int i = 0; i < 6; i++){
				if((fParty.getUnits()[i] != null) && (fParty.getUnits()[i].getHp() == leastHp)){
					return fParty.getUnits()[i];
				}
			}
		}
		return null;
	}
	
	/**
	 * Helper function to determine if the enemy party
	 * can kill any member of the friendly party
	 * @param fParty the friendly party
	 * @return the unit which can be killed or null otherwise
	 */
	private Unit weHaveKill(FriendlyParty fParty, int position){
		int maxDamage = 0;
		boolean weHaveKill = false;
		Unit tentativeVictim = null;
		for(int i = 0; i < 6; i++){
			Unit friendlyUnit = fParty.getUnits()[i];						
			if((friendlyUnit != null) && (friendlyUnit.getHp() > 0) && (friendlyUnit.getHp() - characters[position].getDamage() + friendlyUnit.getArmor()*2 <= 0)){
				if(friendlyUnit.getHp() > maxDamage){
					maxDamage = friendlyUnit.getHp();
					weHaveKill = true;
					tentativeVictim = friendlyUnit;						
				}				
			}
		}
		if((characters[position] instanceof SkeletonWarrior) && (tentativeVictim != null)){
			if((!noFriendsInTheWay(tentativeVictim, fParty)) || (!noEnemiesInTheWay(characters[position])))
				weHaveKill = false;
		}
			
		if(weHaveKill == true)
			return tentativeVictim;
		else
			return null;
	}	
	
	/**
	 * Function which determines if there are any friendly units blocking the attack
	 * @param unit the unit which will perform the attack
	 * @param friendlyParty the friendly party on the map
	 * @return true if the attack is blocked and false otherwise
	 */
	private boolean noFriendsInTheWay(Unit unit, FriendlyParty friendlyParty){
		if(unit.getPosition() < 3)
			return true;
		for(int i = 0; i < 3; i++)
			if((friendlyParty.getUnits()[i] != null) && (friendlyParty.getUnits()[i].getHp() != 0))
				return false;
		return true;
	}
	
	/**
	 * Function which determines if there are any enemies blocking the attack
	 * @param unit the unit which will perform the attack
	 * @return true if the attack will be blocked and false otherwise
	 */
	private boolean noEnemiesInTheWay(Unit unit){
		if(unit.getPosition() >= 3)
			return true;
		for(int i = 3; i < 6; i++)
			if((characters[i] != null) && (characters[i].getHp() != 0))
				return false;
		return true;
	}
	
	/**
	 * Function which will perform the attack, part of the AI
	 * @param fParty the friendly party present on the map
	 * @param position the position of the "victim" within the party
	 */
	public void attack(FriendlyParty fParty, int position){
		Unit victim = pickVictim(fParty, position);
		if(victim != null){
			if((characters[position] instanceof SkeletonWarrior) && ((!noFriendsInTheWay(victim, fParty)) || (!noEnemiesInTheWay(characters[position]))))
				return;
			victim.takeDamage(characters[position].getDamage());
		}
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
