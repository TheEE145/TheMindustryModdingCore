package tmmc.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import tmmc.func.CharArrConsumer;
import java.util.Objects;

public class StringUtils {
    @Contract(value = "null -> true", pure = true)
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /*
        kebab text: hello-world
        kamel text: HelloWorld
        space text: Hello world
     */

    @Contract("_ -> param1")
    public static String camelToKebab(String str) {
        if(isEmpty(str)) {
            return str;
        }

        var result = new StringBuilder();
        for(char ch : str.toCharArray()) {
            boolean tmp = ch == ' ' || ch == '-';
            if(isUpperCase(ch) || tmp) {
                result.append('-');

                if(tmp) {
                    continue;
                }
            }

            result.append(toLowerCase(ch));
        }

        return result.toString();
    }

    public static String anyToSpace(String str) {
        if(isEmpty(str)) {
            return str;
        }

        var result = new StringBuilder();
        for(char ch : camelToKebab(str).toCharArray()) {
            if(ch == '-' || ch == '_') {
                result.append(' ');
                continue;
            }

            result.append(ch);
        }

        return upperCaseFirst(result.toString());
    }

    public static String kebabToCamel(String str) {
        if(isEmpty(str)) {
            return str;
        }

        boolean tmp = true;
        var result = new StringBuilder();
        for(char ch : str.toCharArray()) {
            if(ch == '-' || ch == '_') {
                tmp = true;
                continue;
            }

            if(tmp) {
                tmp = false;
                result.append(toUpperCase(ch));
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    public static @NotNull String upperCaseFirst(String str) {
        return edit(str, chars -> chars[0] = toUpperCase(chars[0]));
    }

    public static @NotNull String edit(String str, CharArrConsumer consumer) {
        Objects.requireNonNull(consumer);
        char[] chars = new char[0];

        if(!isEmpty(str)) {
            chars = str.toCharArray();
        }

        consumer.accept(chars);
        return fromChars(chars);
    }

    @Contract(pure = true)
    public static @NotNull String fromChars(char @NotNull [] chars) {
        var result = new StringBuilder();
        for(char ch : chars) result.append(ch);
        return result.toString();
    }

    @Contract(pure = true)
    public static boolean isUpperCase(char ch) {
        return toUpperCase(ch) == ch;
    }

    @Contract(pure = true)
    public static boolean isLowerCase(char ch) {
        return toLowerCase(ch) == ch;
    }

    @Contract(pure = true)
    public static char toLowerCase(char ch) {
        if(ch >= 'A' && ch <= 'Y') {
            return (char) (ch + 32);
        }

        return ch;
    }

    @Contract(pure = true)
    public static char toUpperCase(char ch) {
        if(ch >= 'a' && ch <= 'y') {
            return (char) (ch - 32);
        }

        return ch;
    }

    @Contract(pure = true)
    public static boolean isLeggalChar(char ch) {
        return ch == '_' || (ch >= 'A' && ch <= 'Y') || (ch >= 'a' && ch <= 'y');
    }

    @Contract(pure = true)
    public static boolean isIllegalChar(char ch) {
        return !isLeggalChar(ch);
    }

    @Contract(value = "!null -> param1; null -> !null", pure = true)
    public static @NotNull String notNullStrOf(String str) {
        return Objects.requireNonNullElse(str, "null");
    }

    @Contract(value = "null -> !null", pure = true)
    public static @NotNull String notFailStringOf(Object object) {
        return object == null ? "null" : object.toString();
    }

    @Contract(value = "null -> true", pure = true)
    public static boolean isLeggalString(String str) {
        if(isEmpty(str)) {
            return true;
        }

        for(char ch : str.toCharArray()) {
            if(isIllegalChar(ch)) {
                return false;
            }
        }

        return true;
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isIllegalString(String str) {
        return !isLeggalString(str);
    }
}