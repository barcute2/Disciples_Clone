/**
 * 
 */
package controller;
import java.awt.Dimension;

import view.MapGUI;
import javax.swing.JFrame;

/**
 * This class contains the main for the game
 * @author zeno
 *
 */
public class Disciples {	
	public static void main(String[] args){
		MapGUI game = new MapGUI();
		game.setPreferredSize(new Dimension(1300, 900));
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
		game.pack();
	}
}
