package view;

import java.awt.event.*;
import javax.swing.*;

import model.*;
/**
 * @author zeno
 *
 */
public class FriendlyPanel extends PartyPanel{
	JLabel[] heroLabel, knightLabel, wizardLabel, priestLabel, nothingLabel, currentLabel;
	/**
	 * Default constructor for the class, it calls
	 * the constructor of the base class
	 * @param m the map being used by the class
	 */
	public FriendlyPanel(Map m){
		super(m);
		updateInfo();
	}
	
	/**
	 * Implementation of the helper function from the base class.
	 * It will load the pictures from the HDD 
	 */
	protected void loadPictures(){
		heroLabel = new JLabel[6];
		knightLabel = new JLabel[6];
		wizardLabel = new JLabel[6];
		priestLabel = new JLabel[6];
		nothingLabel = new JLabel[6];
		currentLabel = new JLabel[6];
		for(int i = 0; i < 6; i++){			
			heroLabel[i] = new JLabel(new ImageIcon("hero.jpg"));
			knightLabel[i] = new JLabel(new ImageIcon("knight.jpg"));
			wizardLabel[i] = new JLabel(new ImageIcon("wizard.jpg"));
			priestLabel[i] = new JLabel(new ImageIcon("priest.jpg"));	
			nothingLabel[i] = new JLabel();
			partyButtons[i].add(nothingLabel[i]);
			currentLabel[i] = nothingLabel[i];
		}
	}
	
	/**
	 * This helper function will add the stored images to the buttons
	 */
	public void addImages(){
		FriendlyParty party = map.getFriendlyParty();
		for(int i = 0; i < 6; i++)
			setUpImage(party.getUnits()[i], i);
	}

	/**
	 * Implementation of the helper function from the base class.
	 * It sets up the images for each unit type in the friendly party
	 */
	protected void setUpImage(Unit unit, int i){		
			partyButtons[i].remove(currentLabel[i]);		
			if(unit instanceof Hero){
				partyButtons[i].add(heroLabel[i]);	
				currentLabel[i] = heroLabel[i];
			}
			else if(unit instanceof Knight){
				partyButtons[i].add(knightLabel[i]);
				currentLabel[i] = knightLabel[i];
			}
			else if(unit instanceof Wizard){
				partyButtons[i].add(wizardLabel[i]);
				currentLabel[i] = wizardLabel[i];
			}
			else if(unit instanceof Priest)	{		
				partyButtons[i].add(priestLabel[i]);
				currentLabel[i] = priestLabel[i];
			}
			else{				
				partyButtons[i].add(nothingLabel[i]);
				currentLabel[i] = nothingLabel[i];
			}
			partyButtons[i].repaint();
	}
	
	/**
	 * Function which will update the information of the friendly party
	 */
	public void updateInfo(){
		String unit = new String();
		for(int i = 0; i < 6; i++){
			if(map.getFriendlyParty().getUnits()[i] != null){
				if(map.getFriendlyParty().getUnits()[i] instanceof Hero)
					unit = "       Hero:\n";
				else if(map.getFriendlyParty().getUnits()[i] instanceof Knight)
					unit = "       Knight:\n";
				else if(map.getFriendlyParty().getUnits()[i] instanceof Wizard)
					unit = "       Wizard:\n";
				else if (map.getFriendlyParty().getUnits()[i] instanceof Priest)
					unit = "       Priest:\n";
				unitInfo[i].setText(unit + "  HP: "+ map.getFriendlyParty().getUnits()[i].getHp() + "/" + map.getFriendlyParty().getUnits()[i].getMaxHp()
						+ " Level " + map.getFriendlyParty().getUnits()[i].getLevel()); 
			}
			else
				unitInfo[i].setText("");
		}
	}
	
	/**
	 * This function will add action listeners to the character buttons
	 * @param m the action listener
	 */
	public void setActionListeners(ActionListener m){
		for(int i = 0; i < 6; i++)	
			partyButtons[i].addActionListener(m);
	}
}