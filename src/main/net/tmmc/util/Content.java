package net.tmmc.util;

import mindustry.ctype.UnlockableContent;
import mindustry.Vars;

import java.util.Objects;
import arc.func.Cons;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Content {
    @Contract("null -> fail")
    public static @NotNull String contentTypeId(UnlockableContent content) {
        return Objects.requireNonNull(content).getContentType().name().toLowerCase();
    }

    public static void each(Cons<UnlockableContent> cons) {
        Vars.content.each(cont -> {
            if(cont instanceof UnlockableContent) {
                cons.get((UnlockableContent) cont);
            }
        });
    }
}