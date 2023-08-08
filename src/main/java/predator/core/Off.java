package predator.core;

import com.sun.jna.Pointer;

public class Off {
    //[Region]
    public static final Pointer REGION = new Pointer(0x140000000L);                                                     //[Region]

    //[Miscellaneous]
    //------------------------------------------------------------------------------------------------------------------
    public static final Pointer LEVEL_NAME = new Pointer(0x16eed90);                                                    //[Miscellaneous]->LevelName
    public static final Pointer ENTITY_LIST = new Pointer(0x1e743a8);                                                   //[Miscellaneous]->cl_entitylist
    public static final Pointer LOCAL_PLAYER = new Pointer(0x02224440 + 0xA8);                                          //[ConVars]->player_overheat_time_to_overheat + 0xA8

    //[RecvTable.DT_BaseEntity]
    //------------------------------------------------------------------------------------------------------------------
    public static final Pointer LOCAL_ORIGIN = new Pointer(0x0054);                                                     //[RecvTable.DT_BaseEntity]->m_localOrigin
    public static final Pointer TEAM_NUMBER = new Pointer(0x0480);                                                      //[RecvTable.DT_BaseEntity]->m_iTeamNum
    public static final Pointer SHIELD_HEALTH_MAX = new Pointer(0x01a4);                                                //[RecvTable.DT_BaseEntity]->m_shieldHealthMax
    public static final Pointer NAME = new Pointer(0x05c1);                                                             //[RecvTable.DT_BaseEntity]->m_iName

    //[RecvTable.DT_Player]
    //------------------------------------------------------------------------------------------------------------------
    public static final Pointer ZOOMING = new Pointer(0x1c81);                                                          //[RecvTable.DT_Player]->m_bZooming
    public static final Pointer LIFE_STATE = new Pointer(0x07d0);                                                       //[RecvTable.DT_Player]->m_lifeState
    public static final Pointer BLEEDOUT_STATE = new Pointer(0x2790);                                                   //[RecvTable.DT_Player]->m_bleedoutState
    public static final Pointer AMMO_POOL_CAPACITY = new Pointer(0x25e4);                                               //[RecvTable.DT_Player]->m_ammoPoolCapacity
    public static final Pointer VIEW_ANGLE = AMMO_POOL_CAPACITY.share(-0x14);                                     //AMMO_POOL_CAPACITY - 0x14

    //[RecvTable.DT_BaseCombatCharacter]
    //------------------------------------------------------------------------------------------------------------------
    public static final Pointer HUD_INFO_VISIBILITY_TEST_ALWAYS_PASSES = new Pointer(0x1a9d);                           //[RecvTable.DT_BaseCombatCharacter]->m_hudInfo_visibilityTestAlwaysPasses
    public static final Pointer LAST_VISIBLE_TIME = HUD_INFO_VISIBILITY_TEST_ALWAYS_PASSES.share(0x3);            //HUD_INFO_VISIBILITY_TEST_ALWAYS_PASSES + 0x3
    public static final Pointer LAST_CROSSHAIRS_TIME = LAST_VISIBLE_TIME.share(0x8);                              //LAST_VISIBLE_TIME + 0x8

    //[RecvTable.DT_HighlightSettings]
    //------------------------------------------------------------------------------------------------------------------
    public static final Pointer HIGHLIGHT_SERVER_CONTEXT_ID = new Pointer(0x03f0);                                      //[RecvTable.DT_HighlightSettings]->m_highlightServerContextID
    public static final Pointer GLOW_ENABLE = HIGHLIGHT_SERVER_CONTEXT_ID.share(0x8);                             //HIGHLIGHT_SERVER_CONTEXT_ID + 0x8
    public static final Pointer GLOW_THROUGH_WALL = HIGHLIGHT_SERVER_CONTEXT_ID.share(0x10);                      //HIGHLIGHT_SERVER_CONTEXT_ID + 0x10

    //[Buttons]
    //------------------------------------------------------------------------------------------------------------------
    public static final Pointer IN_ATTACK = new Pointer(0x07472e98);                                                    //[Buttons]->in_attack
}
