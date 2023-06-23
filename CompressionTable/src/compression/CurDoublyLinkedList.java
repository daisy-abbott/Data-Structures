package compression;

import java.util.ArrayList;

/**
 * @author Brisa Salazar & Daisy Abbott
 */

/**
 * Implementation of a doubly linked list that contains an additional pointer to
 * the "current" node. This enables get/insert/delete operations relative to the
 * current position.
 *
 * There is also a notion of having gone "off" the list (e.g., by going next from
 * the last (right of the tail) or back from the first element (left of the head)).
 */

public class CurDoublyLinkedList<E> extends DoublyLinkedList<E> {

	protected DoublyLinkedList<E>.Node current; // pointer to current node

	private boolean off_left; // current has been shifted off left edge (left from head of doubly linked list)
	private boolean off_right; // current has been shifted off right edge (right from tail of doubly linked					// list)

	/**
	 * @post: constructs an empty list, current points to null, off states are false // testing
	 */
	public CurDoublyLinkedList() {
		current = null; 
		off_left = false;  
		off_right = false;
	}

	/**
	 * set current to the first element of list (head)
	 *
	 * @pre: list is non-empty
	 *
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       cannot move current to head"
	 * 
	 * @post: current set to first node of list (head), off states are false
	 *
	 */
	public void first() {	
		if (this.n > 0) {
			current = this.first;
			off_left = false;
			off_right = false;
		}
		else {
			throw new IllegalStateException("Empty list, cannot move current to head");
		}
	}

	/**
	 * set current to last element of list (tail)
	 *
	 * @pre: list is non-empty
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       cannot move current to tail"
	 * 
	 * 
	 * @post: current set to last node of list (tail), off states are false
	 *
	 */
	public void last() {
		if (!isEmpty()) {
			current = this.last;
			off_left = false;
			off_right = false;
		}
		else {
			throw new IllegalStateException("Empty list, cannot move current to tail");
		}
	}

	/**
	 * Move current pointer one node to the right
	 *
	 * @pre: list is non-empty and off right state is false
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       cannot move current to the right"
	 * 
	 *       throws IllegalStateException if off right state is true with message
	 *       "Current is already off right, cannot move it further"
	 * 
	 * @post: if the off left state is true, then current points to head and off
	 *        left becomes false. Else move current pointer one node to the right.
	 *        If already at tail, current points to null and off right state becomes
	 *        true.
	 *
	 */
	public void next() {
		if (!isEmpty() && !off_right) {
			if (off_left) {
				current = this.first;
				off_left = false;
			}
			// when current is set to the last node, you know next node will be empty 
			else if (current == this.last) {
				current = null;
				off_right = true;
			}
			// change current pointer 
			else {	
				current = current.next;
			}	
		}
		else if (isEmpty())
			throw new IllegalStateException("Empty list, cannot move current to the right");
		else if (off_right)
			throw new IllegalStateException("Current is already off right, cannot move it further");
	}
	
	/**
	 * Move current pointer one node to the left
	 *
	 * @pre: list is non-empty and off left state is false
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       cannot move current to the left"
	 * 
	 *       throws IllegalStateException if off left state is true with message
	 *       "Current is already off left, cannot move it further"
	 * 
	 * @post: if the off right state is true, then current points to tail and off
	 *        right becomes false. Else move current pointer one node to the left.
	 *        If already at head, current points to null and off left state becomes
	 *        true.
	 */
	public void back() {

		if (!isEmpty() && !off_left) {
			if (off_right) {
				current = this.last;
				off_right = false;
			}
			else {
				// if current in the first node, then there is nothing before it 
				if (current == this.first) {
					current = null;
					off_left = true;
				}
				// change the current pointer 
				else {
					current = current.prev;
				}
			} 
		}
		else if (isEmpty()) {
			throw new IllegalStateException("Empty list, cannot move to the left");
		}
		
		else if (off_left){
			throw new IllegalStateException("Current is already off left, cannot move it farther.");
		}
		
		
	}

