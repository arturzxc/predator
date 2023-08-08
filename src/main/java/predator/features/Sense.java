package predator.features;

import predator.core.PlayerList;
import predator.core.Util;

public class Sense {

    private final PlayerList playerList;
    public double maxDistance = Util.convertMetersToHammerUnits(300);

    public Sense(PlayerList playerList) {
        this.playerList = playerList;
    }

    public void update() {
        playerList.getEnemyPlayers().forEach(p -> {
            if (p.visible) p.glowCaustic();
            else p.glowBloodhound();
        });
    }

}
