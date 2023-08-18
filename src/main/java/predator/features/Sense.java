package predator.features;

import com.sun.jna.Pointer;
import predator.core.Memory;
import predator.core.Offsets;
import predator.core.Settings;
import predator.core.UnitConverter;
import predator.entities.DummyList;
import predator.entities.LocalPlayer;
import predator.entities.Player;
import predator.entities.PlayerList;
import predator.math.FloatVector3D;

public class Sense {

    private final LocalPlayer localPlayer;
    private final PlayerList playerList;
    private final DummyList dummyList;
    private final Settings settings;

    public Sense(LocalPlayer localPlayer, PlayerList playerList, DummyList dummyList, Settings settings) {
        this.localPlayer = localPlayer;
        this.playerList = playerList;
        this.dummyList = dummyList;
        this.settings = settings;
    }

    public void update() {
        if (!settings.readBoolean(Settings.Key.SENSE_ON)) return;
        if (localPlayer.base == null) return;
        final int MAX_DISTANCE = settings.readInteger(Settings.Key.SENSE_MAX_DISTANCE_METERS);
        playerList.getEnemyPlayers().forEach(p -> {
            if (!localPlayer.dead && p.distanceToLocalPlayer > UnitConverter.convertMetersToHammerUnits(MAX_DISTANCE)) {
                glowStop(p);
            } else {
                if (settings.readBoolean(Settings.Key.SENSE_CUSTOM_COLOR_ON)) {
                    if (p.visible) glowVisibleCustom(p);
                    else glowInvisibleCustom(p);
                } else {
                    if (p.visible) glowVisiblePreset(p);
                    else glowInVisiblePreset(p);
                }
            }
        });
        playerList.getFriendlyPlayers().forEach(p -> {
            if (settings.readBoolean(Settings.Key.SENSE_CUSTOM_COLOR_ON)) {
                if (p.visible) glowVisibleCustom(p);
                else glowInvisibleCustom(p);
            } else {
                if (p.visible) glowVisiblePreset(p);
                else glowInVisiblePreset(p);
            }
        });
        dummyList.getDummies().forEach(p -> {
            if (settings.readBoolean(Settings.Key.SENSE_CUSTOM_COLOR_ON)) {
                if (p.visible) glowVisibleCustom(p);
                else glowInvisibleCustom(p);
            } else {
                if (p.visible) glowVisiblePreset(p);
                else glowInVisiblePreset(p);
            }
        });
    }

    private void glowVisiblePreset(Player p) {
        int glowEnable = settings.readInteger(Settings.Key.SENSE_PRESET_VISIBLE_STYLE);
        if (p.glowEnable != glowEnable)
            Memory.writeInteger(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_ENABLE)), glowEnable);
        if (p.glowThroughWall != 2)
            Memory.writeInteger(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_THROUGH_WALL)), 2);
    }

    private void glowInVisiblePreset(Player p) {
        int glowEnable = settings.readInteger(Settings.Key.SENSE_PRESET_INVISIBLE_STYLE);
        if (p.glowEnable != glowEnable)
            Memory.writeInteger(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_ENABLE)), glowEnable);
        if (p.glowThroughWall != 2)
            Memory.writeInteger(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_THROUGH_WALL)), 2);
    }

    private void glowVisibleCustom(Player p) {
        if (p.glowEnable != 1)
            Memory.writeInteger(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_ENABLE)), 1);
        if (p.glowThroughWall != 2)
            Memory.writeInteger(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_THROUGH_WALL)), 2);

        FloatVector3D glowColor = new FloatVector3D(
                settings.readFloat(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_RED),
                settings.readFloat(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_GREEN),
                settings.readFloat(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_BLUE));
        if (!p.glowColor.equals(glowColor))
            Memory.writeFloatVector3D(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_COLOR)), glowColor);

//        if (!p.glowMode.empty())
        Memory.writeGlowModeVisible(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_MODE)), p.glowMode);
    }

    private void glowInvisibleCustom(Player p) {
        if (p.glowEnable != 1)
            Memory.writeInteger(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_ENABLE)), 1);
        if (p.glowThroughWall != 2)
            Memory.writeInteger(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_THROUGH_WALL)), 2);
        FloatVector3D glowColor = new FloatVector3D(
                settings.readFloat(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_RED),
                settings.readFloat(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_GREEN),
                settings.readFloat(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_BLUE));
        if (!p.glowColor.equals(glowColor))
            Memory.writeFloatVector3D(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_COLOR)), glowColor);

//        if (!p.glowMode.empty())
        Memory.writeGlowModeInvis(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_MODE)), p.glowMode);

    }

    private void glowStop(Player p) {
        if (p.glowEnable != 2)
            Memory.writeInteger(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_ENABLE)), 2);
        if (p.glowThroughWall != 2)
            Memory.writeInteger(p.base.share(Pointer.nativeValue(Offsets.OFF_GLOW_THROUGH_WALL)), 2);
    }

}
