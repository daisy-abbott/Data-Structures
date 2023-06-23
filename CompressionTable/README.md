# Assignment - Compression

## Learning Goals

* Practice inheritance by extending and using a doubly linked list data structure to include a `current` reference.
* Gain experience designing and implementing a non-trivial algorithm.
* Gain experience debugging complex and troublesome code.
* Gain experience using `JUnit`.

## Key Terms and Concepts

* `DoublyLinkedList` - A list consisting of nodes that each contain an item and a reference to the node ahead and behind them. This means the list has minimal structure and instead consists of individual elements strung together. Please *review* the `DoublyLinkedList.java` file we have provided you with as it contains methods that you need to reuse in the `CurDoublyLinkedList` class. Being familiar with this class will ensure that you don't duplicate work in its subclass `CurDoublyLinkedList`.
* `Association` - A data structure containing a key and a value together (think of it as an entry in a python dictionary). This allows for two pieces of data to be "associated" and referenced together, and can be useful for storing certain information in a dictionary. We have provided an `Association.java` file for your convenience. Please review it.
* Compression - A very useful technique where large amounts of data is efficiently stored to reduce the space it takes up, through encoding or other methods.)

## Intro


Sometimes we need to store massive amounts of information about an object. A good example is storing
graphic images. To save space on disks and in transmission of information across the internet, researchers
have designed algorithms to compress data. In this assignment, you will learn one of these compression
techniques.

A graphic image can be represented by a two dimensional array of information about the colors of various
picture elements (or pixels). At high resolution, the image may be composed of 1000 rows and 1000 columns
of information, leading to the need to store information on 1,000,000 pixels per image. Needless to say this
creates serious problems for storing and transmitting these images. However most images tend to have many
contiguous groups of pixels, each of which are the same color. We can take advantage of this by trying to
encode information about the entire block in a relatively efficient manner.

We have provided you with a lot of code here, but you will find that much of the code you must write is
quite tricky. This project will require you to be very careful in developing the code for the methods. Look
carefully at the provided code and design your methods very carefully. In particular, be sure to test your
code carefully as it is developed as you will likely make several logical errors if you are not extremely careful.
This is your most complex program yet. You should start early on this assignment and make a very
complete design for your program before you ever sit down at the computer to program.

## Description

In this assignment, you will create a program that compresses a table of strings to reduce the space it takes. You can think of the table representing an image, where each cell corresponds to a pixel. The basic idea of this project is that we can find ways to store only the necessary information. For instance, suppose we have the following table of information that corresponds to a 3x4 image with only black pixels (k for short):

| | | | |
|-|-|-|-|
|k|k|k|k|
|k|k|k|k|
|k|k|k|k|

If we imagine tracing through the table from left to right starting with the top row and going through
successive rows then we notice that we only need to record the following entry:

| | | | |
|-|-|-|-|
|k|-|-|-|
|-|-|-|-|
|-|-|-|-|

Our program, rather than storing unnecessarily the entire table, will model it as a doubly linked list that consists of associations, where the key will correspond to a cell in the table (an object of type `RowOrderedPosn`) and the value to a pixel (or more generally to a String). To capture the table above, our doubly linked list would only contain a single node:

    <Association: Position: (0,0)=k>


Interestingly, if we choose to alter the second pixel (position 0, 1) to red (r):

| | | | |
|-|-|-|-|
|k|r|k|k|
|k|k|k|k|
|k|k|k|k|

we would now need to update our doubly linked list to encode this additional information. The contents of the doubly linked list would now be:

    <Association: Position: (0,0)=k>
    <Association: Position: (0,1)=r>
    <Association: Position: (0,2)=k>

If we were to update the (0,1) pixel back to black (k), our doubly linked list would need to contract back to:

    <Association: Position: (0,0)=k>


You can imagine that our program can become more complicated. For instance, suppose we have the following table of information (where we will imagine r corresponding to the color red, g to green and b to blue):

| | | | | |
|-|-|-|-|-|
|r|r|r|g|g|
|r|r|r|r|r|
|r|b|r|r|r|
|r|r|r|g|r|
|r|r|b|r|r|


If we imagine tracing through the table from left to right starting with the top row and going through
successive rows then we notice that we only need to record the following entries:

