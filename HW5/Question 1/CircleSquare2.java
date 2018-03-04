import java.math.BigInteger;

class CircleSquare2 {
	public static void main(String[] args) {
		long before = System.currentTimeMillis();

		final double RADIUS = 5.0;

		final double RADIUS_SQUARED = RADIUS * RADIUS;

		final int ONE_BILLION_POINTS = 1000000000;

		final int NUMBER_OF_BILLIONS = 4;

		double pointX, pointY;

		int[] pointsInCircleEachBillion = new int[NUMBER_OF_BILLIONS];

		for (int i = 0; i < NUMBER_OF_BILLIONS; i++) {
			for (int j = 0; j < ONE_BILLION_POINTS; j++) {

				pointX = Math.random() * RADIUS;
				pointY = Math.random() * RADIUS;

				if ((pointX * pointX) + (pointY * pointY) <= RADIUS_SQUARED) {
					++pointsInCircleEachBillion[i];
				}
			}
		}

		double pointsInCircle = 0;
		
		for (int i = 0; i < NUMBER_OF_BILLIONS; i++) {
			pointsInCircle += pointsInCircleEachBillion[i];
		}

		System.out.println((pointsInCircle / ((double)ONE_BILLION_POINTS * NUMBER_OF_BILLIONS)) * 4);

		System.out.println(System.currentTimeMillis() - before);
	}
}