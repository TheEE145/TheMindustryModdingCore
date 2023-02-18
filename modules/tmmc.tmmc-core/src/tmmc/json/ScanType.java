package tmmc.json;

import arc.Core;
import mindustry.ctype.UnlockableContent;
import tmmc.util.Bundles;

public record ScanType(boolean mods, boolean local, boolean named) {
    public boolean needChange(UnlockableContent content, ModJsonData data) {
        boolean mods = this.mods;
        boolean local = this.local;
        boolean named = this.named;

        if(mods & local & named) {
            return true;
        }

        if(!mods & !local & !named) {
            return false;
        }

        boolean valid = Core.bundle.has(
                Bundles.namePropertyOf(content)
        );

        if(named & valid) {
            return true;
        } else {
            if(valid) {
                return false;
            }
        }

        String ctProp = Bundles.propertyOf(content);
        ctProp = ctProp.substring(content.getContentType().name().length() + 1);
        valid = ctProp.startsWith(data.getName() + "-");

        if(local & valid) {
            return true;
        } else {
            if(valid) {
                return false;
            }
        }

        return mods;
    }
}