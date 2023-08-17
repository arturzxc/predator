package predator.entities;

import predator.core.Settings;

import java.util.ArrayList;
import java.util.List;

public class DummyList {

    private final List<Player> DUMMIES = new ArrayList<>();
    private final LocalPlayer localPlayer;
    private final Settings settings;

    public DummyList(LocalPlayer localPlayer, Settings settings) {
        this.localPlayer = localPlayer;
        this.settings = settings;
        for (int i = 0; i < 15000; i++)
            DUMMIES.add(new Player(i, localPlayer, settings));
    }

    public void update() {
        if (DUMMIES.size() < 4)
            for (int i = 0; i < 15000; i++)
                DUMMIES.add(new Player(i, localPlayer, settings));

        DUMMIES.forEach(Player::update);
        DUMMIES.removeIf(e -> e.base == null || !"dynamic_dummie".equals(e.entityType));
    }

    public List<Player> getDummies() {
        return DUMMIES.stream()
                .filter(e -> e.base != null)
                .filter(e -> "dynamic_dummie".equals(e.entityType))
                .toList();
    }

}
