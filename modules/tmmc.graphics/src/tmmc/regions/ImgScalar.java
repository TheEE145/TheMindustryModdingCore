package tmmc.regions;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import org.jetbrains.annotations.Contract;

public class ImgScalar {
    private static TextureRegion current = Core.atlas.find("error");

    public static void set(TextureRegion region) {
        if(region != null) {
            current = region;
        }
    }

    @Contract(pure = true)
    public static float width() {
        return current.width / 4f;
    }

    @Contract(pure = true)
    public static float height() {
        return current.height / 4f;
    }

    @Contract(pure = true)
    public static float width(float scl) {
        return width() * scl;
    }

    @Contract(pure = true)
    public static float height(float scl) {
        return height() * scl;
    }
}