package model;

import java.util.*;

/**
 * The map is one of the two playable
 * configurations for the game, the other
 * one being the combat mode
 * @author zeno
 *
 */
public class Map {
	private Square[][] squares;
	private Capital capital;
	private Town[] towns;
	public FriendlyParty friendlyParty;
	public EnemyParty[] enemyParties;
	private int money = 0, day = 1, movement;
	ArrayList<Square> path;
	private final int GREEN = 0, BLUE = 1, RED = 2, GRAY = 3, BROWN = 4, BLACK = 5;
	
	/**
	 * The constructor will keep it simple,
	 * since all the initializations are done
	 * through private functions
	 */
	public Map(){		
		setUpTheSquares();
		setUpTheUnits();
		movement = friendlyParty.getHero().getPath();
		setUpTheTowns();
		path = new ArrayList<Square>();
		money = 0;
		resetMap();
	}
	
	public FriendlyParty getFriendlyParty(){
		return friendlyParty;
	}
	public EnemyParty[] getEnemyParties(){
		return enemyParties;
	}
	public int getMoney(){
		return money;
	}
	public int getDay(){
		return day;
	}
	public void setMoney(int m){
		money = m;
	}
	public ArrayList<Square> getPath(){
		return path;
	}
	public Square[][] getSquares(){
		return squares;
	}
	
	/**
	 * This is a helper function destined to set up 
	 * the squares on the map
	 */
	private void setUpTheSquares(){
		squares = new Square[50][50];
		for(int i = 0; i < 50; i++)
			for(int j = 0; j < 50; j++){
				squares[i][j] = new Square(i, j);
			}
	}
	
	/**
	 * This is a helper function destined
	 * to set up the player's party and the
	 * enemy parties
	 */
	private void setUpTheUnits(){
		friendlyParty = new FriendlyParty();		
		friendlyParty.setLocation(4, 4);
		enemyParties = new EnemyParty[20];
		for(int i = 0; i < 20; i++)
			enemyParties[i] = new EnemyParty(null, null);
		for(int i = 0; i < 10; i++){
			//field parties have 4 units each
			addUnits(i+10, 4);
			//town parties have 6 units each
			addUnits(i, 6);
		}	
		enemyParties[10].setLocation(0, 0);
		enemyParties[11].setLocation(12, 12);
		enemyParties[12].setLocation(1, 7);
		enemyParties[13].setLocation(49,49);
		enemyParties[14].setLocation(20, 20);
		enemyParties[15].setLocation(40, 3);
		enemyParties[16].setLocation(10, 46);
		enemyParties[17].setLocation(30, 30);
		enemyParties[18].setLocation(40, 40);
		enemyParties[19].setLocation(32, 10);
	}
	
	/**
	 * Helper function that is here for refactoring purposes
	 * @param i the index of the unit array
	 * @param jCounter the upper limit of the j counter
	 */
	private void addUnits(int i, int jCounter){
		for(int j = 0; j < jCounter; j++){
			Unit randomUnit;			
			int randomThree = (int) Math.ceil(Math.random() * 3);
			if(randomThree == 1){
				randomUnit = new SkeletonArcher();				
			}
			else if(randomThree == 2){
				randomUnit = new SkeletonWarrior();				
			}
			else{
				randomUnit = new Gargoyle();
			}
			enemyParties[i].addCharacter(randomUnit);
	
		}
	}
		
	/**
	 * Helper function which will randomly put the 
	 * towns on the map
	 */
	private void setUpTheTowns(){
		capital = new Capital();
		squares[4][4].setColor(BLACK);
		squares[4][5].setColor(BLACK);
		squares[5][4].setColor(BLACK);
		squares[5][5].setColor(BLACK);
		towns = new Town[10];		
		towns[0] = new Town(8, 8, enemyParties[0]);
		enemyParties[0].setLocation(8, 8);
		towns[1] = new Town(4, 14, enemyParties[1]);
		enemyParties[1].setLocation(4, 14);
		towns[2] = new Town(14, 4, enemyParties[2]);
		enemyParties[2].setLocation(14,4);
		towns[3] = new Town(10, 24, enemyParties[3]);
		enemyParties[3].setLocation(10, 24);
		towns[4] = new Town(4, 42, enemyParties[4]);
		enemyParties[4].setLocation(4, 42);
		towns[5] = new Town(10, 32, enemyParties[5]);
		enemyParties[5].setLocation(10, 32);
		towns[6] = new Town(22, 44, enemyParties[6]);
		enemyParties[6].setLocation(22, 44);
		towns[7] = new Town(36, 44, enemyParties[7]);
		enemyParties[7].setLocation(36, 44);
		towns[8] = new Town(30, 12, enemyParties[8]);
		enemyParties[8].setLocation(30, 12);
		towns[9] = new Town(40, 20, enemyParties[9]);
		enemyParties[9].setLocation(40, 20);
		for(int i = 0; i < 10; i++){
			squares[towns[i].getLocationX()][towns[i].getLocationY()].setColor(BROWN);
			squares[towns[i].getLocationX()+1][towns[i].getLocationY()].setColor(BROWN);
			squares[towns[i].getLocationX()][towns[i].getLocationY()+1].setColor(BROWN);
			squares[towns[i].getLocationX()+1][towns[i].getLocationY()+1].setColor(BROWN);
		}
	}
	
