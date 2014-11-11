/**
 * 
 */
package view;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import model.*;
/**
 * This class will display the map-mode of the game.'
 * Combat mode is to be implemented later, in week 4
 * @author zeno
 *
 */
public class MapGUI extends JFrame{
	Map map;
	EnemyPanel enemyPanel;
	FriendlyPanel friendlyPanel;
	MapPanel mapPanel;
	TownPanel townPanel;
	ResourcePanel resourcePanel;
	GridLayout leftLayout;
	BorderLayout rightLayout;
	JPanel leftPanel, rightPanel;
	CombatWindow cw;
	
	int moveToX = -1, moveToY = -1, newX, newY; 
	private final int GREEN = 0, BLUE = 1, RED = 2, GRAY = 3, BROWN = 4, BLACK = 5;
	boolean firstClick = true;
	int actionTaken = 0;
	private final int SWITCHPLACES = 4, HEAL = 1, REVIVE = 2, DISMISS =3;
	Unit unit;
	int friendlyTurn=0, enemyTurn = 3, successfulAttack = 0, enemyIndex;
	
	/**
	 * Default constructor for the class, it 
	 * sets up the location of elements within the JFrame
	 */
	public MapGUI(){
		map = new Map();
		//map.getFriendlyParty().getHero().setHp(0);
		enemyPanel = new EnemyPanel(map);
		friendlyPanel = new FriendlyPanel(map);
		friendlyPanel.setActionListeners(new CharacterListener());
		mapPanel = new MapPanel(map);
		mapPanel.addMoveListener(new MoveListener());
		townPanel = new TownPanel(map);
		townPanel.setActionListeners(new ReviveListener(), new HealListener(), new HireKnightListener(), new HireWizardListener(), new HirePriestListener(), new DismissListener());
		resourcePanel = new ResourcePanel(map);
		leftLayout = new GridLayout(2, 1);
		rightLayout = new BorderLayout();
		leftPanel = new JPanel();
		leftPanel.setLayout(leftLayout);
		leftPanel.add(friendlyPanel);
		leftPanel.add(townPanel);
		rightPanel = new JPanel();
		rightPanel.setLayout(rightLayout);
		rightPanel.add(enemyPanel, BorderLayout.NORTH);
		rightPanel.add(resourcePanel, BorderLayout.SOUTH);	
		setLayout(new BorderLayout());
		add(leftPanel, BorderLayout.WEST);		
		add(mapPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
		scale();
		enemyPanel.setVisible(false);		
	}
	
	/**
	 * Helper function which will make sure all the elements fit nicely within the frame
	 */
	private void scale(){
		leftPanel.setPreferredSize(new Dimension(260, 600));
		rightPanel.setPreferredSize(new Dimension(260, 600));
		mapPanel.setPreferredSize(new Dimension(600, 600));
		resourcePanel.setPreferredSize(new Dimension(260, 150));
		enemyPanel.setPreferredSize(new Dimension(260, 400));
	}
	
	
	/**
	 * This class will implement the action listener and will be used
	 * when the friendly party is moving across the map.
	 * @author zeno
	 *
	 */
	class MoveListener implements ActionListener{
		JButton clickedButton;		
		/**
		 * Implemented method from the abstract class
		 * It will perform a different action based on the
		 * number of clicks performed
		 */
		public void actionPerformed(ActionEvent e){
			clickedButton = (JButton)e.getSource();
			for(int i = 0; i < 50; i++)
				for(int j = 0; j < 50; j++)
					if(clickedButton == mapPanel.location[i][j]){
						newX = i;
						newY = j;
					}
			if((moveToX != newX) || (moveToY != newY)){		
				moveToX = newX;
				moveToY = newY;
				ArrayList<Square> squarePath = map.setUpPath(newX, newY);
				convertPath(squarePath, mapPanel.path);	
				if(mapPanel.map.areThereEnemiesAt(newX, newY)){
					enemyPanel.setParty(mapPanel.map.getEnemiesAt(newX, newY));					
					enemyPanel.updateInfo();
					enemyPanel.repaint();
					enemyPanel.setVisible(true);
				}
				else
					enemyPanel.setVisible(false);
			}
			else{
				moveToX = -1;
				moveToY = -1;
				map.move();
				mapPanel.colorTheMap();
				if(mapPanel.map.weAreInTown()){
					townPanel.setVisible(true);
				}
				else
					townPanel.setVisible(false);
				if(mapPanel.map.areThereEnemiesAt(mapPanel.map.friendlyParty.getLocationX(), mapPanel.map.friendlyParty.getLocationY())){
					enemyPanel.setParty(mapPanel.map.getEnemiesAt(mapPanel.map.friendlyParty.getLocationX(), mapPanel.map.friendlyParty.getLocationY()));
					enemyPanel.updateInfo();
					enemyPanel.repaint();
					enemyPanel.setVisible(true);
					initiateCombat(mapPanel.map.friendlyParty, map.getEnemiesAt(mapPanel.map.friendlyParty.getLocationX(), mapPanel.map.friendlyParty.getLocationY()), map.getEnemyIndex(mapPanel.map.friendlyParty.getLocationX(), mapPanel.map.friendlyParty.getLocationY()));
				}
				else
					enemyPanel.setVisible(false);
			}
		}
		
		/**
		 * This function will convert the path found from the squares of the model map
		 * to the squares of the view map
		 * @param squarePath the ArrayList from the model 
		 * @param path the ArrayList corresponding to the view
		 */
		public void convertPath(ArrayList<Square> squarePath, ArrayList<JButton> path){
			//reset the color of the current setup
			if(path.isEmpty() == false){
				mapPanel.colorTheMap();
				path = new ArrayList<JButton>();
			}
			if(squarePath == null){
				return;
			}
			for(int i = 0; i < squarePath.size(); i++){				
				if(squarePath.get(i).getColor() == BLUE)
					mapPanel.location[squarePath.get(i).getX()][squarePath.get(i).getY()].setBackground(Color.BLUE);
				else if(squarePath.get(i).getColor() == RED)
					mapPanel.location[squarePath.get(i).getX()][squarePath.get(i).getY()].setBackground(Color.RED);
				else if(squarePath.get(i).getColor() == GRAY)
					mapPanel.location[squarePath.get(i).getX()][squarePath.get(i).getY()].setBackground(Color.GRAY);
				path.add(mapPanel.location[squarePath.get(i).getX()][squarePath.get(i).getY()]);
			}
		}
		/**
		 * This function will start combat mode
		 * @param fParty the friendly party
		 * @param eParty the enemy party
		 */
		public void initiateCombat(FriendlyParty fParty, EnemyParty eParty, int index){
			enemyIndex = index;
			cw = new CombatWindow(fParty, eParty);
			cw.setActionListener(new AttackListener());
			cw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			cw.setVisible(true);
			cw.pack();
		}
	}
	
	/**
	 * This class will just determine if a
	 * unit will get healed
	 * @author zeno
	 *
	 */
	class HealListener implements ActionListener{
		public void actionPerformed(ActionEvent e){			
			firstClick = false;
			actionTaken = HEAL;			
		}
	}
	
	/**
	 * This class will determine if a unit
	 * will get revived
	 * @author zeno
	 *
	 */
	class ReviveListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			firstClick = false;
			actionTaken = REVIVE;
		}
	}
	
	/**
	 * This class is for hiring a new knight
	 * in the friendly party
	 * @author zeno
	 *
	 */
	class HireKnightListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Knight knight = new Knight();
			map.hireUnit(knight);
			resourcePanel.update();
			friendlyPanel.loadPictures();
			friendlyPanel.addImages();
			friendlyPanel.updateInfo();
			friendlyPanel.repaint();
			repaint();
		}
	}
	
	
	/**
	 * This class is for hiring a new wizard in the friendly party
	 * @author zeno
	 *
	 */
	class HireWizardListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Wizard wizard = new Wizard();
			map.hireUnit(wizard);
			resourcePanel.update();
			friendlyPanel.addImages();
			friendlyPanel.updateInfo();
			friendlyPanel.repaint();
			repaint();
		}
	}
	
	
	/**
	 * This class is for hiring a new priest in the
	 * friendly party
	 * @author zeno
	 *
	 */
	class HirePriestListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Priest priest = new Priest();
			map.hireUnit(priest);
			resourcePanel.update();
			friendlyPanel.addImages();
			friendlyPanel.updateInfo();
			friendlyPanel.repaint();
			repaint();
		}
	}
	
	/**
	 * Class which will be used when a character from the friendly party is dismissed
	 * @author zeno
	 *
	 */
	class DismissListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			actionTaken = DISMISS;
		}
	}
	
	/**
	 * This class will be applied to all buttons in the
	 * friendly party class, it will determine whether
	 * a unit gets healed, revived, dismissed or moved
	 * @author zeno
	 *
	 */
	class CharacterListener implements ActionListener{
		JButton clickedButton;
		int position;
		public void actionPerformed(ActionEvent e){
			clickedButton = (JButton)e.getSource();
			for(int i = 0; i < 6; i++){
				if(friendlyPanel.partyButtons[i] == clickedButton)
					position = i;
			}
			if(actionTaken == HEAL){
				map.healUnit(position);
				actionTaken = 0;
			}
			else if(actionTaken == REVIVE){
				map.reviveUnit(position);
				actionTaken = 0;
			}
			else if(actionTaken == DISMISS){
				map.getFriendlyParty().dismiss(position);
				actionTaken = 0;
			}
			else if(actionTaken == SWITCHPLACES){
				map.getFriendlyParty().changeCharacterPosition(unit, position);
				actionTaken = 0;
			}
			else{
				actionTaken = SWITCHPLACES;
				unit = map.getFriendlyParty().unitAt(position);
			}
			friendlyPanel.addImages();
			friendlyPanel.updateInfo();
			resourcePanel.update();
		}
	}
	
	/**
	 * Class that implements action listener and it will
	 * implement the actions to happen once a button is clicked
	 * on the combat window. Once the combat part is over,
	 * it will update the current map.
	 * @author zeno
	 *
	 */
	class AttackListener implements ActionListener{
		JButton clickedButton;
		int position;
		
		/**
		 * Implementation of the actionPerformed function 
		 * of the action listener
		 */
		public void actionPerformed(ActionEvent e){
			clickedButton = (JButton)e.getSource();
			for(int i = 0; i < 6; i++){
				if(cw.enemyButtons[i] == clickedButton)
					position = i;
				if(cw.friendlyButtons[i] == clickedButton)
					position = i + 6;
			}
			if(position >= 6)
				performPriestAction(map.friendlyParty.getUnits()[friendlyTurn]);			
			else if((map.friendlyParty.getUnits()[friendlyTurn] instanceof Hero) || (map.friendlyParty.getUnits()[friendlyTurn] instanceof Knight))
				performMeleeAttack(map.friendlyParty.getUnits()[friendlyTurn], cw.eParty.getUnits()[position]);	
			else if(map.friendlyParty.getUnits()[friendlyTurn] instanceof Wizard)
				performAOEAttack(map.friendlyParty.getUnits()[friendlyTurn]);
			//if the attack was performed
			if(successfulAttack == 1){
				fightBack();
			}
		}
		
		/**
		 * Helper function that will have the enemy units deal damage
		 * After the enemy attacks, it will check if the fight should go on
		 */
		private void fightBack(){
			successfulAttack = 0;
			if(weStillHaveEnemies()){
				cw.eParty.attack(cw.fParty, enemyTurn);	
				updateInfo();
			}
			if(cw.fParty.allDead()){
				cw.dispose();					
			}
			if(cw.eParty.allDead()){
				map.enemyParties[enemyIndex] = null;
				//cw.eParty = null;
				cw.dispose();
			}		
			if(map.gameOver())
				JOptionPane.showMessageDialog(null, "You lost!");
			map.resetMap();
			mapPanel.colorTheMap();
		}
		
		/**
		 * Function to determine whether there are any enemies left to kill
		 * @return
		 */
		private boolean weStillHaveEnemies(){
			if(cw.eParty.allDead())
				return false;
			else
				return true;
		}
		
		/**
		 * Function designed to provide a sort of game loop within the combat
		 */
		public void updateInfo(){
			String currentEnemyUnit = "", currentFriendlyUnit = "";
			if(cw.eParty.getUnits()[enemyTurn] instanceof Gargoyle)
				currentEnemyUnit = "gargoyle";
			else if(cw.eParty.getUnits()[enemyTurn] instanceof SkeletonWarrior)
				currentEnemyUnit = "skeleton warrior";
			else if(cw.eParty.getUnits()[enemyTurn] instanceof SkeletonArcher)
				currentEnemyUnit = "skeleton archer";
			if(cw.eParty.allDead())
				return;
			if(cw.fParty.allDead())
				return;
			do{
				enemyTurn += 1;
				if(enemyTurn >= 6)
					enemyTurn = 0;
			}while((cw.eParty.getUnits()[enemyTurn] == null) || (cw.eParty.getUnits()[enemyTurn].getHp() == 0));
			do{
				friendlyTurn += 1;			
				if(friendlyTurn >= 6)
					friendlyTurn = 0;
			}while((cw.fParty.getUnits()[friendlyTurn] == null) || (cw.fParty.getUnits()[friendlyTurn].getHp() == 0));
			cw.updateInfo();
			if(cw.fParty.getUnits()[friendlyTurn] instanceof Hero)
				currentFriendlyUnit = "hero";
			else if(cw.fParty.getUnits()[friendlyTurn] instanceof Knight)
				currentFriendlyUnit = "knight";
			else if(cw.fParty.getUnits()[friendlyTurn] instanceof Wizard)
				currentFriendlyUnit = "wizard";
			else if(cw.fParty.getUnits()[friendlyTurn] instanceof Priest)
				currentFriendlyUnit = "priest";
			
			cw.middleArea.setText(currentEnemyUnit + " was selected\n" + currentFriendlyUnit + " attacks next");
		}
		
		/**
		 * Attack function in case the unit is a priest
		 * @param selectedUnit the unit which will attack
		 */
		public void performPriestAction(Unit selectedUnit){
			if(selectedUnit instanceof Priest){
				for(int i = 0; i < 6; i++){
					if(map.friendlyParty.getUnits()[i] != null)
						map.friendlyParty.getUnits()[i].updateHp(selectedUnit.getDamage());
				}
				successfulAttack = 1;
			}
		}
		
		/**
		 * Attack function for melee units, like the hero and the knight
		 * @param aggressor the attacking unit
		 * @param victim the unit getting attacked
		 */
		public void performMeleeAttack(Unit aggressor, Unit victim){
			if((victim == null) || (aggressor == null))
				return;
			if(victim.getPosition() >= 3){
				if(aggressor.getPosition() < 3){
					victim.takeDamage(aggressor.getDamage());
					successfulAttack = 1;
				}
				else if(noFriendsInTheWay(aggressor)){
						victim.takeDamage(aggressor.getDamage());
						successfulAttack = 1;
				}				
			}
			else{
				if(noEnemiesInTheWay(victim) && (aggressor.getPosition() < 3)){
					victim.takeDamage(aggressor.getDamage());
					successfulAttack = 1;
				}
				else if((noEnemiesInTheWay(victim) && noFriendsInTheWay(aggressor))){
					victim.takeDamage(aggressor.getDamage());
					successfulAttack = 1;
				}					
			}
			if(victim.getHp() <= 0)
				victim = null;
		}
		
		/**
		 * Function to determine if any friendly units
		 * will block the attack
		 * @param unit the attacker
		 * @return true if the attack is blocked, false otherwise
		 */
		private boolean noFriendsInTheWay(Unit unit){
			if(unit.getPosition() < 3)
				return true;
			for(int i = 0; i < 3; i++)
				if((map.friendlyParty.getUnits()[i] != null) && (map.friendlyParty.getUnits()[i].getHp() != 0))
					return false;
			return true;
		}
		
		/**
		 * Function to determine if any enemies 
		 * will block the attack
		 * @param unit the victim
		 * @return true if the attack is blocked and false otherwise
		 */
		private boolean noEnemiesInTheWay(Unit unit){
			if(unit.getPosition() >= 3)
				return true;
			for(int i = 3; i < 6; i++)
				if((cw.eParty.getUnits()[i] != null) && (cw.eParty.getUnits()[i].getHp() != 0))
					return false;
			return true;
		}
		
		/**
		 * Function designed for the area-of-effect attack,
		 * specific to a wizard, in our case
		 * @param wizard the wizard who will be attacking
		 */
		public void performAOEAttack(Unit wizard){
			for(int i = 0; i < 6; i++)
				if(cw.eParty.getUnits()[i] != null)
					cw.eParty.getUnits()[i].takeDamage(wizard.getDamage());
			successfulAttack = 1;
		}
	}
}
