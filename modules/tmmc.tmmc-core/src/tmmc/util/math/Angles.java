package tmmc.util.math;

import org.jetbrains.annotations.Contract;

public class Angles {
    public static final float theta = (float) (Math.PI * 2);

    @Contract(pure = true)
    public static float thx(float angle, float rad) {
        return (float) (Math.cos((angle / 360) * theta) * rad);
    }

    @Contract(pure = true)
    public static float thy(float angle, float rad) {
        return (float) (Math.sin((angle / 360) * theta) * rad);
    }
}