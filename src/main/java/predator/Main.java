package predator;

import predator.core.Level;
import predator.core.LocalPlayer;
import predator.core.Mem;
import predator.core.PlayerList;
import predator.features.Sense;
import predator.features.TriggerBot;
import predator.ui.MainFrame;

import javax.swing.*;

public class Main {

    public static Level LEVEL;
    public static LocalPlayer LOCAL_PLAYER;
    public static PlayerList PLAYER_LIST;
    public static Sense SENSE;
    public static TriggerBot TRIGGER_BOT;
    public static MainFrame UI;

    static void init() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        LEVEL = new Level();
        LOCAL_PLAYER = new LocalPlayer();
        PLAYER_LIST = new PlayerList(LOCAL_PLAYER);
        SENSE = new Sense(PLAYER_LIST);
        TRIGGER_BOT = new TriggerBot(LOCAL_PLAYER, PLAYER_LIST);
        UI = new MainFrame(LEVEL, LOCAL_PLAYER, PLAYER_LIST, SENSE);
        Mem.AwaitPID();
    }

    public static void main(String[] args) throws Exception {
        init();
        for (int counter = 0; counter < 1000; counter++) {
            try {
                LEVEL.update();
                if (LEVEL.playable) {
                    LOCAL_PLAYER.update();
                    PLAYER_LIST.update();
                    SENSE.update();
                    TRIGGER_BOT.update();
                } else {
                    LOCAL_PLAYER.reset();
                    PLAYER_LIST.reset();
                }
                UI.update(counter);
                Thread.sleep(10);
            } catch (Exception ex) {
                ex.printStackTrace();
                UI.setTitle("Error. Retry in 10 seconds");
                Mem.AwaitPID();
            }
            if (counter == 999) counter = 0;
        }
    }
}
