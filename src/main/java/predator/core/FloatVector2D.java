package predator.core;

@SuppressWarnings("unused")
public class FloatVector2D implements Cloneable {
    public float x;
    public float y;

    public FloatVector2D() {
    }

    public FloatVector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public FloatVector2D subtract(FloatVector2D v) {
        FloatVector2D newVector = new FloatVector2D();
        newVector.x = x - v.x;
        newVector.y = y - v.y;
        return newVector;
    }

    public FloatVector2D add(FloatVector2D v) {
        FloatVector2D newVector = new FloatVector2D();
        newVector.x = x + v.x;
        newVector.y = y + v.y;
        return newVector;
    }

    public double distanceSquared(FloatVector2D v) {
        FloatVector2D subtractVector = subtract(v);
        return Math.pow(subtractVector.x, 2) + Math.pow(subtractVector.y, 2);
    }

    public double distance(FloatVector2D v) {
        return Math.sqrt(distanceSquared(v));
    }

    public FloatVector2D clone() {
        try {
            return (FloatVector2D) super.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString() {
        return "FloatVector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
