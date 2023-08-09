package predator.features;

import predator.core.DummyList;
import predator.core.PlayerList;
import predator.core.Util;

public class Sense {

    private final PlayerList playerList;
    private final DummyList dummyList;
    public double maxDistance = Util.convertMetersToHammerUnits(300);

    public Sense(PlayerList playerList, DummyList dummyList) {
        this.playerList = playerList;
        this.dummyList = dummyList;
    }

    public void update() {
        playerList.getEnemyPlayers().forEach(p -> {
            if (p.visible) p.glowVisible();
            else p.glowInvisible();
        });

        dummyList.getDummies().forEach(p -> {
            if (p.visible) p.glowVisible();
            else p.glowInvisible();
        });
    }

}
