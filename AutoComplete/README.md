# Autocomplete

## Key Terms and Concepts

* `Comparators` - An interface used to order objects in Java. For custom Java objects that have an inherent order (like `int`s and `String`s), you can implement this interface to let the program know how they should be ordered.

* Autocomplete - A system where a program identifies likely results based on a prefix typed by the user.

## Description

This week, we will be implementing a program which autocompletes phrases. After you type a phrase, common words and phrases beginning with what you typed will appear, allowing you to save time by selecting a suggested term.

This autocomplete will only display a limited number of completions. Otherwise, the user would see an unmanageable number of possibilities and the autocomplete would be more confusing than useful. To see more on how to decide which results to display, see **Appendix A - Which Results to Display**. However, this is not directly relevant for this assignment.

For this assignment, you are given a file with keys and associated weights. These weights describe how frequently the result is displayed, and will determine which you display. You will then
read in the given information and suggest completions when the user types in a prefix. The original file will
be sorted by the keys. When a query is given, you will find all the keys that start with the query, sort the
matching terms by weight, and then print the top entries.

## Classes

### `AutocompleteInterface`

This class provides the interface for `Autocomplete` called `AutocompleteInterface`. You can use it to see the signature of the method you need to implement (`allMatches`). `Autocomplete` should implement this interface, which basically serves as a suggestion for how this class should work.

### `Term`

Write a class `Term` to represent a pair including a `key` (represented as a `String`) and a `weight` (represented
as a `long` – the frequencies can be large!). These objects should be immutable (no changes
allowed to the `key` or `weight` fields after a `Term` has been created).

The class should implement the interface `Comparable<Term>`, and thus it must include the `compareTo` method. It should also override the `toString` method so you can easily print the `key` and `weight` of a term.

We also ask you to implement two static methods that return `Comparators` (see the lecture notes on comparing and iterating). These can be static because they don’t depend on the instance variables of the `Term`. They return `Comparators` that can be used to compare any two `Terms`. The first method, `byReverseWeightOrder` should return a comparator that has a `compare` method that ignores the `key`
field, but compares the `weights` in reverse order by size. That is, if used in a sort, terms with higher weight would occur before those elements with smaller weight.

