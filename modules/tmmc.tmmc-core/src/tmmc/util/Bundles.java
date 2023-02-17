package tmmc.util;

import mindustry.ctype.UnlockableContent;
import java.util.Objects;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Bundles {
    @Contract("null -> fail")
    public static @NotNull String propertyOf(UnlockableContent content) {
        Objects.requireNonNull(content);
        return content.getContentType().name().toLowerCase() + "." + content.name;
    }

    @Contract("null -> fail")
    public static @NotNull String namePropertyOf(UnlockableContent content) {
        return propertyOf(content) + ".name";
    }

    @Contract("null -> fail")
    public static @NotNull String descriptionPropertyOf(UnlockableContent content) {
        return propertyOf(content) + ".description";
    }
}