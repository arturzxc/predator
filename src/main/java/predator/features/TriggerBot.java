package predator.features;

import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import predator.core.LocalPlayer;
import predator.core.PlayerList;

import java.awt.*;
import java.awt.event.InputEvent;

public class TriggerBot implements NativeKeyListener {

    private final LocalPlayer localPlayer;
    private final PlayerList playerList;
    private final Robot robot;
    private Long timeLastShot;

    public TriggerBot(LocalPlayer localPlayer, PlayerList playerList) {
        this.localPlayer = localPlayer;
        this.playerList = playerList;
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        if (localPlayer.base == null) return;
        if (localPlayer.dead == null || localPlayer.dead) return;
        if (localPlayer.inZoom == null || !localPlayer.inZoom) return;
        final int MILLIS_BETWEEN_SHOTS = 10;
        if (timeLastShot != null)
            if (System.currentTimeMillis() - timeLastShot < MILLIS_BETWEEN_SHOTS)
                return;
        if (!playerList.getEnemyPlayers().stream()
                .filter(p -> p.visible != null && p.visible)
                .filter(p -> p.aimedAt != null && p.aimedAt)
                .toList().isEmpty()) {
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(1);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(1);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            timeLastShot = System.currentTimeMillis();
        }
    }

}
