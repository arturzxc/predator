package predator.features;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import predator.core.Settings;
import predator.entities.*;

import java.util.Comparator;
import java.util.Optional;

public class AimBot implements NativeKeyListener {

    private boolean triggerButtonDown = false;
    private final Level level;
    private final LocalPlayer localPlayer;
    private final PlayerList playerList;
    private final DummyList dummyList;
    private final Settings settings;

    public AimBot(Level level, LocalPlayer localPlayer, PlayerList playerList, DummyList dummyList, Settings settings) {
        this.level = level;
        this.localPlayer = localPlayer;
        this.playerList = playerList;
        this.dummyList = dummyList;
        this.settings = settings;
        GlobalScreen.addNativeKeyListener(this);
    }

    public void update() {
        if (!triggerButtonDown) return;
        if (localPlayer.base == null) return;
        if (localPlayer.dead == null || localPlayer.dead) return;
        if (localPlayer.knocked == null || localPlayer.knocked) return;

        Optional<Player> bestTargetOpt;
        if (level.isTrainingArea)
            bestTargetOpt = dummyList.getDummies().stream()
//                    .filter(e -> e.visible)
                    .min(Comparator.comparing(e -> e.distanceToLocalPlayerCrosshairs));
        else
            bestTargetOpt = playerList.getEnemyPlayers().stream()
                    .filter(e -> e.visible)
                    .filter(e -> !e.dead)
                    .filter(e -> !e.knocked)
                    .min(Comparator.comparing(e -> e.distanceToLocalPlayerCrosshairs));

        if (bestTargetOpt.isEmpty()) return;
        Player bestTarget = bestTargetOpt.get();
        localPlayer.aimAt(bestTarget.desiredViewAngles);

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT)
            triggerButtonDown = true;
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT)
            triggerButtonDown = false;
    }
}
