package tmmc;

import arc.ApplicationListener;
import tmmc.json.JsonMod;
import mindustry.mod.Mod;
import mindustry.Vars;

public class ApplicationMod extends Mod implements ApplicationListener {
    public JsonMod loadedMod;

    @Override
    public void init() {
        var raw = Vars.mods.getMod(this.getClass());
        var mod = JsonMod.create(this, raw);

        raw.meta.author = mod.getAuthors();
        raw.setRepo(mod.getRepoURL());
        mod.apply(raw.meta);

        this.loadedMod = mod;
    }
}