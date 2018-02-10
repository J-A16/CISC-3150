import java.util.Scanner;

public class TokenizeCharacters {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);

		cin.useDelimiter("");

		while (cin.hasNext()) {

			String next = cin.next();

			System.out.println(next);
		}

		cin.close();
	}

}