package predator.features;

import predator.core.Player;

import java.util.List;

public class Sense {

    private final List<Player> players;

    public Sense(List<Player> players) {
        this.players = players;
    }

    public void update() {
        for (Player p : players.stream()
                .filter(p -> p.base != null)
                .filter(p -> p.visible != null)
                .filter(p -> p.isFriendlyPlayer != null && !p.isFriendlyPlayer)
                .toList()) {
            if (p.visible) p.glowCaustic();
            else p.glowMadMaggie();
        }
    }

}
