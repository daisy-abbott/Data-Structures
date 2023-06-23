# Assignment - Hex-a-Pawn

## Objectives

For this assignment, you will:

* Gain experience working with tree data structures of higher than 2 arity (number of children)
* Gain more experience with recursive algorithms
* Implement a basic artificial intelligence algorithm for a two-player zero-sum game


## Description

This week's assignment uses trees to play a small chess-like game called Hex-a-Pawn. You will build a tree representing
all possible states that the game board can be in. You will then implement several different players that
consult this tree to make moves as they play Hex-a-Pawn. The players include: a human player that asks
the user for moves to make; a random player that picks possible moves at random; and a computer player
that improves its strategy by learning from past mistakes (but not from past victories). In the end, you will be able to run the different
players against each other.

This assignment can been found in the [12.11 Laboratory of the Java Structures textbook by Bailey](http://www.cs.williams.edu/~bailey/JavaStructures/Book_files/JavaStructures.pdf).
You can find more information at Appendix A which describes the Hex-a-Pawn
game as well as listing some steps to help you get started. It also tells you that a complete game
tree for 3x3 boards has 252 nodes. You will find some helpful debugging
advice (e.g., if your tree has 370 nodes then you have the wrong win test).


## Classes

In this assignment, you must implement a `GameTree` class as well as three different classes corresponding
to different types of players. Each of these player classes will implement the `Player` interface.


### `GameTree`

You are responsible for designing the `GameTree` class. This is a tree structure with potentially many children
instead of just two. The included starter defines the most basic methods to implement and traverse a
GameTree.  But, as you design your `Player` sub-classes, you might identify additional methods
that you would like to implement in the `GameTree` class.  Think about the methods you will need for
this class and how you can represent the structure.

The picture below shows the top two levels (i.e. the nodes at depth 0 and 1) of the game tree that results
from a call to the constructor:

~~~java
    new GameTree(new HexBoard(3, 3), HexBoard.WHITE)
~~~

![Board](board.png "Board")

The root node of the tree contains the starting board position (`new HexBoard(3,3)`) and the player is
white (`HexBoard.WHITE`). Note that this is not a binary tree since it is possible for a node in the tree to
have multiple children each child corresponding to a legal action by the player.

Do not start implementing this class before you have carefully reviewed the provided `HexBoard` and `HexMove` classes.
They contain useful methods and variables that you can incorporate in your `GameTree` class. Make sure you also check the Getting started section and Appendix A.


### `Player` classes

You will implement three player classes: `HumanPlayer`, `RandPlayer`, and `CompPlayer`. Each player implements
the `Player` interface. This interface has only one method:

~~~java
    public Player play(GameTree node, Player opponent);
~~~

The `play` method takes in a `GameTree` corresponding to some configuration of the board and the opponent
player. The return value of this method is the `Player` who won the game. Inside play, you should check if
the board configuration is a win for the opponent. If not, the player makes a move based on what type of
player it is. After making a move, you should then call the opponents play method.

You will have to think carefully about how the `Player` classes interact with the `GameTree` class; this
should inform what methods and instance variables you include in the `GameTree` class.
We have provided you with a lot of TODO comments and hints on how to achieve this and a main method for each class that will help you with debugging.

<!-- You are also encouraged to create
a main method inside each `Player` class where you create two players and have them play a game. This will
help you debug your code. -->

## Grading

You will be graded based on the following criteria:


| Criterion                                      | Points |
| :--------------------------------------------- | :----- |
| Efficient implementation of `GameTree` class   | 3      |
| Correct `HumanPlayer`                          | 3      |
| Correct `RandPlayer`                           | 3      |
| Correct `CompPlayer` that trims tree over time | 3      |
| General Correctness                            | 2      |
| Appropriate comments + JavaDoc                 | 1      |
| Style and Formatting                           | 1      |


NOTE: Code that does not compile will not be accepted! Make sure that your code compiles before submitting it.

## Getting started

1. Follow the same steps with the first lab/assignment to clone the github repository for this assignment.

2. The javadoc documentation for the classes can be found [here](http://www.cs.williams.edu/~freund/cs136-073/javadoc/hexapawn/index.html).

3. You can play a game of Hex-a-Pawn by running the `HexBoard` class (that class
can compile on its own even though the `Player` class requires `GameTree` to be defined).

4. You can look at the `main` method in the `HexBoard` class to see an example of how to code up a game between two players.


### Appendix A - Gardner's Hex-a-Pawn

The Hex-a-Pawn game was developed in the early sixties by [Martin Gardner](https://en.wikipedia.org/wiki/Martin_Gardner). Three white and
three black pawns are placed on a 3x3 board. On alternate moves, they
may be either moved forward one square, or they may capture an opponent on
the immediate diagonal. The game ends when a pawn is promoted to the opposite rank, or
if a player loses all their pieces, or if no legal move is possible.

In his article in the March 1962 Scientific American, Gardner discussed a
method for teaching a computer to play this simple game using a relatively
small number of training matches. The process involved keeping track of the
different states of the board and the potential for success (a win) from each
board state. When a move led directly to a loss, the computer forgot the move,
thereby causing it to avoid that particular loss in the future. This pruning of
moves could, of course, cause an intermediate state to lead indirectly to a loss,
in which case the computer would be forced to prune out an intermediate move. We can
use nodes of a tree stored in a computer to maintain the necessary information
about each board state. The degree of each node is determined by the number
of possible moves.

#### Procedure

During the course of this assignment you are to:

1. Construct a tree of Hex-a-Pawn board positions. Each node of the tree is
called a `GameTree`. The underlying data structure is of your own design but we provide you with a lot of hints.
Think about the ways in which you might want to navigate this tree (downwards
and upwards) and design a data structure to enable those operations.

2. Construct three classes of `Player`s that play the game of Hex-a-Pawn.
These three classes may interact in pairs to play a series of games.

Available for your use are three Java files:

`HexBoard`: This class describes the state of a board. The default board is the 3×3
starting position. You can ask a board to print itself out (`toString`) or to
return the `HexMove`s (moves) that are possible from this position. You can
also ask a `HexBoard` if the current position is a win for a particular color—
`HexBoard.WHITE` or `HexBoard.BLACK`. A static utility method, `opponent`,
takes a color and returns the opposite color. The `main` method of this class
demonstrates how `HexBoard`s are manipulated.

`HexMove`: This class describes a valid move. The components of the arraylist
turned from the `HexBoard.moves` contains objects of type `HexMove`. Given
a `HexBoard` and a `HexMove` one can construct the resulting `HexBoard` using
a `HexBoard` constructor.

`Player`: When one is interested in constructing players that play Hex-a-Pawn,
the `Player` interface describes the form of the `play` method that must
be provided. The `play` method takes a `GameTree` node and an opposing
`Player`. It checks for a loss, plays the game according to the `GameTree`,
and then turns control over to the opposing player.

Read these class files carefully. You should not expect to modify them.
There are many approaches to experimenting with Hex-a-Pawn. One series
of experiments might be the following:

1. Run `HexBoard` and play a few games
against the computer. If you wish to modify the size of the board, very
little is known about the games larger than 3x3.

2. Implement a `GameTree` class. This class should have a constructor that,
   given a `HexBoard` and a color (a `char`, `HexBoard.WHITE` or `HexBoard.BLACK`),
   generates the tree of all boards reachable from the specified board position
   during normal game play. Alternate levels of the tree represent boards
   that are considered by alternate players. Leaves are winning positions for
   the player at hand. The references to other `GameTree` nodes are suggested
   by the individual moves returned from the moves method. A complete
   tree for 3 × 3 boards has 252 nodes.

   The hardest part of your initial implementation of this class will be
   generating the (recursive) tree of all possible moves.  If you look
   at the `HexBoard` class you will see sample code that generates
   a list of possible moves and the board resulting from each.

 Hints: 608 nodes? No win test. 370? Wrong win test. 150? early stop.

3. Implement the first of three players. It should be called `HumanPlayer`. If it
hasn’t already lost (i.e., if the opponent hasn’t won), this player prints the
board, presents the moves, and allows a human (through a scanner)
to select a move. The play is then handed off to the opponent.

4. The second player, `RandPlayer`, should pick a move randomly. Make sure you
check for a loss before attempting a move.

5. The third player, called `CompPlayer`, should attempt to have the CompPlayer
object modify the game tree to remove losing moves. Too bad it doesn't learn from its victories, too.

`Player`s may be made to play against each other in any combination.
