package predator;

import predator.core.Level;
import predator.core.LocalPlayer;
import predator.core.Mem;
import predator.core.Player;
import predator.features.PlayersInspector;
import predator.features.Radar;
import predator.features.Sense;
import predator.features.TriggerBot;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {

    private final Level level;
    private final PlayersInspector playersPnl;
    private final Radar radarPnl;

    public Main(Level level, LocalPlayer localPlayer, List<Player> players) {
        this.level = level;
        playersPnl = new PlayersInspector(players);
        JScrollPane playersScrollPnl = new JScrollPane();
        playersScrollPnl.setViewportView(playersPnl);
        radarPnl = new Radar(level, localPlayer, players);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("PLAYERS", playersScrollPnl);
        tabbedPane.addTab("RADAR", radarPnl);
        setContentPane(tabbedPane);
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void update(int counter) {
        setTitle("Counter: " + counter + " Map: " + level.name);
        repaint();
        radarPnl.repaint();
        playersPnl.update();
    }


    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Mem.findGamePID();

        final Level level = new Level();
        final LocalPlayer localPlayer = new LocalPlayer();
        final List<Player> players = new ArrayList<>();
        for (int i = 0; i < 60; i++) players.add(new Player(i, localPlayer));
        final Sense sense = new Sense(players);
        final TriggerBot triggerBot = new TriggerBot(localPlayer, players);
        final Main ui = new Main(level, localPlayer, players);

        int counter = 0;
        while (true) {
            try {
                level.update();
                if (level.playable) {
                    localPlayer.update();
                    players.forEach(Player::update);
                    sense.update();
                    triggerBot.update();
                } else {
                    localPlayer.resetFields();
                    players.forEach(Player::resetFields);
                }
            } catch (Exception ex) {
                System.out.println("Something went wrong, waiting 10 seconds and retrying...");
                ex.printStackTrace();
                Thread.sleep(1000 * 10);
                Mem.findGamePID();
            }
            ui.update(counter);
            counter = (counter >= 1000) ? 0 : counter + 1;
            Thread.sleep(16);
        }
    }
}