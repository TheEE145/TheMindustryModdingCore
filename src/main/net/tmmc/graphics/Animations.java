package net.tmmc.graphics;

import arc.graphics.g2d.TextureRegion;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Animations {
    @Contract("_ -> new")
    public static @NotNull AnimatedTextureRegion create(TextureRegion[] regions) {
        return new AnimatedTextureRegion(regions);
    }

    @Contract("null, _ -> null; !null, null -> null")
    public static AnimatedTextureRegion create(ModAtlas atlas, String prefix) {
        if(atlas == null || prefix == null) return null;
        return create(atlas.frames(prefix));
    }

    @Contract("_, _, _, _ -> new")
    public static @NotNull AnimatedTextureRegion create(TextureRegion assets, int w, int h, int tileScale) {
        return create(Splitter.getRegions(assets, w, h, tileScale));
    }
}