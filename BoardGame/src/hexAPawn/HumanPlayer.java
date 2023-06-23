package hexAPawn;

import java.util.Scanner;

public class HumanPlayer implements Player {

	// instance variables
	private char color;
	private HexBoard board;

	public HumanPlayer(char color) {
		this.color = color;
		
	}

	public Player play(GameTree node, Player opponent) {
		
        // Print current board
        System.out.println(node);

        // if the state is a winning state for the opponent or there aren't any moves:
		if (node.getBoard().win(HexBoard.opponent(color)) || node.numChildren() == 0) {
			//return the opponent
			return opponent;
		}
		 
		else {
			 // Print all available moves in the form of "1: Move from [1,1] to [2,1]." etc.
			for (int i = 0; i < node.numChildren(); i++) {
				System.out.println(i + ". " + node.getChild(i).getMove().toString());
			}
			 //Ask the user for which move they have chosen
			Scanner input = new Scanner(System.in);
			System.out.println("Choose a move: ");
			String move = input.nextLine();
			
			 //Find the GameTree child that corresponds to the chosen move (be careful if you have printed the moves starting at 1) 
			int intMove = Integer.parseInt(move);
			GameTree chosenChild = node.getChild(intMove);
			 //return opponent.play on the chosen child with "this" player as the opponent
			return opponent.play(chosenChild, this); // ask carl 
		}
        
       
	}

	public String toString() {
		return color == HexBoard.BLACK ? "black" : "white";
	}

	public static void main(String s[]) {
		HumanPlayer h1 = new HumanPlayer(HexBoard.WHITE);
		HumanPlayer h2 = new HumanPlayer(HexBoard.BLACK);
		System.out.println(
			h1.play(new GameTree(new HexBoard(), HexBoard.WHITE), h2)+" wins");
	}
}
