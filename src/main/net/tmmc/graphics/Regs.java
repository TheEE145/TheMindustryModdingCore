package net.tmmc.graphics;

import arc.graphics.Texture;
import arc.graphics.g2d.TextureRegion;
import arc.scene.style.TextureRegionDrawable;
import org.jetbrains.annotations.Contract;

public class Regs {
    @Contract(value = "null -> null", pure = true)
    public static Texture of(TextureRegion region) {
        return region == null ? null : region.texture;
    }

    @Contract("null -> null")
    public static TextureRegion of(TextureRegionDrawable region) {
        return region == null ? null : region.getRegion();
    }

    @Contract("null -> null")
    public static Texture ofDrawable(TextureRegionDrawable region) {
        return region == null ? null : of(of(region));
    }

    @Contract("null -> null; !null -> new")
    public static TextureRegionDrawable to(TextureRegion region) {
        return region == null ? null : new TextureRegionDrawable(region);
    }

    @Contract("null -> null; !null -> new")
    public static TextureRegion to(Texture texture) {
        return texture == null ? null : new TextureRegion(texture);
    }

    @Contract("null -> null")
    public static TextureRegionDrawable toDrawable(Texture texture) {
        return texture == null ? null : to(to(texture));
    }
}