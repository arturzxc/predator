package predator.features;

import predator.core.Settings;
import predator.core.UnitConverter;
import predator.entities.DummyList;
import predator.entities.PlayerList;

public class Sense {

    private final PlayerList playerList;
    private final DummyList dummyList;
    private final Settings settings;

    public Sense(PlayerList playerList, DummyList dummyList, Settings settings) {
        this.playerList = playerList;
        this.dummyList = dummyList;
        this.settings = settings;
    }

    public void update() {
        if (!settings.readBoolean(Settings.Key.SENSE_ON)) return;
        final int MAX_DISTANCE = settings.readInteger(Settings.Key.SENSE_MAX_DISTANCE_METERS);
        playerList.getEnemyPlayers().forEach(p -> {
            if (p.distanceToLocalPlayer > UnitConverter.convertMetersToHammerUnits(MAX_DISTANCE)) {
                p.glowStop();
            } else {
                if (settings.readBoolean(Settings.Key.SENSE_CUSTOM_COLOR_ON)) {
                    if (p.visible) p.glowVisibleCustom();
                    else p.glowInvisibleCustom();
                } else {
                    if (p.visible) p.glowVisiblePreset();
                    else p.glowInVisiblePreset();
                }
            }
        });
        dummyList.getDummies().forEach(p -> {
            if (p.distanceToLocalPlayer > UnitConverter.convertMetersToHammerUnits(MAX_DISTANCE)) {
                p.glowStop();
            } else {
                if (p.visible) p.glowVisiblePreset();
                else p.glowInVisiblePreset();
            }
        });
    }

}
