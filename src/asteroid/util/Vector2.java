package asteroid.util;

public class Vector2 {

    public double x;
    public double y;

    public Vector2(double angle) {
        this.x = Math.cos(angle);
        this.y = Math.sin(angle);
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public Vector2 set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2 add(Vector2 vec) {
        this.x += vec.x;
        this.y += vec.y;
        return this;
    }

    public Vector2 scale(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2 normalize() {
        double length = getLengthSquared();
        if (length != 0.0f && length != 1.0f) {
            length = Math.sqrt(length);
            this.x /= length;
            this.y /= length;
        }
        return this;
    }

    public double getLengthSquared() {
        return (x * x + y * y);
    }

    public double getDistanceToSquared(Vector2 vec) {
        double dx = this.x - vec.x;
        double dy = this.y - vec.y;
        return (dx * dx + dy * dy);
    }
}