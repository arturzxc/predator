package predator.entities;

import com.sun.jna.Pointer;
import predator.core.FloatVector2D;
import predator.core.FloatVector3D;
import predator.core.Memory;
import predator.core.Offsets;

public class LocalPlayer {

    public Pointer base;
    public Boolean dead;
    public Boolean knocked;
    public FloatVector3D localOrigin;
    public Integer teamNumber;
    public FloatVector2D viewAngles;
    public Boolean inZoom;
    public Boolean inAttack;
    public Boolean isDucking;

    public void update() {
        base = Memory.resolvePointer(Offsets.OFF_REGION.share(Pointer.nativeValue(Offsets.OFF_LOCAL_PLAYER)));
        if (base == null) return;
        loadFields();
    }

    public void loadFields() {
        dead = Memory.readShort(base.share(Pointer.nativeValue(Offsets.OFF_LIFE_STATE))) > 0;
        knocked = Memory.readShort(base.share(Pointer.nativeValue(Offsets.OFF_BLEEDOUT_STATE))) > 0;
        localOrigin = Memory.readFloatVector3D(base.share(Pointer.nativeValue(Offsets.OFF_LOCAL_ORIGIN)));
        teamNumber = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_TEAM_NUMBER)));
        viewAngles = Memory.readFloatVector2D(base.share(Pointer.nativeValue(Offsets.OFF_VIEW_ANGLE)));
        inZoom = Memory.readShort(base.share(Pointer.nativeValue(Offsets.OFF_ZOOMING))) != 0;
        inAttack = Memory.readShort(Offsets.OFF_REGION.share(Pointer.nativeValue(Offsets.OFF_IN_ATTACK))) > 0;
        isDucking = Memory.readShort(base.share(Pointer.nativeValue(Offsets.OFF_DUCK_STATE))) > 0;
    }

    public void aimAt(FloatVector2D desiredViewAngles) {
        if (Math.abs(desiredViewAngles.x) > 90)
            throw new RuntimeException("PITCH CANNOT BE GREATER THAN 90 OR LESS THAN -90 DEGREES. PITCH PASSED: "
                    + desiredViewAngles.x);
        if (Math.abs(desiredViewAngles.y) > 180)
            throw new RuntimeException("YAW CANNOT BE GREATER THAN 180 OR LESS THAN 180 DEGREES. YAW PASSED: "
                    + desiredViewAngles.y);
        Memory.writeFloatVector2D(base.share(Pointer.nativeValue(Offsets.OFF_VIEW_ANGLE)), desiredViewAngles);
    }

}
