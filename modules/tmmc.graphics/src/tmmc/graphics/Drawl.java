package tmmc.graphics;

import arc.graphics.g2d.Draw;
import org.jetbrains.annotations.Contract;

public class Drawl {
    @Contract(pure = true)
    public static float l() {
        return Draw.z();
    }

    public static void l(float l) {
        Draw.z(l);
    }

    public static void lRes(float l) {
        l(l() + l);
    }
}