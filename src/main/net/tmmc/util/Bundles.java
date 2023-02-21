package net.tmmc.util;

import arc.Core;
import mindustry.ctype.UnlockableContent;
import org.jetbrains.annotations.NotNull;

public class Bundles {
    public static @NotNull String propertyRootOf(UnlockableContent content) {
        return Content.contentTypeId(content) + "." + content.name;
    }

    public static @NotNull String namePropOf(UnlockableContent content) {
        return propertyRootOf(content) + ".name";
    }

    public static @NotNull String descriptionPropOf(UnlockableContent content) {
        return propertyRootOf(content) + ".description";
    }

    public static boolean hasName(UnlockableContent content) {
        return Core.bundle.has(namePropOf(content));
    }

    public static boolean hasDescription(UnlockableContent content) {
        return Core.bundle.has(descriptionPropOf(content));
    }
}