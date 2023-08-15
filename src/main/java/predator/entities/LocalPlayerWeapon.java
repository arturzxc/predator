package predator.entities;

import com.sun.jna.Pointer;
import predator.core.Memory;
import predator.core.Offsets;

public class LocalPlayerWeapon {

    private final LocalPlayer localPlayer;
    public Pointer base;
    public Integer index;
    public Boolean semiAuto;

    public LocalPlayerWeapon(LocalPlayer localPlayer) {
        this.localPlayer = localPlayer;
    }

    public void update() {
        if (localPlayer.base == null) return;
        Pointer localPlayerWeaponHandle = Memory.resolvePointer(localPlayer.base.share(Pointer.nativeValue(Offsets.OFF_WEAPON)));
        long a = Pointer.nativeValue(localPlayerWeaponHandle);//get value as long
        a &= 0xffff; //apply mask. Don't know why, I guess some voodoo shit
        a = a << 5; //apply left shift. Don't know why, I guess some voodoo shit again
        Pointer weaponHandle = new Pointer(a);
        Pointer entityListWeaponPointer = Offsets.OFF_REGION
                .share(Pointer.nativeValue(Offsets.OFF_ENTITY_LIST))
                .share(Pointer.nativeValue(weaponHandle));
        base = Memory.resolvePointer(entityListWeaponPointer);
        if (base == null) return;
        index = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_WEAPON_NAME_INDEX)));
        semiAuto = Memory.readInteger(base.share(Pointer.nativeValue(Offsets.OFF_WEAPON_IS_SEMI))) > 0;

    }
}
