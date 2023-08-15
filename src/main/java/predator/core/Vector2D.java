package predator.core;

@SuppressWarnings("unused")
public class Vector2D {
    public float x;
    public float y;

    public Vector2D subtract(Vector2D v) {
        Vector2D newVector = new Vector2D();
        newVector.x = x - v.x;
        newVector.y = y - v.y;
        return newVector;
    }

    public Vector2D add(Vector2D v) {
        Vector2D newVector = new Vector2D();
        newVector.x = x + v.x;
        newVector.y = y + v.y;
        return newVector;
    }

    public double distanceSquared(Vector2D v) {
        Vector2D subtractVector = subtract(v);
        return Math.pow(subtractVector.x, 2) + Math.pow(subtractVector.y, 2);
    }

    public double distance(Vector2D v) {
        return Math.sqrt(distanceSquared(v));
    }

    @Override
    public String toString() {
        return "FloatVector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
