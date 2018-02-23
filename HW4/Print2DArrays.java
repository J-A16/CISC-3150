import java.util.Scanner;

public class Print2DArrays {

	public static void main(String[] args) {

		System.out.println("Enter the 2d array");

		Scanner cin = new Scanner(System.in);

		int numArrays = cin.nextInt();

		char[][] chars = new char[numArrays][];

		int[] numCharsEachArray = new int[chars.length];

		getChars(cin, chars, numCharsEachArray);

		cin.close();

		int numberOfCombos = 1;

		for (int i = 0; i < numCharsEachArray.length; ++i) {
			numberOfCombos *= numCharsEachArray[i];
		}

		int combo = 0;

		int[] indexNextChars = new int[chars.length];

		while (combo < numberOfCombos) {

			getNextIndexes(indexNextChars, combo, numCharsEachArray);

			printNextCombo(chars, indexNextChars);
			
			combo++;
		}
	}

	static void getChars(Scanner cin, char[][] chars, int[] numCharsEachArray) {

		for (int i = 0; i < chars.length; ++i) {
			numCharsEachArray[i] = cin.nextInt();

			chars[i] = new char[numCharsEachArray[i]];

			for (int j = 0; j < numCharsEachArray[i]; ++j) {
				chars[i][j] = cin.next().charAt(0);
			}
		}
	}

	static void getNextIndexes(int[] indexNextChars, int combo, int[] numCharsEachArray) {

		indexNextChars[indexNextChars.length - 1] = combo;

		for (int i = indexNextChars.length - 1; i > 0; --i) {
			indexNextChars[i - 1] = indexNextChars[i] / numCharsEachArray[i];
			indexNextChars[i] = indexNextChars[i] % numCharsEachArray[i];
		}
	}

	static void printNextCombo(char[][] chars, int[] indexNextChars) {

		for (int i = 0; i < chars.length; ++i) {
			System.out.print(chars[i][indexNextChars[i]]);
		}

		System.out.println();
	}
}