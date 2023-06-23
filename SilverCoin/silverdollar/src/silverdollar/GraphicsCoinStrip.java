/**
 * CS062: silverdollar.GraphicsCoinStrip


 * A simple coin-moving game implemented with ArrayLists. Written by Yotam and Daisy the week of 
 * September 12th. We wrote the code to implement the silver dollar game. 
 *
 * @author Daisy Abbott + Yotam Twersky 
 */
package silverdollar;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import java.util.Random;
import java.util.ArrayList; 


/**
 * A class for the graphical version of the Silver
 * Dollar Game
 * <p>
 * See Chapter 3 of Java Structures, by Duane Bailey.
 * We adapt (but do not inherit from) the methods of
 * the text-based version of the game. The class
 * is a specialization of JFrame.
 */
public class GraphicsCoinStrip extends JFrame {
	protected static final int   SQUARE_SIZE = 120;
	protected static final int   COIN_DIAMETER = SQUARE_SIZE / 2;
	protected static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
	protected static final Color BOUNDARY_COLOR = Color.BLACK;
	protected static final Color COIN_COLOR = Color.RED;
	protected static final int DISPLAY_WIDTH = SQUARE_SIZE * 12;
	public boolean togglev = true;

	protected BufferedImage bf = new BufferedImage(DISPLAY_WIDTH, SQUARE_SIZE, 
												BufferedImage.TYPE_INT_RGB);
	protected ArrayList<CoinSquare> strip;  // the arraylist of squares for the game
	protected Coin movingCoin;          // the coin that is currently being dragged by the mouse
	
	/**
	 * The main method simply creates GraphicsCoinStrip,
	 * initializes it, adds the mouse listeners, and 
	 * plays the game.
	 * 
	 * See the CS 62 handout on the graphics for an explanation of the methods used.
	 * 
	 * @param args command-line arguments (none
	 *        in this case)
	 */
	public static void main(String[] args) {
		GraphicsCoinStrip f = new GraphicsCoinStrip (12, 5);
		
	}
	
	/**
	 * Create a new coin strip with randomly placed coins
	 * 
	 * @param squares the number of squares in the strip
	 * @param coins the number of coins to place
	 */
	public GraphicsCoinStrip(int squares, int coins) {		
		//TODO: add some code here!
		
		//creates new strip
		strip = new ArrayList<CoinSquare>();
		
		// loops through number of squares gets index
		for (int i = 0; i < squares; i++) {

			// creates new object coinsquare, adds
			CoinSquare coinSquare = new CoinSquare(i, SQUARE_SIZE);
			strip.add(coinSquare);
			
			
		}
		
		//places coins randomly in strip
		Random rand = new Random (); 
		
		// loops thru coins and places randomly 
		while (0 < coins) { 
			
			//creates i, check thru squares, creates new object coin
			int i = rand.nextInt (squares);
			Coin coin = new Coin(this, COIN_DIAMETER);
			
			// if the square is not occupied the coin can be placed. 
			if (!strip.get(i).isOccupied()) {
				strip.get(i).setCoin(coin);
				coins --;
				
				
			}
		}
			
		// --- only add code above here in this method ---
		CoinMouseListener listener = new CoinMouseListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(strip.size() * SQUARE_SIZE, SQUARE_SIZE);
		setVisible(true);  // this should always be at the end of the constructor
	}

	/** 
	 * gameIsOver determines if a game is completed.
	 * 
	 * @return true if there are no more moves 
	 */ 
	public boolean gameIsOver() {
		//TODO: check whether the game is over
			
			for (int i = 0; i < strip.size() -1; i++) {
				if (!strip.get(i).isOccupied() && strip.get(i + 1).isOccupied()) {
					
					return false; 
				}
			} if (togglev) {
				
				return true; 
				
			}
			return false; 
	}

	
	/**
	 * The paint method is inherited from JFrame.
	 * 
	 * It is called automatically or in response to
	 * an invocation of repaint(). It simply refreshes
	 * the screen by first laying down the background
	 * color, then outlining the squares, and finally
	 * drawing the coins.
	 * 
	 * @param g a Graphics object that is supplied by
	 *        the (unknown to us) caller
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) bf.getGraphics();;

		g2.setPaint(BACKGROUND_COLOR);
		
		for (int i = 0; i < strip.size(); i++) {
			g2.fill(strip.get(i));
		}

		g2.setPaint(BOUNDARY_COLOR);
		
		for (int i = 0; i < strip.size(); i++) {
			g2.draw(strip.get(i));
		}

		g2.setPaint(COIN_COLOR);
		
		// checks if game is over then changes coin color
		if ( gameIsOver()) {
			g2.setPaint(Color.CYAN);
		}
		
		for (int i = 0; i < strip.size(); i++) {
			if (strip.get(i).isOccupied()) {
				g2.fill(strip.get(i).getCoin());
			}
			
		}
		

		if (movingCoin != null) {
			g2.fill(movingCoin);
		}	
		
		g.drawImage(bf,0,0,null);	
	}
	
	/**
	 * Given an x, y location, return the index of the CoinSquare within "strip"
	 * that the location occurs.  Return -1 if the x, y location does not occur
	 * in any CoinSquare within "strip"
	 *
	 * @param x 
	 * @param y
	 * @return the index within the strip
	 */
	
