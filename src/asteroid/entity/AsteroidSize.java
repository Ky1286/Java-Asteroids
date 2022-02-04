package asteroid.entity;

import java.awt.Polygon;

public enum AsteroidSize {
    Small(15.0, 100),
    Medium(25.0, 50),
    Large(40.0, 20);

    private static final int NUM_OF_POINTS = 5;
    public final Polygon polygon;
    public final double radius;
    public final int killValue;

    private AsteroidSize(double radius, int value) {
        this.polygon = generatePolygon(radius);
        this.radius = radius + 1.0;
        this.killValue = value;
    }

    private static Polygon generatePolygon(double radius) {
        int[] x = new int[NUM_OF_POINTS];
        int[] y = new int[NUM_OF_POINTS];
        double angle = (2 * Math.PI / NUM_OF_POINTS);
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            x[i] = (int) (radius * Math.sin(i * angle));
            y[i] = (int) (radius * Math.cos(i * angle));
        }
        return new Polygon(x, y, NUM_OF_POINTS);
    }
}