package predator.entities;

import com.sun.jna.Pointer;
import predator.core.*;

import java.util.Objects;

public class Player {
    public final Integer index;
    public final LocalPlayer localPlayer;
    public final Settings settings;
    public Pointer base;
    //base entity
    public String entityType;
    public FloatVector3D localOrigin;
    public Integer teamNumber;
    public Integer shieldHealthMax;
    //player
    public Boolean dead;
    public Boolean knocked;
    public FloatVector2D viewAngles;
    public Integer glowEnable;
    public Integer glowThroughWall;
    public Integer lastTimeVisible;
    public Integer lastTimeVisible_previous;
    public Boolean visible;
    public Integer lastCrosshairsTime_previous;
    public Integer lastCrosshairsTime;
    public Boolean aimedAt;
    public Boolean isDucking;
    //calculated
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
        loadFields();
    }

    public void loadFields() {
        //BaseEntity
        entityType = Memory.readString(base.share(Pointer.nativeValue(Offsets.OFF_NAME)), 32);
        if (!isPlayer() && !isDummy()) return;
        localOrigin = Memory.readFloatVector3D(base.share(Pointer.nativeValue(Offsets.OFF_LOCAL_ORIGIN)));
        teamNumber = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_TEAM_NUMBER)));
        shieldHealthMax = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_SHIELD_HEALTH_MAX)));
        glowEnable = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_ENABLE)));
        glowThroughWall = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_THROUGH_WALL)));
        lastTimeVisible_previous = lastTimeVisible;
        lastTimeVisible = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_LAST_VISIBLE_TIME)));
        visible = !Objects.equals(lastTimeVisible_previous, lastTimeVisible);
        lastCrosshairsTime_previous = lastCrosshairsTime;
        lastCrosshairsTime = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_LAST_CROSSHAIRS_TIME)));
        aimedAt = !Objects.equals(lastCrosshairsTime_previous, lastCrosshairsTime);

        //Only Players have these
        if (isPlayer()) {
            dead = Memory.readShort(base.share(Pointer.nativeValue(Offsets.OFF_LIFE_STATE))) > 0;
            knocked = Memory.readShort(base.share(Pointer.nativeValue(Offsets.OFF_BLEEDOUT_STATE))) > 0;
            viewAngles = Memory.readFloatVector2D(base.share(Pointer.nativeValue(Offsets.OFF_VIEW_ANGLE)));
            isDucking = Memory.readShort(base.share(Pointer.nativeValue(Offsets.OFF_DUCK_STATE))) > 0;
        }

        //calculated values
        if (localPlayer.base != null) {
            isLocalPlayer = localPlayer.base.toString().equals(base.toString());
            isFriendlyPlayer = Objects.equals(localPlayer.teamNumber, teamNumber);
            distanceToLocalPlayer = localPlayer.localOrigin.distance(localOrigin);
            if (visible) { //heavy calculation to be performed only on the players we can see
                desiredViewAngles = new FloatVector2D(calculateDesiredPitch().floatValue(), calculateDesiredYaw().floatValue());
                distanceToLocalPlayerCrosshairs = localPlayer.viewAngles.distance(desiredViewAngles);
            } else {
                desiredViewAngles = null;
                distanceToLocalPlayerCrosshairs = null;
            }
        }
    }

    public void glowVisiblePreset() {
        int glowEnable = settings.readInteger(Settings.Key.SENSE_PRESET_VISIBLE_STYLE);
        Memory.writeInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_ENABLE)), glowEnable);
        Memory.writeInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_THROUGH_WALL)), 2);
    }

    public void glowInVisiblePreset() {
        int glowEnable = settings.readInteger(Settings.Key.SENSE_PRESET_INVISIBLE_STYLE);
        Memory.writeInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_ENABLE)), glowEnable);
        Memory.writeInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_THROUGH_WALL)), 2);
    }

    public void glowVisibleCustom() {
        Memory.writeInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_ENABLE)), 1);
        Memory.writeInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_THROUGH_WALL)), 2);
        Memory.writeFloat(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_COLOR_R)), settings.readFloat(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_RED));
        Memory.writeFloat(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_COLOR_R) + 0x4), settings.readFloat(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_GREEN));
        Memory.writeFloat(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_COLOR_R) + 0x8), settings.readFloat(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_BLUE));
    }

    public void glowInvisibleCustom() {
        Memory.writeInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_ENABLE)), 1);
        Memory.writeInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_THROUGH_WALL)), 2);
        Memory.writeFloat(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_COLOR_R)), settings.readFloat(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_RED));
        Memory.writeFloat(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_COLOR_R) + 0x4), settings.readFloat(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_GREEN));
        Memory.writeFloat(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_COLOR_R) + 0x8), settings.readFloat(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_BLUE));
    }

    public void glowStop() {
        if (glowEnable != 2)
            Memory.writeInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_ENABLE)), 2);
        if (glowThroughWall != 2)
            Memory.writeInteger(base.share(Pointer.nativeValue(Offsets.OFF_GLOW_THROUGH_WALL)), 2);
    }

    public boolean isPlayer() {
        return entityType != null && entityType.equalsIgnoreCase("player");
    }

    public boolean isDummy() {
        return entityType != null && entityType.equalsIgnoreCase("dynamic_dummie");
    }

    private Double calculateDesiredYaw() {
        final FloatVector2D subtractionVector = localOrigin.toFloatVector2D().subtract(localPlayer.localOrigin.toFloatVector2D());
        double yawInRadians = Math.atan2(subtractionVector.y, subtractionVector.x);
        return Math.toDegrees(yawInRadians);
    }

    private Double calculateDesiredPitch() {
        FloatVector3D localOriginClone = localOrigin.clone();
        if (isDucking != null && isDucking && !localPlayer.isDucking)
            localOriginClone.z -= 30;
        if (isDucking != null && !isDucking && localPlayer.isDucking)
            localOriginClone.z += 30;
        final double locationDeltaZ = localOriginClone.z - localPlayer.localOrigin.z;
        final double distanceBetweenPlayers = localOriginClone.distance(localPlayer.localOrigin);
        double pitchInRadians = Math.atan2(-locationDeltaZ, distanceBetweenPlayers);
        return Math.toDegrees(pitchInRadians);
    }
}
