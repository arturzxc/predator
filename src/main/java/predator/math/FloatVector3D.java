package predator.math;


@SuppressWarnings("unused")
public class FloatVector3D implements Cloneable {
    public float x;
    public float y;
    public float z;

    public FloatVector3D() {
    }

    public FloatVector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public FloatVector3D subtract(FloatVector3D v) {
        FloatVector3D newVector = new FloatVector3D();
        newVector.x = x - v.x;
        newVector.y = y - v.y;
        newVector.z = z - v.z;
        return newVector;
    }

    public FloatVector3D add(FloatVector3D v) {
        FloatVector3D newVector = new FloatVector3D();
        newVector.x = x + v.x;
        newVector.y = y + v.y;
        newVector.z = z + v.z;
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
        return new FloatVector2D(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FloatVector3D that = (FloatVector3D) o;

        if (Float.compare(that.x, x) != 0) return false;
        if (Float.compare(that.y, y) != 0) return false;
        return Float.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }

    public FloatVector3D clone() {
        try {
            return (FloatVector3D) super.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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
