package model;

/**
 * This is the unique hero unit
 * @author zeno
 *
 */
public class Hero  extends Unit{
	protected int moveDistance;
	
	/**
	 * The constructor for this class calls the
	 * constructor for the parent class and also
	 * sets the unique attribute of this class
	 */
	public Hero(){
		super(100, 18, 5, 100, 0);
		moveDistance = 5;
	}
	
	public int getPath(){
		return moveDistance;
	}
	
	public void setPath(){
		moveDistance += level - 1;
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
		moveDistance += level - 1;
	}
}
