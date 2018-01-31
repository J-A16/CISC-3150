import java.util.Scanner;

public class TriangleAnalyser {

	public static void main(String[] args) {

		Scanner cin = new Scanner(System.in);

		System.out.println("Please enter the x and y coordinates of the 3 vertices of the triangle");
		System.out.println("in the form: x1 y1 x2 y2 x3 y3");

		double x1, y1, x2, y2, x3, y3, side1, side2, side3;

		x1 = cin.nextDouble();

		y1 = cin.nextDouble();

		x2 = cin.nextDouble();

		y2 = cin.nextDouble();
		
		x3 = cin.nextDouble();

		y3 = cin.nextDouble();

		side1 = distanceBetweenPoints(x1, y1, x2, y2);
		
		side2 = distanceBetweenPoints(x2, y2, x3, y3);
		
		side3 = distanceBetweenPoints(x1, y1, x3, y3);
		
		if(side1 + side2 > side3 && side1 + side3 > side2 && side2 + side3 > side1){
			System.out.println("Triangle Valid");
		} else {
			System.out.println("Triangle Invalid");
		}
		
		cin.close();
	}
	
	static double distanceBetweenPoints(double x1, double y1, double x2, double y2){
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
}
