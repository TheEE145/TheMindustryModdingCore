package net.tmmc.util;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import arc.files.Fi;
import arc.func.Cons;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Reflect;
import arc.util.Strings;

import mindustry.Vars;
import net.tmmc.json.JsonMod;
import org.jetbrains.annotations.NotNull;

public class ModSoundAdapter {
    public static Cons<Throwable> onError = Throwable::printStackTrace;
    public static final String soundDir = "sounds/";

    public Seq<ModSound> soundAtlas = new Seq<>();
    public final JsonMod mod;

    public ModSoundAdapter(JsonMod mod) {
        this.mod = mod;
    }

    public Fi soundDir() {
        return this.mod.getRaw().root.child(FFiles.transformPathEnd(soundDir));
    }

    public Cons<Fi> getHandler() {
        var path = soundDir().toString();

        return fi -> {
            if(!fi.isDirectory()) {
                var loc = fi.toString();
                var ex = fi.extension();

                String id = loc.substring(path.length());
                id = FFiles.transformPathStart(id);

                var name = id.substring(0, id.length() - ex.length() - 1);
                if(ex.equals("ogg") || ex.equals("mp3") || ex.equals("wav")) {
                    soundAtlas.add(new ModSound(Strings.kebabToCamel(name), id, loadSound(id)));
                }
            }
        };
    }

    public void dispose() {
        soundAtlas.copy().map(ModSound::id).forEach(ModSoundAdapter::disposeSound);
    }

    public Sound get(String name) {
        var cell = soundAtlas.find(sound -> {
            if(sound == null) {
                return false;
            }

            if(name == null) {
                return sound.name() == null;
            } else {
                return name.equals(sound.name());
            }
        });

        return cell == null ? null : cell.sound();
    }

    public void loadAtlas() {
        FFiles.eachInAll(soundDir(), getHandler());
    }

    public boolean reflectLoad(Object object)  {
        try {
            for(String key : soundAtlas.copy().map(ModSound::name)) {
                Reflect.set(Sound.class, object, key, get(key));
            }

            return true;
        } catch(Throwable ignored) {
            return false;
        }
    }

    public static @NotNull Sound loadSound(String soundName) {
        if(!Vars.headless && soundName != null) {
            String path = soundDir + soundName;
            Sound sound = new Sound();

            AssetDescriptor<?> desc = Core.assets.load(path, Sound.class,
                    new SoundLoader.SoundParameter(sound)
            );

            desc.errored = onError;
            return sound;
        } else {
            return new Sound();
        }
    }

    public static void disposeSound(String soundName) {
        if(!Vars.headless) {
            String path = soundDir + soundName;
            if(Core.assets.isLoaded(path, Sound.class)) {
                Core.assets.unload(path);
            }
        }
    }

    public record ModSound(String name, String id, Sound sound) {
    }
}