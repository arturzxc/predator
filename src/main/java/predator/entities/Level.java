package predator.entities;

import com.sun.jna.Pointer;
import predator.core.Memory;
import predator.core.Offsets;

public class Level {
    public String name;
    public Boolean playable;
    public Boolean isTrainingArea;

    public void update() {
        name = Memory.readString(Offsets.OFF_REGION.share(Pointer.nativeValue(Offsets.OFF_LEVEL_NAME)), 32);
        playable = !name.isEmpty() && !name.equals("mp_lobby");
        isTrainingArea = name.equals("mp_rr_canyonlands_staging_mu1");
    }
}
