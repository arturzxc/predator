package predator.core;

import com.sun.jna.Pointer;

public class LocalPlayerWeapon {

    private final LocalPlayer localPlayer;
    public Integer index;
    public Boolean semiAuto;

    public LocalPlayerWeapon(LocalPlayer localPlayer) {
        this.localPlayer = localPlayer;
    }

    public void reset() {
        index = null;
        semiAuto = null;
    }

    public void update() {
        if (localPlayer.base == null) {
            reset();
            return;
        }
        Pointer localPlayerWeaponHandle = Mem.resolvePointer(localPlayer.base.share(Pointer.nativeValue(Off.OFFSET_WEAPON)));
        long a = Pointer.nativeValue(localPlayerWeaponHandle);//get value as long
        a &= 0xffff; //apply mask. Don't know why, I guess some voodoo shit
        a = a << 5; //apply left shift. Don't know why, I guess some voodoo shit again
        Pointer weaponHandle = new Pointer(a);
        Pointer entityListWeaponPointer = Off.REGION
                .share(Pointer.nativeValue(Off.ENTITY_LIST))
                .share(Pointer.nativeValue(weaponHandle));
        Pointer weaponEntity = Mem.resolvePointer(entityListWeaponPointer);
        index = Mem.readInteger(weaponEntity.share(Pointer.nativeValue(Off.OFFSET_WEAPON_NAME_INDEX)));
        semiAuto = Mem.readInteger(weaponEntity.share(Pointer.nativeValue(Off.OFFSET_WEAPON_IS_SEMI))) > 0;
    }
}
