package tmmc;

import arc.ApplicationListener;
import arc.Events;

import arc.struct.Seq;
import tmmc.json.BundleCreation;
import tmmc.json.Contributor;
import tmmc.json.ModJsonData;
import tmmc.json.Repository;

import mindustry.game.EventType.ClientLoadEvent;
import mindustry.mod.Mods;
import mindustry.mod.Mod;
import tmmc.util.FileFinder;
import tmmc.util.Log;

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

        Seq<Contributor> authors;
        if((authors = this.data.getContributors()).size > 0) {
            StringBuilder builder = new StringBuilder();

            authors.forEach(contributor -> {
                builder.append(contributor.fullName());
            });

            this.loadedMod.meta.author = builder.toString();
        }
    }
}