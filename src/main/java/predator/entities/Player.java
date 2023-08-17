package predator.entities;

import com.sun.jna.Pointer;
import predator.core.Memory;
import predator.core.Offsets;
import predator.core.Settings;
import predator.math.Calc;
import predator.math.FloatVector2D;
import predator.math.FloatVector3D;
import predator.structs.GlowMode;

import java.util.Objects;

public class Player {

    public final Integer index;
    public final LocalPlayer localPlayer;
    public final Settings settings;
    public Pointer base;
    public String entityType;
    public FloatVector3D localOrigin;
    public Integer teamNumber;
    public Integer shieldHealthMax;
    public Boolean dead;
    public Boolean knocked;
    public FloatVector2D viewAngles;
    public Integer glowEnable;
    public Integer glowThroughWall;
    public FloatVector3D glowColor;
    public GlowMode glowMode;
    public Integer lastTimeVisible;
    public Integer lastTimeVisible_previous;
    public Boolean visible;
    public Integer lastCrosshairsTime_previous;
    public Integer lastCrosshairsTime;
    public Boolean aimedAt;
    public Boolean isDucking;
    public Boolean isLocalPlayer;
    public Boolean isFriendlyPlayer;
    public Double distanceToLocalPlayer;
    public FloatVector2D desiredViewAngles;
    public Double distanceToLocalPlayerCrosshairs;

    public Player(Integer index, LocalPlayer localPlayer, Settings settings) {
        this.index = index;
        this.localPlayer = localPlayer;
        this.settings = settings;
    }

    public void update() {
        Pointer entityListPointer = Offsets.OFF_REGION.share(Pointer.nativeValue(Offsets.OFF_ENTITY_LIST));
        long shiftValue = ((index + 1) * 32L);
        Pointer entityListPointerShifted = entityListPointer.share(shiftValue);
        base = Memory.resolvePointer(entityListPointerShifted);
        if (base == null) return;
        entityType = Memory.readString(base.share(Pointer.nativeValue(Offsets.OFF_NAME)), 32);
        if (!isPlayer() && !isDummy()) return;
        localOrigin = Memory.readFloatVector3D(base.share(Pointer.nativeValue(Offsets.OFF_LOCAL_ORIGIN)));
        teamNumber = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_TEAM_NUMBER)));
        shieldHealthMax = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_SHIELD_HEALTH_MAX)));
        glowEnable = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_ENABLE)));
        glowThroughWall = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_THROUGH_WALL)));
        glowColor = Memory.readFloatVector3D(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_COLOR)));
//        glowMode = Memory.readGlowMode(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_MODE)));
        lastTimeVisible_previous = lastTimeVisible;
        lastTimeVisible = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_LAST_VISIBLE_TIME)));
        visible = !Objects.equals(lastTimeVisible_previous, lastTimeVisible);
        lastCrosshairsTime_previous = lastCrosshairsTime;
        lastCrosshairsTime = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_LAST_CROSSHAIRS_TIME)));
        aimedAt = !Objects.equals(lastCrosshairsTime_previous, lastCrosshairsTime);
        if (isPlayer()) {
            dead = Memory.readShort(base.share(Pointer.nativeValue(Offsets.OFF_LIFE_STATE))) > 0;
            knocked = Memory.readShort(base.share(Pointer.nativeValue(Offsets.OFF_BLEEDOUT_STATE))) > 0;
            viewAngles = Memory.readFloatVector2D(base.share(Pointer.nativeValue(Offsets.OFF_VIEW_ANGLE)));
            isDucking = Memory.readShort(base.share(Pointer.nativeValue(Offsets.OFF_DUCK_STATE))) > 0;
        }
        if (localPlayer.base != null) {
            isLocalPlayer = localPlayer.base.toString().equals(base.toString());
            isFriendlyPlayer = Objects.equals(localPlayer.teamNumber, teamNumber);
            distanceToLocalPlayer = localPlayer.localOrigin.distance(localOrigin);
            if (visible) {
//                desiredViewAngles = new FloatVector2D(Calc.calcPitch(localPlayer.localOrigin, localOrigin), Calc.calcYaw(localPlayer.localOrigin, localOrigin));
//                distanceToLocalPlayerCrosshairs = localPlayer.viewAngles.distance(desiredViewAngles);
            }
        }
    }

    private boolean isPlayer() {
        return entityType != null && entityType.equalsIgnoreCase("player");
    }

    private boolean isDummy() {
        return entityType != null && entityType.equalsIgnoreCase("dynamic_dummie");
    }

}
