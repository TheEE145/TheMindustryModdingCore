package tmmc.util;

import mindustry.ctype.Content;
import mindustry.ctype.UnlockableContent;

import java.util.Objects;
import java.util.function.Consumer;

import static mindustry.Vars.*;

public class ContentLoad {
    public static void each(Consumer<Content> consumer) {
        content.each(Objects.requireNonNull(consumer)::accept);
    }

    public static void eachUnlockable(Consumer<UnlockableContent> consumer) {
        each(cont -> {
            if(cont instanceof UnlockableContent) {
                consumer.accept((UnlockableContent) cont);
            }
        });
    }
}