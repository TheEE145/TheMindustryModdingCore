package net.tmmc;

import arc.ApplicationListener;
import net.tmmc.graphics.ModAtlas;
import net.tmmc.json.JsonMod;
import mindustry.mod.Mod;
import mindustry.Vars;

public class ApplicationMod extends Mod implements ApplicationListener {
    public JsonMod loadedMod;
    public ModAtlas atlas;

    @Override
    public void init() {
        var raw = Vars.mods.getMod(this.getClass());
        var mod = JsonMod.create(this, raw);

        raw.setRepo(mod.getRepoFil());
        mod.getMeta(met -> met.author = mod.getAuthors());
        mod.runBundleCreation();

        this.loadedMod = mod;
        this.atlas = new ModAtlas(mod);
    }
}