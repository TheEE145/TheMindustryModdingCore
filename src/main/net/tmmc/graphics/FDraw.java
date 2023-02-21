package net.tmmc.graphics;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;

public class FDraw {
    public static float width() {
        return Core.scene.getWidth();
    }

    public static float width(float scl) {
        return width() * scl;
    }

    public static float height() {
        return Core.scene.getHeight();
    }

    public static float height(float scl) {
        return height() * scl;
    }

    public static void fill(TextureRegion region) {
        float w = width(), h = height();
        Draw.rect(region, w/2, h/2, w, h);
    }
}