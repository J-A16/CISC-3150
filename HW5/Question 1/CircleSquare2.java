import java.util.SplittableRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

class RunnableDemo implements Runnable {
	private Thread t;
	private String threadName;
	private int numberOfPoints;
	private int numberOfThreads;
	private int pointsInCircle;
	final private static long NUMBER_OF_POINTS = 4000000000L;
	private static AtomicInteger threadsDone = new AtomicInteger();
	private static AtomicLong totalPointsInCircle = new AtomicLong();
	private long before;
	private double pointX, pointY;
	private SplittableRandom rand;
	private long seed;

	RunnableDemo(String name, int numPoints, int numThreads, long before) {
		threadName = name;
		numberOfPoints = numPoints;
		numberOfThreads = numThreads;
		pointsInCircle = 0;
		this.before = before;
		rand = new SplittableRandom();
		seed = rand.nextLong();
	}

	public void run() {
		
		for (int i = 0; i < numberOfPoints; i = i + 1) {
			
			seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
			
			pointX = (seed << 5) / (double)(1L << 53);
			
			seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
			
			pointY = (seed << 5) / (double)(1L << 53);

			pointsInCircle = pointsInCircle +
					(((pointX * pointX) + (pointY * pointY) <= 1.0) ? 1 : 0);
			
		}

		totalPointsInCircle.addAndGet(pointsInCircle);
		
		if (threadsDone.incrementAndGet() == numberOfThreads) {
			System.out.println((totalPointsInCircle.get() / (double) NUMBER_OF_POINTS) * 4);
			System.out.println(System.currentTimeMillis() - before);
		}
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}

public class TestThread {

	public static void main(String args[]) {
		long before = System.currentTimeMillis();
		int numberOfThreads = 64;
		long NUMBER_OF_POINTS = 4000000000L;
		int pointsPerThread = (int)(NUMBER_OF_POINTS / numberOfThreads);
		
		RunnableDemo[] testers = new RunnableDemo[numberOfThreads];
		
		for (int i = 0; i < numberOfThreads; ++i) {
			testers[i] = new RunnableDemo("Thread-" + (i + 1), pointsPerThread, numberOfThreads, before);
			testers[i].start();
		}
	}
}
