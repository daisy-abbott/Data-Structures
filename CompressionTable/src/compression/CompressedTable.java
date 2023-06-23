/* Class representing an efficient implementation of a 2-dimensional table 
 * when lots of repeated entries as a doubly linked list. Idea is to record entry only when a 
 * value changes in the table as scan from left to right through 
 * successive rows.
 * 
 * @author Brisa Salazar &  Daisy Abbott 
 * @param <E> type of value stored in the table
 */
package compression;

class CompressedTable<E> implements TwoDTable<E> {
	// List holding table entries - do not change
	// We've made the variables protected to facilitate testing (grading)
	protected CurDoublyLinkedList<Association<RowOrderedPosn, E>> tableInfo;
	protected int numRows, numCols; // Number of rows and cols in table
	
	protected RowOrderedPosn posn;
	protected Association<RowOrderedPosn, E> posnValue;

	/**
	 * Constructor for table of size rows x cols, all of whose values are initially
	 * set to defaultValue
	 * 
	 * @param rows
	 *            # of rows in table
	 * @param cols
	 *            # of columns in table
	 * @param defaultValue
	 *            initial value of all entries in table
	 */
	public CompressedTable(int rows, int cols, E defaultValue) {
		// assigns table size through num rows and cols that user passes in
		this.numRows = rows;
		this.numCols = cols;
		// creates new position upper left hand corner with fixed rows and cols
		posn = new RowOrderedPosn(0, 0, numRows, numCols);
		// creates value that holds this position (0,0) 
		posnValue = new Association<RowOrderedPosn, E>(posn, defaultValue);
		// creates doubly linked list holding values for compression table
		tableInfo = new CurDoublyLinkedList<Association<RowOrderedPosn, E>>();
		// adds info to table 
		tableInfo.add(posnValue);
	}

	/**
	 * Given a (x, y, rows, cols) RowOrderedPosn object, it searches for it in the
	 * table which is represented as a doubly linked list with a current pointer. If
	 * the table contains the (x,y) cell, it sets the current pointer to it.
	 * Otherwise it sets it to the closest cell in the table which comes before that
	 * entry.
	 * 
	 * e.g., if the table only contains a cell at (0,0) and you pass the cell (3,3)
	 * it will set the current to (0,0).
	 */
	private void find(RowOrderedPosn findPos) {
		tableInfo.first();
		Association<RowOrderedPosn, E> entry = tableInfo.currentValue();
		RowOrderedPosn pos = entry.getKey();
		while (!findPos.less(pos)) {
			// search through list until pass elt looking for
			tableInfo.next();
			if (tableInfo.isOff()) {
				break;
			}
			entry = tableInfo.currentValue();
			pos = entry.getKey();
		}
		tableInfo.back(); // Since passed desired entry, go back to it.
	}

	/**
	 * Given a legal (row, col) cell in the table, update its value to newInfo. 
	 * 
	 * @param row
	 *            row of cell to be updated
	 * @param col
	 *            column of cell to be update
	 * @param newInfo
	 *            new value to place in cell (row, col)
	 */
	