	/**
	 * Check whether current pointer is off the right side of the list (right of the
	 * tail)
	 * 
	 * @return whether current is off right side of list
	 */ // or could i just return off right?
	public boolean isOffRight() {
		return off_right;
	}

	/**
	 * Check whether current pointer is off the left side of the list (left of the
	 * head)
	 * 
	 * @return whether current is off left side of list
	 */
	public boolean isOffLeft() {
		return off_left;
	}

	/**
	 * Check whether current pointer is off the right side of the list (right of the
	 * tail) or off the left side of the list (left of the head)
	 * 
	 * @return whether current is either off left or off right side of list
	 */
	public boolean isOff() {
		return (isOffLeft() || isOffRight());
	}

	/**
	 * Returns the value of the node that current points to
	 *
	 * @pre: list is non-empty and current is not off list
	 *
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 * 
	 *       throws IllegalStateException if current is off list with message
	 *       "Current is off list"
	 * 
	 */
	public E currentValue() {
		if (!isEmpty() && !isOff()) {
			return current.item;
		}
		else if (isEmpty()) {
			throw new IllegalStateException("Empty list, current points to null");
		}
		else {
			throw new IllegalStateException("Current is off list");
		}
	}
	
	/**
	 * Create a new node with specified value and make it the new head. Move current
	 * pointer to point to the newly-created node.
	 *
	 * @pre: Given value for new node to be created is not null
	 * 
	 *       throws IllegalArgumentException if given value is null "Cannot create a
	 *       node that contains the null value"
	 *
	 * @post: creates a new node with specified element and makes it the new head.
	 *        Upon creation, current now points to the newly-created node. Off left
	 *        and off right states are set to false.
	 * 
	 */
	public void addFirst(E newFirst) {
		if (newFirst != null) {
			super.addFirst(newFirst); // this creates a new node in the super class
			current = super.first; // set current pointer to the first 
			off_left = false; 
			off_right = false;
		}
		else {
			throw new IllegalArgumentException("Cannot create a node that contains the null value");
		}
	}

	/**
	 * Create a new node with specified value and make it the new tail. Move current
	 * pointer to point to the newly-created node.
	 *
	 * @pre: Given value for new node to be created is not null
	 * 
	 *       throws IllegalArgumentException if given value is null "Cannot create a
	 *       node that contains the null value"
	 *
	 * @post: creates a new node with specified element and makes it the new tail.
	 *        Upon creation, current now points to the newly-created node. Off left
	 *        and off right states are set to false.
	 * 
	 */
	public void addLast(E newLast) {
		if (newLast != null) {
			super.addLast(newLast); //this creates a new node at the end of super linked list 
			current =this.last;  // sets current to the newly added element 
			off_left = false;
			off_right = false;
		}
		else {
			throw new IllegalArgumentException("Cannot create a node that contains the null value");
		}
	}

	/**
	 * Remove the head node and return its value. Current now points to the new
	 * head.
	 *
	 * @pre: list is non-empty.
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 *
	 * @post: removes the head node and returns its value. Current now points either
	 *        to the new head if the list has at least one node (and therefore the
	 *        off left and right state should be false), or points to null with the
	 *        off left state becoming true.
	 * 
	 */
	public E removeFirst() {
		if (!isEmpty()) {
			// set pointer to the element after the first 
			current = this.first.next;
			off_left = false;
			off_right = false;
			// removes the head node 
			return super.removeFirst();
		}
		else 
			throw new IllegalStateException("Empty list, current points to null");
	}

	/**
	 * Remove the tail node and return its value. Current now points to null and off
	 * right becomes true (off left is false)
	 * 
	 * @pre: list is non-empty.
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 *
	 * @post: removes the tail node and returns its value. Current now points to
	 *        null with the off right state becoming true (and off left becoming
	 *        false).
	 * 
	 */
	public E removeLast() {
		if (!isEmpty()) {
			
			current = null;
			off_right = true;
			off_left = false;
			return super.removeLast();
		}
		else 
			throw new IllegalStateException("Empty list, current points to null");
	}

