import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

class NQueensSolver implements Runnable {
	public Thread t;
	private String threadName;

	public static ConcurrentHashMap<String, int[][]> solutions = new ConcurrentHashMap<String, int[][]>();
	public static ConcurrentHashMap<String, int[][]> middleSolutions = new ConcurrentHashMap<String, int[][]>();
	public static ConcurrentHashMap<Integer, String> assignments = new ConcurrentHashMap<Integer, String>();
	public static AtomicLong totalUnitsDone = new AtomicLong();
	public static AtomicBoolean canPrint = new AtomicBoolean(true);
	public static long lastOutput;
	private int numbersOfSquares;
	private int n;
	private long[] positionsOfQueens;
	private long configurationStart;
	private long configuration;
	private long configurationStop;
	private long totalNumUnits;
	private long UNIT = 1000000000000L;
	private long unitsDone;
	private boolean middle;

	NQueensSolver(int threadNum, int n, long configuration, long configurationStop, long totalNumConfigurations,
			boolean middle) {

		this.threadName = "solver" + threadNum;

		this.n = n;

		numbersOfSquares = n * n;

		positionsOfQueens = new long[n];

		this.configurationStart = configuration;

		this.configuration = configuration;

		this.configurationStop = configurationStop;

		this.totalNumUnits = totalNumConfigurations / UNIT;

		unitsDone = 0L;

		this.middle = middle;

		lastOutput = System.currentTimeMillis();

		assignments.put(threadNum, configuration + " - " + configurationStop);
	}

	public void run() {

		int[][] board;

		while (configuration < configurationStop) {

			configuration = getPositionsOfQueens(positionsOfQueens, configuration, numbersOfSquares);

			if (configuration < configurationStop) {

				board = positionQueens(positionsOfQueens, n);

				addSolution(board, solutions, middleSolutions, middle);
			}

			if (configuration > configurationStop) {
				configuration = configurationStop;
			}

			if (System.currentTimeMillis() - lastOutput > 300000) {
				if (canPrint.getAndSet(false)) {

					System.out.println(threadName + "  " + ((configuration - configurationStart) / UNIT) + " / "
							+ ((configurationStop - configurationStart) / UNIT));

					long currentTime = System.currentTimeMillis();
					lastOutput = currentTime;

					canPrint.set(true);
				}
			}

			if (unitsDone < (configuration - configurationStart) / UNIT) {
				if (canPrint.getAndSet(false)) {
					long moreUnitsDone = ((configuration - configurationStart) / UNIT) - unitsDone;

					for (long i = 0; i < moreUnitsDone; ++i) {
						++unitsDone;
						System.out.println(totalUnitsDone.addAndGet(1L) + " / " + totalNumUnits);
						// System.out.println(threadName + " " + ((configuration
						// - configurationStart) / UNIT) + " / "
						// + ((configurationStop - configurationStart) / UNIT));

						long currentTime = System.currentTimeMillis();
						System.out.println((currentTime - lastOutput) / 1000.0 + " Seconds");
						lastOutput = currentTime;
					}

					canPrint.set(true);
				}
			}

			++configuration;
		}

	}