The second static method, `byPrefixOrder(int r)`, returns a comparator with a `compare` method that only considers the first `r` characters of the `key` field, and represents the usual lexicographic order. Thus if `r` is 3, the term with `key` `"hello"` would come before `"hopper"`, but `"hello"` and `"help"` would be considered equal (because their first three characters are the same.

You may build the comparators in the static methods using a static inner classes. Here are two files for you to reference:
See `nameComparator` in (https://github.com/pomonacs622021fa/LectureCode/blob/main/Lecture11/Employee.java). 

See `avgComparator` in (https://github.com/pomonacs622020fa/LectureCode/blob/3414bdd8cb5b8a01ec34ba0d9878ee90ba48847e/CompareAndIterate/StudentComp.java).

Test the methods in this class thoroughly (using `JUnit` or a `main` method) before proceeding to the
other classes. We suggest you build a small `ArrayList` of `Terms` and then sort them in several different
ways using the comparators returned by the static methods. Feel free to use the built-in static `sort`
method in `Collections`. See the `Java` documentation for details.

### `BinarySearchForAll`

Next you should implement the class `BinarySearchForAll`. This is an unusual class in that it has no
instance variables and only provides two static public methods to find elements in a list. The first
finds the index of the first element in a list that equals (according to the comparator) the key. The
second finds the index of the last element that equals (according to the comparator) the key. This will allow us
to quickly grab all the terms that match a prefix in the `Autocomplete` class.
Notice the phrase "according to the comparator"! We will be using this with the `Term` comparator
`byPrefixOrder(r)` and we will want to get a match if the key we are searching for is a prefix of the
element in the list.

**Do not use the `equals` method to check for a
match.** Instead, see if `compare` returns a 0. The problem that we have is that the comparator may not be consistent with the `equals` method. If we knew what
comparator we would be using for comparing terms then we could override `equals`, but in this case we will use different
comparators at different times.

**You should not use a linear search to find elements in the list.**
Instead, as the name of the class indicates, you should use binary search (think of what preconditions need to be true to achieve its superior performance).

```java
/**
* Returns the index of the first element in aList that equals key
*
* @param aList
* Ordered (via comparator) list of items to be searched
* @param key
* item searching for
* @param comparator
* Object with compare method corresponding to order on aList
* @return Index of first item in aList matching key or -1 if not in aList
**/
public static <Key> int firstIndexOf(List<Key> aList, Key key, Comparator<Key> comparator);
/**
* Returns the index of the last element in aList that equals key
*
* @param aList
* Ordered (via comparator) list of items to be searched
* @param key
* item searching for
* @param comparator
* Object with compare method corresponding to order on aList
* @return Location of last item of aList matching key or -1 if no such key.
**/
public static <Key> int lastIndexOf(List<Key> a, Key key, Comparator<Key> comparator);
```

### `Autocomplete`

This class will use `Term` and `BinarySearchForAll` to find all of the terms that match
a given prefix and to return them in a list held in descending order by weight. The constructor of the class should take in a `List<Term>` and sort it according to the keys of the terms. Don’t forget the list
returned should be sorted in descending order by weight! We have provided an interface `AutocompleteInterface` that your class should implement.
The class has only a
single method:

```java
/**
* @param prefix
* string to be matched
* @return List of all matching terms in descending order by weight
*/
public List<Term> allMatches(String prefix);
```

### `AutocompleteMain`

Finally, you should write a static `main` method in a class `AutocompleteMain` that takes two runtime
parameters. The first is an `int` that determines how many matching items should be printed in
response to a query, while the second is the name of the file holding the weights and keys. The file’s
first line will be an `int` specifying the number of lines of data in the file. All subsequent lines will
consist of a `long` value representing the weight followed by one or more tab symbols and then a `String`
representing the key. You can use a `Scanner` to read in the input. After reading in the `long` value, we
suggest you read in the rest of the line and then use the `trim` method in `String` to throw away any
white space preceding or following the key. You can then package these into a value of type `Term`. The
`Terms` read in should be held in an `ArrayList`.

Once the file is loaded, the program should prompt the user to type in a prefix and then print the top
group of matching items (both keys and weights). It should then prompt the user to enter another
prefix. Be aware that the number of matching items may be smaller than the number specified to be
returned. In that case, just print out all the matching items.

To test your program we have provided two files, `cities.txt` and `wiktionary.txt`. They are both very
large so don’t print them out. For testing feel free to select a small amount of data from these files.

Note that `cities.txt` contains some cities that have accented characters, which, depending on the OS (read, Windows) can sometimes cause problems with the `Scanner` class.  If you have troubles constructing a `Scanner` with the `cities.txt` file, you can pass in a second parameter to the constructor specifying the character encoding.  In this case "utf-8" would be good, e.g., 

```
new Scanner(theFile, "utf-8")
```

Here is some sample output if the command line parameters are 5 and `wiktionary.txt`.

```
Enter a new prefix: hel
There are 10 matches.
The 5 largest are:
24371200 help
23547400 held
4048780 helped
3461920 helen
3177030 hell
Enter a new prefix: pom
There are 2 matches.
The matching items are:
873729 pomp
403557 pompous
Enter a new prefix:
```

## Getting started

1. Follow the steps from previous assignments to copy the Github repository and start your project.

2. Start working on this assignment in the order illustrated by the classes section. This project has many working pieces, and it is key to complete individual sections before linking them together.

3. Begin with the `Term` class and move on when you have convinced yourself it is complete. If you encounter issues at any point, try to identify which class they are coming from.


## Helpful Considerations

* Individual testing - In a project with so many different pieces fitting together, it is important that each works individually before they work together. How can you identify which piece is broken? How does each piece in this project fit with the other?

* Which data structure to use? - We had you use an `ArrayList` to hold the data in this program. Are there any reasons that it might be better or worse to use an array rather than `ArrayList`?

* Program structure - Rather than defining the (rather odd) new class `BinarySearchForAll` that consisted only of static methods, why didn’t we just subclass `ArrayList` and add the new methods to the subclass (leaving off the `ArrayList` parameters, of course)? What parts of your program would have broken?

## Grading

You will be graded based on the following criteria:


| Criterion                                | Points |
| :--------------------------------------- | :----- |
| `Term` class and comparables             | 3      |
| Search operations                        | 3     |
| `allMatches`                             | 2     |
| Load files and user interaction          | 3     |
| Thoroughness of test code                | 2      |
| Appropriate comments + JavaDoc           | 3      |
| Quality of code                          | 4      |


NOTE: Code that does not compile will not be accepted! Make sure that your code compiles before submitting it.


### Appendix A - Which Results to Display

Suppose you have typed only a few letters. If there were no limitations on completions displayed the system would end up
displaying hundreds of thousands of possible completions.

Given that only a limited number of completions
are displayed, how do you decide which ones to display? This is a difficult question for all searches.
For an early (and influential) answer, see the paper ["The Anatomy of a
Large-Scale Hypertextual Web Search Engine"]( http://ilpubs.stanford.edu:8090/361/) by Brin and Page, founders of Google.

Typically, the application depends on some historical data on how often certain completions are chosen,
and each completion is associated with a number representing its frequency or likelihood of being chosen.

Acknowledgment: This assignment is based on a similar exercise developed by Matthew Drabick and Kevin Wayne at Princeton University.