| | | | | |
|-|-|-|-|-|
|r|-|-|g|-|
|r|-|-|-|-|
|-|b|r|-|-|
|-|-|-|g|r|
|-|-|b|r|-|


Rather than recording this in a two-dimensional table, it will now generally be more efficient to keep this
information in a linear list of `Assocations` where it is assumed we sweep across an entire row before going
on to the next:

    <Association: Position: (0,0)=r>
    <Association: Position: (0,3)=g>
    <Association: Position: (1,0)=r>
    <Association: Position: (2,1)=b>
    <Association: Position: (2,2)=r>
    <Association: Position: (3,3)=g>
    <Association: Position: (3,4)=r>
    <Association: Position: (4,2)=b>
    <Association: Position: (4,3)=r>

This assignment asks you to apply this technique to a  program, where individual cells of a table can be changed while your goal is to store that table in the most efficient way.

## Classes

This assignment has many classes; please read them carefully in the provided order before you start implementing. You are responsible for `CurDoublyLinkedList` and `CompressedTable`. The first of these is the underlying data structure, a doubly linked list that includes a reference, called `current`, and the second is the class which handles the updating of the table. To make your life easier, we have provided you with a `TestCurDoublyLinkedList` class that contains JUnit tests for the `CurDoublyLinkedList` class. Review them carefully and ensure they all pass before you proceed with `CompressedTable`. All other classes have all been implemented for you.

### `CurDoublyLinkedList`

