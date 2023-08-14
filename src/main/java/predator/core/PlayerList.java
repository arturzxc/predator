package predator.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerList {
    private final List<Player> PLAYERS = new ArrayList<>();

    public PlayerList(LocalPlayer LOCAL_PLAYER) {
        for (int i = 0; i < 65; i++)
            PLAYERS.add(new Player(i, LOCAL_PLAYER));
    }

    public void update() {
        PLAYERS.forEach(Player::update);
    }

    public void reset() {
        PLAYERS.forEach(Player::reset);
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
