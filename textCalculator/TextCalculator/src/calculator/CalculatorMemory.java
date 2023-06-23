package calculator;
import java.util.ArrayList;

import java.util.Stack;

public class CalculatorMemory {
	
	public ArrayList<Integer> stack= new ArrayList<Integer>();
	
	// Add the number to memory.
	public void push(int number) {
		stack.add(number);
	}
	//Return and remove the most recently pushed value
	public int pop() {
		if (stack.size() == 0) {
			throw new IllegalArgumentException();
		}
			
		return stack.remove(stack. size()-1);
	}
	//checks to see if stack is emtpy 
	public boolean isEmpty() {
		return stack.size() == 0;
	}
	public int size() {
		return stack.size();
	}
	//remove everything form the memory
	public void clear() {
		stack.clear();
	}
	
	public String toString() {
		String output = "";
		
		for (int i = stack.size() -1; i > -1; i--) {
			output += stack.get(i) + "\n";
		}
		output += ("----");
		return output; 
}
}
