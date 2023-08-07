package predator.ui;

import predator.core.Level;
import predator.core.LocalPlayer;
import predator.core.PlayerList;
import predator.features.Sense;

import javax.swing.*;

public class MainFrame extends JFrame {

    private final Level level;
    private final PlayersPnl playersPnl;
    private final RadarPnl radarPnl;
    private final SensePnl sensePnl;

    public MainFrame(Level level, LocalPlayer localPlayer, PlayerList playerList, Sense sense) {
        this.level = level;
        playersPnl = new PlayersPnl(playerList);
        JScrollPane playersScrollPnl = new JScrollPane();
        playersScrollPnl.setViewportView(playersPnl);
        radarPnl = new RadarPnl(level, localPlayer, playerList);
        JTabbedPane tabbedPane = new JTabbedPane();
        sensePnl = new SensePnl(sense);

        tabbedPane.addTab("PLAYERS", playersScrollPnl);
        tabbedPane.addTab("RADAR", radarPnl);
        tabbedPane.addTab("SENSE", sensePnl);

        setContentPane(tabbedPane);
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void update(int counter, String msg) {
        if (msg != null) setTitle(msg);
        else setTitle("Counter: " + counter + " Map: " + level.name);
        repaint();
        radarPnl.repaint();
        playersPnl.update();
    }

    public void update(int counter) {
        update(counter, "Counter: " + counter + " Map: " + level.name);
    }
}