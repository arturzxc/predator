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
    private final LocalPlayer localPlayer;
    private final RadarPnl radarPnl;

    public MainFrame(Level level, LocalPlayer localPlayer, PlayerList playerList, DummyList dummyList, Settings settings) {
        this.level = level;
        this.localPlayer = localPlayer;

        //main container
        JTabbedPane tabbedPane = new JTabbedPane();
        setContentPane(tabbedPane);

        //sense settings panel
        tabbedPane.addTab("SENSE", new JScrollPane(new SensePnl(settings)));

        //players panel
        playersPnl = new PlayersPnl(playerList);
        tabbedPane.addTab("PLAYERS", new JScrollPane(playersPnl));

        //dummy panel
        dummiesPnl = new DummiesPnl(dummyList);
        tabbedPane.addTab("DUMMIES", new JScrollPane(dummiesPnl));

        //radar panel
        radarPnl = new RadarPnl(level, localPlayer, playerList, dummyList);
        tabbedPane.addTab("RADAR", radarPnl);

        //finalise and show
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void update(int counter, String msg) {
        if (localPlayer.base != null)
            setTitle(localPlayer.localOrigin.toString() + " | " + localPlayer.viewAngles.toString());
        radarPnl.repaint();
        playersPnl.update();
        dummiesPnl.update();
    }

    public void update(int counter) {
        update(counter, "Counter: " + counter + " Map: " + level.name);
    }
}