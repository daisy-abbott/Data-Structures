# Assignment - Text Calculator

For this assignment, you will be implementing a text-based postfix calculator which takes input in postfix notation and displays the results of the computation.  As always, read through the entire handout first before you start working on the assignment.

## Learning Goals

* Write your own program from scratch
* Think about different ways of implementing stacks
* Practice dealing with error handling
<!-- * Gain experience with unit testing -->

## Postfix Notation

When performing mathematical operations (additions, subtractions, etc), the normal mathematical notation that we use is called "infix" notation, where the mathematical operator is in between the operands (e.g., `3 - 5`).  There is a different notation known as postfix notation where the operator comes after the operands. For example, the expression `3 - 5` would be
written as `3 5 -`. As another example, the expression `47 - (5 + 7) * 4` would be written as:

    47 5 7 + 4 * -

And one final example, `3+5*7` would be written as `3 5 7 * +` in postfix notation.

One of the key advantages of postfix notation is that we can do the calculation as we go, working left to right (with some memory).  The main idea is to use a "Stack" to keep track of the values we've seen so far, but have not used in computation.  A stack is a data structure where we can add things ("push" in stack terminology) and remove things ("pop").  Stacks operate like a stack of plates: we add plates to the top and we grab a plate from the top.  When data is added to a stack, it is added to the "top" and when we remove data, the first thing we remove is the item on the top.  Another way this is sometimes referred to is "last in first out" (or LIFO), i.e., the most recent thing that was added will be the first thing to get removed.

Using a stack, we can do postfix computations as follows:

* If the next thing is a number, push it on the stack.

* If the next thing is an operation, remove the top two things from the stack, perform the operation *and* push the answer back on the stack.

Here is the computation of `47 5 7 + 4 * -`,

	47	Stack top (left): 47
	5	Stack top (left): 5 47
	7	Stack top (left): 7 5 47
	+	Stack top (left): 12 47
	4	Stack top (left): 4 12 47
	*	Stack top (left): 48 47
	-	Stack top (left): -1

Be careful about the ordering of the operands, i.e., when applying an operator the first thing off the stack is the right operand (in infix terminology) and the second thing off the stack is the left operand.  This is why we get -1 and not 1 at the end.

## Text Postfix Calculator

Your program will prompt the user for either a number or an operator and will either add the number to the stack directly or perform the operation and then add the result back to the stack.  Here is an example execution of the program following the example above:

	Enter a number or operator: 47
	
	Memory contents: 
	47
	---
	
	Enter a number or operator: 5
	
	Memory contents: 
	5
	47
	---
	
	Enter a number or operator: 7
	
	Memory contents: 
	7
	5
	47
	---
	
	Enter a number or operator: +
	Answer: 12
	
	Memory contents: 
	12
	47
	---
	
	Enter a number or operator: 4
	
	Memory contents: 
	4
	12
	v
	---
	
	Enter a number or operator: *
	Answer: 48
	
	Memory contents: 
	48
	47
	---
	
	Enter a number or operator: -
	Answer: -1
	
	Memory contents: 
	-1
	---
	
	Enter a number or operator:

## Requirements

For this assignment, we're not giving you any starter code.  However, we are giving you a specification for the classes that you need to write, specifically, the public methods.  All of the classes should be inside the `calculator` package.  To create a new Java file in that package, right-click on the `calculator` package and select "New -> class".  Make sure you follow the specifications exactly for each of the classes and public methods.

### High-level requirements

Your interactive calculator must meet the following requirements:

* Follow *exactly* the output shown in the example above.

* Keep going until the user enters "quit" (without quotes).

* Support +, -, *, and /, as well as the following two operators:

	* `pop`: the answer shown should be the top element of the Stack and the top item should be removed from the Stack

	* `clear`: remove all items from the Stack

