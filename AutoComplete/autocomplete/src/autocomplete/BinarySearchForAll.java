package autocomplete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class BinarySearchForAll {
	// flag indicating whether a key occurs at all in the list
	public static final int NOT_FOUND = -1;

	/**
	 * Returns the index of the first element in aList that equals key
	 *
	 * @param aList
	 *            Ordered (via comparator) list of items to be searched
	 * @param key
	 *            item searching for
	 * @param comparator
	 *            Object with compare method corresponding to order on aList
	 * @return Index of first item in aList matching key or -1 if not in aList
	 **/
	public static <Key> int firstIndexOf(List<Key> aList, Key key,
			Comparator<Key> comparator) {
		
			int lo = 0;
			int hi = aList.size();
			int mid;

			while (lo <= hi) {
				mid =  (hi + lo) / 2;
				if (comparator.compare(key, aList.get(mid)) == 0) {
					if (mid == 0 || comparator.compare(key, aList.get(mid -1)) != 0){
						return mid;
					}
					else
						hi = mid -1;
				}
		
				else {
					// look at right child 
					if (comparator.compare(key, aList.get(mid)) > 0) {
						lo = mid + 1;
					}
					else if (lo == mid) {
						return mid;
					}
					// look at left child 
					else {
						hi = mid - 1 ; 
					}
				}
			}
			//System.out.println("ntohing");
		return NOT_FOUND; 
	}

	/**
	 * Returns the index of the last element in aList that equals key
	 * 
	 * @param aList
	 *            Ordered (via comparator) list of items to be searched
	 * @param key
	 *            item searching for
	 * @param comparator
	 *            Object with compare method corresponding to order on aList
	 * @return Location of last item of aList matching key or -1 if no such key.
	 **/
	public static <Key> int lastIndexOf(List<Key> aList, Key key,
			Comparator<Key> comparator) {
		

		int lo = 0;
		int hi = aList.size() - 1;
		int mid;
	
		while (lo <= hi) {
			mid = (lo + hi) / 2;
			if (comparator.compare(key, aList.get(mid)) == 0) {
				if(mid == aList.size()-1 || comparator.compare(key, aList.get(mid + 1)) != 0) {
					return mid;
				}
				else {
					lo = mid +1;
				}
			}
			else {
				if (mid == hi) {
					return mid;
				}
				// look at right child 
				else if (comparator.compare(key, aList.get(mid)) > 0) {
					lo = mid + 1;
				}
				// look at left child 
				else {
					hi = mid - 1; 
				}
				//mid = (lo + hi) /2;
			}
		}
		
		return NOT_FOUND; 	
	}

	public static void main (String[] args) {
		Term a = new Term ("clue", 100);
		Term b = new Term ("ace", 250);
		Term c = new Term ("blank", 500);
		Term d = new Term ("blinker", 10);
		//Term x = new Term(a.toString(), 1); // 
		
		ArrayList<Term> terms = new ArrayList<Term>(Arrays.asList(a, a, b, a, a, b, c, d, d, a));
		Collections.sort(terms, Term.byPrefixOrder(3));
		System.out.println("\nAfter sort with 'byPrefixOrder'.");
        for (int i =0; i< terms.size(); i++) {
	    	 System.out.println(terms.get(i));
	     }
//        
        
        //terms.add(x);
        //System.out.println("hello" + x);
		
		Comparator<Term> newComparator = Term.byPrefixOrder(3);
		//System.out.println(firstIndexOf(terms, x, newComparator));
		System.out.println(firstIndexOf(terms, a, newComparator));
		System.out.println(lastIndexOf(terms, a, newComparator));
		System.out.println(firstIndexOf(terms, b, newComparator));
		System.out.println(lastIndexOf(terms, b, newComparator));
		System.out.println(firstIndexOf(terms, c, newComparator));
		System.out.println(lastIndexOf(terms, c, newComparator));
		System.out.println(firstIndexOf(terms, d, newComparator));
		System.out.println(lastIndexOf(terms, d, newComparator));
		
	}
}