package tmmc.util;

import org.jetbrains.annotations.Contract;
import arc.util.Time;

public class TimeCycle {
    @Contract(pure = true)
    public static boolean every(int sec) {
        return Math.floor(Time.globalTime) % sec == 0;
    }
}