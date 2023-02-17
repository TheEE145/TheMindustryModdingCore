package tmmc.depedency;

import arc.Core;
import arc.files.Fi;

import tmmc.util.Log;
import tmmc.util.ModUtils;
import tmmc.util.FileFinder;
import tmmc.json.ModJsonData;

import mindustry.mod.Mods;
import org.jetbrains.annotations.NotNull;

public class AssetsTransformProcessor {
    public static ModJsonData data;

    private static @NotNull String prefix() {
        return data == null ? "" : data.getName() + "-";
    }

    public static void transform() {
        Mods.LoadedMod mod = ModUtils.getCurrentMod();
        if(mod == null) {
            return;
        }

        Fi root = FileFinder.getSearcher(
                "sprites/transformations/" + mod.name + "/"
        ).log().nextProtocol();

        if(FileFinder.validFile(root)) {
            for(Fi fi : root.list()) {
                if(fi.toString().endsWith(".png")) {
                    String name = fi.nameWithoutExtension();

                    Core.atlas.addRegion(mod.name + "-" + name,
                            Core.atlas.find(prefix() + name)
                    );
                }
            }
        } else {
            Log.fine("no transformation dir");
        }
    }
}