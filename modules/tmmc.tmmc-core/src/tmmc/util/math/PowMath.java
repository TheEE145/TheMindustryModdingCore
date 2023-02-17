package tmmc.util.math;

import org.jetbrains.annotations.Contract;

public class PowMath {
    @Contract(pure = true)
    public static float pow(float f) {
        return f * f;
    }

    @Contract(pure = true)
    public static double pow(double d) {
        return d * d;
    }

    @Contract(pure = true)
    public static long pow(long l) {
        return l * l;
    }

    @Contract(pure = true)
    public static int pow(int i) {
        return i * i;
    }

    @Contract(pure = true)
    public static float pow3(float f) {
        return f * f * f;
    }

    @Contract(pure = true)
    public static double pow3(double d) {
        return d * d * d;
    }

    @Contract(pure = true)
    public static long pow3(long l) {
        return l * l * l;
    }

    @Contract(pure = true)
    public static int pow3(int i) {
        return i * i * i;
    }
}