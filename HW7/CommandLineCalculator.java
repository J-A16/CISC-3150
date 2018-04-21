//import java.io.File;
//import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class CommandLineCalculator {
	public static void main(String[] args) {
		Scanner cin = null;
/*
		try {
			cin = new Scanner(new File("C:\\Users\\Pleasure Cruise\\Desktop\\input.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
*/
		System.out.println("Please enter the mathematical expression");
		
		cin = new Scanner(System.in);
		
		String expression = cin.nextLine();

		cin.close();

		try {
			checkExpression(expression);
		} catch (IllegalArgumentException e) {
			if (e instanceof AlgebraFailException) {
				System.out.println("You entered something else where a number belonged");
				System.exit(0);
			} else if (e instanceof QuitMashingOnYourKeyboardException) {
				System.out.println("You entered something else where an operator belonged");
				System.exit(0);
			} else if (e instanceof UserIsADumbassException) {
				System.out.println("You are missing a number");
				System.exit(0);
			}
		}

		System.out.println(solveExpression(expression));
	}

	static void checkExpression(String expression) throws IllegalArgumentException {

		HashSet<String> operators = new HashSet<>();

		operators.add("*");
		operators.add("/");
		operators.add("+");
		operators.add("-");

		boolean numberExpected = true;

		Scanner cin = new Scanner(expression);

		while (cin.hasNext()) {

			String part = cin.next();

			if (numberExpected) {
				if (part.compareTo("(") == 0) {
					continue;
				} else if (isNum(part)) {
					numberExpected = false;
				} else {
					System.out.println(part);
					cin.close();
					throw new AlgebraFailException();
				}
			} else {
				if (part.compareTo(")") == 0) {
					continue;
				} else if (operators.contains(part)) {
					numberExpected = true;
				} else {
					System.out.println(part);
					cin.close();
					throw new QuitMashingOnYourKeyboardException();
				}
			}
		}

		cin.close();
		
		if (numberExpected) {
			throw new UserIsADumbassException();
		}
	}

	static double solveExpression(String expression) {
		HashMap<String, Integer> precedence = new HashMap<>();

		precedence.put("*", 2);
		precedence.put("/", 2);
		precedence.put("+", 1);
		precedence.put("-", 1);

		ArrayDeque<Double> numbersStack = new ArrayDeque<>();

		ArrayDeque<String> operatorsStack = new ArrayDeque<>();

		String[] parts = expression.split(" ");
		
		for (int i = 0; i < parts.length; i++) {

			String part = parts[i];

			if (part.compareTo("(") == 0) {
				
				i = solveExpressionInParentheses(i, parts, part, numbersStack);
				
			} else if (isNum(part)) {
				
				numbersStack.push(Double.parseDouble(part));
				
			} else if (precedence.containsKey(part)) {
				
				if (!operatorsStack.isEmpty() && precedence.get(part) <= precedence.get(operatorsStack.peek())) {
					double secondNum = numbersStack.pop();
					double firstNum = numbersStack.pop();
					String operator = operatorsStack.pop();

					numbersStack.push(performOperation(firstNum, secondNum, operator));
					
					if (!operatorsStack.isEmpty()){
						secondNum = numbersStack.pop();
						firstNum = numbersStack.pop();
						operator = operatorsStack.pop();

						numbersStack.push(performOperation(firstNum, secondNum, operator));
					}
					
					operatorsStack.push(part);
				} else {
					operatorsStack.push(part);
				}
			} else {
				System.out.println(part);
				System.out.println("Invalid input");
				System.exit(0);
			}
		}
		
		while(!operatorsStack.isEmpty()){
			double secondNum = numbersStack.pop();
			double firstNum = numbersStack.pop();
			String operator = operatorsStack.pop();

			numbersStack.push(performOperation(firstNum, secondNum, operator));
		}
		
		return numbersStack.pop();
	}
	
	static int solveExpressionInParentheses(int i, String[] parts, String part, ArrayDeque<Double> numbersStack) {
		int numberOfUnclosedParenthesis = 1;
		String expressionInParentheses = "";

		for (int j = i + 1; j < parts.length; j++) {
			part = parts[j];

			if (part.compareTo("(") == 0) {
				numberOfUnclosedParenthesis++;
			} else if (part.compareTo(")") == 0) {
				numberOfUnclosedParenthesis--;
			}

			if (numberOfUnclosedParenthesis == 0) {
				numbersStack.push(solveExpression(
						expressionInParentheses.substring(0, expressionInParentheses.length() - 1)));
				i = j;
				break;
			} else {
				expressionInParentheses += part + " ";
			}
		}
		
		if(numberOfUnclosedParenthesis > 0) {
			System.out.println("Missing closing Parenthesis");
			System.exit(0);
		}
		
		return i;
	}
	
	static double performOperation(double firstNum, double secondNum, String operator) {
		double result = 0;
		
		if (operator.compareTo("*") == 0) {
			result = firstNum * secondNum;
		} else if (operator.compareTo("/") == 0) {
			try {
				result = firstNum / secondNum;
			} catch (ArithmeticException e) {
				System.out.println("You tried to divide by 0");
				System.exit(0);
			}

		} else if (operator.compareTo("+") == 0) {
			result = firstNum + secondNum;
		} else if (operator.compareTo("-") == 0) {
			result = firstNum - secondNum;
		}
		
		return result;
	}

	static boolean isNum(String part) {

		try {
			Double.parseDouble(part);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
}

class AlgebraFailException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
}

class QuitMashingOnYourKeyboardException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
}

class UserIsADumbassException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
}