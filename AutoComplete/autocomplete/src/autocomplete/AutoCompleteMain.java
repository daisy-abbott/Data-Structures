package autocomplete;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AutoCompleteMain {
	
	public static void main (String[] args) {
		int matches = Integer.parseInt(args[0]);
		String fileName = args[1];
		// initialize list
		ArrayList<Term> terms = new ArrayList<Term>();
		try {
			//read file, and next line
			Scanner scanner = new Scanner(new File(fileName), "utf-8");
			String numLines = scanner.nextLine();
			// parse to int
			int intLines =Integer.parseInt(numLines); 
			
			//loop through lines, create term, add to list
			for (int i = 1; i <= intLines; i++) {
				String line = scanner.nextLine();
				line.trim();
				
				String[] stringList = new String[2]; 
				
				stringList = line.split(" ");
				
				Long weight = Long.parseLong(stringList[0]); 
				Term term = new Term(stringList[1], weight); 
				
				terms.add(term);
			}
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		
		Scanner userInput = new Scanner(System.in);
		System.out.println("Enter prefix: ");
		
		String prefix  = userInput.nextLine();
		// loop to keep asking for prefix
		while (!prefix.equals("-1")) {
			// finds all matching keys / weights
			Autocomplete autocomplete = new Autocomplete(terms);
			List<Term> listMatches = autocomplete.allMatches(prefix);
			// prints num of matches
			System.out.println("There are " + listMatches.size() + " matches.");
			//sorts list by reverse weight, prints them 
			Collections.sort(listMatches, Term.byReverseWeightOrder());
			
			// if list with mathces is less than user input matches
			if (listMatches.size() < matches) {
				System.out.println("The matching items are:");
				for (int n = 0; n < listMatches.size(); n++) {
					System.out.println(listMatches.get(n));
				}
			}
			else {
				System.out.println("The " + matches + " largest are:");
				for (int j = 0; j <= matches; j++) {
					System.out.println(listMatches.get(j));	
				}
			}
			System.out.println("Enter a new prefix:");
			prefix = userInput.nextLine();
		}
		userInput.close();
	}

}