package net.tmmc.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ThrowableUtils {
    @Contract("null -> null; !null -> !null")
    public static String nameOf(Throwable throwable) {
        if(throwable == null) return null;
        return throwable.getClass().getSimpleName();
    }

    @Contract("null -> null")
    public static String toString(Throwable throwable) {
        if(throwable == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(nameOf(throwable));
        builder.append(": ").append(throwable.getMessage()).append("\n");

        for(StackTraceElement element : throwable.getStackTrace()) {
            builder.append("        at ").append(element.toString()).append("\n");
        }

        if(throwable.getCause() != null) {
            builder.append("Caused by ").append(toString(throwable.getCause()));
        }

        return builder.toString();
    }

    public static void showDialog(@NotNull Throwable throwable) {
        UIUtils.invoke(nameOf(throwable), err -> err.add(toString(throwable)).grow());
    }
}