package predator.math;

public class Calc {

    public static float calcYaw(FloatVector3D localPlayerOrigin, FloatVector3D enemyPlayerOrigin) {
        //clone & shift so that we are in the coordinate quadrant no. 1
        final FloatVector2D shift = new FloatVector2D(100000, 100000);
        final FloatVector2D originA = localPlayerOrigin.toFloatVector2D().add(shift);
        final FloatVector2D originB = enemyPlayerOrigin.toFloatVector2D().add(shift);

        //calculate angle
        final FloatVector2D diff = originB.subtract(originA);
        final double yawInRadians = Math.atan2(diff.y, diff.x);

        return (float) Math.toDegrees(yawInRadians);
    }

    public static float calcPitch(FloatVector3D localPlayerOrigin, FloatVector3D enemyPlayerOrigin) {
        //clone & shift so that we are in the coordinate quadrant no. 1
        final FloatVector3D shift = new FloatVector3D(100000, 100000, 100000);
        final FloatVector3D originA = localPlayerOrigin.clone().add(shift);
        final FloatVector3D originB = enemyPlayerOrigin.clone().add(shift);
        originB.z -= 15; //aim middle of the head and not the top of it

        //calculate angle
        final double deltaZ = originB.z - originA.z;
        final double distanceBetweenPlayers = originA.toFloatVector2D().distance(originB.toFloatVector2D());
        final double pitchInRadians = Math.atan2(-deltaZ, distanceBetweenPlayers);

        return (float) Math.toDegrees(pitchInRadians);
    }

}
