import java.util.Scanner;

public class StateOfCircles {

	public static void main(String[] args) {

		Scanner cin = new Scanner(System.in);

		System.out.println("Please enter the enter the center coordinates and radiuses of two circles");
		System.out.println("in the form: xC1 yC1 r1 xC2 yC2 r2");

		double xC1, yC1, r1, xC2, yC2, r2, distanceBetweenCenters;

		xC1 = cin.nextDouble();

		yC1 = cin.nextDouble();

		r1 = cin.nextDouble();

		xC2 = cin.nextDouble();

		yC2 = cin.nextDouble();

		r2 = cin.nextDouble();

		distanceBetweenCenters = distanceBetweenPoints(xC1, yC1, xC2, yC2);
		
		if(distanceBetweenCenters > r1 + r2){
			System.out.println("Circles are separate from each other");
		} else if (distanceBetweenCenters - (r1 + r2) < 0.0001){
			System.out.println("Circles are touching");
		} else if (distanceBetweenCenters + r1 > r2 && distanceBetweenCenters + r2 > r1){
			System.out.println("Circles are overlapping");
		} else {
			System.out.println("Circles are within one another");
		}
		
		cin.close();
	}
	
	static double distanceBetweenPoints(double x1, double y1, double x2, double y2){
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
}
