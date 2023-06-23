# Assignment - Graphics Silver Dollar Game

## Learning Goals

* Gain more practice using the `ArrayList` class. 
* Gain exposure to Java graphics, and Java event handling.
* Gain experience testing for errors and writing code that handles edge cases.

## Key Terms and Concepts
* `ArrayList` - An ArrayList is a resizable array-like data structure, where items can be added and removed regardless of the initialized size (See 1.3 pg. 136 in the textbook and the lecture slides). In this assignment we will use the default java.util.ArrayList class. Please look into its [documentation](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html) for more details about its methods.
* Inner classes - A class which is a member of another class (See [Java documentation](https://docs.oracle.com/javase/tutorial/java/javaOO/innerclasses.html)).
* `EventListener` - A function in code that waits for an event to occur, such as a key press or mouse click, before triggering a response (See  [Java documentation](https://docs.oracle.com/javase/tutorial/uiswing/events/intro.html)).
* Edge cases - A possible scenario of user input which requires a specific or non-standard response from the code, such as a user clicking out of bounds or attempting an illegal move.


## Overview
The goal of this assignment is to create a graphical version of the Silver Dollar game. See **Appendix A - Rules** for more information on the specifics of the game. In this version of the game, the user will use the mouse to move the coins instead of typing commands on the keyboard. Your game must respond correctly to any and all forms of user input, never allowing the rules or structure of the game to be broken.

Be sure to read over the entirety of this document and the code, including the classes already implemented for you, before beginning work on the code. This will help you better understand the tools provided and the parameters required for this project.

`Coin` and `CoinSquare` have already been implemented for you. You are responsible for `GraphicsCoinStrip`, which has been partially implemented. We've added TODO statements where you need to edit the code (there are four places).

The correctness of the assignments in this class will be automatically verified. For this reason, you must follow all naming conventions specified in this assignment.

## Classes

![Calculator Mockup](coinsquaremockup.png)

### `Coin`
The `Coin` class represents a single coin. This class is already implemented for you. This class contains several useful methods for creating and moving the coin, which will be useful for the assignment. Make sure to read its code!

Note that the `Coin` constructor requires a `JFrame` (a drawable panel
on the screen) as a parameter.  The primary (`GraphicsCoinStrip`) class 
in this project extends the `JFrame`, and so (`this`) can be passed
as the required `JFrame` when the new coins are constructed if you are within the `GraphicsCointStrip` class.

### `CoinSquare`
The `CoinSquare` class represents a square. This class is already implemented for you and contains methods to interact with and gain information about the coin contained in a square. Again, these methods are useful for the assignment and it will be very helpful to understand them before beginning the assignment.

### `GraphicsCoinStrip`
The `GraphicsCoinStrip` class uses the `Coin` and `CoinSquare` classes to implement the Silver Dollar Game. This class is partially implemented. There are TODO comments where you need to add code. Both `Coin` and `CoinSquare` have a `contains` method that takes an `x` and `y` and returns a `boolean` indicating if the `x,y` location is within them (there are inherited from `Ellipse2D` and `Rectangle2D`, respectively).

Notice that there are no `play` or `move` methods in the `GraphicsCoinStrip` class because the mouse is in control of the game. Much of what drives the game is the mouse event handling which can be found in the inner class `CoinMouseListener` inside the `GraphicsCoinStrip` class. The purpose of the inner class `CoinMouseListener` is to encapsulate all of the methods that deal with the mouse movement.

You can add whatever methods you think would be useful to the `GraphicsCoinStrip` or `CoinMouseListener` classes, but do not change the names of any of the existing methods or variables.

After you have a working copy of the game, fill in the `gameIsOver` method in this class that checks to see if the game is over and, if so, use this method to signal this to the user in some fashion. Possible examples are to print out a message to the console, or better, change the color of all of the coins. You can also make sure that the coins no longer move once the game is completed (although this is not required). Test this program rigorously to make sure that it works consistently, even when the user attempts unusual or unexpected actions.

**Note**: Make sure that you do not "hard-code" your solution to the specific number of coins and strip positions we have given you. Your code should work for any strip size and any number of coins.

## Getting Started
1. Read through the [CS 062 style guide](https://github.com/pomonacs622021fa/Handouts/blob/master/style_guide.md "Style guide"). You must follow these guidelines for all of your assignments. In short, write clean and readable code, thorough comments, and understandable variable and method names.
2. As before, we will provide you with a URL that will consist your invitation to the assignment. Follow the same steps, changing the URL where appropriate so that you clone Assignment01.
3. Look closely at the classes and methods that have been provided already. In this case, these are the `Coin` and `CoinSquare` classes. Figure out what they do, what methods they have available, and how these methods might be useful to your code.
4. You are now ready to get started! This assignment asks you to fill in the constructor and fill in other methods in the `GraphicsCoinStrip` class and inner class to play the game. As much as possible, try and develop incrementally. That is, get one small piece working and then move on to another piece. Don't forget to push your code to Github as you go.
5. Make sure you edit the `.json` file you are given with every lab/assignment to include your username, your partner's username (if collaboration is allowed), and indicate if you did any extra credit work.


## Helpful Considerations
* Breaking the rules - What happens when the user attempts to make an illegal move? Keep in mind that the user has much more power over your program than in the text iteration, as they have full control of the mouse to click or drag coins anywhere. Make sure that the user cannot break the rules of the game, or, even worse, break your program entirely.
* Make sure you understand what all of the instance variables are used for in both the `GraphicsCoinStrip` class as well as the CoinMouseListener class.
* Testing - Considering the more open-ended nature of this program, how can we predict the possible actions that could break our code? What would these be? How can we test for them?
* Saving your work - Make sure to commit and push your work to GitHub MULTIPLE TIMES throughout the process! Not only does this help us see your unique progress, but it ensures that you have frequent backups of your work.

## Grading
You will be graded based on the following criteria:

| Criterion                                         | Points |
| :------------------------------------------------ | :----- |
| Game starts with random coin positions            | 1      |
| Coins can be dragged                              | 2      |
| Coins can be dragged multiple squares             | 1      |
| Dropped coins end up centered in correct location | 1      |
| Illegal moves are not allowed                     | 3      |
| Game over is indicated correctly                  | 2      |
| General Correctness                               | 2      |
| Code Quality*                                     | 3      |
| Appropriate comments including JavaDoc            | 2      |
| [Style and formatting](https://github.com/pomonacs622021fa/Handouts/blob/master/style_guide.md)                               | 2      |
| Submitted correctly                               | 1      |
| **Total**                                         | **20** |

* Code quality refers to the correct use of Java constructs including booleans, loop constructs, etc. Think of it as good writing style for programs.

NOTE: Code that does not compile will not be accepted! Make sure that your code compiles before submitting it.

## Submitting your work
You must comment your code. We will be using the JavaDoc commenting style. To be compliant with JavaDoc, you must have the following:

Each comment on a method or class should start with `/**` and end with `**/`. Every line in between should start with a `*` and be appropriately indented. (Comments on variables and constants do NOT have to use this style unless they are public.)

A comment describing the class right before the class declaration (i.e. after the `import` statements). This comment should include the `@author` tag after the class description, and the `@version` tag after the author tag.

A comment for each method describing what the method does. This comment should describe the what but not the how.
`@param`, `@return` and `@throws` tags for each method (when appropriate)
`pre-` and `post`- conditions as appropriate
Double-check that your work is indeed pushed in Github! It is your responsibility to ensure that you do so before the deadline.

### Appendix A - Rules

In the silver dollar game, your objective is to move all the coins to the left side. Coins can be moved multiple squares at a time, and only to the left. Coins cannot jump over each other, nor can they occupy the same square as another coin. The coins start in random positions along the strip.

The game is over when all the coins are next to each other starting in the far left square.