`CurDoublyLinkedList` (short for 'extends the `DoublyLinkedList` class to include a `current` reference to a "current" node).  Think carefully about what it means for one class to extend another (hint, review the lecture on inheritance).

The `CurDoublyLinkedList` class should support all of the parent methods. In addition, 
the new class should support the following methods:

* `first()`, `last()`,
* `next()`, `back()`,
* `isOffRight()`, `isOffLeft()`, `isOff()`
* `currentValue()`,
* `addFirst()`, `addLast()`, `removeFirst()`, `removeLast()`, `getFirst()`, `getLast()`, `addAfterCurrent(Object value)`, and `removeCurrent()`.

Detailed specifications for these methods can be found in the provided code. For most of the methods we have provided pre-conditioins and post-conditions.  Pre-conditions are assumed to be true before the method is called.  If these are not true, then there is no guarantee about what the method will do.  Post-conditions are things that should be true when the method finishes, i.e., what you need to make happen!  Review the pre-conditions and post-conditions carefully. You should start this
assignment by finishing the `CurDoublyLinkedList` class.

### `TestCurDoublyLinkedList`

This is a **`JUnit`** test class for the `CurDoublyLinkedList` class. We have provided you with a large number of tests that your `DoublyLinkedList` class should pass. You are welcome to add more.

### `RowOrderedPosn`

The `RowOrderedPosn` class represents a single cell in a row-ordered table. The constructor takes four
parameters: the row of the entry in the table, the column of the entry in the table, the total number of rows
in the table, and the total number of columns in the table. Thus,

    new RowOrderedPosn(0, 0, 5, 3)

represents the entry at location (0, 0) i.e. the upper-left corner in a table with 5 rows and 3 columns. This
class also contains methods to return the next position after a given one (look at method `next()`) and to compare two positions in a
table (look at methods `less()` and `greater()`). This class is already implemented for you.


### `TwoDTable`
This interface represents a two-dimensional table. `CompressedTable` will implement it, so read the signatures of the methods carefully.


### `CompressedTable`

The `CompressedTable` class represents the compressed table. `CompressedTable` implements the `TwoDTable`
interface. It has an instance variable `tableInfo` of type:
`CurDoublyLinkedList<Association<RowOrderedPosn, ValueType>>`. The instance variable `tableInfo` is a `CurDoublyLinkedList` where each node in the list is an `Association`
whose key is an entry in the table of type `RowOrderedPosn` and whose value is of type `ValueType`. Feel free
to add other instance variables to this class.
You must fill in the constructor for the `CompressedTable` class as well as the two methods:
* `updateInfo(row,col,newInfo)`
* `getInfo(row,col)`

The `updateInfo` method of `CompressedTable` is probably the trickiest code to write. The examples above should help. Here is a brief
outline of the logic:

1. We have provided you with code to find the node of the list that encodes the position being updated.
Of course not every position is in the list, only those representing changes to the table. If the node is
not there, the method returns the node before the given position in the list. The class `RowOrderedPosn`
(see the startup code) not only encodes a position, but, because it also contains information on the
number of rows and columns in the table, can determine if one position would come before or after
another.

2. If the new information in the table is the same as that in the node found in step 1, then nothing needs
to be done. Otherwise determine if the node represents exactly the position being updated.
If it is the same, update the value of the node, otherwise add a new node representing the new position.

3. If you are not careful you may accidentally change several positions in the table to the new value. Avoid
this by considering putting in a new node representing the position immediately after the position with
the new value. (Why? Draw pictures of the list so you can see what is happening!)

4. If there is already a node with this successor position then nothing needs to be done. Otherwise add a
new node with the successor position and the original value. (Do you see why this is necessary?)

5. When you have modeled the basic functionality, move on  to eliminating consecutive items
with the same value, essentially contracting your doubly linked list. Ideally this optimization will only take `O(1)` time each time something is updated in the table.

Try to draw examples of this logic with several sample lists so that you can understand how it works!

## `Compression`

This is the class that contains the main method which controls the entire program. It is provided for you. Essentially, it asks the user to specify the size of the table (number of rows, number of columns) and an initial value to start with (all separated by comma), e.g., 

    Provide #rows #cols defaultValue:
    4 4 k

It continuously asks the user 

    What's next?

and the user can specify whether they want to

    display

the underlying compressed table

    exit 

the application

or update a specific cell with a new value, e.g., 

    update 3 3 r

Please review this class carefully.

## Getting started

1. Read through this writeup completely before you start. Then, get a sheet of paper and pencil and
draw pictures to help you understand how the doubly linked list works and how the compressed table
should work. These examples can also help you understand unit tests. Don’t forget to think about
special cases.

2. After reading the writeup and going through examples, start working on the design of the program. Look out for methods
that you can implement in terms of the other existing methods in either the `CurDoublyLinkedList` or
`DoublyLinkedList` class, along with our hints.

3. Try to interweave testing your code and writing your code. It is much better to write a method and
then stop and test it instead of writing all of the code for a class and only afterwards testing. 


## Helpful Considerations

* Bug hunting - This is a complex project where many things can go wrong. It is more likely than not that there will be large bugs, even late into the project. Try to identify where these bugs are coming from - often, there is an issue with `CurDoublyLinkedList` that is causing them but it is difficult to see as you are working on an entirely different piece. How can we test incrementally to try to stop these bugs from occurring? What's a good way to identify the source of a problem?

* Understanding what's given - As in the other projects, a significant amount of code has been provided to you. Try to go through it and understand how it can help you, and through this isolate what you need to focus on. After `CurDoublyLinkedList`, there is really only a couple more methods for you to implement. How can the provided classes and methods help you write these? In particular, we recommend looking at the `find` method in `CompressedTable` and the methods in `RowOrderedPosn`.

* Why a doubly linked list? - This assignment is a great example of how data structures like LinkedLists can be helpful in the real world. What are the advantages of a DoublyLinkedList in this scenario? Why would this project not work with an ArrayList? What are some other potential applications of LinkedLists?

## Grading

You will be graded based on the following criteria:


| Criterion                                | Points |
| :--------------------------------------- | :----- |
| Passes JUnit tests for all methods in `CurDoublyLinkedList` | 4      |
| No change if new color same as current   | 1      |
| Change color of position already in list | 2      |
| Change color of position not in list     | 2      |
| Correctly adds second node to list when needed | 2      |
| Correctly shrinks list | 2      |
| Appropriate comments (including JavaDoc) | 1      |
| General correctness                      | 2      |
| Style and Formatting                     | 1      |


NOTE: Code that does not compile will not be accepted! Make sure that your code compiles before submitting it.

## Two part assignment

This assignment spans two weeks.  However, we will still be grading some of the assignment next week.  Specifically, you must complete and push the `CurDoublyLinkedList` class by next Friday midnight. **The assignment grade portion for this class will be based on its status at the end of the day on Friday**.  You may include other files/changes in your repository.  We will not grade these, but make sure that they compile.  You may change the `CurDoublyLinkedList` file after the first Friday, e.g., if you find a bug that affects the other classes. The entire program must be submitted by the final deadline.