	long getPositionsOfQueens(long[] positionsOfQueens, long configuration, int numSquares) {
		boolean done = false;

		while (!done) {

			done = true;

			positionsOfQueens[positionsOfQueens.length - 1] = configuration;

			for (int i = positionsOfQueens.length - 1; i > 0; --i) {
				positionsOfQueens[i - 1] = positionsOfQueens[i] / numSquares;
				positionsOfQueens[i] = positionsOfQueens[i] % numSquares;
			}

			HashSet<Integer> rows = new HashSet<Integer>();

			final int N = positionsOfQueens.length;

			int row, column;

			for (int i = 0; i < N; ++i) {
				
				row = (int) positionsOfQueens[i] / N;

				if (!rows.contains(row)) {
					rows.add(row);
				} else {
					column = (int) positionsOfQueens[i] % N;
					configuration += (N - column) * (long) Math.pow(N * N, N - (i + 1));
					rows.add(row + 1);
					done = false;
				}
			}

			if (done) {
				
				HashSet<Integer> columns = new HashSet<Integer>();

				for (int i = 0; i < N; ++i) {
					column = (int) positionsOfQueens[i] % N;

					if (!columns.contains(column)) {
						columns.add(column);
					} else {
						configuration += (long) Math.pow(N * N, N - (i + 1));
						columns.add(column + 1);
						done = false;
					}
				}
			}

			if (done) {
				
				HashSet<Integer> forwardDiagonals = new HashSet<Integer>();

				int diagonal;

				for (int i = 0; i < N; ++i) {
					diagonal = (int) positionsOfQueens[i] / N + (int) positionsOfQueens[i] % N;

					if (!forwardDiagonals.contains(diagonal)) {
						forwardDiagonals.add(diagonal);
					} else {
						configuration += (long) Math.pow(N * N, N - (i + 1));
						forwardDiagonals.add(diagonal + 1);
						done = false;
					}
				}
			}

			if (done) {
				
				HashSet<Integer> backwardDiagonals = new HashSet<Integer>();

				int diagonal;

				for (int i = 0; i < N; ++i) {
					diagonal = (int) positionsOfQueens[i] / N - (int) positionsOfQueens[i] % N;

					if (!backwardDiagonals.contains(diagonal)) {
						backwardDiagonals.add(diagonal);
					} else {
						configuration += (long) Math.pow(N * N, N - (i + 1));
						backwardDiagonals.add(diagonal - 1);
						done = false;
					}
				}
			}

		}

		return configuration;
	}

	int[][] positionQueens(long[] positionsOfQueens, int n) {
		int[][] board = new int[n][n];

		for (int i = 0; i < positionsOfQueens.length; ++i) {
			board[(int) positionsOfQueens[i] / board.length][(int) positionsOfQueens[i] % board.length] = 1;
		}

		return board;
	}

	void addSolution(int[][] board, ConcurrentHashMap<String, int[][]> solutions, ConcurrentHashMap<String, int[][]> middleSolutions, boolean middle) {

		if (!middle) {
			String key = generateKey(board);

			solutions.put(key, board);
		} else {
			String key = generateKey(board);

			middleSolutions.put(key, board);
		}

	}

