package predator.core;

@SuppressWarnings("unused")
public class Vector3D {
    public float x;
    public float y;
    public float z;

    public Vector3D subtract(Vector3D v) {
        Vector3D newVector = new Vector3D();
        newVector.x = x - v.x;
        newVector.y = y - v.y;
        newVector.z = z - v.z;
        return newVector;
    }

    public Vector3D add(Vector3D v) {
        Vector3D newVector = new Vector3D();
        newVector.x = x + v.x;
        newVector.y = y + v.y;
        newVector.z = z + v.z;
        return newVector;
    }

    public double distanceSquared(Vector3D v) {
        Vector3D subtractVector = subtract(v);
        return Math.pow(subtractVector.x, 2) + Math.pow(subtractVector.y, 2) + Math.pow(subtractVector.z, 2);
    }

    public double distance(Vector3D v) {
        return Math.sqrt(distanceSquared(v));
    }

    public Vector2D toFloatVector2D() {
        Vector2D v = new Vector2D();
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
