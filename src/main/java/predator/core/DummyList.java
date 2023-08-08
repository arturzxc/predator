package predator.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DummyList {
    private final List<Player> DUMMIES = new ArrayList<>();

    public DummyList(LocalPlayer LOCAL_PLAYER) {
        for (int i = 0; i < 15000; i++)
            DUMMIES.add(new Player(i, LOCAL_PLAYER));
    }

    public void update() {
        DUMMIES.forEach(Player::update);
    }

    public void reset() {
        DUMMIES.forEach(Player::reset);
    }

    public List<Player> getDummies() {
        return DUMMIES.stream()
                .filter(e -> e.base != null)
                .filter(e -> e.entityType != null && e.entityType.equals("dynamic_dummie"))
                .collect(Collectors.toList());
    }
}
