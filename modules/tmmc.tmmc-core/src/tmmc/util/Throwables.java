package tmmc.util;

import arc.graphics.Color;
import mindustry.ui.dialogs.BaseDialog;
import org.jetbrains.annotations.NotNull;

public class Throwables {
    public static @NotNull String toString(@NotNull Throwable throwable) {
        StringBuilder builder = new StringBuilder(throwable.getClass().getSimpleName());
        builder.append(": ").append(throwable.getMessage()).append("\n");

        for(StackTraceElement element : throwable.getStackTrace()) {
            builder.append("        at ").append(element.toString()).append("\n");
        }

        if(throwable.getCause() != null) {
            builder.append("Caused by ").append(toString(throwable.getCause()));
        }

        return builder.toString();
    }

    public static void showThrowable(@NotNull Throwable throwable) {
        BaseDialog dialog = new BaseDialog(throwable.toString());
        dialog.add(toString(throwable)).color(Color.red).center();
        dialog.show();
    }
}