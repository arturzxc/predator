package predator.core;

import com.sun.jna.Pointer;

import java.util.Objects;

public class Player {
    public final Integer index;
    public final LocalPlayer localPlayer;
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
    //calculated
    public Boolean isLocalPlayer;
    public Boolean isFriendlyPlayer;
    public Double distanceToLocalPlayer;
    public Double desiredPitch;
    public Double desiredYaw;


    public Player(Integer index, LocalPlayer localPlayer) {
        this.index = index;
        this.localPlayer = localPlayer;
    }

    public void update() {
        Pointer entityListPointer = Off.REGION.share(Pointer.nativeValue(Off.ENTITY_LIST));
        long shiftValue = ((index + 1) * 32L);
        Pointer entityListPointerShifted = entityListPointer.share(shiftValue);
        base = Mem.resolvePointer(entityListPointerShifted);
        if (base == null) reset();
        else loadFields();
    }

    public void reset() {
        //BaseEntity
        entityType = null;
        localOrigin = null;
        teamNumber = null;
        shieldHealthMax = null;
        //Player
        dead = null;
        knocked = null;
        viewAngles = null;
        glowEnable = null;
        glowThroughWall = null;
        lastTimeVisible = null;
        lastTimeVisible_previous = null;
        visible = null;
        lastCrosshairsTime_previous = null;
        lastCrosshairsTime = null;
        aimedAt = null;
        //calculated
        isLocalPlayer = null;
        isFriendlyPlayer = null;
        distanceToLocalPlayer = null;
        desiredPitch = null;
        desiredYaw = null;
    }

    public void loadFields() {
        try {
            //BaseEntity
            entityType = Mem.readString(base.share(Pointer.nativeValue(Off.NAME)), 32);
            localOrigin = Mem.readFloatVector3D(base.share(Pointer.nativeValue(Off.LOCAL_ORIGIN)));
            teamNumber = Mem.readInteger(base.share(Pointer.nativeValue(Off.TEAM_NUMBER)));
            shieldHealthMax = Mem.readInteger(base.share(Pointer.nativeValue(Off.SHIELD_HEALTH_MAX)));
            //Player
            if (isPlayer()) {
                dead = Mem.readShort(base.share(Pointer.nativeValue(Off.LIFE_STATE))) > 0;
                knocked = Mem.readShort(base.share(Pointer.nativeValue(Off.BLEEDOUT_STATE))) > 0;
                viewAngles = Mem.readFloatVector2D(base.share(Pointer.nativeValue(Off.VIEW_ANGLE)));
                glowEnable = Mem.readInteger(base.share(Pointer.nativeValue(Off.GLOW_ENABLE)));
                glowThroughWall = Mem.readInteger(base.share(Pointer.nativeValue(Off.GLOW_THROUGH_WALL)));
                lastTimeVisible_previous = lastTimeVisible;
                lastTimeVisible = Mem.readInteger(base.share(Pointer.nativeValue(Off.LAST_VISIBLE_TIME)));
                visible = !Objects.equals(lastTimeVisible_previous, lastTimeVisible);
                lastCrosshairsTime_previous = lastCrosshairsTime;
                lastCrosshairsTime = Mem.readInteger(base.share(Pointer.nativeValue(Off.LAST_CROSSHAIRS_TIME)));
                aimedAt = !Objects.equals(lastCrosshairsTime_previous, lastCrosshairsTime);
            }
            //dummy
            if (isDummy()) {
                lastTimeVisible_previous = lastTimeVisible;
                lastTimeVisible = Mem.readInteger(base.share(Pointer.nativeValue(Off.LAST_VISIBLE_TIME)));
                visible = !Objects.equals(lastTimeVisible_previous, lastTimeVisible);
                lastCrosshairsTime_previous = lastCrosshairsTime;
                lastCrosshairsTime = Mem.readInteger(base.share(Pointer.nativeValue(Off.LAST_CROSSHAIRS_TIME)));
                aimedAt = !Objects.equals(lastCrosshairsTime_previous, lastCrosshairsTime);
            }
            //calculated
            if (localPlayer.base != null) {
                isLocalPlayer = localPlayer.base.toString().equals(base.toString());
                isFriendlyPlayer = Objects.equals(localPlayer.teamNumber, teamNumber);
                if (visible && !isLocalPlayer && localPlayer.dead != null && !localPlayer.dead) {//heavy calculations. Only perform if absolutely necessary.
//                    distanceToLocalPlayer = localPlayer.localOrigin.distance(localOrigin);
//                    desiredPitch = calculateDesiredPitch();
//                    desiredYaw = calculateDesiredYaw();
                } else {
                    distanceToLocalPlayer = null;
                    desiredPitch = null;
                    desiredYaw = null;
                }
            }
        } catch (Exception ex) {
            System.out.println("Player[" + index + "] failed to load all fields: " + ex.getMessage());
            reset();
        }
    }

    private void glow(int myGlowEnable) {
        if (glowEnable != myGlowEnable)
            Mem.writeInteger(base.share(Pointer.nativeValue(Off.GLOW_ENABLE)), myGlowEnable);
        if (glowEnable != 2)
            Mem.writeInteger(base.share(Pointer.nativeValue(Off.GLOW_THROUGH_WALL)), 2);
    }

    public void glowCaustic() {
        glow(5);
    }

    public void glowBloodhound() {
        glow(7);
    }

    public boolean isPlayer() {
        return entityType != null && entityType.equals("player");
    }

    public boolean isDummy() {
        return entityType != null && entityType.equals("dynamic_dummie");
    }

    protected double calculateDesiredYaw() {
        final FloatVector2D subtractionVector = localOrigin.toFloatVector2D().subtract(localPlayer.localOrigin.toFloatVector2D());
        double yawInRadians = Math.atan2(subtractionVector.y, subtractionVector.x);
        return Math.toDegrees(yawInRadians);
    }

    protected double calculateDesiredPitch() {
        final double locationDeltaZ = localOrigin.z - localPlayer.localOrigin.z;
        final double distanceBetweenPlayers = localOrigin.distance(localPlayer.localOrigin);
        double pitchInRadians = Math.atan2(-locationDeltaZ, distanceBetweenPlayers);
        return Math.toDegrees(pitchInRadians);
    }

}
