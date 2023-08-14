package predator;

import predator.core.*;
import predator.features.Sense;
import predator.features.TriggerBot;
import predator.ui.MainFrame;

import javax.swing.*;

public class Main {

    private static Level level;
    private static LocalPlayer localPlayer;
    private static LocalPlayerWeapon localPlayerWeapon;
    private static PlayerList playerList;
    private static DummyList dummyList;
    private static Sense sense;
    private static TriggerBot triggerBot;
    private static MainFrame ui;


    private static void init() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        level = new Level();
        localPlayer = new LocalPlayer();
        localPlayerWeapon = new LocalPlayerWeapon(localPlayer);
        playerList = new PlayerList(localPlayer);
        dummyList = new DummyList(localPlayer);
        sense = new Sense(playerList, dummyList);
        triggerBot = new TriggerBot(level, localPlayer, localPlayerWeapon, playerList, dummyList);
        ui = new MainFrame(level, localPlayer, playerList, dummyList, sense);
        Mem.AwaitPID();
    }

    public static void main(String[] args) throws Exception {
        init();
        for (int counter = 0; counter < 1000; counter++) {
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
                } else {
                    localPlayer.reset();
                    localPlayerWeapon.reset();
                    playerList.reset();
                    dummyList.reset();
                }
                ui.update(counter);
                Thread.sleep(10);
            } catch (Exception ex) {
                ex.printStackTrace();
                ui.setTitle("Error. Retry in 10 seconds");
                Mem.AwaitPID();
            }
            if (counter == 999) counter = 0;
        }
    }
}
