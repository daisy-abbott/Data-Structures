package darwin;

/**
 * This class includes the functions necessary to keep track of the creatures in
 * a two-dimensional world. 
 */

public class World {
	private Matrix<Creature> creatures;
	
	/**
	 * This function creates a new world consisting of width columns and height
	 * rows, each of which is numbered beginning at 0. A newly created world
	 * contains no objects.
	 */
	public World(int w, int h) {
		// BE CAREFUL: think about how width/heights translates to row/col in a matrix
		creatures = new Matrix<Creature>(h, w); 
		
	}

	/**
	 * Returns the height of the world.
	 */
	public int height() {
		return creatures.numRows(); // FIX
	}

	/**
	 * Returns the width of the world.
	 */
	public int width() {
		return creatures.numCols(); // FIX
	}

	/**
	 * Returns whether pos is in the world or not.
	 * 
	 * returns true *if* pos is an (x,y) location within the bounds of the board.
	 */
	public boolean inRange(Position pos) {

		return (pos.getX() >= 0 && pos.getX() < width() && pos.getY() >= 0 && pos.getY() < height()); // FIX
	}

	/**
	 * Set a position on the board to contain e.
	 * 
	 * @throws IllegalArgumentException if pos is not in range
	 */
	public void set(Position pos, Creature e) {
		// FIX
		
		creatures.set(pos.getY(), pos.getX(), e);
		
		if (!inRange(pos) ) {
			throw new IllegalArgumentException("Position is not in range");
		}
		
	}

	/**
	 * Return the contents of a position on the board.
	 * 
	 * @throws IllegalArgumentException if pos is not in range
	 */
	public Creature get(Position pos) {
		
		if (!inRange(pos) ) {
			throw new IllegalArgumentException("Position is not in range");
		}
		// BE CAREFUL: think about how x,y translates to row/col in a matrix
		return creatures.get(pos.getY(), pos.getX()); // FIX
	}

}
