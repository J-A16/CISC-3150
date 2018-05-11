import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MultithreadedAlphabetPrinter {
	public static void main(String[] args) {

		OffsetHolder offsetHolder = new OffsetHolder();

		Printer first = new Printer(offsetHolder, 1, 4, "C:\\Users\\Pleasure Cruise\\Desktop\\output.txt");
		Printer second = new Printer(offsetHolder, 2, 4);
		Printer third = new Printer(offsetHolder, 3, 4);
		Printer fourth = new Printer(offsetHolder, 4, 4);

		first.start();
		second.start();
		third.start();
		fourth.start();
	}
}

class OffsetHolder {
	volatile int offset = 0;
}

class Printer extends Thread {
	static PrintWriter out;
	OffsetHolder offsetHolder;
	int threadNumber;
	int numberOfThreads;
	static int LETTERS_IN_THE_ALPHABET = 26;

	Printer(OffsetHolder offsetHolder, int threadNumber, int numberOfThreads, String fileName) {
		this.offsetHolder = offsetHolder;
		this.threadNumber = threadNumber;
		this.numberOfThreads = numberOfThreads;

		try {
			out = new PrintWriter(new FileWriter(fileName), true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
	Printer(OffsetHolder offsetHolder, int threadNumber, int numberOfThreads) {
		this.offsetHolder = offsetHolder;
		this.threadNumber = threadNumber;
		this.numberOfThreads = numberOfThreads;
	}

	@Override
	public void run() {

		try {
			synchronized (offsetHolder) {

				while (offsetHolder.offset < LETTERS_IN_THE_ALPHABET) {

					while (offsetHolder.offset < LETTERS_IN_THE_ALPHABET
							&& offsetHolder.offset % numberOfThreads != threadNumber - 1) {
						offsetHolder.wait();
					}

					if (offsetHolder.offset < LETTERS_IN_THE_ALPHABET) {
						System.out.println("Thread " + threadNumber + ": "
								+ (char) ('a' + offsetHolder.offset));
						out.println("Thread " + threadNumber + ": "
								+ (char) ('a' + offsetHolder.offset));
						
						offsetHolder.offset++;
						offsetHolder.notifyAll();
					}
				}
			}
		} catch (Exception e) {
		}
	}
}