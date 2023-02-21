package net.tmmc.graphics;

import arc.graphics.g2d.TextureRegion;

public class ImageBackgroundRender extends MenuBackgroundRender {
    public ImageBackgroundRender(TextureRegion region) {
        super(() -> FDraw.fill(region));
    }

    public static void pack(TextureRegion region) {
        new ImageBackgroundRender(region).setAsMain();
    }
}