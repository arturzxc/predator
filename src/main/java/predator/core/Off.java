package predator.core;

import com.sun.jna.Pointer;

public class Off {
    public static final Pointer REGION = new Pointer(0x140000000L);                                                     //[Region]
    public static final Pointer LOCAL_PLAYER = new Pointer(0x22048C8);
    public static final Pointer LEVEL_NAME = new Pointer(0x16fb240);
    public static final Pointer LOCAL_ORIGIN = new Pointer(0x0158);
    public static final Pointer ENTITY_LIST = new Pointer(0x1e54dc8);
    public static final Pointer ZOOMING = new Pointer(0x1c51);
    public static final Pointer LIFE_STATE = new Pointer(0x0798);                                                       //[RecvTable.DT_Player]->m_lifeState
    public static final Pointer BLEEDOUT_STATE = new Pointer(0x2750);                                                   //[RecvTable.DT_Player]->m_bleedoutState
    public static final Pointer TEAM_NUMBER = new Pointer(0x044cL);
    public static final Pointer SHIELD_HEALTH_MAX = new Pointer(0x0174);
    public static final Pointer VIEW_ANGLE = new Pointer(0x25b4 - 0x14);
    public static final Pointer GLOW_ENABLE = new Pointer(0x03c0 + 0x8);
    public static final Pointer GLOW_THROUGH_WALL = new Pointer(0x03c0 + 0x10);
    public static final Pointer LAST_VISIBLE_TIME = new Pointer(0x1a6d + 0x3);                                          //[RecvTable.DT_BaseCombatCharacter]->m_hudInfo_visibilityTestAlwaysPasses + 0x3
    public static final Pointer LAST_CROSSHAIRS_TIME = LAST_VISIBLE_TIME.share(0x8);                              //LAST_VISIBLE_TIME+0x8
    public static final Pointer NAME = new Pointer(0x0589);                                                             //[RecvTable.DT_BaseEntity]->m_iName

    //[buttons]
    public static final Pointer IN_ATTACK = new Pointer(0x0743e510);
}
