package predator.core;

import java.util.ArrayList;
import java.util.List;

public class DummyList {

    private final LocalPlayer localPlayer;
    private final List<Player> DUMMIES = new ArrayList<>();

    public DummyList(LocalPlayer localPlayer) {
        this.localPlayer = localPlayer;
    }

    public void update() {
        //if all dummies are dead then re-acquire the newly spawned ones.
        //We do it this way so that we don't look over 15k entities all the time
        if (DUMMIES.isEmpty())
            for (int i = 0; i < 15000; i++)
                DUMMIES.add(new Player(i, localPlayer));

        //update the values and get rid of baseless entities
        DUMMIES.forEach(Player::update);
        DUMMIES.removeIf(e -> e.base == null || !"dynamic_dummie".equals(e.entityType));
    }

    public void reset() {
        DUMMIES.forEach(Player::reset);
    }

    public List<Player> getDummies() {
        return DUMMIES;
    }

}
