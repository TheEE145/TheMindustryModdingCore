package tmmc;

import arc.ApplicationListener;
import arc.Events;

import tmmc.json.BundleCreation;
import tmmc.json.ModJsonData;
import tmmc.json.Repository;

import mindustry.game.EventType.ClientLoadEvent;
import mindustry.mod.Mods;
import mindustry.mod.Mod;

import static mindustry.Vars.*;

public class ApplicationMod extends Mod implements ApplicationListener {
    public Mods.LoadedMod loadedMod;
    public ModJsonData data;

    @Override
    public void init() {
        this.loadedMod = mods.getMod(this.getClass());
        this.data = ModJsonData.from(this.loadedMod);

        BundleCreation creation;
        if((creation = this.data.getBundleCreation()) != null) {
            Events.on(ClientLoadEvent.class, creation.getRuntime()::accept);
        }

        Repository repository;
        if((repository = this.data.getRepository()) != null) {
            this.loadedMod.setRepo(repository.asURL());
        }
    }
}