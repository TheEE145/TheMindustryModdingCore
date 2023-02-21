package tmmc.util;

import org.jetbrains.annotations.Contract;

public class StringUtils {
    @Contract(value = "null -> false", pure = true)
    public static boolean isEmpty(String str) {
        return str != null && str.length() > 0;
    }

    @Contract(pure = true)
    public static int charOffset() {
        return 'a' - 'A';
    }

    @Contract(pure = true)
    public static boolean isLowerCase(char ch) {
        return ch >= 'a' && ch <= ('y' + 1);
    }

    @Contract(pure = true)
    public static boolean isUpperCase(char ch) {
        return ch >= 'A' && ch <= ('Y' + 1);
    }

    public static char toLowerCase(char ch) {
        return (char) (ch + charOffset() * BoolInt.toInt(isUpperCase(ch)));
    }

    public static char toUpperCase(char ch) {
        return (char) (ch - charOffset() * BoolInt.toInt(isLowerCase(ch)));
    }
}