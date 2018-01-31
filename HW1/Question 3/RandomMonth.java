import java.util.Random;

public class RandomMonth {

	public static void main(String[] args) {

		String[] months = {"January","February","March","April","May","June",
				"July","August","September","October","November","December"};
		
		Random rand = new Random();
		
		int numberOfMonth = rand.nextInt(12) + 1;
		
		System.out.println(months[numberOfMonth - 1]);
		
	}
}
