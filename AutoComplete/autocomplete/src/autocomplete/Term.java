package autocomplete;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Term implements Comparable<Term> {
	private String query;
	private long weight;

	/**
	 * Initializes a term with the given query string and weight.
	 * 
	 * @param query
	 *            word to be stored
	 * @param weight
	 *            associated frequency
	 */
	public Term(String query, long weight) {
		this.query = query;
		this.weight = weight;
	}
	
	// adding getter methods for weight and query 
	public String getQuery() {
		return this.query;
	}
	public long getWeight() {
		return this.weight;
	}
	/**
	 * @return comparator ordering elts by descending weight
	 */
	public static Comparator<Term> byReverseWeightOrder() {
		 return (Term a, Term b) -> {
			 if (a.weight > b.weight)
				return -1;
			else if (a.weight < b.weight) 
				return 1;
			else 
				return 0;
		 };
	}

	/**
	 * @param r
	 *            Number of initial characters to use in comparing words
	 * @return comparator using lexicographic order, but using only the first r
	 *         letters of each word
	 */
	public static Comparator<Term> byPrefixOrder(int r) {
			return (Term a, Term b) -> {
				Term c;
				Term d;
				if (a.query.length() < r) 
					c = a;
				else
					c = new Term(a.query.substring(0, r), a.weight);
				if (b.query.length() < r) 
					d = b;
				else
					d = new Term(b.query.substring(0,r), b.weight);
				return c.query.compareTo(d.query);
		};
	}

	/**
	 * @param that
	 *            Term to be compared
	 * @return -1, 0, or 1 depending on whether the word for THIS is
	 *		   lexicographically smaller, same or larger than THAT
	 */
	public int compareTo(Term that) {
		if (query.compareTo(that.query) > 0) 
			return 1;
		else if (query.compareTo(that.query) < 0)
			return -1;
		else
			return 0;
	}
	/**
	 * @return a string representation of this term in the following format: the
	 *         weight, followed by 2 tabs, followed by the word.
	 **/
	public String toString() {
		return (this.weight + "\t" + "\t" + this.query);
	}
	
	//tester
	public static void main (String[] args) {
		Term a = new Term ("clue", 100);
		Term b = new Term ("ace", 250);
		Term c = new Term ("blank", 500);
		Term d = new Term ("blinker", 10);
		
		ArrayList<Term> terms = new ArrayList<Term>(Arrays.asList(a, b, c, d));
		
		 System.out.println("Before sort.");
	     for (int i =0; i< terms.size(); i++) {
	    	 System.out.println(terms.get(i));
	     }

        Collections.sort(terms);
        System.out.println("\nAfter sort with 'compareTo'.");
        for (int i =0; i< terms.size(); i++) {
	    	 System.out.println(terms.get(i));
	     }

        Collections.sort(terms, Term.byReverseWeightOrder());
        System.out.println("\nAfter sort with 'byReverseWeightOrder'.");
        for (int i =0; i< terms.size(); i++) {
	    	 System.out.println(terms.get(i));
	     }

        Collections.sort(terms, Term.byPrefixOrder(3));
        System.out.println("\nAfter sort with 'byPrefixOrder'.");
        for (int i =0; i< terms.size(); i++) {
	    	 System.out.println(terms.get(i));
	     }
	}
}