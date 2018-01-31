import java.util.Scanner;

public class CircleArea {

	public static void main(String[] args) {

		Scanner cin = new Scanner(System.in);

		System.out.println("Please enter the radius of the circle and then press enter to");
		System.out.println("calculate it's area. Press Ctrl + z and then enter to quit.");

		while (cin.hasNext()) {

			if(cin.hasNextDouble()){
				System.out.println(Math.PI * Math.pow(cin.nextDouble(), 2));
				
			} else {
				cin.next();
				
				System.out.println("Please enter a radius and then press enter to calculate the");
				System.out.println("circle's area or press Ctrl + z and then enter to quit.");
			}
		}

		cin.close();
	}
}
