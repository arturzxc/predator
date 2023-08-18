package predator.structs;

public class GlowMode {

    public final Byte a;
    public final Byte b;
    public final Byte c;
    public final Byte d;


    public GlowMode(Byte a, Byte b, Byte c, Byte d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public boolean empty() {
        return a == 0 || b == 0 || c == 0 || d == 0;
    }

    @Override
    public String toString() {
        return "GlowMode{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                '}';
    }
}