* Handle *all* errors.  When an error occurs, the Stack should not be changed - only an error message is printed out.  The following is the output that should be shown for different errors:
	
	* Divide by zero: "Error: divide by zero"
	
	* Invalid input (not a number of operator): "Error: expected number or operator"

	* Not enough operators: "Error: operator requires two arguments"

	* Bad pop: "Error: pop requires one argument"


Here is an example showing the errors:

	Enter a number or operator: pop
	Error: pop requires one argument
	
	Memory contents: 
	---
	
	Enter a number or operator: asdf
	Error: expected number or operator
	
	Memory contents: 
	---
	
	Enter a number or operator: 1
	
	Memory contents: 
	1
	---
	
	Enter a number or operator: +
	Error: operator requires two arguments
	
	Memory contents: 
	1
	---
	
	Enter a number or operator: 0
	
	Memory contents: 
	0
	1
	---
	
	Enter a number or operator: /
	Error: divide by zero
	
	Memory contents: 
	0
	1
	---
	
	Enter a number or operator: 

### Per-class requirements

The three classes you need to implement are outlined below:

* `Operation`: This class represents a mathematical operation. It should have just a single static method:
	* `public static int performOperation(char op, int left, int right)`: Perform the math operation on these two operands and return the result.  Valid operations are +, -, * and /.  If the input is not one of these four operations, it should throw an `IllegalArgumentException`.  This method could throw an `ArithmeticException` *if* we try and divide by zero (Java will do this naturally for you).
* `CalculatorMemory`: The memory of the calculator.  It must have the following methods:
	* `public void push(int number)`:  Add the number to memory.  **Must run in O(1).**
	* `public int pop()`: Return and remove the most recently pushed value.  **Must run in O(1).**
	* `public boolean isEmpty()`
	* `public int size()`
	* `public void clear()`: Remove everything from the memory.
	* `public String toString()`: A String version of the numbers stored.  The String should look like the memory contents output below.
	
	For example:

		top number
		next number
		...
		bottom number
		---

**Hint:** Don't reinvent the wheel.  Utilize something like `java.util.ArrayList` or `java.util.LinkedList` to do the heavy lifting for you.

* `Calculator`:  This is the class where we actually interact with the user and run everything.  It needs to have the two public methods below and *at least* one private helper method (you pick the name, etc.) :

	* A zero parameter constructor

	* `public void run()`: This runs the calculator, which will prompt the user for numbers, do the calculations, etc.


<!-- ## JUnit tests

In addition to the three classes above, you must also submit two classes `OperationTest` and `CalculatorMemoryTest` that have JUnit tests that test all of the public methods of those two classes.  We will run your JUnit tests on "bad" versions of these classes and you will be graded on how well you identify problematic cases. -->

## One Path to Implementation

There are many ways that you can go about doing this assignment, but I'd strongly encourage you to work incrementally.  Here's one path:

1. Write the `Operation` class.

<!-- 2. Write your JUnit tests for `Operator` (`OperatorTest`).  Fix any bugs you find :). -->

2. Write the `CalculatorMemory` class.

<!-- 4. Write your JUnit tests for `CalculatorMemory` (`CalculatorMemoryTest`).  Fix any bugs you find :). -->

3. Get a basic version of the calculator running without error checking and with just the math operators.

4. Add the pop and clear operations.

5. Add in all of the error checking.

## Grading

| Criterion                                | Points |
| :--------------------------------------- | :----- |
| Calculator output matches specification | 1      |
| Loops and terminates appropriately | 1      |
| Arithmetic operations work correctly | 3      |
| pop and clear work correctly | 1
| Appropriately handle errors and show message | 3     |
| Appropriate comments + JavaDoc           | 3      |
| [Style and formatting](https://github.com/pomonacs622018f/Handouts/blob/master/style_guide.md)                       | 2      |
| Submitted correctly                      | 1      |


<!-- | Good JUnit tests for each method in OperationTest | 1 |
| Good JUnit tests for each method in CalculatorMemoryTest | 1 | -->
## Submitting your work

 Double-check that your work is indeed pushed in Github! It is your responsibility to ensure that you do so before the deadline.
