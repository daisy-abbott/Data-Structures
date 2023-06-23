package hexAPawn;

import java.util.ArrayList;


/**
 * A GameTree is a tree of HexBoard positions.
 * 
 * The children of a position are all of the positions that could
 * result from possible moves from that position.  Knowing what
 * positions are possible can enable us to choose the best.
 * 
 * @author Daisy Abbott & Brisa Salazar
 */
public class GameTree {
	private HexBoard board; 
	private char color; // player color
	private HexMove m; // move
	private GameTree parent; // GameTree parent
	private ArrayList<GameTree> children; //a list of GameTree children,
	
	/**
	 * primary constructor (for new GameTree)
	 * 
	 * @param board with which this tree starts
	 * @param color of the player who gets next move
	 */
	public GameTree(HexBoard board, char color) {
		this(board, color, null, null);
		
	}
	
	/**
	 * alternative constructor (for moves and positions)
	 * 
	 * Generate a GameTree that generates all possible moves originating from this position,
	 * given whose turn it is.
	 * 
	 * @param board	for this position
	 * @param color	of the player who gets next move
	 * @param move HexMove that gets us from our parent to this position
	 * @param parent GameTree for our parent
	 */
	public GameTree(HexBoard board, char color, HexMove m, GameTree parent) {
		// instantiate local variables
		this.board = board;
		this.color = color;
		this.m = m;
		this.parent = parent;
		children = new ArrayList<GameTree>();
		
		//If the opponent has not won
		if (!board.win(HexBoard.opponent(color))) {
			// Iterate over the possible moves that can occur given the current board
			for (int i = 0; i < board.moves(color).size(); i++) {
				//  Create a new board for each of the available moves
				HexBoard newBoard = new HexBoard(board, board.moves(color).get(i));
				//Add to the list of children GameTrees a new GameTree with the newly-created board, the opponent's color, the move that brought you here, and this GameTree as a parent 
				GameTree newTree = new GameTree(newBoard, HexBoard.opponent(color), board.moves(color).get(i), this); // ask abt m 
				children.add(newTree);
			}
		}
				
	}

	/**
	 * @return number of possible moves from this position
	 */
	public int numChildren() {
//		int count = 0;
//		for (int i = m.from(); i < children.size(); i++)
//			count++;
		//return count;	// TODO implement numChildren by reusing the size method 
		return children.size(); // will this be one off? 
	}
	
	/**
	 * @param i index number
	 * @return GameTree for our i'th child
	 */
	public GameTree getChild(int i) {
		return children.get(i);
	}
	
	public HexBoard getBoard() {
		return this.board;
	}
	
	public HexMove getMove() {
		return this.m;
	}
	
	public GameTree getParent() {
		return this.parent;
	}
	
	public ArrayList<GameTree> getChildren(){
		return this.children;
	}
	/**
	 * @return total number of positions in this (sub) GameTree
	 */
	public int size() {
		// TODO implement size so that it cumulatively adds up the current GameTree and the nodes of all its children
		int start = 1;
		for (int i = 0; i < numChildren(); i++) {
			start += getChild(i).size();
		}
		return start;
	
		
		
	}
	
	
	/**
	 * string representation of the board (for this position)
	 */
	public String toString() {
		return board.toString();
		
	} 
	
	/**
	 * Simple test program for GameTree class.
	 */
	public static void main(String args[]) {
		// 1. Instantiate a new (initial positions) board
		HexBoard b = new HexBoard(3,3);
		
		// 2. generate GameTree of all possible games
		GameTree t = new GameTree(b, HexBoard.WHITE);
		
		// 3. count the number of possible moves/positions
		int nodes = t.size();
		
		// print out the result
		System.out.println("Initial position:");
		System.out.println(t);
		System.out.println("Size of Tree: " + nodes + 
					((nodes == 252) ? " (Correct)" : " (incorrect)"));
	}
}
