package predator.structs;

public class GlowMode {

    public final Byte insideSlot;
    public final Byte outlineSlot;
    public final Byte outlineRadius;
    public final Byte state;
    public final Byte entityVisible;
    public final Byte afterPostPr;

    public GlowMode(Byte insideSlot, Byte outlineSlot, Byte outlineRadius, Byte state, Byte entityVisible, Byte afterPostPr) {
        this.insideSlot = insideSlot;
        this.outlineSlot = outlineSlot;
        this.outlineRadius = outlineRadius;
        this.state = state;
        this.entityVisible = entityVisible;
        this.afterPostPr = afterPostPr;
    }

    @Override
    public String toString() {
        return "GlowMode{" +
                "insideSlot=" + insideSlot +
                ", outlineSlot=" + outlineSlot +
                ", outlineRadius=" + outlineRadius +
                ", state=" + state +
                ", entityVisible=" + entityVisible +
                ", afterPostPr=" + afterPostPr +
                '}';
    }
}
