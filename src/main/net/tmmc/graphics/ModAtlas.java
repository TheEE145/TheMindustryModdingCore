package net.tmmc.graphics;

import arc.graphics.g2d.TextureRegion;
import net.tmmc.json.JsonMod;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static arc.Core.atlas;

public record ModAtlas(JsonMod mod) {
    @Contract("null -> false")
    public static boolean validRegion(TextureRegion region) {
        return region != null && region.found();
    }

    @Contract("null, _ -> !null; !null, null -> !null")
    public TextureRegion get(String prefix, TextureRegion def) {
        if(prefix == null) return this.errorRegion();

        try {
            String trans = mod.transformName(prefix);
            return atlas.find(trans, atlas.find(prefix, def));
        } catch(Throwable ignored) {
            return this.errorRegion();
        }
    }

    @Contract("null, _ -> !null")
    public TextureRegion get(String prefix, String def) {
        return this.get(prefix, this.get(def));
    }

    @Contract("null -> !null")
    public TextureRegion get(String prefix) {
        return this.get(prefix, this.errorRegion());
    }

    public boolean has(String prefix) {
        try {
            return validRegion(this.get(prefix));
        } catch(Throwable ignored) {
            return false;
        }
    }

    public AnimatedTextureRegion create(String prefix) {
        return Animations.create(this, prefix);
    }

    @Contract("_, _ -> new")
    public @NotNull AnimatedTextureRegion createAnimation(String prefix, int scale) {
        TextureRegion reg = this.get(prefix);
        int w = reg.width/scale;
        int h = reg.height/scale;
        return Animations.create(reg, w, h, scale);
    }

    @Contract("null -> null")
    public TextureRegion[] frames(String prefix) {
        if(prefix == null) return null;
        int step = 0;
        while(this.has(prefix + "-" + step)) step++;
        TextureRegion[] regions = new TextureRegion[step];
        for(int i = 0; i < step; i++) {
            regions[i] = this.get(prefix + "-" + i);
        }
        return regions;
    }

    public TextureRegion errorRegion() {
        return atlas.find(this.mod.getCoreSettings().errorRegion());
    }

    public static @NotNull TextureRegion defaultErrorRegion() {
        return atlas.find("error");
    }
}