	public void updateInfo(int row, int col, E newInfo) {
		RowOrderedPosn updatedPosn = new RowOrderedPosn(row, col, numRows, numCols); // posn being updated
		Association<RowOrderedPosn, E> updatedPosnValue = new Association<>(updatedPosn, newInfo); // association of updatedPosn
		RowOrderedPosn firstPos = new RowOrderedPosn(0, 0, numRows, numCols);
		RowOrderedPosn lastPos = new RowOrderedPosn (numRows -1, numCols -1, numRows, numCols);
		
		find(updatedPosn); // will set current pointer to position in table (if in table) or position before updatedPosn if not in table 
		Association<RowOrderedPosn, E > currentValue = tableInfo.currentValue();
		
		if (row < numRows && col < numCols) {
			// if  position we want to update is equal to the position we are currently in
			if (currentValue.getKey().equals(updatedPosn)) {
				// check if currently held info is same as new info
				if (!currentValue.getValue().equals(newInfo)){
					if (!updatedPosn.equals(lastPos) && !updatedPosn.equals(firstPos)) {
						// save old info
						E oldInfo = currentValue.getValue();
						currentValue.setValue(newInfo);
						Association<RowOrderedPosn, E> newOld = new Association<>(updatedPosn.next(), oldInfo);
						// if tableInfo.next != position.next
						tableInfo.next();
						
						if (!tableInfo.isOffRight()) {
							// check if posns are the same 
							if (!tableInfo.currentValue().getKey().equals(updatedPosn.next())) {
								
								tableInfo.addAfterCurrent(newOld);
							} 
							else {
								// if not the same, checks if values are same and removes if so
								
								if (tableInfo.currentValue().getValue().equals(updatedPosnValue.getValue())) {
									tableInfo.removeCurrent();
									tableInfo.back(); // now points to updated posn
									tableInfo.back(); // now points to previous node
									// if not off, check if previous is same, if so remove
									if (!tableInfo.isOffLeft()) {
										if (tableInfo.currentValue().getValue().equals(updatedPosnValue.getValue())) {
											tableInfo.next();
											tableInfo.removeCurrent();
											tableInfo.back();
										}
									}
								}
								else {
									//point to updated node add old info after
									tableInfo.back();
									tableInfo.addAfterCurrent(newOld);
									
								}
							}
						}
						// if offRight 
						else {
							
							tableInfo.back();
							
							if (!updatedPosn.equals(lastPos))
								tableInfo.addAfterCurrent(newOld);
								tableInfo.back(); // points to updated
								
								if (!tableInfo.isOffLeft()) {
									tableInfo.back(); // now points to previous before updated
									// removes if same
									if (!tableInfo.isOffLeft()) {
										if (tableInfo.currentValue().getValue().equals(updatedPosnValue.getValue())) {
											tableInfo.next();
											tableInfo.removeCurrent();
											tableInfo.back();
										}
									}
									// moves back on table
									else {
										tableInfo.next();
									}
									
								}
								// moves back on table
								else {
									tableInfo.next();
								}
								
						}
					}
					//HEAD 
					else if (updatedPosn.equals(firstPos)){
						// save old info
						E savedInfo = currentValue.getValue();
						tableInfo.currentValue().setValue(newInfo);
						tableInfo.next(); // moves to next node
						
						if (!tableInfo.isOffRight()) {
							//if a node DOES NOT exists right after updated posn
							if (!tableInfo.currentValue().getKey().equals(updatedPosn.next())) {
								Association<RowOrderedPosn, E> savedNode = new Association<>(updatedPosn.next(), savedInfo);
								tableInfo.back();
								tableInfo.addAfterCurrent(savedNode);
								
							}
							// if node DOES exist, checks if same, removes 
							else {
								if (tableInfo.currentValue().getValue().equals(updatedPosnValue.getValue())) {
									tableInfo.removeCurrent();
									tableInfo.back();
								}
							}
						}
						else {
							tableInfo.back();
						}
					}
					else { // TAIL
						currentValue.setValue(newInfo);
						tableInfo.back(); // sets pointer to node before the node we updated
						if (!tableInfo.isOffLeft()) {
							if (tableInfo.currentValue().getValue().equals(updatedPosnValue.getValue())){
								tableInfo.remove(updatedPosnValue);
								tableInfo.back();
							}
						} 
						// moves back on table
						else 
							tableInfo.next();
				}
			}
			}
			// else ( if there is no node at position we want to update)
			else {
				//save node value
				E savedNodeInfo = tableInfo.currentValue().getValue();
				// add a node at the position we want to update w new info 
				tableInfo.addAfterCurrent(updatedPosnValue); 
				// move pointer back to previous node 
				tableInfo.back();
		
				if (!tableInfo.isOffLeft()) {
					// if current node value == updated posn value
					if (tableInfo.currentValue().getValue().equals(updatedPosnValue.getValue())) {
						tableInfo.remove(updatedPosnValue); // remove the node we just added
						tableInfo.back();
					}
					else {
						//move pointer forward to updated node 
						tableInfo.next();
						tableInfo.next(); // moves pointer to node after updated node 
						if (!tableInfo.isOffRight()) {
							// if the position of the next node is the same as the posiiton following updated node 
							if (tableInfo.currentValue().getKey().equals(updatedPosn.next())) {
								// check if the values of the nodes are the same 
								if (tableInfo.currentValue().getValue().equals(updatedPosnValue.getValue())) {
									tableInfo.removeCurrent();
									tableInfo.back();
								}
							}
							else {
								// add node with savednodeInfo in the position after updatedPosn
								tableInfo.back(); // move pointer to updatedPosnValue 
								Association <RowOrderedPosn, E> savedNode = new Association<> (updatedPosn.next(), savedNodeInfo);
								tableInfo.addAfterCurrent(savedNode);
								// check if just added node is the same value as successor  
								tableInfo.next(); // moves pointer to node after node w old Info
								if (tableInfo.currentValue().getValue().equals(savedNode.getValue())) {
									tableInfo.removeCurrent();
								}
							}
						}
						// we know there is no node after the one we updated 
						else {
							tableInfo.back(); // points us back to updated node 
							tableInfo.back(); // points to node before updated node 
						
							// check if the node we updated is NOT the last position in the table 
							if (!updatedPosn.equals(lastPos)) {
								E oldInfo = tableInfo.currentValue().getValue();
								Association<RowOrderedPosn, E> newOld = new Association<>(updatedPosn.next(), oldInfo);
								// if not last position, then get old info and add after updated node 
								tableInfo.next(); // point back to updated node 
								tableInfo.addAfterCurrent(newOld);
					
							}
						}
					}
				}
				// else node is offLeft, so we need to go back to the head
				else {
					tableInfo.next(); // points to head 
				}
			}
			
			
		}		
			
			
	}
	
