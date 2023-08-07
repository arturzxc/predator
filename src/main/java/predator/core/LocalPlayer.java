package predator.core;

import com.sun.jna.Pointer;

public class LocalPlayer {

    public Pointer base;
    public Boolean dead;
    public FloatVector3D localOrigin;
    public Integer teamNumber;
    public FloatVector2D viewAngles;
    public Boolean inZoom;
    public Boolean inAttack;

    public void update() {
        base = Mem.resolvePointer(Off.REGION.share(Pointer.nativeValue(Off.LOCAL_PLAYER)));
        if (base == null) reset();
        else loadFields();
    }

    public void reset() {
        dead = null;
        localOrigin = null;
        teamNumber = null;
        viewAngles = null;
        inZoom = null;
        inAttack = false;
    }

    public void loadFields() {
        try {
            dead = Mem.readShort(base.share(Pointer.nativeValue(Off.LIFE_STATE))) > 0;
            localOrigin = Mem.readFloatVector3D(base.share(Pointer.nativeValue(Off.LOCAL_ORIGIN)));
            teamNumber = Mem.readInteger(base.share(Pointer.nativeValue(Off.TEAM_NUMBER)));
            viewAngles = Mem.readFloatVector2D(base.share(Pointer.nativeValue(Off.VIEW_ANGLE)));
            inZoom = Mem.readShort(base.share(Pointer.nativeValue(Off.ZOOMING))) != 0;
            inAttack = Mem.readShort(Off.REGION.share(Pointer.nativeValue(Off.IN_ATTACK))) > 0;
        } catch (Exception ex) {
            reset();
        }
    }
}
