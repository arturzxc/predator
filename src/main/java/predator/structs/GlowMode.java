package predator.structs;

public class GlowMode {

    public final Byte bodyStyle;
    public final Byte borderStyle;
    public final Byte borderWidth;


    public GlowMode(Byte bodyStyle, Byte borderStyle, Byte borderWidth) {
        this.bodyStyle = bodyStyle;
        this.borderStyle = borderStyle;
        this.borderWidth = borderWidth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlowMode glowMode = (GlowMode) o;

        if (!bodyStyle.equals(glowMode.bodyStyle)) return false;
        if (!borderStyle.equals(glowMode.borderStyle)) return false;
        return borderWidth.equals(glowMode.borderWidth);
    }

    @Override
    public int hashCode() {
        int result = bodyStyle.hashCode();
        result = 31 * result + borderStyle.hashCode();
        result = 31 * result + borderWidth.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GlowMode{" +
                "bodyStyle=" + bodyStyle +
                ", borderStyle=" + borderStyle +
                ", borderWidth=" + borderWidth +
                '}';
    }
}
