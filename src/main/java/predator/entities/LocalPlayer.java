package predator.entities;

import com.sun.jna.Pointer;
import predator.core.Vector2D;
import predator.core.Vector3D;
import predator.core.Memory;
import predator.core.Offsets;

public class LocalPlayer {

    public Pointer base;
    public Boolean dead;
    public Boolean knocked;
    public Vector3D localOrigin;
    public Integer teamNumber;
    public Vector2D viewAngles;
    public Boolean inZoom;
    public Boolean inAttack;

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
    }

}
