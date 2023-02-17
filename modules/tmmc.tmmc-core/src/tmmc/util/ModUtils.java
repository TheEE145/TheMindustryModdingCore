package tmmc.util;

import arc.math.Mathf;
import mindustry.mod.Mods;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import static mindustry.Vars.content;
import static mindustry.Vars.mods;

public class ModUtils {
    public static boolean isValidMod(String name) {
        return isValidMod(mods.getMod(name));
    }

    @Contract("null -> false")
    public static boolean isValidMod(Mods.LoadedMod mod) {
        return mod != null && mod.enabled();
    }

    public static Mods.@Nullable LoadedMod getCurrentMod() {
        String testName = Mathf.random() + "";
        String test = content.transformName(testName);
        if(test.equals(testName)) {
            return null;
        } else {
            String txt = test.substring(0, test.indexOf('-'));
            Log.fine(txt);
            return mods.getMod(txt);
        }
    }
}