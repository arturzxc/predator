package predator.features;

import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import predator.core.*;

import java.awt.*;
import java.awt.event.InputEvent;

public class TriggerBot implements NativeKeyListener {

    private final Level level;
    private final LocalPlayer localPlayer;
    private final LocalPlayerWeapon localPlayerWeapon;
    private final PlayerList playerList;
    private final DummyList dummyList;
    private final Robot robot;
    public double hipfireRange = Util.convertMetersToHammerUnits(50);

    public TriggerBot(Level level, LocalPlayer localPlayer, LocalPlayerWeapon localPlayerWeapon, PlayerList playerList, DummyList dummyList) {
        this.level = level;
        this.localPlayer = localPlayer;
        this.localPlayerWeapon = localPlayerWeapon;
        this.playerList = playerList;
        this.dummyList = dummyList;
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        if (localPlayer.base == null) return;
        if (localPlayer.dead == null || localPlayer.dead) return;
        if (localPlayer.knocked == null || localPlayer.knocked) return;
        if (localPlayer.inZoom == null || !localPlayer.inZoom) return;
        if (localPlayerWeapon.semiAuto == null || !localPlayerWeapon.semiAuto) return;

        boolean targetAcquired;
        if (level.isTrainingArea)
            targetAcquired = !dummyList.getDummies().stream()
                    .filter(p -> p.visible != null && p.visible)
                    .filter(p -> p.aimedAt != null && p.aimedAt)
                    .toList().isEmpty();
        else
            targetAcquired = !playerList.getEnemyPlayers().stream()
                    .filter(p -> p.visible != null && p.visible)
                    .filter(p -> p.knocked != null && !p.knocked)
                    .filter(p -> p.aimedAt != null && p.aimedAt)
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


