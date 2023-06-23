
package calculator;
/* Calculator application implementing Stacks through ArrayList Data Structure.
 * @author Daisy Abbott 
 */

// two public methods below and at least one private helper method
import java.util.Scanner;


public class Calculator{
	private CalculatorMemory memory;
	private Operation operation;
	private String validInts;
	private String validOps;
	
	// 0 parameter constructor 
	public Calculator() {
		memory = new CalculatorMemory();
		validInts = "123456789";
		validOps = "+-*/";
	}

	// check input is num
	/*
	 * Method checks to see if input is a valid num
	 */
	public boolean isValid(String num) {
		
		int count = 0;
		
		for (int i = 0; i < num.length();  i++) {
			if (validInts.contains(num.subSequence(i, i+1))) {
			count++;
			}
		
		}
		
		return count == num.length();
	}
		
	// prompts user for input
	public void run() {
		
		// termination key word
		String end = "quit";
		Scanner scanner = new Scanner(System.in);
		String userInput = "";
		
		// keep asking until quit
		while (!userInput.equals(end)) {
			System.out.println("Enter a number or operator:");
			userInput = scanner.nextLine();
			
			// terminate key word
			if (userInput.equals("quit"))
					break;
			
			// checks empty space or return
			else if (userInput.equals("")) {
				System.out.println("Expected number or operator");
			}
			
			// if number, push onto stack 
			else if (isValid(userInput)) {
				int intInput = Integer.parseInt(userInput);
				memory.push(intInput);
				
			}
				// if operation, push onto stack 
			else if (validOps.contains(userInput)){
				
				// check to make sure enough arguments to operate on
				if (memory.size() < 2) {
					System.out.println("Error: operator requires two arguments");	
				}
				else {
					// pop first two, grab op char 
					char op = userInput.charAt(0);
					int firstNum = memory.pop();
					int secNum = memory.pop();
					
					// push back onto stack
					try {
						int result = Operation.performOperation(op, secNum, firstNum);
							memory.push(result);
							System.out.println("Answer: " + result);
						}
					
						
					catch (ArithmeticException e) {
						System.out.println("Error: Divide by zero");
						memory.push(secNum);
						memory.push(firstNum);
					}
						
					}
					
					}
			
			// clear stack
			else if(userInput.equals("clear")){
				memory.clear();
			}
			
			// pop from top of stack 
			else if (userInput.equals("pop")) {
				if (memory.isEmpty()) {
					System.out.println("Error: pop requires at one argument");
					
				} else {
					System.out.println("Answer: " + memory.pop());
				}
			}
			
			// anything else
			else {
				System.out.println("Error: expected number or operator");
				
				}
			// print contents
			System.out.println("\nMemory contents: ");
			System.out.println(memory);
			System.out.println();
		}
		
			scanner.close();
		}
	
	
	
	
	public static void main (String[] args) { 
	    ;
		
		Calculator calculator = new Calculator();
	    calculator.run();
	}
	
}