	/**
	 * Function which checks when all the towns 
	 * have been taken and tells if the game was won
	 * @return
	 */
	public boolean gameOver(){
		if(friendlyParty.gameOver() == 1)
			return true;
		for(int i = 0; i < 10; i++){
			if(towns[i].getParty() != null)
				return false;
		}
		return true;
	}
	
	/**
	 * Function which will be called each time a player decides to end 
	 * their turn
	 */
	public void incrementTurn(){
		day++;
		//update the amount of money the player has
		money += capital.getRevenue();
		for(int i = 0; i < 10; i++){
			if(towns[i].getParty() == null){
				money +=towns[i].getRevenue();
			}
		}
		//update the movement of the friendly party on the map
		movement = friendlyParty.getHero().getPath();
		//update the enemies
		for(int i = 0; i < 20; i++){
			if(enemyParties[i] != null)
				enemyParties[i].dailyUpdate();
		}
	}
	
	/**
	 * This function will update the arrayList with 
	 * all the squares necessary to get to a certain
	 * square
	 * @param x the x coordinate of the new square
	 * @param y the y coordinate of the new square
	 */
	private void findPath(int x, int y){
		resetMap();
		path = new ArrayList<Square>();		
		int incrementX = 1, incrementY = 1;
		int tempX = friendlyParty.getLocationX();
		int tempY = friendlyParty.getLocationY();		
		if(x < friendlyParty.getLocationX()){
			incrementX = -1;
		}
		if(y < friendlyParty.getLocationY()){
			incrementY = -1;
		}
		while((tempX != x) && (tempY != y)){
			tempX += incrementX;
			tempY += incrementY;
			path.add(squares[tempX][tempY]);
		}
		if(x == tempX)
			while(y != tempY){
				tempY += incrementY;
				path.add(squares[tempX][tempY]);
			}		
		else
			while(x != tempX){
				tempX += incrementX;
				path.add(squares[tempX][tempY]);				
			}		
	}
	
	/**
	 * Helper function to determine whether there
	 * is an enemy party on a certain square
	 * @param x the x component of the destination
	 * @param y the y component of the destination 
	 * @return true if there are enemies or false otherwise
	 */
	public boolean areThereEnemiesAt(int x, int y){
		for(int i = 0; i < 20; i++)
			if((enemyParties[i] != null) &&(enemyParties[i].getLocationX() == x) &&(enemyParties[i].getLocationY() == y))
				return true;
		for(int i = 0; i < 10; i++)
			if((enemyParties[i] != null) && ((enemyParties[i].getLocationX() == x)||(enemyParties[i].getLocationX() == x-1)) && ((enemyParties[i].getLocationY() == y) || (enemyParties[i].getLocationY() == y - 1)))
				return true;
		return false;
	}
	
	
	
	/**
	 * Helper function to get the enemy party 
	 * located on a certain square
	 * @param x the x component of the destination
	 * @param y the y component of the destination 
	 * @return the enemy party at that location
	 */
	public EnemyParty getEnemiesAt(int x, int y){
		for(int i = 0; i < 20; i++)
			if((enemyParties[i].getLocationX() == x) &&(enemyParties[i].getLocationY() == y))
				return enemyParties[i];
		for(int i = 0; i < 10; i++)
			if(((enemyParties[i].getLocationX() == x)||(enemyParties[i].getLocationX() == x-1)) && ((enemyParties[i].getLocationY() == y) || (enemyParties[i].getLocationY() == y - 1)))
				return enemyParties[i];
		return null;
	}
	
	/**
	 * Helper function to get the index of an enemy party party 
	 * located on a certain square
	 * @param x the x component of the destination
	 * @param y the y component of the destination 
	 * @return the index of enemy party at that location
	 */
	public int getEnemyIndex(int x, int y){
		for(int i = 0; i < 20; i++)
			if((enemyParties[i].getLocationX() == x) &&(enemyParties[i].getLocationY() == y))
				return i;
		for(int i = 0; i < 10; i++)
			if(((enemyParties[i].getLocationX() == x)||(enemyParties[i].getLocationX() == x-1)) && ((enemyParties[i].getLocationY() == y) || (enemyParties[i].getLocationY() == y - 1)))
				return i;
		return -1;
	}
	
