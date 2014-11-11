/**
 * 
 */
package view;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.ArrayList;
import model.*;
import view.MapGUI;
/**
 * This function will represent the panel for the map.
 * @author zeno
 *
 */
public class MapPanel extends JPanel{
	JButton[][] location = new JButton[50][50];
	Map map;
	Color brown;
	ArrayList<JButton> path;
	GridLayout layout = new GridLayout(50, 50);
	private final int GREEN = 0, BLUE = 1, RED = 2, GRAY = 3, BROWN = 4, BLACK = 5;
	
	
	/**
	 * Default constructor for the class,
	 * it sets up its variables in accordance to the frame
	 * @param m the map being used by the class
	 */
	public MapPanel(Map m){
		map = m;
		setLayout(layout);
		brown = new Color(244,164,96);
		for(int i = 0; i < 50; i++)
			for(int j = 0; j < 50; j++){
				location[i][j] = new JButton();
				add(location[i][j]);	
			}
		colorTheMap();		
		path = new ArrayList<JButton>();
	}
	
	/**
	 * This method will help with encapsulation
	 * and decomposition as it will set a listener
	 * from another class
	 * @param m the MoveListener object used
	 */
	public void addMoveListener(ActionListener m){
		for(int i = 0; i < 50; i++)
			for(int j = 0; j < 50; j++){
				location[i][j].addActionListener(m);
			}
	}
	
	/**
	 * Helper method which will color the map in accordance
	 * with the information from the model
	 */
	public void colorTheMap(){
		for(int row = 0; row < 50; row++)
			for(int column = 0; column < 50; column++){				
				if(map.getSquares()[row][column].getColor() == GREEN)
					location[row][column].setBackground(Color.GREEN);
				else if(map.getSquares()[row][column].getColor() == BROWN)
					location[row][column].setBackground(brown);
				else if(map.areThereEnemiesAt(row, column))
					location[row][column].setBackground(Color.RED);					
				location[row][column].repaint();
			}	
		location[4][4].setBackground(Color.BLACK);
		location[5][4].setBackground(Color.BLACK);
		location[4][5].setBackground(Color.BLACK);
		location[5][5].setBackground(Color.BLACK);
		location[map.getFriendlyParty().getLocationX()][map.getFriendlyParty().getLocationY()].setBackground(Color.BLUE);
		for(int i = 0; i < 10; i++){
			if(map.getEnemyParties()[i] != null){	
				location[map.getEnemyParties()[i].getLocationX()][map.getEnemyParties()[i].getLocationY()].setBackground(Color.RED);
				location[map.getEnemyParties()[i].getLocationX()][map.getEnemyParties()[i].getLocationY()].repaint();
			}
		}
		repaint();
	}
	

}
