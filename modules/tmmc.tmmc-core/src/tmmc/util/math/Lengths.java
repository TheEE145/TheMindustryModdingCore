package tmmc.util.math;

import static tmmc.util.math.PowMath.pow;

public class Lengths {
    public static float len(float x1, float x2, float y1, float y2) {
        return (float) Math.sqrt(pow(x2 - x1) + pow(y2 - y1));
    }
}