package predator.enums;

public enum GlowModeBodyStyle {

    NONE(0),
    NOT_SURE_1(-124),
    NOT_SURE_2(-123),
    NOT_SURE_3(-122),
    NOT_SURE_4(-120),
    NOT_SURE_5(-119),
    NOT_SURE_6(-85),
    NOT_SURE_7_NOT_THROUGH_WALLS(112),
    NOT_SURE_8(114),
    NOT_SURE_9(117),
    PINK(1),
    PINK_NOT_THROUGH_WALLS(110),
    MY_COLOR_ONLY_THROUGH_WALLS(2),
    RED_VISIBLE_GREEN_INVISIBLE_FLASHING(3),
    PULSING_LINE_GLOW(12),
    PULSING_LINE(13),
    BLACK(75),
    WAVE(103),
    MY_COLOR_BRIGHT(109),
    MY_COLOR_2_BRIGHT(118),
    SHARP_PULSING(124),
    SHARP_PULSING_THROUGH_WALLS(126);

    public final int value;

    GlowModeBodyStyle(int value) {
        this.value = value;
    }

    public static GlowModeBodyStyle findByValue(int value) {
        for (GlowModeBodyStyle ps : values())
            if (ps.value == value)
                return ps;
        throw new RuntimeException("Could not find enum with the given value: " + value);
    }
}
