package predator.entities;

import predator.core.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerList {

    private final Settings settings;
    private final List<Player> PLAYERS = new ArrayList<>();

    public PlayerList(LocalPlayer LOCAL_PLAYER, Settings settings) {
        this.settings = settings;
        for (int i = 0; i < 100; i++)
            PLAYERS.add(new Player(i, LOCAL_PLAYER, settings));
    }

    public void update() {
        PLAYERS.forEach(Player::update);
    }

    public List<Player> getPlayers() {
        return PLAYERS.stream()
                .filter(e -> e.base != null)
                .filter(e -> e.entityType != null && e.entityType.equals("player"))
                .collect(Collectors.toList());
    }

    public List<Player> getEnemyPlayers() {
        return PLAYERS.stream()
                .filter(e -> e.base != null)
                .filter(e -> e.entityType != null && e.entityType.equals("player"))
                .filter(p -> p.isFriendlyPlayer != null && !p.isFriendlyPlayer)
                .collect(Collectors.toList());
    }


    public List<Player> getFriendlyPlayers() {
        return PLAYERS.stream()
                .filter(e -> e.base != null)
                .filter(e -> e.entityType != null && e.entityType.equals("player"))
                .filter(p -> p.isFriendlyPlayer != null && p.isFriendlyPlayer)
                .collect(Collectors.toList());
    }
}
