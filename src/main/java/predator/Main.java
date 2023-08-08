package predator;

import predator.core.*;
import predator.features.Sense;
import predator.features.TriggerBot;
import predator.ui.MainFrame;

import javax.swing.*;

public class Main {

    public static Level level;
    public static LocalPlayer localPlayer;
    public static PlayerList playerList;
    public static DummyList dummyList;
    public static Sense sense;
    public static TriggerBot triggerBot;
    public static MainFrame ui;

    static void init() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        level = new Level();
        localPlayer = new LocalPlayer();
        playerList = new PlayerList(localPlayer);
        dummyList = new DummyList(localPlayer);
        sense = new Sense(playerList);
        triggerBot = new TriggerBot(level, localPlayer, playerList, dummyList);
        ui = new MainFrame(level, localPlayer, playerList, sense);
        Mem.AwaitPID();
    }

    public static void main(String[] args) throws Exception {
        init();
        for (int counter = 0; counter < 1000; counter++) {
            try {
                level.update();
                if (level.playable) {
                    localPlayer.update();
                    playerList.update();
                    if (level.isTrainingArea)
                        dummyList.update();
//                    sense.update();
//                    triggerBot.update();
                } else {
                    localPlayer.reset();
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