	/**
	 * Function which will help show the path to 
	 * a certain square
	 * @param x the x component of the destination
	 * @param y the y component of the destination
	 */
	public ArrayList<Square> setUpPath(int x, int y){
		findPath(x, y);
		if(path.size() == 0)
			return null;
		int distance = 0;
		while((distance < movement) &&(distance < path.size()) && (areThereEnemiesAt(path.get(distance).getX(), path.get(distance).getY()) == false)){
			path.get(distance).setColor(BLUE);
			distance ++;
		}
		if(distance == movement){
			while(distance < path.size()){
				path.get(distance).setColor(GRAY);
				distance++;
			}
		}
		else{
			while(distance < path.size()){
				path.get(distance).setColor(RED);
				distance++;
			}
		}
		return path;
	}
	
	/**
	 * Function which will move the friendly
	 * party to a certain square after the 
	 * path has been set up
	 * @return the square where the party is now located
	 */
	public Square move(){
		if((path == null) || (path.size() == 0))
				return null;
		int blueIndex = path.size() - 1;
		while((blueIndex >= 0) && (path.get(blueIndex).getColor() != BLUE))
			blueIndex -= 1;
		if(blueIndex < 0)
			return null;
		if((blueIndex < path.size() - 1) && (path.get(blueIndex+1).getColor() == RED))
			blueIndex += 1;
		friendlyParty.setLocation(path.get(blueIndex).getX(), path.get(blueIndex).getY());
		movement -= blueIndex + 1;
		resetMap();
		return squares[path.get(blueIndex).getX()][path.get(blueIndex).getY()];
	}
	
	/**
	 * Function which gets called when the player
	 * hires new units
	 * @param unit the new unit
	 */
	public void hireUnit(Unit unit){		
		if((unit instanceof Knight) && (money >= 250)){
			money -= 250;
			friendlyParty.addCharacter(unit);
		}
		else if((unit instanceof Wizard) && (money >= 300)){
			money -= 300;
			friendlyParty.addCharacter(unit);
		}
		else if((unit instanceof Priest) && (money >= 200)){
			money -= 200;
			friendlyParty.addCharacter(unit);
		}		
	}
	
	/**
	 * Function which gets called when the player 
	 * heals a unit
	 * @param pos the position of the unit which will get healed
	 */
	public void healUnit(int pos){
		int amountToHeal = friendlyParty.unitAt(pos).getMaxHp() - friendlyParty.unitAt(pos).getHp();
		if(money < amountToHeal){			
			friendlyParty.healCharacter(pos, money);
			money = 0;
		}
		else{
			money -= amountToHeal;
			friendlyParty.healCharacter(pos, amountToHeal);
		}
	}
	
	/**
	 * Function which gets called when the player wants to revive a unit 
	 * @param pos the position of the unit
	 */
	public void reviveUnit(int pos){
		if(money >= 150){
			money -= 150;
			friendlyParty.reviveCharacter(pos);
		}		
	}
	
	/**
	 * Helper function which will re-color the map each time the
	 * find path function is called 
	 */
	public void resetMap(){
		for(int i = 0; i < 50; i++)
			for(int j = 0; j < 50; j++){
				squares[i][j].setColor(GREEN);
			}
		for(int i = 0; i < 10; i++){
			squares[towns[i].getLocationX()][towns[i].getLocationY()].setColor(BROWN);
			squares[towns[i].getLocationX()+1][towns[i].getLocationY()].setColor(BROWN);
			squares[towns[i].getLocationX()][towns[i].getLocationY()+1].setColor(BROWN);
			squares[towns[i].getLocationX()+1][towns[i].getLocationY()+1].setColor(BROWN);
		}
		squares[4][4].setColor(BLACK);
		squares[5][4].setColor(BLACK);
		squares[4][5].setColor(BLACK);
		squares[5][5].setColor(BLACK);
		for(int i = 0; i < 20; i++)
			if(enemyParties[i] != null)
				squares[enemyParties[i].getLocationX()][enemyParties[i].getLocationY()].setColor(RED);
		squares[friendlyParty.getLocationX()][friendlyParty.getLocationY()].setColor(BLUE);
	}
	
	/**
	 * Helper function to determine whether we are in a town or not
	 * @return true if we are in a town and false otherwise
	 */
	public boolean weAreInTown(){
		if(((friendlyParty.getLocationX() == 4) || (friendlyParty.getLocationX() == 5)) && ((friendlyParty.getLocationY() == 4) || (friendlyParty.getLocationY() == 5)))
			return true;
		for(int i = 0; i < 10; i++){			
			if(((towns[i].getLocationX() == friendlyParty.getLocationX()) || (towns[i].getLocationX() == friendlyParty.getLocationX() + 1)) && ((towns[i].getLocationY() == friendlyParty.getLocationY()) || (towns[i].getLocationY() == friendlyParty.getLocationY() + 1)))
					return true;
		}
		return false;
	}
}
