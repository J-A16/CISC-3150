import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

class NQueensSolver implements Runnable {
	public Thread t;
	private String threadName;

	public static ConcurrentHashMap<String, int[][]> solutions = new ConcurrentHashMap<String, int[][]>();
	public static ConcurrentHashMap<String, int[][]> middleSolutions = new ConcurrentHashMap<String, int[][]>();
	public static ConcurrentHashMap<Integer, String> assignments = new ConcurrentHashMap<Integer, String>();
	private int numbersOfSquares;
	private int n;
	private long configuration;
	private long configurationStop;
	private long[] positionsOfQueens;
	private boolean middle;

	NQueensSolver(int threadNum, int n, long configuration, long configurationStop, boolean middle) {

		this.threadName = "solver" + threadNum;

		this.n = n;

		numbersOfSquares = n * n;

		positionsOfQueens = new long[n];

		this.configuration = configuration;

		this.configurationStop = configurationStop;

		this.middle = middle;

		assignments.put(threadNum, configuration + " - " + configurationStop);
	}

	public void run() {

		int[][] board;

		while (configuration < configurationStop) {

			configuration = getPositionsOfQueens(positionsOfQueens, configuration, numbersOfSquares);

			if (configuration < configurationStop) {

				board = positionQueens(positionsOfQueens, n);

				addSolution(board, solutions, middle);
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
					configuration += (N - column) * Math.pow(N * N, N - (i + 1));
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
						configuration += Math.pow(N * N, N - (i + 1));
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
						configuration += Math.pow(N * N, N - (i + 1));
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
						configuration += Math.pow(N * N, N - (i + 1));
						backwardDiagonals.add(diagonal + 1);
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

	void addSolution(int[][] board, ConcurrentHashMap<String, int[][]> solutions, boolean middle) {

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

				configuration += (i * N + i) * Math.pow(N * N, N - (i + 1));
			}

			// Once the Queen in the first position passes half way across the
			// first row, the
			// rest can be found by doubling the solutions found since the board
			// is symmetrical
			long configurationStop = (N / 2) * (long) Math.pow(N * N, N - 1);

			int numberOfThreads = 64;

			long configurationsPerThread = (configurationStop - configuration) / numberOfThreads;

			NQueensSolver[] solvers = new NQueensSolver[numberOfThreads];

			for (int i = 0; i < numberOfThreads; ++i) {
				if (i != numberOfThreads - 1) {
					solvers[i] = new NQueensSolver((i + 1), N, configuration + (configurationsPerThread * i),
							configuration + (configurationsPerThread * (i + 1)), false);
					solvers[i].start();
				} else {
					solvers[i] = new NQueensSolver((i + 1), N, configuration + (configurationsPerThread * i),
							configurationStop, false);
					solvers[i].start();
				}
			}

			for (int i = 0; i < numberOfThreads; ++i) {
				try {
					solvers[i].t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else {

			long configuration = 0L;

			// find the configuration where every queen is on it's own row and
			// column
			for (int i = 0; i < N; ++i) {

				configuration += (i * N + i) * Math.pow(N * N, N - (i + 1));
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

			long configurationsPerThread = (configurationStop - configuration) / numberOfLeftThreads;

			long configurationsPerCenterThread = (configurationMiddleStop - configurationMiddle)
					/ numberOfCenterThreads;

			NQueensSolver[] solvers = new NQueensSolver[numberOfThreads];

			for (int i = 0; i < numberOfLeftThreads; ++i) {
				if (i != numberOfLeftThreads - 1) {
					solvers[i] = new NQueensSolver((i + 1), N, configuration + (configurationsPerThread * i),
							configuration + (configurationsPerThread * (i + 1)), false);
					solvers[i].start();
				} else {
					solvers[i] = new NQueensSolver((i + 1), N, configuration + (configurationsPerThread * i),
							configurationStop, false);
					solvers[i].start();
				}
			}

			for (int i = 0; i < numberOfCenterThreads; ++i) {
				if (i != numberOfCenterThreads - 1) {
					solvers[numberOfLeftThreads + i] = new NQueensSolver((numberOfLeftThreads + i + 1), N,
							configurationMiddle + (configurationsPerCenterThread * i),
							configurationMiddle + (configurationsPerCenterThread * (i + 1)), true);
					solvers[numberOfLeftThreads + i].start();
				} else {
					solvers[numberOfLeftThreads + i] = new NQueensSolver((numberOfLeftThreads + i + 1), N,
							configurationMiddle + (configurationsPerCenterThread * i), configurationMiddleStop, true);
					solvers[numberOfLeftThreads + i].start();
				}
			}

			for (int i = 0; i < numberOfThreads; ++i) {
				try {
					solvers[i].t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		ArrayList<Integer> keys = Collections.list(NQueensSolver.assignments.keys());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); ++i) {
			System.out.println(keys.get(i) + " " + NQueensSolver.assignments.get(keys.get(i)));
		}

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
				String[] reversedArray = new StringBuffer(Arrays.toString(board[j]).substring(1, Arrays.toString(board[j]).length() - 1)).reverse().toString().split(" ,");
				
				System.out.println("[" + String.join(", ", reversedArray) + "]");
			}

			System.out.println();
		}

		System.out.println("Seconds " + (System.currentTimeMillis() - before) / 1000.0);

	}
}