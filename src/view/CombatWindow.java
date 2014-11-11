/**
 * 
 */
package view;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import model.*;
/**
 * @author zeno
 * 
 * Class that will extend JFrame to make a combat window, 
 * separate from the map
 */
public class CombatWindow extends JFrame{
	FriendlyParty fParty;
	EnemyParty eParty;
	JButton[] friendlyButtons, enemyButtons;
	JPanel leftPanel, rightPanel, middlePanel;
	JLabel[] friendlyLabel, enemyLabel;
	JTextArea[] friendlyStats, enemyStats;
	JTextArea middleArea;
	GridLayout partyLayout;
	BorderLayout layout;
	
	/**
	 * Constructor for the combat window class
	 * @param fp the friendly party involved in combat
	 * @param ep the enemy party involved in combat
	 */
	public CombatWindow(FriendlyParty fp, EnemyParty ep){
		fParty = fp;
		eParty = ep;
		setUpTheLabels();
		setUpTheButtons();
		setUpTheTextAreas();
		partyLayout = new GridLayout(6,2);
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		middlePanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(300, 900));
		rightPanel.setPreferredSize(new Dimension(300, 900));
		middlePanel.setPreferredSize(new Dimension(200, 900));
		leftPanel.setLayout(partyLayout);
		rightPanel.setLayout(partyLayout);
		middleArea = new JTextArea("Your hero attacks next");
		middlePanel.add(middleArea);
		addLeftButtons();
		addRightButtons();
		layout = new BorderLayout();
		setLayout(layout);
		add(leftPanel, BorderLayout.WEST);
		add(middlePanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}
	
	/**
	 * Helper function designed to set up the images on the labels
	 */
	private void setUpTheLabels(){
		friendlyLabel = new JLabel[6];
		enemyLabel = new JLabel[6];
		for(int i = 0; i < 6; i++){
			if(fParty.getUnits()[i] instanceof Hero)
				friendlyLabel[i] = new JLabel(new ImageIcon("pics/hero.jpg"));
			else if(fParty.getUnits()[i] instanceof Knight)
				friendlyLabel[i] = new JLabel(new ImageIcon("pics/knight.jpg"));
			else if(fParty.getUnits()[i] instanceof Wizard)
				friendlyLabel[i] = new JLabel(new ImageIcon("pics/wizard.jpg"));
			else if(fParty.getUnits()[i] instanceof Priest)
				friendlyLabel[i] = new JLabel(new ImageIcon("pics/priest.jpg"));
			else
				friendlyLabel[i] = new JLabel();
			if(eParty.getUnits()[i] instanceof Gargoyle)
				enemyLabel[i] = new JLabel(new ImageIcon("pics/gargoyle.jpg"));
			else if(eParty.getUnits()[i] instanceof SkeletonWarrior)
				enemyLabel[i] = new JLabel(new ImageIcon("pics/sw.jpg"));
			else if(eParty.getUnits()[i] instanceof SkeletonArcher)
				enemyLabel[i] = new JLabel(new ImageIcon("pics/sa.jpg"));
			else
				enemyLabel[i] = new JLabel();
		}
	}
	
	/**
	 * Helper function to add the buttons to the window
	 */
	private void setUpTheButtons(){
		friendlyButtons = new JButton[6];
		enemyButtons = new JButton[6];
		for(int i = 0; i < 6; i++){
			friendlyButtons[i] = new JButton();
			friendlyButtons[i].add(friendlyLabel[i]);
			enemyButtons[i] = new JButton();
			enemyButtons[i].add(enemyLabel[i]);
		}
	}
	
	/**
	 * Helper function to add the text areas 
	 * and put up the default information, at
	 * the beginning of the battle
	 */
	private void setUpTheTextAreas(){
		friendlyStats = new JTextArea[6];
		enemyStats = new JTextArea[6];
		for(int i = 0; i < 6; i ++){
			friendlyStats[i] = new JTextArea("");
			enemyStats[i] = new JTextArea("");
			if(fParty.getUnits()[i] != null){
			friendlyStats[i].setText("HP: " + fParty.getUnits()[i].getHp() + "/" + fParty.getUnits()[i].getMaxHp()
					+ "\nDamage: " + fParty.getUnits()[i].getDamage() + "\nArmor: " + fParty.getUnits()[i].getArmor());
			}
			else
				friendlyStats[i].setText("");
			if(eParty.getUnits()[i] != null){
			enemyStats[i].setText("HP: " + eParty.getUnits()[i].getHp() + "/" + eParty.getUnits()[i].getMaxHp()
					+ "\nDamage: " + eParty.getUnits()[i].getDamage() + "\nArmor: " + eParty.getUnits()[i].getArmor());
			}
			else
				enemyStats[i].setText("");
		}
	}
	
	/**
	 * Helper function which adds the buttons on the left panel
	 */
	private void addLeftButtons(){
		leftPanel.add(friendlyButtons[3]);
		leftPanel.add(friendlyButtons[0]);		
		leftPanel.add(friendlyStats[3]);
		leftPanel.add(friendlyStats[0]);	
		leftPanel.add(friendlyButtons[4]);
		leftPanel.add(friendlyButtons[1]);
		leftPanel.add(friendlyStats[4]);
		leftPanel.add(friendlyStats[1]);
		leftPanel.add(friendlyButtons[5]);
		leftPanel.add(friendlyButtons[2]);	
		leftPanel.add(friendlyStats[5]);
		leftPanel.add(friendlyStats[2]);
	}
	
	/**
	 * Helper function which adds the buttons on the right panel
	 */
	private void addRightButtons(){
		rightPanel.add(enemyButtons[3]);
		rightPanel.add(enemyButtons[0]);
		rightPanel.add(enemyStats[3]);
		rightPanel.add(enemyStats[0]);		
		rightPanel.add(enemyButtons[4]);
		rightPanel.add(enemyButtons[1]);
		rightPanel.add(enemyStats[4]);
		rightPanel.add(enemyStats[1]);
		rightPanel.add(enemyButtons[5]);
		rightPanel.add(enemyButtons[2]);		
		rightPanel.add(enemyStats[5]);
		rightPanel.add(enemyStats[2]);
	}
	
	/**
	 * Function which will update the text held in the 
	 * text areas
	 */
	public void updateInfo(){
		for(int i = 0; i < 6; i ++){			
			if(fParty.getUnits()[i] != null){
			friendlyStats[i].setText("HP: " + fParty.getUnits()[i].getHp() + "/" + fParty.getUnits()[i].getMaxHp()
					+ "\nDamage: " + fParty.getUnits()[i].getDamage() + "\nArmor: " + fParty.getUnits()[i].getArmor());
			}
			else
				friendlyStats[i].setText("");
			if(eParty.getUnits()[i] != null){
			enemyStats[i].setText("HP: " + eParty.getUnits()[i].getHp() + "/" + eParty.getUnits()[i].getMaxHp()
					+ "\nDamage: " + eParty.getUnits()[i].getDamage() + "\nArmor: " + eParty.getUnits()[i].getArmor());
			}
			else
				enemyStats[i].setText("");
		}
	}
	
	/**
	 * Function with the specific purpose of passing
	 * the action listener from the map class
	 * @param m the action listener used
	 */
	public void setActionListener(ActionListener m){
		for(int i = 0; i < 6; i++){
			friendlyButtons[i].addActionListener(m);
			enemyButtons[i].addActionListener(m);
		}
	}
}
