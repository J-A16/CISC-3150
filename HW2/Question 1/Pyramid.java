import java.util.Scanner;
import java.io.PrintStream;

public class Pyramid {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);

		System.out.println("Please enter the height of the pyramid you would like");

		int height = cin.nextInt();

		int numLeadingSpaces = 2 * (height - 1);

		String leadingSpaces = "";
		String pyramidNums;
		String format = "%s%s%n";

		for (int i = 0; i < numLeadingSpaces; i++) {
			leadingSpaces += " ";
		}
		
		for (int i = 0; i < height; i++) {
			pyramidNums = "1";

			for (int j = 2; j <= height - (height - (i + 1)); j++) {
				pyramidNums += " " + j;
			}

			for (int j = height - (height - (i)); j > 0; j--) {
				pyramidNums += " " + j;
			}

			System.out.printf(format, leadingSpaces, pyramidNums);

			if (leadingSpaces.length() >= 2) {
				leadingSpaces = leadingSpaces.substring(2);
			}
		}
		
		cin.close();
	}
}