package predator.features;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import predator.core.LocalPlayer;
import predator.core.Player;
import predator.core.Util;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;

public class TriggerBot implements NativeKeyListener {

    private final LocalPlayer localPlayer;
    private final List<Player> players;
    private final Robot robot;
    private Long timeLastShot;
    private boolean triggerDown = false;

    public TriggerBot(LocalPlayer localPlayer, List<Player> players) {
        this.localPlayer = localPlayer;
        this.players = players;
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(this);
    }

    public void update() {
        if (!triggerDown) return;
        final int MILLIS_BETWEEN_SHOTS = 10;
        if (timeLastShot != null)
            if (System.currentTimeMillis() - timeLastShot < MILLIS_BETWEEN_SHOTS)
                return;
        for (Player p : players.stream()
                .filter(p -> p.base != null)
                .filter(p -> p.visible != null && p.visible)
                .filter(p -> p.isFriendlyPlayer != null && !p.isFriendlyPlayer)
                .filter(p -> p.desiredYaw != null)
                .filter(p -> p.desiredPitch != null)
                .filter(p -> p.distanceToLocalPlayer != null)
                .toList()) {
            //The greater the distance the smaller the acceptable FOV for the trigger
            //increase the numbers for more loose trigger and decrease the number for more strict trigger
            final double YAW_EPSILON = 200f / p.distanceToLocalPlayer;
            if (Math.abs(p.desiredYaw - localPlayer.viewAngles.y) < YAW_EPSILON) {
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(5);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(5);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                timeLastShot = System.currentTimeMillis();
                break;
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals("Shift")) {
            triggerDown = !triggerDown;
            if (triggerDown) Util.playSound("sound_beep1.wav");
            else Util.playSound("bass.wav");
        }
    }


}
