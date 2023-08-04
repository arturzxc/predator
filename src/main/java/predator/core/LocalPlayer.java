package predator.core;

import com.sun.jna.Pointer;

public class LocalPlayer {

    public Pointer base;
    public Boolean dead;
    public FloatVector3D localOrigin;
    public Boolean zooming;
    public Integer teamNumber;
    public FloatVector2D viewAngles;

    public void update() {
        base = Mem.resolvePointer(Off.REGION.share(Pointer.nativeValue(Off.LOCAL_PLAYER)));
        if (base == null) resetFields();
        else loadFields();
    }

    public void resetFields() {
        dead = null;
        localOrigin = null;
        teamNumber = null;
        zooming = null;
        viewAngles = null;
    }

    public void loadFields() {
        dead = Mem.readShort(base.share(Pointer.nativeValue(Off.LIFE_STATE))) > 0;
        localOrigin = Mem.readFloatVector3D(base.share(Pointer.nativeValue(Off.LOCAL_ORIGIN)));
        teamNumber = Mem.readInteger(base.share(Pointer.nativeValue(Off.TEAM_NUMBER)));
        zooming = Mem.readShort(base.share(Pointer.nativeValue(Off.ZOOMING))) != 0;
        viewAngles = Mem.readFloatVector2D(base.share(Pointer.nativeValue(Off.VIEW_ANGLE)));
    }
}
