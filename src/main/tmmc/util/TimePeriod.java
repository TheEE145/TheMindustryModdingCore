package tmmc.util;

import arc.util.Time;
import org.jetbrains.annotations.Contract;

public class TimePeriod {
    @Contract(pure = true)
    public static boolean isTimePeriod(float period) {
        return Math.floor(Time.globalTime % period) == 0;
    }

    @Contract(pure = true)
    public static boolean isTimePeriod(int period) {
        return Math.floor(Time.globalTime) % period == 0;
    }
}