	String generateKey(int[][] board) {
		String key = "";

		for (int i = 0; i < board.length; ++i) {

			key += Arrays.toString(board[i]);
		}

		return key;
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}

class NQueens {
	public static void main(String[] args) {

		long before = System.currentTimeMillis();

		System.out.println("Enter the number of queens");

		Scanner cin = new Scanner(System.in);

		final int N = cin.nextInt();

		cin.close();

		if (N % 2 == 0) {

			long configuration = 0L;

			// find the configuration where every queen is on it's own row and
			// column
			for (int i = 0; i < N; ++i) {

				configuration += (i * N + i) * (long) Math.pow(N * N, N - (i + 1));
			}

			// Once the Queen in the first position passes half way across the
			// first row, the
			// rest can be found by doubling the solutions found since the board
			// is symmetrical
			long configurationStop = (N / 2) * (long) Math.pow(N * N, N - 1);

			int numberOfThreads = 64;

			long configurationsPerThread = (configurationStop - configuration) / numberOfThreads;

			System.out.println("Number per thread - " + configurationsPerThread);
			System.out.println("Total configurations - " + (configurationStop - configuration));

			NQueensSolver[] solvers = new NQueensSolver[numberOfThreads];

			for (int i = 0; i < numberOfThreads; ++i) {
				if (i != numberOfThreads - 1) {
					solvers[i] = new NQueensSolver((i + 1), N, configuration + (configurationsPerThread * i),
							configuration + (configurationsPerThread * (i + 1)), configurationStop - configuration,
							false);
					solvers[i].start();
				} else {
					solvers[i] = new NQueensSolver((i + 1), N, configuration + (configurationsPerThread * i),
							configurationStop, configurationStop - configuration, false);
					solvers[i].start();
				}
			}

			for (int i = 0; i < numberOfThreads; ++i) {
				try {
					solvers[i].t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} else {

			long configuration = 0L;

			// find the configuration where every queen is on it's own row and
			// column
			for (int i = 0; i < N; ++i) {

				configuration += (i * N + i) * (long) Math.pow(N * N, N - (i + 1));
			}

			// Once the Queen in the first position reaches the middle column,
			// the other side of
			// the middle column can be found by doubling the solutions found on
			// the left side since the board is symmetrical
			long configurationStop = (N / 2) * (long) Math.pow(N * N, N - 1);

			long configurationMiddle = configurationStop;

			long configurationMiddleStop = ((N / 2) + 1) * (long) Math.pow(N * N, N - 1);

			int numberOfThreads = 64;

			int numberOfLeftThreads = numberOfThreads - numberOfThreads / 4;

			int numberOfCenterThreads = numberOfThreads / 4;

			long configurationsPerLeftThread = (configurationStop - configuration) / numberOfLeftThreads;

			long configurationsPerCenterThread = (configurationMiddleStop - configurationMiddle)
					/ numberOfCenterThreads;
			
			System.out.println("Number per left thread - " + configurationsPerLeftThread);
			System.out.println("Number per center thread - " + configurationsPerCenterThread);
			System.out.println("Total configurations - " + (configurationMiddleStop - configuration));

			NQueensSolver[] solvers = new NQueensSolver[numberOfThreads];

			for (int i = 0; i < numberOfLeftThreads; ++i) {
				if (i != numberOfLeftThreads - 1) {
					solvers[i] = new NQueensSolver((i + 1), N, configuration + (configurationsPerLeftThread * i),
							configuration + (configurationsPerLeftThread * (i + 1)),
							configurationMiddleStop - configuration, false);
					solvers[i].start();
				} else {
					solvers[i] = new NQueensSolver((i + 1), N, configuration + (configurationsPerLeftThread * i),
							configurationStop, configurationMiddleStop - configuration, false);
					solvers[i].start();
				}
			}

			for (int i = 0; i < numberOfCenterThreads; ++i) {
				if (i != numberOfCenterThreads - 1) {
					solvers[numberOfLeftThreads + i] = new NQueensSolver((numberOfLeftThreads + i + 1), N,
							configurationMiddle + (configurationsPerCenterThread * i),
							configurationMiddle + (configurationsPerCenterThread * (i + 1)),
							configurationMiddleStop - configuration, true);
					solvers[numberOfLeftThreads + i].start();
				} else {
					solvers[numberOfLeftThreads + i] = new NQueensSolver((numberOfLeftThreads + i + 1), N,
							configurationMiddle + (configurationsPerCenterThread * i), configurationMiddleStop,
							configurationMiddleStop - configuration, true);
					solvers[numberOfLeftThreads + i].start();
				}
			}

			for (int i = 0; i < numberOfThreads; ++i) {
				try {
					solvers[i].t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		/*
		 * ArrayList<Integer> keys =
		 * Collections.list(NQueensSolver.assignments.keys());
		 * Collections.sort(keys); for (int i = 0; i < keys.size(); ++i) {
		 * System.out.println(keys.get(i) + " " +
		 * NQueensSolver.assignments.get(keys.get(i))); }
		 */

		System.out.println("Number of solutions - "
				+ ((NQueensSolver.solutions.size() * 2) + NQueensSolver.middleSolutions.size()));

		System.out.println();

		ArrayList<String> boardKeys = Collections.list(NQueensSolver.solutions.keys());

		Collections.sort(boardKeys);

		for (int i = 0; i < boardKeys.size(); ++i) {

			int[][] board = NQueensSolver.solutions.get(boardKeys.get(i));

			for (int j = 0; j < board.length; ++j) {

				System.out.println(Arrays.toString(board[j]));
			}

			System.out.println();
		}

		boardKeys = Collections.list(NQueensSolver.middleSolutions.keys());

		Collections.sort(boardKeys);

		for (int i = 0; i < boardKeys.size(); ++i) {

			int[][] board = NQueensSolver.middleSolutions.get(boardKeys.get(i));

			for (int j = 0; j < board.length; ++j) {

				System.out.println(Arrays.toString(board[j]));
			}

			System.out.println();
		}

		boardKeys = Collections.list(NQueensSolver.solutions.keys());

		Collections.sort(boardKeys);

		for (int i = 0; i < boardKeys.size(); ++i) {

			int[][] board = NQueensSolver.solutions.get(boardKeys.get(i));

			for (int j = 0; j < board.length; ++j) {
				String[] reversedArray = new StringBuffer(
						Arrays.toString(board[j]).substring(1, Arrays.toString(board[j]).length() - 1)).reverse()
								.toString().split(" ,");

				System.out.println("[" + String.join(", ", reversedArray) + "]");
			}

			System.out.println();
		}

		System.out.println("Seconds " + (System.currentTimeMillis() - before) / 1000.0);

	}
}