	/**
	 * Return value of the head and point current to head
	 *
	 * @pre: list is non-empty
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 * 
	 * @post: points current to first element of list (head). Off states are false
	 *
	 */
	public E getFirst() {
		if(!isEmpty()) {
			E item =  super.get(0); 
			current = this.first; // current points to first node 
			off_left = false;
			off_right = false;
			return item;
		}
		else {
			throw new IllegalStateException("Empty list, current points to null");
		}
		
	}

	/**
	 * Return value of the tail and point current to tail
	 *
	 * @pre: list is non-empty
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 * 
	 * @post: points current to last element of list (tail). Off states are false
	 *
	 */
	public E getLast() {
		if (!isEmpty()) { 
			current = this.last; // current points to last node 
			off_left = false;
			off_right = false;
			return last.item;
		}
		else {
			throw new IllegalStateException("Empty list, curent points to null");
		}
	}

	/**
	 * Create a new node with specified value immediately after the current node.
	 * Move current pointer to point to the newly-created node.
	 *
	 * @pre: Given value for new node to be created is not null, list is non-empty,
	 *       and current is not off list
	 * 
	 *       throws IllegalArgumentException if given value is null "Cannot create a
	 *       node that contains the null value"
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 * 
	 *       throws IllegalStateException if current is off list with message
	 *       "Current is off list"
	 *
	 * @post: creates a new node with specified element and adds it right after the
	 *        node that current points to. Upon creation, current now points to the
	 *        newly-created node.
	 * 
	 */
	public void addAfterCurrent(E value) {
		if (!isEmpty() && value != null && current != null) {
			// gets index of the node next to current, so that the new Node can be added
			int index = getIndex(current.item);
			// if current points to last node then we add a value to the end of the list 
			if (current == super.last)
				addLast(value);
			// add node w/ value after current 
			else {
				super.add(index + 1, value);
				current = current.next;
			}
		}
		else if (value == null) {
			throw new IllegalArgumentException("Cannot create a node that contains a null value");
		}
		else if (isEmpty()) {
			throw new IllegalStateException("Empty list, current points to null");
		}
		else {
			throw new IllegalStateException("Current is off list");
		}
	}

	/**
	 * Removes the node that current points to. Current now points to the successor.
	 *
	 * @pre: List is non-empty and current is not off list
	 * 
	 * 
	 *       throws IllegalStateException if list is empty with message "Empty list,
	 *       current points to null"
	 * 
	 *       throws IllegalStateException if current is off list with message
	 *       "Current is off list"
	 *
	 * @post: Removes the node that current points to and moves current to its
	 *        successor
	 */
	public void removeCurrent() {
		if (!isEmpty() && current != null) {
			int curIndex = getIndex(current.item);
			current = current.next;
			super.remove(curIndex);
		}
		else if (isEmpty()) {
			throw new IllegalStateException("Empty list, current points to null");
		}
		else  {
			throw new IllegalStateException("Current is off list");
		}
	}

	/**
	 * Clear doubly linked list and reset current pointer and off states
	 */
	public void clear() {
		// Hint: look at the parent class methods
		super.clear();
		current = null;
		off_left = false;
		off_right = false;
	}

	/**
	 * @return readable representation of table
	 * 
	 *         Shows contents of underlying list rather than all elements of the
	 *         table.
	 */
	public String toString() { // do not change
		return super.toString() + "\nCurrent is " + current;
	}

	/**
	 * An alternative representation of the object
	 * 
	 * @return a string representation of the list, with each element on a new line.
	 */
	public String otherString() { // do not change
		DoublyLinkedList<E>.Node finger = first;
		StringBuilder ans = new StringBuilder("CurDoublyLinkedList:\n");
		while (finger != null) {
			ans.append(finger + "\n");
			finger = finger.next;
		}
		return ans.toString();
	}
}
