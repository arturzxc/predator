package predator.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerList {
    private final List<Player> PLAYERS = new ArrayList<>();

    public PlayerList(LocalPlayer LOCAL_PLAYER) {
        for (int i = 0; i < 60; i++)
            PLAYERS.add(new Player(i, LOCAL_PLAYER));
    }

    public void update() {
        PLAYERS.forEach(Player::update);
    }

    public void reset() {
        PLAYERS.forEach(Player::reset);
    }

    public Player getLocalPlayer() {
        return PLAYERS.stream()
                .filter(e -> e.base != null)
                .filter(e -> e.isLocalPlayer != null && e.isLocalPlayer)
                .findFirst()
                .orElseThrow();
    }

    public List<Player> getPlayers() {
        return PLAYERS.stream()
                .filter(e -> e.base != null)
                .collect(Collectors.toList());
    }

    public List<Player> getAlivePlayers() {
        return PLAYERS.stream()
                .filter(e -> e.base != null)
                .filter(e -> e.dead != null && !e.dead)
                .collect(Collectors.toList());
    }

    public List<Player> getEnemies() {
        return PLAYERS.stream()
                .filter(e -> e.base != null)
                .filter(p -> p.isFriendlyPlayer != null && !p.isFriendlyPlayer)
                .collect(Collectors.toList());
    }

    public List<Player> getVisibleEnemies() {
        return PLAYERS.stream()
                .filter(e -> e.base != null)
                .filter(p -> p.visible != null && p.visible)
                .filter(p -> p.isFriendlyPlayer != null && !p.isFriendlyPlayer)
                .collect(Collectors.toList());
    }

    public List<Player> getInVisibleEnemies() {
        return PLAYERS.stream()
                .filter(e -> e.base != null)
                .filter(p -> p.visible != null && !p.visible)
                .filter(p -> p.isFriendlyPlayer != null && !p.isFriendlyPlayer)
                .collect(Collectors.toList());
    }

    public List<Player> getVisibleHealthyEnemies() {
        return PLAYERS.stream()
                .filter(e -> e.base != null)
                .filter(p -> p.visible != null && p.visible)
                .filter(p -> p.knocked != null && !p.knocked)
                .filter(p -> p.isFriendlyPlayer != null && !p.isFriendlyPlayer)
                .collect(Collectors.toList());
    }

    public List<Player> getFriendlies() {
        return PLAYERS.stream()
                .filter(e -> e.base != null)
                .filter(p -> p.isFriendlyPlayer != null && p.isFriendlyPlayer)
                .collect(Collectors.toList());
    }
}
