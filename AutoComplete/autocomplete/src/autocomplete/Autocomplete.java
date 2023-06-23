package autocomplete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Autocomplete implements AutocompleteInterface { // should implement the AutocompleteInterface 
	public List<Term> terms;
	
	public Autocomplete(List<Term> list) {
		this.terms = list;
		Collections.sort(terms);
	}
	/**
	 * @param prefix
	 *            string to be matched
	 * @return List of all matching terms in descending order by weight
	 */
public List<Term> allMatches(String prefix){
		int r = prefix.length();
		List<Term> prefixList = new ArrayList<Term>();
		Term term = new Term(prefix, 1);
		int firstIndex = BinarySearchForAll.firstIndexOf(terms, term, Term.byPrefixOrder(r));
		int lastIndex = BinarySearchForAll.lastIndexOf(terms, term, Term.byPrefixOrder(r));
		System.out.println(firstIndex);
		System.out.println(lastIndex);
		
		// check if in list (not -1)
		if (firstIndex >= 0) {
			// if matching, add to prefixList
			for (int i = firstIndex; i <= lastIndex; i++) {
				prefixList.add(terms.get(i));
			}
			// sort by reverse weight
			Collections.sort(prefixList, Term.byReverseWeightOrder());
		}
		// return list of matches 
		return prefixList;
	}


	public static void main (String[] args) {
		List<Term> lst = new ArrayList<Term>();
		lst.add(new Term("hello", 7));
		lst.add(new Term("goodrye", 99));
		lst.add(new Term("ice", 47));
		lst.add(new Term("goodie", 12));
		lst.add(new Term("goodrye", 45));
		lst.add(new Term("goodrye", 45));
		lst.add(new Term("zebra", 2));
		
		Autocomplete auto = new Autocomplete(lst);
		String key = "ze";
		List<Term> matches = auto.allMatches(key);
		System.out.println("Matching "  + key);
		
		for (Term elt : matches) {
			System.out.println(elt);
		}

	}

}