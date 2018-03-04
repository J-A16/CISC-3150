import java.math.BigInteger;

class CircleSquare {
	public static void main(String[] args) {
		long before = System.currentTimeMillis();

		final double RADIUS = 5.0;

		final double RADIUS_SQUARED = Math.pow(RADIUS, 2);

		final BigInteger NUMBER_OF_POINTS = new BigInteger("4000000000");

		double pointX, pointY;

		BigInteger pointsInCircle = BigInteger.ZERO;

		for (BigInteger i = BigInteger.ZERO; i.compareTo(NUMBER_OF_POINTS) < 0; i = i.add(BigInteger.ONE)) {

			pointX = Math.random() * RADIUS;
			pointY = Math.random() * RADIUS;

			if (Math.pow(pointX, 2) + Math.pow(pointY, 2) <= RADIUS_SQUARED) {
				pointsInCircle = pointsInCircle.add(BigInteger.ONE);
			}
		}

		System.out.println(((double) pointsInCircle.floatValue() / NUMBER_OF_POINTS.floatValue()) * 4);

		System.out.println(System.currentTimeMillis() - before);
	}
}