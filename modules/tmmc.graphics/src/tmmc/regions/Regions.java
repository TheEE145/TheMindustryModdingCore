package tmmc.regions;

import arc.Core;
import arc.graphics.Texture;
import arc.graphics.g2d.TextureRegion;
import arc.scene.style.TextureRegionDrawable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import mindustry.mod.Mods;
import java.util.Objects;

public class Regions {
    public static Mods.LoadedMod loadedMod = null;
    private static TextureRegion errorRegion = null;

    @Contract("null -> fail; _ -> new")
    public static @NotNull TextureRegionDrawable toDrawable(TextureRegion reg) {
        return new TextureRegionDrawable(Objects.requireNonNull(reg));
    }

    @Contract(pure = true)
    public static Texture textureOf(TextureRegion reg) {
        return Objects.requireNonNull(reg).texture;
    }

    @Contract(pure = true)
    public static @NotNull TextureRegion errorRegion() {
        if(errorRegion == null) {
            errorRegion = Core.atlas.find("error");
        }

        return errorRegion;
    }

    @Contract(pure = true)
    private static @NotNull String prefix() {
        return loadedMod == null ? "" : loadedMod.name + "-";
    }

    @Contract("null -> true")
    public static boolean isErrorRegion(TextureRegion reg) {
        return reg == null || reg.equals(errorRegion());
    }

    public static boolean hasRegion(String prefix) {
        return Core.atlas.has(prefix) || Core.atlas.has(prefix() + prefix);
    }

    public static @NotNull TextureRegion getRegion(String prefix, TextureRegion def) {
        TextureRegion region = getRegion(prefix);
        return isErrorRegion(region) ? def : region;
    }

    public static @NotNull TextureRegion getRegion(String prefix) {
        if(prefix == null || prefix.equals("error")) {
            return errorRegion();
        }

        TextureRegion reg = Core.atlas.find(prefix() + prefix, Core.atlas.find(prefix));
        return reg == null ? errorRegion() : reg;
    }
}