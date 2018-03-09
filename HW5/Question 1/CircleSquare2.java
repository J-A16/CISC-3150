import java.util.SplittableRandom;
import java.util.concurrent.atomic.AtomicLong;

class RunnableDemo implements Runnable {
	public Thread t;
	private String threadName;
	private int numberOfPoints;
	private int pointsInCircle;
	public static AtomicLong totalPointsInCircle = new AtomicLong();
	private double pointX, pointY;
	private SplittableRandom rand;
	private long seed;

	RunnableDemo(String name, int numPoints) {
		threadName = name;
		numberOfPoints = numPoints;
		pointsInCircle = 0;
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
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}

public class CircleSquare2 {

	public static void main(String args[]) {
		long before = System.currentTimeMillis();
		int numberOfThreads = 64;
		long NUMBER_OF_POINTS = 4000000000L;
		int pointsPerThread = (int)(NUMBER_OF_POINTS / numberOfThreads);
		
		RunnableDemo[] testers = new RunnableDemo[numberOfThreads];
		
		for (int i = 0; i < numberOfThreads; ++i) {
			testers[i] = new RunnableDemo("Thread-" + (i + 1), pointsPerThread);
			testers[i].start();
		}
		
		for (int i = 0; i < numberOfThreads; ++i) {
			try {
				testers[i].t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println((RunnableDemo.totalPointsInCircle.get() / (double) NUMBER_OF_POINTS) * 4);
		System.out.println(System.currentTimeMillis() - before);
	}
}
