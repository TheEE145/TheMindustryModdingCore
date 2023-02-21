package tmmc.util;

import org.jetbrains.annotations.Contract;

public class BoolInt {
    @Contract(pure = true)
    public static int toInt(boolean bool) {
        return bool ? 1 : 0;
    }
}