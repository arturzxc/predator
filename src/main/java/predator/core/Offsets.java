package predator.core;

import com.sun.jna.Pointer;

public class Offsets {
    //Core
    //------------------------------------------------------------------------------------------------------------------
    public static final Pointer OFF_REGION = new Pointer(0x140000000L);                                                 //Always the same
    public static final Pointer OFF_LEVEL_NAME = new Pointer(0x16eed90);                                                //[Miscellaneous]->LevelName
    public static final Pointer OFF_ENTITY_LIST = new Pointer(0x1e743a8);                                               //[Miscellaneous]->cl_entitylist
    public static final Pointer OFF_LOCAL_PLAYER = new Pointer(0x2224528);                                              //[?]->?
    public static final Pointer OFF_IN_ATTACK = new Pointer(0x07472e98);                                                //[Buttons]->in_attack
    //Player
    //------------------------------------------------------------------------------------------------------------------
    public static final Pointer OFF_LOCAL_ORIGIN = new Pointer(0x017c);                                                 //[RecvTable.DT_BaseEntity]->m_localOrigin
    public static final Pointer OFF_TEAM_NUMBER = new Pointer(0x0480);                                                  //[RecvTable.DT_BaseEntity]->m_iTeamNum
    public static final Pointer OFF_SHIELD_HEALTH_MAX = new Pointer(0x01a4);                                            //[RecvTable.DT_BaseEntity]->m_shieldHealthMax
    public static final Pointer OFF_NAME = new Pointer(0x05c1);                                                         //[RecvTable.DT_BaseEntity]->m_iName
    public static final Pointer OFF_ZOOMING = new Pointer(0x1c81);                                                      //[RecvTable.DT_Player]->m_bZooming
    public static final Pointer OFF_LIFE_STATE = new Pointer(0x07d0);                                                   //[RecvTable.DT_Player]->m_lifeState
    public static final Pointer OFF_BLEEDOUT_STATE = new Pointer(0x2790);                                               //[RecvTable.DT_Player]->m_bleedoutState
    public static final Pointer OFF_VIEW_ANGLE = new Pointer(0x25e4 - 0x14);                                            //[RecvTable.DT_Player]->m_ammoPoolCapacity - 0x14
    public static final Pointer OFF_DUCK_STATE = new Pointer(0x2a8c);                                                   //[RecvTable.DT_Player]->m_duckState
    public static final Pointer OFF_LAST_VISIBLE_TIME = new Pointer(0x1a9d + 0x3);                                      //[RecvTable.DT_BaseCombatCharacter]->m_hudInfo_visibilityTestAlwaysPasses + 0x3
    public static final Pointer OFF_LAST_CROSSHAIRS_TIME = new Pointer(0x1a9d + 0x3 + 0x8);                             //[RecvTable.DT_BaseCombatCharacter]->m_hudInfo_visibilityTestAlwaysPasses + 0x3 +0x8

    //Current weapon
    //------------------------------------------------------------------------------------------------------------------
    public static final Pointer OFF_WEAPON = new Pointer(0x1a44);                                                       //[RecvTable.DT_Player]->m_latestPrimaryWeapons
    public static final Pointer OFF_WEAPON_NAME_INDEX = new Pointer(0x1888);                                            //[RecvTable.DT_WeaponX]->m_weaponNameIndex
    public static final Pointer OFF_WEAPON_IS_SEMI = new Pointer(0x1a9d + 0x3 + 0x018c);                                //[RecvTable.DT_BaseCombatCharacter]->m_hudInfo_visibilityTestAlwaysPasses + 0x3 + 0x018c
    //Glow
    //------------------------------------------------------------------------------------------------------------------
    public static final Pointer OFF_GLOW_ENABLE = new Pointer(0x03f0 + 0x8);                                            //[RecvTable.DT_HighlightSettings]->m_highlightServerContextID + 0x8
    public static final Pointer OFF_GLOW_THROUGH_WALL = new Pointer(0x03f0 + 0x10);                                     //[RecvTable.DT_HighlightSettings]->m_highlightServerContextID + 0x10
    public static final Pointer OFF_GLOW_COLOR = new Pointer(0x200);                                                    //[?]->?
    public static final Pointer OFF_GLOW_MODE = new Pointer(0x2C0);                                                     //[?]->?
}
