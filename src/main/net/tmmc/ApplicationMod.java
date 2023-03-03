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
    public void loadContent() {
        var mod = JsonMod.create(this);

        var settings = mod.getCoreSettings();
        if(settings != null && settings.throwExceptionIntoDialog()) {
            Events.postDataRun = (data) -> {
                if(data.hasErrors()) {
                    data.causes().forEach(ThrowableUtils::showDialog);
                }
            };
        }

        this.loadedMod = mod;
        this.atlas = new ModAtlas(mod);
        this.logger = new ModLogger(mod);
        (this.adapter = new ModSoundAdapter(mod)).loadAtlas();

        Events.register(DisposeEvent.class, adapter::dispose);
        Events.post(new ModContentLoad(mod));
    }

    @Override
    public void init() {
        var mod = this.loadedMod;

        mod.getRaw().setRepo(mod.getRepository().asUrlFragment());
        mod.getMeta(met -> met.author = mod.getAuthors());
        mod.runBundleCreation();

        var settings = mod.getCoreSettings();
        if(settings != null && settings.fixOtherModsRepository()) {
            Events.register(ClientLoadEvent.class, ModUtils::setRepositories);
        }

        Events.post(new ModInitEvent(mod));
    }
}