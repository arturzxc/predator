package predator.entities;

import predator.core.Settings;

import java.util.ArrayList;
import java.util.List;

public class DummyList {

    private final LocalPlayer localPlayer;
    private final Settings settings;
    private final List<Player> DUMMIES = new ArrayList<>();

    public DummyList(LocalPlayer localPlayer, Settings settings) {
        this.localPlayer = localPlayer;
        this.settings = settings;
    }

    public void update() {
        //There are 4 actual non-fake dummies in the range. If we are missing any then re-acquire them
        if (DUMMIES.size() < 4) {
            DUMMIES.clear();
            for (int i = 0; i < 15000; i++)
                DUMMIES.add(new Player(i, localPlayer, settings));
        }

        //update the values and get rid of baseless entities
        DUMMIES.forEach(Player::update);
        DUMMIES.removeIf(e -> e.base == null || !"dynamic_dummie".equals(e.entityType));
    }

    public List<Player> getDummies() {
        return DUMMIES;
    }

}
