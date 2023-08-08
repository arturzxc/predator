package predator.features;

import predator.core.Player;
import predator.core.PlayerList;
import predator.core.Util;

public class Sense {

    private final PlayerList playerList;
    private double maxDistance = Util.convertMetersToHammerUnits(300);

    public Sense(PlayerList playerList) {
        this.playerList = playerList;
    }

    public void update() {
        playerList.getVisibleEnemies().forEach(Player::glowCaustic);
        playerList.getInVisibleEnemies().forEach(Player::glowBloodhound);
    }

    public int getMaxDistanceInMeters() {
        return Util.convertHammerUnitsToMeters(maxDistance);
    }

    public void setMaxDistanceInMeters(int maxDistance) {
        this.maxDistance = Util.convertMetersToHammerUnits(maxDistance);
    }
}
