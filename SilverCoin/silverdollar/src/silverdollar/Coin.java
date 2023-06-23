/**
 * CS062: silverdollar.Coin
 *	a simple coin-moving game implemented with ArrayLists
 */
package silverdollar;

import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;

/**
 * A Coin class, for the Silver Dollar Game
 * <p>
 * We simply extend the Ellipse2D.Double class to
 * produce circles with the reference point at the
 * center.
 * <p>
 * The containing frame is a necessary parameter to
 * the constructor, because we want the moved coin
 * to appear in its new location. For that, we must
 * call the repaint method of the frame.
 * <p>
 * The reference point is the center of the circle,
 * as opposed to the Java Ellipse2D in which the 
 * reference point is at the upper left corner of
 * the containing rectangle.
 */
 
public class Coin extends Ellipse2D.Double {
    private JFrame frame;  // the container in which the coins appear
    private int diameter;   // the diameter of a coin
    
    /**
     * Creates a coin with the specified and defaults to diam/2, diam/2 coordinates
     * 
     * @param f the frame in which the coin will appear
     * @param diam the diameter of the coin
     * 
     */
    public Coin(JFrame f, int diam) {
    	super(diam / 2, diam / 2, diam, diam);
        frame = f;
        diameter = diam;
    }
    
    /**
     * Creates a coin with the specified diameter and center
     * at the specified coordinates.
     * 
     * @param f the frame in which the coin will appear
     * @param diam the diameter of the coin
     * @param xCoord the horizontal coordinate of the coin
     * @param yCoord the vertical coordinate of the coin
     * 
     */
    public Coin(JFrame f, int diam, int xCoord, int yCoord) {
        super(xCoord - diam / 2, yCoord - diam / 2, diam, diam);
        frame = f;
        diameter = diam;
    }


    /**
     * @post the coin appears at a new position
     * @param xCoord the new horizontal coordinate
     * @param yCoord the new vertical coordinate
     * 
     */
    public void moveTo(int xCoord, int yCoord) {
        setFrame(xCoord - diameter / 2,
                       yCoord - diameter / 2, 
                       diameter, diameter);
        frame.repaint();
    }
}
