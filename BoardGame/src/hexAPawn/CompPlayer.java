package hexAPawn;

import java.util.Random;
import java.util.Scanner;

public class CompPlayer implements Player {

	// instance variables
	private char color;


	public CompPlayer(char color) {
		this.color = color;
	}

	public Player play(GameTree node, Player opponent) {
        
        // print current board
		System.out.println(node);
		
        // if the state is a winning state for the opponent or there aren't any moves:
		if (node.getBoard().win(HexBoard.opponent(color)) || node.numChildren() == 0) {
			// trim tree rooted in node
				// go to node corresponding to parent
				GameTree parent = node.getParent();
				// if parent is not null
				if (parent != null) {
					// find grandparent 
					GameTree grandparent = parent.getParent();
					// remove grandparent's child which corresponds to parent
					int parentIndex = grandparent.getChildren().indexOf(parent);
					// print what you removed
					System.out.println(grandparent.getChildren().remove(parentIndex));
				}
				// else
				else {
					// print that the parent is null and that was nothing there for you to remove
					System.out.println("Parent is null, nothing to remove.");
					
				}
            // return the opponent
				return opponent;
		}
        // else:  
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
		Scanner r = new Scanner(System.in);
		GameTree gt = new GameTree(new HexBoard(), HexBoard.WHITE);
		String ans = "yes";
		while (ans.equals( "yes")) {
			Player h1 = new CompPlayer(HexBoard.BLACK);
			Player h2 = new HumanPlayer(HexBoard.WHITE);
			System.out.println(h2 + " plays first.");
			System.out.println(h2.play(gt, h1) + " wins!");
			System.out.println("The tree now has size " + gt.size());
			System.out.println("Play again?  answer yes or no");
			ans = r.next().toLowerCase();
			System.out.println("Your answer was " + ans);
		}
		System.out.println("Thanks for playing!");
		r.close();
	}
}
