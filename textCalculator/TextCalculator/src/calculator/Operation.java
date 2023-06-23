package calculator; 

public class Operation{

	public static int performOperation(char op, int left, int right) {
		
		
	try {
		
		int result = 0;
		
		if (op == '+') {
			result =  left + right;
		}
		
		else if (op == '-') {
			result = left - right;
		}
		
		else if (op == '/') {
			result = left / right;
		}
		
		else if (op == '*') {
			result =  left * right; 
		}
		return result;
		
	}
		
	catch (IllegalArgumentException  e) {
		throw e;

	} catch(ArithmeticException x ) {
		throw x;
	}
	
		
	} 
	
}