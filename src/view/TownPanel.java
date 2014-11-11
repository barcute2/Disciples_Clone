/**
 * 
 */
package view;
import java.awt.*;

import java.awt.event.*;

import javax.swing.*;
import model.*;
/**
 * This panel will only be visible when the player is within a 
 * town or the capital. 
 * @author zeno
 *
 */
public class TownPanel extends JPanel{
	JButton revive;
	JButton heal;
	JButton dismiss;
	JButton hireKnight, hireWizard, hirePriest;
	GridLayout layout = new GridLayout(1,4);
	Map map;
	
	/**
	 * Constructor for the class, it sets up
	 * the way the elements are displayed within the panel.
	 * @param m the map being used by the class
	 */
	public TownPanel(Map m){
		map = m;
		revive = new JButton("Revive");
		heal = new JButton("Heal");
		dismiss = new JButton("Dismiss");
		hireKnight = new JButton("Hire Knight");
		hireWizard = new JButton("Hire Wizard");
		hirePriest = new JButton("Hire Priest");
		add(heal);
		add(revive);		
		add(dismiss);
		add(hireKnight);
		add(hireWizard);
		add(hirePriest);
	}
	
	public void setActionListeners(ActionListener r, ActionListener h, ActionListener hKnight, 
			ActionListener hWizard, ActionListener hPriest, ActionListener dismissListener){
		revive.addActionListener(r);
		heal.addActionListener(h);
		hireKnight.addActionListener(hKnight);
		hireWizard.addActionListener(hWizard);
		hirePriest.addActionListener(hPriest);
		dismiss.addActionListener(dismissListener);
	}
	
}
