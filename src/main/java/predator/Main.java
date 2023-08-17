package predator;

import com.github.kwhat.jnativehook.GlobalScreen;
import predator.core.Memory;
import predator.core.Settings;
import predator.entities.*;
import predator.features.AimBot;
import predator.features.Sense;
import predator.features.TriggerBot;
import predator.ui.MainFrame;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {
        if (GlobalScreen.isNativeHookRegistered()) GlobalScreen.unregisterNativeHook();
        GlobalScreen.registerNativeHook();
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Settings settings = new Settings();
        Memory.FindPID();
        Level level = new Level();
        LocalPlayer localPlayer = new LocalPlayer();
        LocalPlayerWeapon localPlayerWeapon = new LocalPlayerWeapon(localPlayer);
        PlayerList playerList = new PlayerList(localPlayer, settings);
        DummyList dummyList = new DummyList(localPlayer, settings);
        Sense sense = new Sense(localPlayer, playerList, dummyList, settings);
        TriggerBot triggerBot = new TriggerBot(level, localPlayer, localPlayerWeapon, playerList, dummyList, settings);
        AimBot aimBot = new AimBot(level, localPlayer, playerList, dummyList, settings);
        MainFrame ui = new MainFrame(level, localPlayer, playerList, dummyList, settings);
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
                    try {
                        level.update();
                        if (level.playable) {
                            localPlayer.update();
                            localPlayerWeapon.update();
                            playerList.update();
                            if (level.isTrainingArea)
                                dummyList.update();
                            sense.update();
                            triggerBot.update();
//                            aimBot.update();
                        }
                        ui.update(0, level.name);
                    } catch (Exception ex) {
                        try {
                            ex.printStackTrace();
                            ui.update(0, "Error. Sleeping for 10 seconds");
                            Thread.sleep(1000 * 10); //sleep 10 seconds
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                0,
                7,//16.67MS=60FPS | 6.76MS=144FPS
                TimeUnit.MILLISECONDS);
    }
}
