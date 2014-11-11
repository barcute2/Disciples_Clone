package model;

public class Unit {
	protected int hp, maxHp, xp, damage, armor, xpGiven, level, position;
	protected int levels[] = {200, 500, 1000, 2000, 4000, 8000, 15000};
	
	/**
	 * This is the constructor for the unit class
	 * By default, the unit will have 0 xp at level 1
	 * @param h the hit points of the unit
	 * @param d the damage which can be dealt by the unit
	 * @param a the armor of the unit
	 * @param xGiven the experience given when the unit dies
	 */
	public Unit(int h, int d, int a, int xGiven, int pos){
		hp = h;
		maxHp = h;
		xp = 0;
		damage = d;
		armor = a;
		xpGiven = xGiven;
		level = 1;
		position = pos;
	}
	
	public int getHp(){
		return hp;
	}
	public int getMaxHp(){
		return maxHp;
	}
	public int getXp(){
		return xp;
	}
	public int getDamage(){
		return damage;
	}
	public int getArmor(){
		return armor;
	}
	public int getXpGiven(){
		return xpGiven;
	}
	public int getPosition(){
		return position;
	}
	public int getLevel(){
		return level;
	}
	public void setHp(int h){
		hp = h;
	}
	public void setMaxHp(int h){
		maxHp = h;
	}
	public void setXp(int x){
		xp = x;
	}
	public void setDamage(int d){
		damage = d;
	}
	public void setArmor(int a){
		armor = a;
	}
	public void setXpGiven(int xGiven){
		xpGiven = xGiven;
	}
	public void setPosition(int pos){
		position = pos;
	}	
	
	/**
	 * This function will add to the hit points of a unit
	 * To make sure the unit stays dead, or that the player
	 * doesn't attempt to heal a dead unit, the action will
	 * only be performed if the unit is already alive
	 * @param h the amount of health recovered by the unit
	 */
	public void updateHp(int h){
		if(hp > 0){
			if(hp + h <= maxHp)	
				hp += h;
			else
				hp = maxHp;
		}
	}
	
	/**
	 * This method will update the experience
	 * points of the unit. It will be called when the 
	 * unit is receiving experience
	 * @param x
	 */
	public void updateXp(int x){
		if(hp > 0){
			xp += x;
			updateLevel();
		}
	}
	
	/**
	 * This function will be called after each fight,
	 * to check whether any of the units have leveled up after
	 * the experience gained
	 */
	public void updateLevel(){
		for(int i = 0; i < 6; i++){
			if((xp >= levels[i]) && (xp < levels[i+1])){
				if(i != level)
					updateCharacter();
				level = i+2;
			}
		}
	}
	
	/**
	 * This function will get called right after the level has been updated
	 * and it will update the attributes of the units accordingly
	 */
	public void updateCharacter(){
		maxHp += 5;
		hp +=5;
		damage +=3;
		armor += (int)level/3;
	}
	
	public void updateXpGiven(){
		xpGiven += 10*(level - 1);
	}
	
	/**
	 * This function will be called each time a unit takes damage
	 * @param d
	 */
	public void takeDamage(int d){
		if(d - armor * 2 > 0)
			hp -= d - armor * 2;
		if(hp < 0){
			hp = 0;
		}
	}
	
}
