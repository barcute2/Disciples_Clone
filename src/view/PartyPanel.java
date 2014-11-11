/**
 * 
 */
package view;

import java.awt.*;
import javax.swing.*;

import model.*;
/**
 * This is an abstract class which has the basic setup 
 * for the friendly party and enemy party panels.
 * @author zeno
 *
 */
public abstract class PartyPanel extends JPanel{
	JButton[] partyButtons = new JButton[6];
	JTextArea[] unitInfo = new JTextArea[6];
	GridLayout layout = new GridLayout(6, 2);
	JLabel[] characterPictures = new JLabel[6];
	Map map;
	
	/**
	 * Default constructor for the class, it
	 * creates the buttons that will be displayed
	 * @param m
	 */
	public PartyPanel(Map m){
		map = m;
		for(int i = 0; i < 6; i ++){
			partyButtons[i] = new JButton();
			unitInfo[i] = new JTextArea();
		}
		setLayout(layout);
		add(partyButtons[3]);		
		add(partyButtons[0]);
		add(unitInfo[3]);
		add(unitInfo[0]);
		add(partyButtons[4]);		
		add(partyButtons[1]);
		add(unitInfo[4]);
		add(unitInfo[1]);
		add(partyButtons[5]);		
		add(partyButtons[2]);
		add(unitInfo[5]);
		add(unitInfo[2]);
		loadPictures();
		addImages();		
	}
	
	
	/**
	 * This function will load pictures from the HDD, must be implemented
	 */
	protected abstract void loadPictures();
	
	/**
	 * This helper function will add the stored images to the buttons
	 */
	public abstract void addImages();
	
	/**
	 * Abstract function which will add a certain unit its corresponding image
	 * @param unit the unit getting the image
	 * @param i the position of the unit within the party
	 */
	protected abstract void setUpImage(Unit unit, int i);
}
