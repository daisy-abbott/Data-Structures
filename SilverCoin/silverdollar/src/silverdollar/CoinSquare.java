/**
 * CS062: silverdollar.CoinSquare
 *	a simple coin-moving game implemented with ArrayLists
 */
package silverdollar;

import java.awt.geom.Rectangle2D;

/**
 * A CoinSquare class, for the Silver Dollar Game
 * <p>
 * A Square is one element of a coin strip. There are
 * methods to add, query, and remove coins from 
 * Squares. 
 * <p>
 * No graphics are necessary. However, the position
 * of the square in the strip is necessary so that we
 * can inform the coin where to move.
 */
public class CoinSquare extends Rectangle2D.Double {
    
    private Coin occupant; // the coin if there is one; null otherwise
    private int index;     // the position of the square in the strip
    private int dimen;     // the size of one side of the square    
    
    /**
     * creates a Square
     * @param i the index in the strip of squares
     * @param s the size of a side of the square
     * 
     */
    public CoinSquare(int index, int dimen) {
    	super(index * dimen, 0, dimen, dimen);
    	occupant = null;
        this.index = index;
        this.dimen = dimen;
    }
    
    /**
     * @return true if there is a coin in the square
     */
    public boolean isOccupied() {
        return occupant != null;
    }

    /**
     * Add a coin to this squares 
     * @param coin the coin
     */
    public void setCoin(Coin coin) {
        occupant = coin;
        coin.moveTo(index * dimen + dimen / 2, dimen / 2);
    }

    /**
     * Remove and return the coin occupying this square 
     * @return the coin that was removed
     */
    public Coin release() {
        Coin coin = occupant;
        occupant = null;
        return coin;
    }

    /**
     * @return a reference to the coin in the square; may be null
     */
    public Coin getCoin() {
        return occupant;
    }
}
