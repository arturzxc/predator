package predator.features;

import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import predator.core.Settings;
import predator.core.UnitConverter;
import predator.entities.*;

import java.awt.*;
import java.awt.event.InputEvent;

public class TriggerBot implements NativeKeyListener {

    private final Level level;
    private final LocalPlayer localPlayer;
    private final LocalPlayerWeapon localPlayerWeapon;
    private final PlayerList playerList;
    private final DummyList dummyList;
    private final Settings settings;
    private final Robot robot;

    public TriggerBot(Level level, LocalPlayer localPlayer, LocalPlayerWeapon localPlayerWeapon, PlayerList playerList, DummyList dummyList, Settings settings) {
        this.level = level;
        this.localPlayer = localPlayer;
        this.localPlayerWeapon = localPlayerWeapon;
        this.playerList = playerList;
        this.dummyList = dummyList;
        this.settings = settings;
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        if (!settings.readBoolean(Settings.Key.TRIGGERBOT_ON)) return;
        if (localPlayer.base == null) return;
        if (localPlayer.dead == null || localPlayer.dead) return;
        if (localPlayer.knocked == null || localPlayer.knocked) return;
        if (localPlayer.inZoom == null || !localPlayer.inZoom) return;
        if (localPlayerWeapon.semiAuto == null || !localPlayerWeapon.semiAuto) return;

        final int MAX_DISTANCE = settings.readInteger(Settings.Key.SENSE_MAX_DISTANCE_METERS);

        boolean targetAcquired;
        if (level.isTrainingArea)
            targetAcquired = !dummyList.getDummies().stream()
                    .filter(p -> p.visible != null && p.visible)
                    .filter(p -> p.aimedAt != null && p.aimedAt)
                    .filter(p -> p.distanceToLocalPlayer < UnitConverter.convertMetersToHammerUnits(MAX_DISTANCE))
                    .toList().isEmpty();
        else
            targetAcquired = !playerList.getEnemyPlayers().stream()
                    .filter(p -> p.visible != null && p.visible)
                    .filter(p -> p.knocked != null && !p.knocked)
                    .filter(p -> p.aimedAt != null && p.aimedAt)
                    .filter(p -> p.distanceToLocalPlayer < UnitConverter.convertMetersToHammerUnits(MAX_DISTANCE))
                    .toList().isEmpty();

        if (targetAcquired)
            shootOneBullet();
    }

    private void shootOneBullet() {
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(1);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(5);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(1);
    }

}


