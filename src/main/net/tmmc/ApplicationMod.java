package net.tmmc;

import mindustry.game.EventType.*;
import mindustry.mod.Mods;
import mindustry.mod.Mod;

import net.tmmc.util.BaseEvents.*;
import net.tmmc.util.ModLogger;
import net.tmmc.util.ModSoundAdapter;
import net.tmmc.util.ModUtils;
import net.tmmc.util.ThrowableUtils;
import net.tmmc.util.events.Events;
import net.tmmc.graphics.ModAtlas;
import net.tmmc.json.JsonMod;

public class ApplicationMod extends Mod implements arc.ApplicationListener {
    public ModSoundAdapter adapter;
    public JsonMod loadedMod;
    public ModLogger logger;
    public ModAtlas atlas;

    @Deprecated
    public void contentLoad(Runnable runnable) {
        Events.register(ModContentLoad.class, (event) -> {
            if(event != null && event.mod() == loadedMod) {
                runnable.run();
            }
        });
    }

    public void init(Runnable runnable) {
        Events.register(ModInitEvent.class, (event) -> {
            if(event != null && event.mod() == loadedMod) {
                runnable.run();
            }
        });
    }

    public Mods.LoadedMod self() {
        return mindustry.Vars.mods.getMod(getClass());
    }

    @Override
    public void init() {
        var mod = JsonMod.create(this);

        var settings = mod.getCoreSettings();
        if(settings != null) {
            if(settings.throwExceptionIntoDialog()) {
                Events.postDataRun = (data) -> {
                    if(data.hasErrors()) {
                        data.causes().forEach(ThrowableUtils::showDialog);
                    }
                };
            }

            if(settings.fixOtherModsRepository()) {
                Events.register(ClientLoadEvent.class, ModUtils::setRepositories);
            }
        }

        this.loadedMod = mod;
        this.atlas = new ModAtlas(mod);
        this.logger = new ModLogger(mod);
        (this.adapter = new ModSoundAdapter(mod)).loadAtlas();

        Events.register(DisposeEvent.class, adapter::dispose);

        mod.getRaw().setRepo(mod.getRepository().asUrlFragment());
        mod.getMeta(met -> met.author = mod.getAuthors());
        mod.runBundleCreation();

        Events.post(new ModInitEvent(mod));
    }
}