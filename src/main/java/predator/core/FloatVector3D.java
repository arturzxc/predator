package predator.core;

@SuppressWarnings("unused")
public class FloatVector3D {
    public float x;
    public float y;
    public float z;

    public FloatVector3D subtract(FloatVector3D v) {
        FloatVector3D newVector = new FloatVector3D();
        newVector.x = x - v.x;
        newVector.y = y - v.y;
        return newVector;
    }

    public double distanceSquared(FloatVector3D v) {
        FloatVector3D subtractVector = subtract(v);
        return Math.pow(subtractVector.x, 2) + Math.pow(subtractVector.y, 2) + Math.pow(subtractVector.z, 2);
    }

    public double distance(FloatVector3D v) {
        return Math.sqrt(distanceSquared(v));
    }

    public FloatVector2D toFloatVector2D() {
        FloatVector2D v = new FloatVector2D();
        v.x = x;
        v.y = y;
        return v;
    }

    @Override
    public String toString() {
        return "FloatVector3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
