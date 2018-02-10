import java.util.Scanner;

public class PalindromeChecker {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		
		System.out.println("Please enter a string to check if it is a palindrome");
		
		String toBeChecked = cin.next();
		
		int front = 0, back = toBeChecked.length() - 1;
		
		boolean palindrome = true;
		
		while(front < back){
			
			if(toBeChecked.charAt(front) != toBeChecked.charAt(back)){
				palindrome = false;
			}
			front++;
			back--;
		}
		
		if(palindrome){
			System.out.println("String is a palindrome");
		}else{
			System.out.println("String is not a palindrome");
		}
		
		cin.close();
	}
}