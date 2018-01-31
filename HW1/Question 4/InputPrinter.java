import java.util.Scanner;

public class InputPrinter {

	public static void main(String[] args) {

		Scanner cin = new Scanner(System.in);

		while (cin.hasNextLine()) {
			
			String nextLine = cin.nextLine();

			System.out.println(nextLine);
		}

		cin.close();
	}
}
