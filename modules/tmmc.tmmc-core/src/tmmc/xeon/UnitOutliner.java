package tmmc.xeon;

import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import mindustry.graphics.MultiPacker;

public interface UnitOutliner {
    void outlineRegion(MultiPacker packer,
                       TextureRegion textureRegion,
                       Color outlineColor,
                       String outputName,
                       int outlineRadius);
}