package predator.enums;

public enum GlowModeBorderStyle {

    NONE(0),
    MY_COLOR_BRIGHT(6),
    MY_COLOR_BRIGHT_FADES(102),
    MY_COLOR_DARK(101),
    MY_COLOR_FADES(104),
    PINK(1),
    BLUE(4),
    GOLD_FLASHING(5),
    GOLD(7),
    BROWN(8),
    GOLD_SINGLE_PIXEL(9),
    WAVE_EFFECT(103),
    RED(107),
    RED_BRIGHT(108),
    BREATHING_EFFECT(110),
    HEARTBEAT_EFFECT(110);

    public final int value;

    GlowModeBorderStyle(int value) {
        this.value = value;
    }

    public static GlowModeBorderStyle findByValue(int value) {
        for (GlowModeBorderStyle ps : values())
            if (ps.value == value)
                return ps;
        throw new RuntimeException("Could not find preset with the given value: " + value);
    }
}
