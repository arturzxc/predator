package predator.ui;

import predator.core.Settings;
import predator.entities.DummyList;
import predator.entities.Level;
import predator.entities.LocalPlayer;
import predator.entities.PlayerList;

import javax.swing.*;

public class MainFrame extends JFrame {

    private final Level level;
    private final PlayersPnl playersPnl;
    private final DummiesPnl dummiesPnl;
    private final RadarPnl radarPnl;
    private final SettingsPnl settingsPnl;

    public MainFrame(Level level, LocalPlayer localPlayer, PlayerList playerList, DummyList dummyList, Settings settings) {
        this.level = level;

        playersPnl = new PlayersPnl(playerList);
        JScrollPane playersScrollPnl = new JScrollPane();
        playersScrollPnl.setViewportView(playersPnl);

        dummiesPnl = new DummiesPnl(dummyList);
        JScrollPane dummiesScrollPnl = new JScrollPane();
        dummiesScrollPnl.setViewportView(dummiesPnl);

        radarPnl = new RadarPnl(level, localPlayer, playerList, dummyList);
        JTabbedPane tabbedPane = new JTabbedPane();
        settingsPnl = new SettingsPnl(settings);

        tabbedPane.addTab("PLAYERS", playersScrollPnl);
        tabbedPane.addTab("DUMMIES", dummiesScrollPnl);
        tabbedPane.addTab("RADAR", radarPnl);
        tabbedPane.addTab("SETTINGS", settingsPnl);

        setContentPane(tabbedPane);
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void update(int counter, String msg) {
        if (msg != null) setTitle(msg);
        else setTitle("Counter: " + counter + " Map: " + level.name);
        radarPnl.repaint();
        playersPnl.update();
        dummiesPnl.update();
    }

    public void update(int counter) {
        update(counter, "Counter: " + counter + " Map: " + level.name);
    }
}