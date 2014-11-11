/**
 * 
 */
package model;

/**
 * The smallest functional component of the map
 * @author zeno
 *
 */
public class Square {
	/**
	 * The constructor for the square
	 * class initializes the coordinates
	 */
	private int x, y;
	private int color;	
	public Square(int xCoord, int yCoord){
		x = xCoord;
		y = yCoord;
		color = 0;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getColor(){
		return color;
	}
	public void setX(int xCoord){
		x = xCoord;
	}
	public void setY(int yCoord){
		y = yCoord;
	}
	public void setCoordinates(int xCoord, int yCoord){
		x = xCoord;
		y = yCoord;
	}
	public void setColor(int c){
		color = c;
	}
}
