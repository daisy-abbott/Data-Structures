package hexAPawn;

import java.util.Random;

// TODO: Any useful imports to pick random choice

public class RandPlayer implements Player {

	// instance variables
	private char color;


	public RandPlayer(char color) {
		this.color = color;
	}

	public Player play(GameTree node, Player opponent) {
        //  Print current board
        System.out.println(node);

        // if the state is a winning state for the opponent or there aren't any moves:
        if (node.getBoard().win(HexBoard.opponent(color)) || node.numChildren() == 0) {
        	 //    return the opponent
			return opponent;
        }  
        else {
        	 //    Pick a random GameTree child
        	Random rand = new Random();
        	int randInt = rand.nextInt(node.numChildren());
        	GameTree randomChild = node.getChild(randInt);
        	 //    return opponent.play on the chosen random child with "this" player as the opponent
        	return opponent.play(randomChild, this);
        } 
	}

	public String toString() {
		return color == HexBoard.BLACK ? "black" : "white";
	}

	public static void main(String s[]) {
		RandPlayer h1 = new RandPlayer(HexBoard.WHITE);
		HumanPlayer h2 = new HumanPlayer(HexBoard.BLACK);
		System.out.println(
			h1.play(new GameTree(new HexBoard(), HexBoard.WHITE), h2)+" wins");
	}
}
