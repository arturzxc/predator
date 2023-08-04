package predator.core;

@SuppressWarnings("unused")
public class FloatVector2D {
    public float x;
    public float y;

    public FloatVector2D subtract(FloatVector2D v) {
        FloatVector2D newVector = new FloatVector2D();
        newVector.x = x - v.x;
        newVector.y = y - v.y;
        return newVector;
    }

    public double distanceSquared(FloatVector2D v) {
        return Math.pow(x, 2) + Math.pow(y, 2);
    }

    public double distance(FloatVector2D v) {
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
