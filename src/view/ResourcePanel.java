/**
 * 
 */
package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import model.*;
/**
 * This class will display the information relevant to the game loop,
 * such as the day, the gold and the button to end the turn
 * @author zeno
 *
 */
public class ResourcePanel extends JPanel{
	JButton endTurnButton;
	Map map;
	JTextField money;
	JTextField day;
	JLabel goldLabel, dayLabel;
	GridLayout buttonLayout = new GridLayout(3,2);
	BorderLayout layout = new BorderLayout();
	JPanel buttonPanel = new JPanel();
	ActionListener turnListener = new EndTurnListener();
	
	
	/**
	 * Default constructor for the class, it sets up the
	 * position of the elements within the panel
	 * @param m the map being used by the class
	 */
	public ResourcePanel(Map m){
		map = m;
		endTurnButton = new JButton("End Turn");
		endTurnButton.addActionListener(turnListener);
		money = new JTextField("" + map.getMoney());
		day = new JTextField("" + map.getDay());
		goldLabel = new JLabel("Gold");
		dayLabel = new JLabel("Day");
		buttonPanel.setLayout(buttonLayout);
		setLayout(layout);
		buttonPanel.add(goldLabel);
		buttonPanel.add(money);
		buttonPanel.add(dayLabel);
		buttonPanel.add(day);
		add(buttonPanel, BorderLayout.NORTH);
		add(endTurnButton, BorderLayout.SOUTH);
	}
	
	public void update(){
		money.setText("" + map.getMoney());
	}
	
	/**
	 * This class will implement the action listener
	 * required to end the turn. It will update the 
	 * elements of the panel.
	 * @author zeno
	 *
	 */
	class EndTurnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			map.incrementTurn();
			money.setText(""+map.getMoney());
			day.setText("" + map.getDay());
		}
	}
}
