package tmmc.graphics;

import arc.scene.style.TextureRegionDrawable;
import org.jetbrains.annotations.NotNull;
import arc.util.Strings;

import tmmc.regions.Regions;
import java.util.Objects;

public class ModIcons {
    public static @NotNull TextureRegionDrawable loadIcon(String name) {
        TextureRegionDrawable drawable = Regions.toDrawable(
                Regions.getRegion(Objects.requireNonNull(name))
        );

        drawable.setName(Strings.kebabToCamel(name));
        return drawable;
    }
}