	/**
	 * Returns contents of specified cell
	 * 
	 * @pre: (row,col) is legal cell in table
	 * 
	 * @param row
	 *            row of cell to be queried
	 * @param col
	 *            column of cell to be queried
	 * @return value stored in (row, col) cell of table
	 */
	public E getInfo(int row, int col) {
		// create new position with argument information and finds it.
		
			RowOrderedPosn getPos = new RowOrderedPosn(row, col, numRows, numCols);    
			find(getPos); // sets current pointer to getPos	
			return tableInfo.currentValue().getValue();
		
	}

	/**
	 *  @return
	 *  		 succinct description of contents of table
	 */
	public String toString() { // do not change
	    return tableInfo.otherString();
	}

	public String entireTable() { //do not change
		StringBuilder ans = new StringBuilder("");
		for (int r = 0; r<numRows; r++) {
			for (int c = 0; c< numCols; c++) {
				ans.append(this.getInfo(r, c));
			}
			ans.append("\n");
		}
		return ans.toString();

	}

	/**
	 * program to test implementation of CompressedTable
	 * @param args
	 * 			ignored, as not used in main
	 */
	public static void main(String[] args) {
		
		// add your own tests to make sure your implementation is correct!!
		CompressedTable<String> table = new CompressedTable<String>(3, 3, "a");
		System.out.println("table is " + table);
		// tests for new node 
		table.updateInfo(2, 2, "z");
		System.out.println("table is " + table);
		
		table.updateInfo(2, 1, "z");
		System.out.println("table is " + table);
		
		table.updateInfo(1, 2, "z");
		System.out.println("table is " + table);
		
		table.updateInfo(0,  0 , "z");
		System.out.println("table is " + table);
		
		//test to update already existing node in table 
		table.updateInfo(0, 1, "x");
		System.out.println("table is " + table);	
		
		// test to update head 
		table.updateInfo(0, 0, "a");
		System.out.println("table is " + table);
		
		// test to update tail when there is no node in tail 
		table.updateInfo(4, 5, "a");
		System.out.println("table is " + table);
		
		// test to update tail when there is a node in tail 
		table.updateInfo(4, 5, "x");
		System.out.println("table is " + table);
		
		// test for two redundant nodes 
		table.updateInfo(3, 5, "x");
		System.out.println("table is " + table);
		
		// test out of bounds 
		table.updateInfo(7, 7, "a");
		System.out.println("table is " + table);
		
		//test getInfo there is a node w info (should return non default val)
		System.out.println(table.getInfo(0, 1));
		// test getInfop when no node (should return default val)
		System.out.println(table.getInfo(0, 2));
				
		//test for getInfo when pos is not valid - WORKS
		System.out.println(table.getInfo(7, 7));
				
			
		
	}

}
