package predator.core;

import com.sun.jna.Pointer;

public class Level {
    public String name;
    public Boolean playable;
    public Boolean isTrainingArea;

    public void update() {
        name = Mem.readString(Off.REGION.share(Pointer.nativeValue(Off.LEVEL_NAME)), 32);
        playable = !name.isEmpty() && !name.equals("mp_lobby");
        isTrainingArea = name.equals("mp_rr_canyonlands_staging_mu1");
    }
}
