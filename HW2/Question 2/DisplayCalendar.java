import java.util.Scanner;
import java.io.PrintStream;
import java.util.GregorianCalendar;

public class DisplayCalendar {

	public static void main(String[] args) {

		Scanner cin = new Scanner(System.in);

		System.out.println("Please enter the year and what day the first of January fell on");
		System.out.println("using 1 through 7 for Sunday through Saturday For example:");
		System.out.println("1976 3");
		System.out.println("Which would be 1976 with the first day of January being a Tuesday");

		String year = cin.next();
		int startDay = cin.nextInt();
		int endDay;

		int NUMBER_OF_MONTHS = 12;

		String[] namesOfMonths = { "    January %4s    %n", "   Feburary %4s    %n", "     March %4s     %n",
				"     April %4s     %n", "      May %4s      %n", "     June %4s      %n", "     July %4s      %n",
				"    August %4s     %n", "   September %4s   %n", "    October %4s    %n", "   November %4s    %n",
				"   December %4s    %n" };

		String dayFormat = "%2s %2s %2s %2s %2s %2s %2s%n";

		int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		GregorianCalendar cal = new GregorianCalendar();
		
		if(cal.isLeapYear(Integer.parseInt(year))){
			daysInMonths[1]++;
		}
		
		for (int i = 0; i < NUMBER_OF_MONTHS; i++) {
			System.out.printf(namesOfMonths[i], year);
			System.out.printf(dayFormat, "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa");
			int dayInMonth = 0;

			while (daysInMonths[i] - dayInMonth > 0) {
				endDay = 7;
				
				String sunday = "";
				String monday = "";
				String tuesday = "";
				String wednesday = "";
				String thursday = "";
				String friday = "";
				String saturday = "";
				
				if(daysInMonths[i] - dayInMonth < 7){
					endDay = daysInMonths[i] % dayInMonth;
				}
				
				switch (startDay) {
				case 1:
					sunday = Integer.toString(++dayInMonth);
				case 2:
					if(endDay < 2) break;
					monday = Integer.toString(++dayInMonth);;
				case 3:
					if(endDay < 3) break;
					tuesday = Integer.toString(++dayInMonth);;
				case 4:
					if(endDay < 4) break;
					wednesday = Integer.toString(++dayInMonth);;
				case 5:
					if(endDay < 5) break;
					thursday = Integer.toString(++dayInMonth);;
				case 6:
					if(endDay < 6) break;
					friday = Integer.toString(++dayInMonth);;
				case 7:
					if(endDay < 7) break;
					saturday = Integer.toString(++dayInMonth);;
				}
				
				if (endDay == 7){
					startDay = 1;
				}else{
					startDay = endDay + 1;
				}
				
				System.out.printf(dayFormat, sunday, monday, tuesday, wednesday, thursday, friday, saturday);
			}
		}
		
		cin.close();
	}

}