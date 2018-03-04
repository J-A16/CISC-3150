
class CircleSquare2 {
	public static void main(String[] args) {
		long before = System.currentTimeMillis();

		final double RADIUS = 5.0;

		final double RADIUS_SQUARED = RADIUS * RADIUS;

		final int NUMBER_OF_POINTS = 1000000000;

		double pointX, pointY;

		int pointsInCircle = 0;

		for (int i = 0; i < NUMBER_OF_POINTS; i++) {

			pointX = Math.random() * RADIUS;
			pointY = Math.random() * RADIUS;

			if ((pointX * pointX) + (pointY * pointY) <= RADIUS_SQUARED) {
				++pointsInCircle;
			}
		}

		System.out.println(((double) pointsInCircle / NUMBER_OF_POINTS) * 4);

		System.out.println(System.currentTimeMillis() - before);
	}
}