	private int getCoinSquareIndex(int x, int y) {
		// TODO: fill in code here
		
		// loops through squares
		for (int i = 0; i < strip.size(); i++) {
				
			//if location is in strip, return index
			if (strip.get(i).contains(x, y)) {
				return i;
			}
		
		}
		return -1;
	}
		
	/** 
	 * An inner class to respond to mouse events.
	 */
	private class CoinMouseListener implements MouseListener, MouseMotionListener {
		private int origin;	// the index of the square when a coin is picked up
		
		/**
		 * Method mousePressed is called when the mouse is
		 * clicked.
		 * 
		 * For this application, pressing the mouse button
		 * picks up a coin if the cursor is on a coin, and
		 * that coin is dragged until the mouse is released.
		 */
		public void mousePressed(MouseEvent event) {
			int newX = event.getX();
			int newY = event.getY();
			
			if (clickedOnCoin(newX, newY)) {
				int squareIndex = getCoinSquareIndex(newX, newY);
				movingCoin = strip.get(squareIndex).release();
				movingCoin.moveTo(newX, newY);
				origin = squareIndex;
				
			} else {
				movingCoin = null;
			}
		}
		
		/**
		 * Determines if an x, y location contains a coin.
		 * 
		 * @param x
		 * @param y
		 * @return true if a coin is at location x, y, false otherwise
		 */
		private boolean clickedOnCoin(int x, int y) {
			// TODO: add code here
			// NOTE: The only way to access coins is through a CoinSquare
			togglev = false;
			// initialize vas, check if space is taken
			int index = getCoinSquareIndex(x, y);
			if (strip.get(index).isOccupied()) {
				
				// gets index of square, checks if x y is in coin not just square
				if ( strip.get(index).getCoin().contains(x, y) ) {
					return true;
				}
				
			}
			
			
			return false;
		}
		
		/**
		 * Method mouseReleased is called when the mouse
		 * button is released.
		 * 
		 * If a coin is released in a square, and that square
		 * is not already occupied and the move satisfies
		 * the rules of the game, then the coin is placed
		 * into the square. Otherwise, the coin is returned
		 * to its original square. 
		 */
		public void mouseReleased(MouseEvent event) {
			int newX = event.getX();
			int newY = event.getY();
			
			togglev = true; 
						
			if (isValidCoinDrop(newX, newY)) {
				int squareIndex = getCoinSquareIndex(newX, newY);
				strip.get(squareIndex).setCoin(movingCoin);
			} else {
				if( movingCoin != null ){
					strip.get(origin).setCoin(movingCoin);
				}
			}

			// Give up the coin, if any, that was
			// dragged; it has now been returned to
			// some square.
			movingCoin = null;
		}
		
		/**
		 * Returns true if we are currently moving a coin and
		 * the x, y location represents a valid location on the coin
		 * strip to drop that coin based on the contents of the strip
		 * and the rules of the game.
		 * 
		 * @param x
		 * @param y
		 * @return true if we are moving a coin and this is a valid location to drop it
		 */
		private boolean isValidCoinDrop(int x, int y) {
			// TODO: add code here
			
			int location = getCoinSquareIndex(x, y);
			// if (movingCoin != null) 
			if (location < 0 || location >= strip.size() ){
				return false;
			}
			
			// if there square is clear
			if (!strip.get(location).isOccupied()) {
				
				// if start - distance is >= 0
				if (origin - location >= 0) {
					
						// loops through from placement location to starting location
						for (int i = location; i <= origin; i++) {
							// checks if square is occupied and returns false if it is
							if (strip.get(i).isOccupied() ) {
								return false;
						}
					}
					return true;
						
				}
				
			}
			
			return false;
		}


		/**
		 * dragging a coin
		 * @post the selected coin, if there is one, is moved
		 */
		public void mouseDragged(MouseEvent event) {
			if (movingCoin != null){
				movingCoin.moveTo(event.getX(), event.getY());
			}
			
			repaint();
		}

		/*
		 * These methods are required by the interfaces.
		 * For this program, they do nothing.
		 */
		public void mouseClicked(MouseEvent event) {}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
		public void mouseMoved(MouseEvent event) {}
	}
}
