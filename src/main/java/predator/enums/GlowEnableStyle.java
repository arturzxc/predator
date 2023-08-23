package predator.enums;

public enum GlowEnableStyle {

    CAUSTIC(5),
    BLOODHOUND(7),
    MAD_MAGGIE(10),
    BRIGHT_GREEN(21);

    public final int value;

    GlowEnableStyle(int value) {
        this.value = value;
    }

    public static GlowEnableStyle findByValue(int value) {
        for (GlowEnableStyle ps : values())
            if (ps.value == value)
                return ps;
        throw new RuntimeException("Could not find preset with the given value: " + value);
    }
}
