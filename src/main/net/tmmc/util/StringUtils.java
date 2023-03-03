package net.tmmc.util;

import arc.util.Strings;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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

    public static @NotNull String toSpace(String name) {
        name = Strings.camelToKebab(name);
        var builder = new StringBuilder();
        char[] arr = name.toCharArray();
        builder.append(toUpperCase(arr[0]));
        for(int i = 1; i < arr.length; i++) {
            char ch = arr[i];
            if(ch == '-' || ch == '_') {
                builder.append(' ');
                continue;
            }

            builder.append(ch);
        }
        return builder.toString();
    }

    public static char toLowerCase(char ch) {
        return (char) (ch + charOffset() * BoolInt.toInt(isUpperCase(ch)));
    }

    public static char toUpperCase(char ch) {
        return (char) (ch - charOffset() * BoolInt.toInt(isLowerCase(ch)));
    }

    public static char upperCaseOf(String msg, int index) {
        return msg == null || index < 0 ? 'A' : toUpperCase(msg.charAt(index));
    }

    public static char lowerCaseOf(String msg, int index) {
        return msg == null || index < 0 ? 'a' : toLowerCase(msg.charAt(index));
    }

    @Contract(pure = true)
    public static @NotNull String prefixate(String prefix) {
        return "[" + prefix + "]";
    }

    @Contract(pure = true)
    public static @NotNull String prefixateSpace(String prefix) {
        return prefixate(prefix) + " ";
    }
}