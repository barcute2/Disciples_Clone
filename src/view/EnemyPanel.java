package view;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;

import model.*;
/**
 * This class will hold the displayed information for
 * each enemy party
 * @author zeno
 *
 */
public class EnemyPanel extends JPanel{	
	EnemyParty party;	
	JTextArea[] unitInfo = new JTextArea[6];
	GridLayout layout = new GridLayout(3, 2);
	
	Map map;
	/**
	 * Default constructor for the class, calls the base class
	 * @param m the map used by the class
	 */
	public EnemyPanel(Map m){
		map = m;	
		for(int i = 0; i < 6; i++)
			unitInfo[i] = new JTextArea("");
		setLayout(layout);
		add(unitInfo[3]);
		add(unitInfo[0]);
		add(unitInfo[4]);
		add(unitInfo[1]);
		add(unitInfo[5]);
		add(unitInfo[2]);		
	}
	
	public void setParty(EnemyParty p){
		party = p;
	}

	/**
	 * Function which will update the information of the friendly party
	 */
	public void updateInfo(){
		String unit = new String();
		for(int i = 0; i < 6; i++){
			if(party.getUnits()[i] != null){
				if(party.getUnits()[i] instanceof Gargoyle)
					unit = "Gargoyle\n";
				else if(party.getUnits()[i] instanceof SkeletonWarrior)
					unit = "Skeleton Warrior\n";
				else if(party.getUnits()[i] instanceof SkeletonArcher)
					unit = "Skeleton Archer\n";
				unitInfo[i].setText(unit + "\nHP: "+ party.getUnits()[i].getHp() + "/" + party.getUnits()[i].getMaxHp()
						+ " Level " + party.getUnits()[i].getLevel()); 
			}
			else
				unitInfo[i].setText("");
		}
	}
}
